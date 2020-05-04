/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.controller;

import com.parking.app.dao.IParkingDAO;
import com.parking.app.dao.IPlayaDAO;
import com.parking.app.dao.ITarifaDAO;
import com.parking.app.dto.TTarifaDTO;
import com.parking.app.entity.TPlaya;
import com.parking.app.entity.TTarifa;
import com.parking.app.service.IEmailService;
import com.parking.app.service.impl.INotificationServiceImpl;
import com.parking.app.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    ITarifaDAO iTarifaDAO;

    @Autowired
    IEmailService iEmailService;

    @Autowired
    IPlayaDAO iPlayaDAO;

    @Autowired
    INotificationServiceImpl iNotificationServiceImpl;

    @Value("${spring.mail.properties.mail.administrador}")
    private String administrador;
    
    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "updateTarifaPorProbabilidad", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public TTarifaDTO updateTarifaPorProbabilidad(@RequestBody(required = false) TTarifaDTO nueva_tarifa) {

        TPlaya playa = iPlayaDAO.findOne(nueva_tarifa.getIdplaya());

        if(playa != null){
            iTarifaDAO.updateByIdPlaya(nueva_tarifa.getIdplaya(), nueva_tarifa.getTarifa());

            String mensaje = "Se ha generado una nueva tarifa para la playa" + playa.getNombre() + "ubicada en:" + playa.getDireccion() + ". La oferta estára vigente hasta nuevo aviso. Gracias por utilizar nuestro servicio.";
            String title = "Mensaje de Validación";

            iNotificationServiceImpl.sendMessageToAllUsers(title, mensaje);

            Thread thread = new Thread(() -> {
                iEmailService.sendMail(new String[]{administrador}, title, mensaje);
            });
            thread.start();
        }

        return nueva_tarifa;
        

    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
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
