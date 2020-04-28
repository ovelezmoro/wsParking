/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.dao;

import com.parking.app.entity.TUsuario;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrador
 */
@Mapper
@Repository
public interface IUsuarioDAO {

    void save(TUsuario t);

    void update(TUsuario t);

    void delete(Integer id);

    List<TUsuario> find();

    TUsuario findOne(Integer id);

    TUsuario findByEmail(String email);
    
    TUsuario findByUid(String uid);
    
    TUsuario findByEmailAndUid(String email, String with);

}


