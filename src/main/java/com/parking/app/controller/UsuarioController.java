/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.controller;

import com.parking.app.dao.IParkingDAO;
import com.parking.app.dao.IUsuarioDAO;
import com.parking.app.dao.IVehiculoDAO;
import com.parking.app.dto.SingInDTO;
import com.parking.app.entity.TUsuario;
import com.parking.app.entity.TVehiculo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Slf4j
@RestController
@RequestMapping("usuario/")
public class UsuarioController {

    @Autowired
    IUsuarioDAO iUsuarioDAO;

    @Autowired
    IParkingDAO iParkingDAO;

    @Autowired
    IVehiculoDAO iVehiculoDAO;

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = RequestMethod.GET, value = "getUsuario")
    @ResponseBody
    public TUsuario getReservasByUser(@RequestParam(value = "idUsuario", required = true) Integer idUsuario) {

        TUsuario usuario = iUsuarioDAO.findOne(idUsuario);
        List<TVehiculo> vehiculos = iVehiculoDAO.findByIdUsuario(idUsuario);
        usuario.setVehiculos(vehiculos);

        return usuario;

    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "save", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public TUsuario save(@RequestBody(required = false) SingInDTO singInDTO) {

        TUsuario u = iUsuarioDAO.findByUid(singInDTO.getUid());
        if (u == null) {
            u = new TUsuario(singInDTO);
            iUsuarioDAO.save(new TUsuario(singInDTO));
        } else {
            u.setVehiculos(iParkingDAO.getVehiculosByUser(u.getId()));
        }
        return u;

    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "update", produces = "application/json", consumes = "application/json")
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
