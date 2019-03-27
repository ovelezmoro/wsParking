/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.controller;

import com.parking.app.dao.IPlayaDAO;
import com.parking.app.service.IParkingService;
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
@Api
@RestController
@RequestMapping("playa/")
public class PlayaController {

    @Autowired
    IParkingService iParkingService;

    @Autowired
    IPlayaDAO iPlayaDAO;

    @CrossOrigin(origins = "*", allowCredentials = "false")
    @RequestMapping(method = RequestMethod.GET, value = "getCercanos")
    @ApiOperation(value = "getCercanos", nickname = "Obtener Cercanos", response = String.class)
    @ResponseBody
    public List getCercanos(
            @ApiParam(value = "latitud", required = true) @RequestParam(value = "latitud", required = true) Double latitud,
            @ApiParam(value = "longitud", required = true) @RequestParam(value = "longitud", required = true) Double longitud,
            @ApiParam(value = "distancia", required = true) @RequestParam(value = "distancia", required = true) Double distancia) {

        return iPlayaDAO.getCercanos(latitud, longitud, distancia);

    }

    @CrossOrigin(origins = "*", allowCredentials = "false")
    @RequestMapping(method = RequestMethod.GET, value = "getPlayas")
    @ApiOperation(value = "getPlayas", nickname = "Obtener Todas las Playas", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @ResponseBody
    public List getPlayas() {

        return iParkingService.getAllPlayas();

    }

    @CrossOrigin(origins = "*", allowCredentials = "false")
    @RequestMapping(method = RequestMethod.GET, value = "getSugerenciaByProbabilidad")
    @ApiOperation(value = "getSugerenciaByProbabilidad", nickname = "Obtener disponibilidad", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @ResponseBody
    public List getSugerenciaByProbabilidad(
            @ApiParam(value = "dia", required = true) @RequestParam(value = "dia", required = true) String dia,
            @ApiParam(value = "hora", required = true) @RequestParam(value = "hora", required = true) Integer hora,
            @ApiParam(value = "playa", required = true) @RequestParam(value = "playa", required = true) Integer playa) {

        return iParkingService.getProbabilidad(hora, dia, playa);

    }

}
