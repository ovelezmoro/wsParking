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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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


}
