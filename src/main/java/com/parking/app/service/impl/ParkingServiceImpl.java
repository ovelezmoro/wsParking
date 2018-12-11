/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.service.impl;

import com.parking.app.dao.IParkingDAO;
import com.parking.app.dto.TReservaDTO;
import com.parking.app.entity.TReserva;
import com.parking.app.service.IEmailService;
import com.parking.app.service.IParkingService;
import com.parking.app.util.StrUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Service
public class ParkingServiceImpl implements IParkingService {

    @Autowired
    IParkingDAO iParkingDAO;

    @Autowired
    IEmailService iEmailService;

    @Override
    public List<Map<String, Object>> getAllByDitancia(Double latitud, Double longitud, Double distancia) {
        return iParkingDAO.getDistancia(latitud, longitud, distancia);
    }

    @Override
    public TReserva saveReserva(TReservaDTO reserva) {
        TReserva tReserva = new TReserva();

        TReserva lastReserva = iParkingDAO.getUltimaReserva();
        Integer incremental = lastReserva.getId() + 1;

        tReserva.setFechaReserva(reserva.getFechaReserva());
        tReserva.setIdPlaya(reserva.getIdPlaya());
        tReserva.setIdUsuario(reserva.getIdUsuario());
        tReserva.setPrecioReserva(reserva.getPrecioReserva());
        tReserva.setShaReserva("RES-COD-" + incremental);
        iParkingDAO.saveReserva(tReserva);

        Thread thread = new Thread(() -> {
            iEmailService.sendMail(new String[]{"ovelezmoro@gmail.com"}, "NUEVA RESERVA: " + tReserva.getShaReserva(), "RESERVA REGISTRADA PARA EL " + StrUtil.getDate(tReserva.getFechaReserva(), "dd/MM/yyyy") + " A LAS " + StrUtil.getDate(tReserva.getFechaReserva(), "hh:mm"));
        });
        thread.start();

        return tReserva;
    }

    @Override
    public List<Map<String, Object>> getAllPlayas() {
        return iParkingDAO.getAllPlayas();
    }

    @Override
    public List<Map<String, Object>> getProbabilidad(Integer hora, String dia, Integer idPlaya) {

        return iParkingDAO.getProbabilidad(hora, dia, idPlaya);

    }

    @Override
    public void actualizarTarifa(Integer idPlaya, Double tarifa) {

        iParkingDAO.updateTarifa(idPlaya, tarifa);
    }

    @Override
    public List<Map<String, Object>> getTarifas() {
        return iParkingDAO.getTarifas();
    }

    @Override
    public Map<String, Object> getPlaya(Integer id_playa) {
        return iParkingDAO.getPlaya(id_playa);
    }

    
    
}
