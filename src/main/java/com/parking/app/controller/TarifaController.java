/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.controller;

import com.parking.app.dao.IParkingDAO;
import com.parking.app.dto.TTarifaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Slf4j
@Api
@RestController
@RequestMapping("tarifa/")
public class TarifaController {

    @Autowired
    IParkingDAO iParkingDAO;

    @CrossOrigin(origins = {"http://localhost:8100", "file://"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "updateTarifaPorProbabilidad", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "updateTarifaPorProbabilidad", nickname = "Actualizar Tarifa", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class)
            , @ApiResponse(code = 201, message = "Created")
            , @ApiResponse(code = 400, message = "Bad Request")
            , @ApiResponse(code = 401, message = "Unauthorized")
            , @ApiResponse(code = 403, message = "Forbidden")
            , @ApiResponse(code = 404, message = "Not Found")
            , @ApiResponse(code = 500, message = "Failure")})
    @ResponseBody
    public TTarifaDTO updateTarifaPorProbabilidad(@RequestBody(required = false) TTarifaDTO nueva_tarifa) {

        iParkingDAO.updateTarifa(nueva_tarifa.getIdplaya(), nueva_tarifa.getTarifa());
        return nueva_tarifa;

    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://"})
    @RequestMapping(method = {RequestMethod.GET}, value = "getTarifas")
    @ApiOperation(value = "getTarifas", nickname = "Obtener Tarifa", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = List.class)
            , @ApiResponse(code = 201, message = "Created")
            , @ApiResponse(code = 400, message = "Bad Request")
            , @ApiResponse(code = 401, message = "Unauthorized")
            , @ApiResponse(code = 403, message = "Forbidden")
            , @ApiResponse(code = 404, message = "Not Found")
            , @ApiResponse(code = 500, message = "Failure")})
    @ResponseBody
    public List getTarifas() {

        return iParkingDAO.getTarifas();

    }

}
