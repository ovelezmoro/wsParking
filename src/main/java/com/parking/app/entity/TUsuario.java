/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.entity;

import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Data
public class TUsuario {

    private Integer id;
    private String nombre;
    private String email;
    private String tokenFb;
    private Date fregistro;
    private List<TVehiculo> vehiculos;

}
