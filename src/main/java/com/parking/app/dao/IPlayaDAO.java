/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.dao;

import com.parking.app.entity.TPlaya;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Administrador
 */
@Mapper
@Repository
public interface IPlayaDAO {

    void save(TPlaya t);

    void update(TPlaya t);

    void delete(Integer id);

    List<TPlaya> find();

    TPlaya findOne(Integer id);

    List<Map<String, Object>> findOneWithTarifas(Integer id);

    List<Map<String, Object>> getCercanos(@Param("latitud") Double latitud, @Param("longitud") Double longitud, @Param("distancia") Double distancia);

}
