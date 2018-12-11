/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Data
public class TTarifa {

    private Integer id;
    private BigDecimal tarifaEstacionamiento;
    private BigDecimal tarifaReserva;
    private BigDecimal nuevaTarifa;
    private Integer vigencia;
    private Date fregistro;
    private TPlaya idPlaya;

}
