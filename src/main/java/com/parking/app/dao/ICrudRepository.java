/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.dao;

import java.util.List;

/**
 *
 * @author Administrador
 */
public interface ICrudRepository<T, K> {

    void save(T t);

    void update(T t);

    void delete(K id);

    List<T> find();

    T findOne(K id);

}
