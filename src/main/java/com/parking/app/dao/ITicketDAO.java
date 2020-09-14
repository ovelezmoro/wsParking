package com.parking.app.dao;

import com.parking.app.entity.TTicket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Mapper
@Repository
public interface ITicketDAO {

    void creaTicket(String placa, String codReserva, String fechaIngreso, String playa, BigDecimal tarifaEstacionamiento, BigDecimal tarifaReserva);

    TTicket buscarTicket(String codTicket);

    void finalizaTicket(Integer id, String fechaSalida);

    TTicket buscarTicketPorReserva(String codReserva);
}
