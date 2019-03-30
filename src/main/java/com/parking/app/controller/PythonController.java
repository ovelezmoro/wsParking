/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.controller;

import com.parking.app.dao.IParkingDAO;
import com.parking.app.util.DateUtil;
import com.parking.app.util.MathUtil;
import com.parking.app.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Slf4j
@RestController
@RequestMapping("python/")
public class PythonController {

    @Autowired
    IParkingDAO iParkingDAO;

    @Value("${python.probabilidad.host}")
    private String host;

    @Value("${python.probabilidad.path}")
    private String path;

    @Value("${python.probabilidad.port}")
    private String port;

    @Value("${python.probabilidad.username}")
    private String username;

    @Value("${python.probabilidad.password}")
    private String password;

    @Value("${python.probabilidad.database}")
    private String database;

    @CrossOrigin(origins = "*", allowCredentials = "false")
    @RequestMapping(method = RequestMethod.GET, value = "getSugerenciaByPython")
    @ResponseBody
    public Map<String, Object> getSugerenciaByPython(
            @ApiParam(value = "dia", required = true) @RequestParam(value = "dia", required = true) String dia,
            @ApiParam(value = "hora", required = true) @RequestParam(value = "hora", required = true) Integer hora,
            @ApiParam(value = "playa", required = true) @RequestParam(value = "playa", required = true) Integer playa) throws InterruptedException, IOException {

        Map<String, Object> map = iParkingDAO.getPlaya(playa);

        String[] params = new String[10];
        File script = new File("probabilidad.py");
        params[0] = path;
        params[1] = script.getAbsolutePath();
        params[2] = host;
        params[3] = port;
        params[4] = username;
        params[5] = password;
        params[6] = database;
        Calendar c = Calendar.getInstance();
        c.setTime(DateUtil.getDate(dia, "yyyy-MM-dd"));
        params[7] = StrUtil.getString(playa); //ID DE LA PLAYA
        params[8] = StrUtil.getString(c.get(Calendar.DAY_OF_WEEK) - 1); //DIA DE LA SEMANA
        params[9] = StrUtil.getString(hora); //HORA DE LA PROBABILIDAD
        Process exec = Runtime.getRuntime().exec(params);
        String reg = "";
        for (String param : params) {
            reg += (param + " ");
        }
        InputStream stream;
        boolean error = false;
        if (exec.waitFor() == 0) {
            stream = exec.getInputStream();
        } else {
            stream = exec.getErrorStream();
            error = true;
        }
        if (exec.isAlive()) {
            exec.destroy();
        }
        byte[] buffer = new byte[stream.available()];
        stream.read(buffer);
        String str = new String(buffer).replaceAll("\\r\\n", "").replace("[", "").replace("]", "");
        if (error) {
            throw new RuntimeException(str);
        }

        map.put("probabilidad_python", MathUtil.getDouble(str));

        return map;
    }

}
