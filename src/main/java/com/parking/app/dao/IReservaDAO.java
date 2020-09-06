/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.dao;

import com.parking.app.entity.TReserva;
import com.parking.app.entity.TTarifa;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Administrador
 */
@Mapper
@Repository
public interface IReservaDAO {

    void save(TReserva t);

    void update(TReserva t);

    void delete(Integer id);

    List<TReserva> find();

    TReserva findOne(Integer id);

    List<TReserva> findByIdUsuario(Integer id_usuario);

    void cancel(Integer id);

    TReserva last();

    TReserva findLastTicket(@Param("id_usuario") String usuario, @Param("placa") String placa);

    TReserva findByCodReserva(String codigoReserva);

    void iniciarReserva(String codigoReserva, String fechaIngreso);

    void finalizarReserva(String codigoReserva, String fechaIngreso);
   
}
