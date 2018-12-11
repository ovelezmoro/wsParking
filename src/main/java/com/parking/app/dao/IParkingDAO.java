package com.parking.app.dao;

import com.parking.app.entity.TReserva;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Mapper
@Repository
public interface IParkingDAO {

    List<Map<String, Object>> getDistancia(@Param("latitud") Double latitud, @Param("longitud") Double longitud, @Param("distancia") Double distancia);

    void saveReserva(@Param("reserva") TReserva reserva);

    List<Map<String, Object>> getAllPlayas();
    
    Map<String, Object> getPlaya(@Param("id_playa") Integer id_playa);

    List<Map<String, Object>> getTarifas();

    List<Map<String, Object>> getProbabilidad(@Param("hora") Integer hora, @Param("dia") String dia, @Param("idPlaya") Integer idPlaya);

    void updateTarifa(@Param("idPlaya") Integer idPlaya, @Param("tarifa") Double tarifa);

    public TReserva getUltimaReserva();

}