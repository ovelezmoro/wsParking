/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.dao;

import com.parking.app.entity.TUsuario;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrador
 */
@Mapper
@Repository
public interface IUsuarioDAO extends ICrudRepository<TUsuario, Integer> {

    TUsuario findByUid(String uid);
    
}


