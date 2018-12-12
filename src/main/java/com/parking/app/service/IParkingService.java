/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.service;

import com.parking.app.dto.TReservaDTO;
import com.parking.app.entity.TReserva;
import com.parking.app.entity.TUsuario;
import com.parking.app.entity.TVehiculo;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
public interface IParkingService {

    List<Map<String, Object>> getAllByDitancia(Double latitud, Double longitud, Double distancia);

    List<Map<String, Object>> getAllPlayas();

    TReserva saveReserva(TReservaDTO reserva);

    List<Map<String, Object>> getProbabilidad(Integer hora, String dia, Integer idPlaya);

    void actualizarTarifa(Integer idPlaya, Double tarifa);

    List<Map<String, Object>> getTarifas();

    Map<String, Object> getPlaya(Integer id_playa);

    List<TReserva> getReservas(Integer id_usuario);

    void cancelarReserva(Integer id_reserva);

    TUsuario getUsuario(Integer id_usuario);

    List<TVehiculo> getVehiculosByUser(Integer id_usuario);

}
