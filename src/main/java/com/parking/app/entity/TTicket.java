package com.parking.app.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TTicket {

    private Integer id;
    private String ticket;
    private String placa;
    private String codReserva;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private BigDecimal montoTotal;
    private String playa;
    private BigDecimal tarifaEstacionamiento;
    private BigDecimal tarifaReserva;

}
