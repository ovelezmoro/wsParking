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
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.TextDetection;
import com.parking.app.dao.IPlayaDAO;
import com.parking.app.dao.IReservaDAO;
import com.parking.app.dao.IUsuarioDAO;
import com.parking.app.dao.IVehiculoDAO;
import com.parking.app.entity.TReserva;
import com.parking.app.entity.TVehiculo;
import com.parking.app.util.MathUtil;

import static com.parking.app.util.Util.CREDENTIALS;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
        response.put("message", "No se detecto una placa valida");
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

            TVehiculo nVehiculo = iVehiculoDAO.findByPlaca(detection.getDetectedText());
            if (nVehiculo == null) {
                Integer idUsuario = MathUtil.getInt(photo.get("usuario"));
                nVehiculo = new TVehiculo();
                nVehiculo.setPlaca(detection.getDetectedText());
                nVehiculo.setIdUsuario(idUsuario);
                iVehiculoDAO.save(nVehiculo);
                response.put("status", "OK");
                response.put("message", "Placa " + detection.getDetectedText() + " registrada exitosamente");
                response.put("placa", detection.getDetectedText());
            } else {
                response.put("status", "error");
                response.put("message", "Placa " + detection.getDetectedText() + " ya se encuentra registrada");
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

        TReserva reserva = iReservaDAO.findLastTicket(photo.get("usuario"), detection.getDetectedText());

        if (reserva != null) {
            reserva.setUsuario(iUsuarioDAO.findOne(reserva.getIdUsuario()));
            reserva.setPlaya(iPlayaDAO.findOne(reserva.getIdPlaya()));
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
                isVehicle = true;
            }
        }
        if (isVehicle) {
            TextDetection detection = detectTextRequest(decoded);
            response.put("status", "OK");
            response.put("message", "Placa " + detection.getDetectedText() + " encontrada exitosamente");
            response.put("placa", detection.getDetectedText());
        }
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
