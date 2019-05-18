/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Data
public class TPlaya {

    private Integer id;
    private String nombre;
    private String direccion;
    private String referencia;
    private Integer distrito;
    private Integer provincia;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String capacidad;
    private Integer abonados;

    private TTarifa tarifa;

}
