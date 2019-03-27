/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.controller;

import com.parking.app.dao.IUsuarioDAO;
import com.parking.app.dto.SingInDTO;
import com.parking.app.dto.TReservaDTO;
import com.parking.app.entity.TReserva;
import com.parking.app.entity.TUsuario;
import com.parking.app.entity.TVehiculo;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("usuario/")
public class UsuarioController {

    @Autowired
    IParkingService iParkingService;

    @Autowired
    IUsuarioDAO iUsuarioDAO;

    @CrossOrigin(origins = "*", allowCredentials = "false")
    @RequestMapping(method = RequestMethod.GET, value = "getUsuario")
    @ApiOperation(value = "getUsuario", nickname = "Obtener Usuario", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = List.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @ResponseBody
    public TUsuario getReservasByUser(
            @ApiParam(value = "idUsuario", required = true) @RequestParam(value = "idUsuario", required = true) Integer idUsuario) {

        TUsuario usuario = iParkingService.getUsuario(idUsuario);
        List<TVehiculo> vehiculos = iParkingService.getVehiculosByUser(idUsuario);
        usuario.setVehiculos(vehiculos);

        return usuario;

    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "save", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "save", nickname = "Guardar Usuario", response = SingInDTO.class)
    @ResponseBody
    public TUsuario save(@RequestBody(required = false) SingInDTO sg) {

        TUsuario u = iUsuarioDAO.findByUid(sg.getUid());
        if (u == null) {
            u = new TUsuario(sg);
            iUsuarioDAO.save(new TUsuario(sg));
        } else {
            u.setVehiculos(iParkingService.getVehiculosByUser(u.getId()));
        }
        return u;

    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "update", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "update", nickname = "Modificar Usuario", response = SingInDTO.class)
    @ResponseBody
    public TUsuario update(@RequestBody(required = false) SingInDTO sg) {

        TUsuario u = iUsuarioDAO.findByUid(sg.getUid());
        if (u != null) {
            if (u.getLoginWith().equals("email")) {
                u.setNombre(sg.getNombre());
            }
            u.setPhone(sg.getPhone());
            iUsuarioDAO.update(u);
        }
        return u;

    }

}
