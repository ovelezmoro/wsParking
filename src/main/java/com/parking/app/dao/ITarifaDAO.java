/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.dao;

import com.parking.app.entity.TTarifa;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrador
 */
@Mapper
@Repository
public interface ITarifaDAO extends ICrudRepository<TTarifa, Integer> {

    List<Map<String, Object>> findWithPlaya(Integer id);

    void updateByIdPlaya(@Param("id_playa") Integer idPlaya, @Param("tarifa") Double tarifa);

}
