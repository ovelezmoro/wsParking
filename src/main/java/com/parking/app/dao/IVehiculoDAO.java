/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.dao;

import com.parking.app.entity.TVehiculo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Administrador
 */
@Mapper
@Repository
public interface IVehiculoDAO {

    void save(TVehiculo t);

    void update(TVehiculo t);

    void delete(Integer id);

    List<TVehiculo> find();

    TVehiculo findOne(Integer id);

    List<TVehiculo> findByIdUsuario(@Param("idUsuario") Integer idUsuario);

    TVehiculo findByPlaca(@Param("placa") String placa);


}
