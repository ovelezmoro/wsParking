package com.parking.app.dao;

import com.parking.app.entity.TAutorizacion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by LENOVO on 13/04/2019.
 */
@Mapper
@Repository
public interface IAutorizacionDAO {

    void save(TAutorizacion t);

    void update(TAutorizacion t);

    void delete(Integer id);

    List<TAutorizacion> find();

    TAutorizacion findOne(Integer id);

    TAutorizacion findByPlaca(String placa);

    List<TAutorizacion> findByDni(String dni);

}
