/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.restcontroller;

import com.parking.app.dto.TReservaDTO;
import com.parking.app.entity.TReserva;
import com.parking.app.service.IEmailService;
import com.parking.app.service.IParkingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Slf4j
@Api
@RestController
@RequestMapping("reserva/")
public class ReservaController {

    @Autowired
    IParkingService iParkingService;
    
    @CrossOrigin(origins = {"http://localhost:8100", "file://"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "saveReserva", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "saveReserva", nickname = "Guardar Reserva", response = String.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = String.class)
        , @ApiResponse(code = 201, message = "Created")
        , @ApiResponse(code = 400, message = "Bad Request")
        , @ApiResponse(code = 401, message = "Unauthorized")
        , @ApiResponse(code = 403, message = "Forbidden")
        , @ApiResponse(code = 404, message = "Not Found")
        , @ApiResponse(code = 500, message = "Failure")})
    @ResponseBody
    public TReserva saveReserva(@RequestBody(required = false) TReservaDTO reserva) {

        return iParkingService.saveReserva(reserva);
        
    }

}