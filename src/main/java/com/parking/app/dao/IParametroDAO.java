package com.parking.app.dao;

import com.parking.app.entity.TParametro;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface IParametroDAO {

    TParametro find(String codigo);

}
