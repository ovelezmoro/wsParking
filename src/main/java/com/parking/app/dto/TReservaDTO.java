/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.dto;

import com.parking.app.entity.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Data
public class TReservaDTO {

    private Date fechaReserva;
    private BigDecimal precioReserva;
    private Integer idPlaya;
    private Integer idUsuario;
    private Integer idVehiculo;

}
