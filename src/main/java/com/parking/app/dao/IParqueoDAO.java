package com.parking.app.dao;

import com.parking.app.entity.TParqueo;
import com.parking.app.entity.TUsuario;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LENOVO on 28/03/2019.
 */
@Mapper
@Repository
public interface IParqueoDAO {


    void save(TParqueo t);

    void update(TParqueo t);

    void delete(Integer id);

    List<TParqueo> find();

    TParqueo findOne(Integer id);

    TParqueo findByPlaca(String placa);


}
