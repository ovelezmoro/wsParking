/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.entity;

import com.parking.app.dto.SingInDTO;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TUsuario {

    private Integer id;
    private String nombre;
    private String email;
    private String photoUrl;
    private String phone;
    private String uid;
    private String loginWith;
    private List<TVehiculo> vehiculos;

    public TUsuario(SingInDTO singInDTO) {
        this.nombre = singInDTO.getNombre();
        this.email = singInDTO.getEmail();
        this.photoUrl = singInDTO.getPhotoURL();
        this.phone = singInDTO.getPhone();
        this.uid = singInDTO.getUid();
        this.loginWith = singInDTO.getLoginWith();
    }

}
