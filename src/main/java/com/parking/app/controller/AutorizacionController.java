package com.parking.app.controller;

import com.parking.app.dao.IAutorizacionDAO;
import com.parking.app.dto.AutorizacionDTO;
import com.parking.app.entity.TAutorizacion;
import com.parking.app.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;

/**
 * Created by LENOVO on 13/04/2019.
 */
@Slf4j
@RestController
@RequestMapping("autorizacion_salida/")
public class AutorizacionController {

    @Autowired
    private IAutorizacionDAO iAutorizacionDAO;


    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "image", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public Map<String, Object> image(@RequestBody(required = true) Map<String, String> image) {

        Map<String, Object> map = new HashMap<>();
        map.put("message", "success");

        String photo = image.get("imagen").replace("data:image/png;base64,", "");
        byte[] decoded = Base64.decodeBase64(photo);

        String time = StrUtil.getString(new Date().getTime());
        File folder = new File("firmas");
        if(!folder.exists()){
            folder.mkdir();
        }
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("firmas/" + time + ".png"))) {
            bufferedOutputStream.write(decoded);
            map.put("image", time + ".png");
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            map.put("message", "error");
        }

        return map;

    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "save", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public TAutorizacion save(@RequestBody(required = false) AutorizacionDTO autorizacion) {

        TAutorizacion tAutorizacion = new TAutorizacion(autorizacion);

        iAutorizacionDAO.save(tAutorizacion);

        return tAutorizacion;

    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "consultaPlaca", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public Map<String, Object> consultaPlaca(@RequestBody(required = true) Map<String, String> request) throws IOException {

        String placa = request.get("texto");
        TAutorizacion autorizacion = iAutorizacionDAO.findByPlaca(placa);
        Map<String, Object> map = new HashMap<>();
        if (autorizacion != null) {
            return this.getMap(autorizacion);
        }
        return null;
    }

    private Map<String, Object> getMap(TAutorizacion autorizacion) {
        Map<String, Object> map = new HashMap<>();
        map.put("autorizacion", autorizacion);
        try {
            File file = new File("firmas/" + autorizacion.getFirma());
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            map.put("imagen", new String(Base64.encodeBase64(bytes)));
            return map;
        } catch (Exception ex) {
            ex.printStackTrace();
            map.put("imagen", "");
        }
        return map;
    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "consultaPorDni", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public List consultaPlacaDni(@RequestBody(required = true) Map<String, String> request) throws IOException {

        String dni = request.get("dni");
        List<Object> autorizacionesMap = new ArrayList<>();

        List<TAutorizacion> autorizaciones = iAutorizacionDAO.findByDni(dni);
        for (TAutorizacion autorizacion : autorizaciones) {
            autorizacionesMap.add(this.getMap(autorizacion));
        }

        return autorizacionesMap;

    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "consultaDni", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public TAutorizacion consultaDni(@RequestBody(required = true) Map<String, String> request) throws IOException {
        String dni = request.get("dni");
        List<TAutorizacion> autorizaciones = iAutorizacionDAO.findByDni(dni);
        if(autorizaciones.size() > 0){
            return autorizaciones.get(0);
        }
        return null;

    }


}
