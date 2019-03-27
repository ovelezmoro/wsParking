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
 *
 * @author Administrador
 */
@Mapper
@Repository
public interface IVehiculoDAO extends ICrudRepository<TVehiculo, Integer> {

    List<TVehiculo> findByIdUsuario(@Param("id_usuario") Integer id_usuario);
    
    TVehiculo findByPlaca(@Param("placa") String placa);

}
