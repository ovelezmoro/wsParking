/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Administrador
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SingInDTO {
 
    private String email;
    private String nombre;
    private String photoURL;
    private String phone;
    private String uid; 
    private String loginWith;
    private String tipo;
    
}
