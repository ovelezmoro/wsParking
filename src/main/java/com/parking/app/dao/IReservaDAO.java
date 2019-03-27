/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.dao;

import com.parking.app.entity.TReserva;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrador
 */
@Mapper
@Repository
public interface IReservaDAO extends ICrudRepository<TReserva, Integer> {

    List<TReserva> findByIdUsuario(Integer id_usuario);

    void cancel(Integer id);

    TReserva last();
    
    TReserva findLastTicket(@Param("id_usuario") String usuario, @Param("placa") String placa);

}
