/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.entity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Data
public class TReserva {

    private Integer id;
    private Date fechaReserva;
    private BigDecimal precioReserva;
    private Integer idPlaya;
    private Integer idUsuario;
    private String shaReserva;
    private Integer idVehiculo;
    private String estado;
    private TUsuario usuario;
    private TVehiculo vehiculo;
    private TPlaya playa;
    private Date fechaIngreso;
    private Date fechaSalida;

}
