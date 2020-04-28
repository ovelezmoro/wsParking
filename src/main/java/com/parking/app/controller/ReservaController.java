/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.controller;

import com.parking.app.dao.*;
import com.parking.app.dto.CancelReservaResponseDTO;
import com.parking.app.dto.TReservaDTO;
import com.parking.app.entity.TParametro;
import com.parking.app.entity.TReserva;
import com.parking.app.entity.TUsuario;
import com.parking.app.service.IEmailService;
import com.parking.app.util.DateUtil;
import com.parking.app.util.MathUtil;
import com.parking.app.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @Autowired
    IParametroDAO iParametroDAO;

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "saveReserva", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public TReserva saveReserva(@RequestBody(required = false) TReservaDTO reserva) {

        TUsuario usuario = iUsuarioDAO.findOne(reserva.getIdUsuario());

        TReserva lastReserva = iReservaDAO.last();
        Integer incremental = 1;
        if (lastReserva != null) {
            incremental = lastReserva.getId() + 1;
        }

        TReserva tReserva = new TReserva();
        tReserva.setFechaReserva(reserva.getFechaReserva());
        tReserva.setIdPlaya(reserva.getIdPlaya());
        tReserva.setIdUsuario(reserva.getIdUsuario());
        tReserva.setIdVehiculo(reserva.getIdVehiculo());
        tReserva.setPrecioReserva(reserva.getPrecioReserva());
        tReserva.setShaReserva("RES-COD-" + incremental);
        iReservaDAO.save(tReserva);

        iEmailService.sendMail(new String[]{usuario.getEmail()}, "NUEVA RESERVA: " + tReserva.getShaReserva(), "RESERVA REGISTRADA PARA EL " + StrUtil.getDate(tReserva.getFechaReserva(), "dd/MM/yyyy") + " A LAS " + StrUtil.getDate(tReserva.getFechaReserva(), "hh:mm"));
        
        return tReserva;

    }

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.PUT}, value = "updateReserva/{idreserva}", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public TReserva updateReserva(@PathVariable(name = "idreserva") Integer idreserva,
                                  @RequestBody(required = false) TReservaDTO reserva) {

        Calendar calFechaInicial = Calendar.getInstance();
        Calendar calFechaFinal = Calendar.getInstance();

        TParametro parametro = iParametroDAO.find("001");
        TReserva tReserva = iReservaDAO.findOne(idreserva);

        TUsuario usuario = iUsuarioDAO.findOne(tReserva.getIdUsuario());

        calFechaInicial.setTime(tReserva.getFechaRegistro());
        calFechaFinal.setTime(new Date());

        long totalMinutos = 0;
        totalMinutos = ((calFechaFinal.getTimeInMillis() - calFechaInicial.getTimeInMillis()) / 1000 / 60);


        if (totalMinutos <= MathUtil.getInt(parametro.getValor())) {
            tReserva.setFechaReserva(reserva.getFechaReserva());
            tReserva.setIdVehiculo(reserva.getIdVehiculo());
            iReservaDAO.update(tReserva);
            iEmailService.sendMail(new String[]{usuario.getEmail()}, "ACTUALIZACION DE RESERVA: " + tReserva.getShaReserva(), "RESERVA REGISTRADA PARA EL " + StrUtil.getDate(tReserva.getFechaReserva(), "dd/MM/yyyy") + " A LAS " + StrUtil.getDate(tReserva.getFechaReserva(), "hh:mm"));
        } else {
            tReserva = null;
        }

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

        Calendar calFechaInicial = Calendar.getInstance();
        Calendar calFechaFinal = Calendar.getInstance();

        TParametro parametro = iParametroDAO.find("002");
        TReserva tReserva = iReservaDAO.findOne(idreserva);

        calFechaInicial.setTime(tReserva.getFechaRegistro());
        calFechaFinal.setTime(new Date());

        long totalMinutos = 0;
        totalMinutos = ((calFechaFinal.getTimeInMillis() - calFechaInicial.getTimeInMillis()) / 1000 / 60);

        CancelReservaResponseDTO message = new CancelReservaResponseDTO();
        message.setSuccess(0);
        message.setStatus_message("No se ha podido cancelar su reserva");

        if (totalMinutos <= MathUtil.getInt(parametro.getValor())) {
            
            try {
                iReservaDAO.cancel(idreserva);
                message.setSuccess(1);
                message.setStatus_message("Se canceló su reserva exitosamente");

                TUsuario usuario = iUsuarioDAO.findOne(tReserva.getIdUsuario());
                iEmailService.sendMail(new String[]{usuario.getEmail()}, "CANCELACION DE RESERVA: " + tReserva.getShaReserva(), "SU RESERVA RESERVA REGISTRADA EL " + StrUtil.getDate(tReserva.getFechaReserva(), "dd/MM/yyyy") + " A LAS " + StrUtil.getDate(tReserva.getFechaReserva(), "hh:mm") + " HA SIDO CANCELADA");

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else {
            message.setStatus_message("No se puede cancelar en estos momento, se encuentra fuera del plazo para realizar dicha operación");
        }

        return message;

    }

}
