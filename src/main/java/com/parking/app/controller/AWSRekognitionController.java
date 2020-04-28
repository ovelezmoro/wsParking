/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.controller;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.parking.app.dao.*;
import com.parking.app.entity.TPlaya;
import com.parking.app.entity.TReserva;
import com.parking.app.entity.TVehiculo;
import com.parking.app.util.MathUtil;
import com.parking.app.util.StrUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

import static com.parking.app.util.Util.CREDENTIALS;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrador
 */
@Slf4j
@Api
@RestController
@RequestMapping("aws/")
public class AWSRekognitionController {

    @Autowired
    IReservaDAO iReservaDAO;

    @Autowired
    IVehiculoDAO iVehiculoDAO;

    @Autowired
    IPlayaDAO iPlayaDAO;

    @Autowired
    IUsuarioDAO iUsuarioDAO;

    @Autowired
    ITarifaDAO iTarifaDAO;

    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
            .standard()
            .withRegion(Regions.US_WEST_2)
            .withCredentials(new AWSStaticCredentialsProvider(CREDENTIALS))
            .build();

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "addVehiculo", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public Map<String, Object> addVehiculo(@RequestBody(required = true) Map<String, String> photo) throws IOException, AmazonRekognitionException {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", "No se detecto una placa válida");
        response.put("placa", "");

        String image = photo.get("photo").replace("data:image/jpeg;base64,", "");

        boolean isVehicle = false;
        byte[] decoded = Base64.decodeBase64(image);

        for (Label label : detectLabelsRequest(decoded)) {
            if (label.getName().equals("Vehicle") && label.getConfidence() > 80) {
                isVehicle = true;
            }
        }

        if (isVehicle) {

            TextDetection detection = detectTextRequest(decoded);

            String placa = StrUtil.reemplazarCaracteresEspeciales(detection.getDetectedText());

            TVehiculo nVehiculo = iVehiculoDAO.findByPlaca(placa);
            if (nVehiculo == null && placa.length() == 7) {
                Integer idUsuario = MathUtil.getInt(photo.get("usuario"));
                nVehiculo = new TVehiculo();
                nVehiculo.setPlaca(placa);
                nVehiculo.setIdUsuario(idUsuario);
                iVehiculoDAO.save(nVehiculo);
                response.put("status", "OK");
                response.put("message", "Placa " + placa + " registrada exitosamente");
                response.put("placa", placa);
            } else {
                response.put("status", "error");
                response.put("message", "Placa " + placa + " ya se encuentra registrada");
            }
        }
        return response;

    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "consultaTicket", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public TReserva consultaTicket(@RequestBody(required = true) Map<String, String> photo) throws IOException, AmazonRekognitionException {

        String image = photo.get("photo").replace("data:image/jpeg;base64,", "");

        byte[] decoded = Base64.decodeBase64(image);

        TextDetection detection = detectTextRequest(decoded);

        String placa = StrUtil.reemplazarCaracteresEspeciales(detection.getDetectedText());

        TReserva reserva = iReservaDAO.findLastTicket(photo.get("usuario"), placa);

        if (reserva != null) {
            reserva.setUsuario(iUsuarioDAO.findOne(reserva.getIdUsuario()));

            TPlaya playa = iPlayaDAO.findOne(reserva.getIdPlaya());
            playa.setTarifa(iTarifaDAO.findByPlaya(playa.getId()));
            reserva.setPlaya(playa);
            reserva.setVehiculo(iVehiculoDAO.findOne(reserva.getIdVehiculo()));

            return reserva;
        }

        return null;

    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "consultaPlaca", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public Map<String, Object> consultaPlaca(@RequestBody(required = true) Map<String, String> photo) throws IOException, AmazonRekognitionException {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", "No se detecto una placa valida");
        response.put("placa", "");
        String image = photo.get("imagen");
        boolean isVehicle = false;
        byte[] decoded = Base64.decodeBase64(image);
        for (Label label : detectLabelsRequest(decoded)) {
            if (label.getName().equals("Vehicle") && label.getConfidence() > 80) {
                log.info("LABEL ==>" + label.toString());
                isVehicle = true;
            }
        }
        
        List<TextDetection> detections = detectTextRequestList(decoded);
        
        for (TextDetection detection : detections) {
            String texto = detection.getDetectedText().toUpperCase();
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{3}-[a-zA-Z0-9]{3}");
            log.info("TEXTO => " + texto);
            Matcher matcher = pattern.matcher(texto);
            while(matcher.find()) {
                String placa = matcher.group(0);
                response.put("status", "OK");
                response.put("message", "Placa " + placa + " encontrada exitosamente");
                response.put("placa", placa);
                log.info("PLACA => " + placa);
            }
            
        }
        
//        if (isVehicle) {
//            TextDetection detection = detectTextRequest(decoded);
//            log.info("PLACA => " + detection.getDetectedText());
//
//            String mydata = detection.getDetectedText();
//            Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{3}-[a-zA-Z0-9]{3}");
//            Matcher matcher = pattern.matcher(mydata);
//            if (matcher.find()) {
//                response.put("status", "OK");
//                //String placa = detection.getDetectedText();
//                String placa = matcher.group(0);
//                if (matcher.groupCount() > 0) {
//                    placa = matcher.group(1);
//                }
//                response.put("message", "Placa " + placa + " encontrada exitosamente");
//                response.put("placa", placa);
//                System.out.println(matcher.group(1));
//            }
//        }
        return response;
    }

    private List<Label> detectLabelsRequest(byte[] image) {
        DetectLabelsRequest requestLabel = new DetectLabelsRequest()
                .withImage(new Image().withBytes(ByteBuffer.wrap(image)))
                .withMaxLabels(10)
                .withMinConfidence(77F);

        DetectLabelsResult result = rekognitionClient.detectLabels(requestLabel);
        return result.getLabels();
    }

    
    private List<TextDetection> detectTextRequestList(byte[] image) {

        DetectTextRequest requestText = new DetectTextRequest()
                .withImage(new Image()
                        .withBytes(ByteBuffer.wrap(image)));

        DetectTextResult resultText = rekognitionClient.detectText(requestText);

        return resultText.getTextDetections();

    }
    
    private TextDetection detectTextRequest(byte[] image) {

        DetectTextRequest requestText = new DetectTextRequest()
                .withImage(new Image()
                        .withBytes(ByteBuffer.wrap(image)));

        DetectTextResult resultText = rekognitionClient.detectText(requestText);

        List<TextDetection> textDetections = new ArrayList<>();
        textDetections = resultText.getTextDetections().stream()
                .filter((t) -> t.getDetectedText().contains("-"))
                .sorted(Comparator.comparing(TextDetection::getConfidence))
                .collect(Collectors.toList());

        TextDetection detection = null;
        if (textDetections.size() > 0) {
            detection = textDetections.stream().findFirst().get();
        }

        return detection;

    }

}
