/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.entity;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Data
public class TVehiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String placa;

}
