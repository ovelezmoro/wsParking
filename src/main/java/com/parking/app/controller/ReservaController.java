/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.controller;

import com.parking.app.dao.*;
import com.parking.app.dto.CancelReservaResponseDTO;
import com.parking.app.dto.TReservaDTO;
import com.parking.app.entity.TPlaya;
import com.parking.app.entity.TReserva;
import com.parking.app.entity.TUsuario;
import com.parking.app.service.IEmailService;
import com.parking.app.util.StrUtil;
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
@RestController
@RequestMapping("reserva/")
public class ReservaController {

    @Autowired
    IEmailService iEmailService;

    @Autowired
    IReservaDAO iReservaDAO;

    @Autowired
    IUsuarioDAO iUsuarioDAO;

    @Autowired
    IPlayaDAO iPlayaDAO;

    @Autowired
    IVehiculoDAO iVehiculoDAO;

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "saveReserva", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public TReserva saveReserva(@RequestBody(required = false) TReservaDTO reserva) {

        TReserva tReserva = new TReserva();

        TReserva lastReserva = iReservaDAO.last();
        Integer incremental = lastReserva.getId() + 1;

        tReserva.setFechaReserva(reserva.getFechaReserva());
        tReserva.setIdPlaya(reserva.getIdPlaya());
        tReserva.setIdUsuario(reserva.getIdUsuario());
        tReserva.setIdVehiculo(reserva.getIdVehiculo());
        tReserva.setPrecioReserva(reserva.getPrecioReserva());
        tReserva.setShaReserva("RES-COD-" + incremental);
        iReservaDAO.save(tReserva);

        Thread thread = new Thread(() -> {
            iEmailService.sendMail(new String[]{"ovelezmoro@gmail.com"}, "NUEVA RESERVA: " + tReserva.getShaReserva(), "RESERVA REGISTRADA PARA EL " + StrUtil.getDate(tReserva.getFechaReserva(), "dd/MM/yyyy") + " A LAS " + StrUtil.getDate(tReserva.getFechaReserva(), "hh:mm"));
        });
        thread.start();

        return tReserva;

    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.PUT}, value = "updateReserva/{idreserva}", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public TReserva updateReserva(@PathVariable(name = "idreserva") Integer idreserva,
                                  @RequestBody(required = false) TReservaDTO reserva) {

        TReserva tReserva = iReservaDAO.findOne(idreserva);
        tReserva.setFechaReserva(reserva.getFechaReserva());
        tReserva.setIdVehiculo(reserva.getIdVehiculo());

        iReservaDAO.update(tReserva);
        Thread thread = new Thread(() -> {
            iEmailService.sendMail(new String[]{"ovelezmoro@gmail.com"}, "ACTUALIZACION DE RESERVA: " + tReserva.getShaReserva(), "RESERVA REGISTRADA PARA EL " + StrUtil.getDate(tReserva.getFechaReserva(), "dd/MM/yyyy") + " A LAS " + StrUtil.getDate(tReserva.getFechaReserva(), "hh:mm"));
        });
        thread.start();
        return tReserva;


    }

    @CrossOrigin(origins = "*", allowCredentials = "false")
    @RequestMapping(method = RequestMethod.GET, value = "getReservasByUser")
    @ResponseBody
    public List<TReserva> getReservasByUser(@RequestParam(value = "idUsuario", required = true) Integer idUsuario) {

        TUsuario usuario = iUsuarioDAO.findOne(idUsuario);
        List<TReserva> reservaList = iReservaDAO.findByIdUsuario(idUsuario);
        for (TReserva tReserva : reservaList) {
            tReserva.setUsuario(usuario);
            tReserva.setPlaya(iPlayaDAO.findOne(tReserva.getIdPlaya()));
            tReserva.setVehiculo(iVehiculoDAO.findOne(tReserva.getIdVehiculo()));
        }

        return reservaList;
    }

    @CrossOrigin(origins = "*", allowCredentials = "false")
    @RequestMapping(method = RequestMethod.GET, value = "cancelarReserva")
    @ResponseBody
    public CancelReservaResponseDTO cancelarReserva(@RequestParam(value = "idreserva", required = true) Integer idreserva) {

        CancelReservaResponseDTO message = new CancelReservaResponseDTO();
        message.setSuccess(0);
        message.setStatus_message("No se pudo realizar cancelacion");
        try {
            iReservaDAO.cancel(idreserva);
            message.setSuccess(1);
            message.setStatus_message("Cancelacion exitosa");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return message;

    }

}
