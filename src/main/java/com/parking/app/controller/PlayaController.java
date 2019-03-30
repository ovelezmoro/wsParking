/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.controller;

import com.parking.app.dao.IParkingDAO;
import com.parking.app.dao.IPlayaDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("playa/")
public class PlayaController {

    @Autowired
    IPlayaDAO iPlayaDAO;

    @Autowired
    IParkingDAO iParkingDAO;

    @CrossOrigin(origins = "*", allowCredentials = "false")
    @RequestMapping(method = RequestMethod.GET, value = "getCercanos")
    @ResponseBody
    public List getCercanos(@RequestParam(value = "latitud", required = true) Double latitud,
                            @RequestParam(value = "longitud", required = true) Double longitud,
                            @RequestParam(value = "distancia", required = true) Double distancia) {

        return iPlayaDAO.getCercanos(latitud, longitud, distancia);

    }

    @CrossOrigin(origins = "*", allowCredentials = "false")
    @RequestMapping(method = RequestMethod.GET, value = "getPlayas")
    @ResponseBody
    public List getPlayas() {

        return iPlayaDAO.find();

    }

    @CrossOrigin(origins = "*", allowCredentials = "false")
    @RequestMapping(method = RequestMethod.GET, value = "getSugerenciaByProbabilidad")
    @ResponseBody
    public List getSugerenciaByProbabilidad(
            @ApiParam(value = "dia", required = true) @RequestParam(value = "dia", required = true) String dia,
            @ApiParam(value = "hora", required = true) @RequestParam(value = "hora", required = true) Integer hora,
            @ApiParam(value = "playa", required = true) @RequestParam(value = "playa", required = true) Integer playa) {

        return iParkingDAO.getProbabilidad(hora, dia, playa);

    }

}
