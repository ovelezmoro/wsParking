package com.parking.app.entity;

import com.parking.app.dto.AutorizacionDTO;
import lombok.*;

import java.util.Date;

/**
 * Created by LENOVO on 13/04/2019.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TAutorizacion {

    private Integer id;
    private String dni;
    private Date fecha;
    private String playa;
    private String nombres;
    private String direccion;
    private String telefono;
    private String brevete;
    private String tpropiedad;
    private String marca;
    private String modelo;
    private String placa;
    private String color;
    private String ano;
    private String fsalida;
    private String hsalida;
    private String firma;
    private Integer idUsuario;

    public TAutorizacion(AutorizacionDTO autorizacionDTO) {
        this.dni = autorizacionDTO.getDni();
        this.playa = autorizacionDTO.getPlaya();
        this.nombres = autorizacionDTO.getNombres();
        this.direccion = autorizacionDTO.getDireccion();
        this.telefono = autorizacionDTO.getTelefono();
        this.brevete = autorizacionDTO.getBrevete();
        this.tpropiedad = autorizacionDTO.getTpropiedad();
        this.marca = autorizacionDTO.getMarca();
        this.modelo = autorizacionDTO.getModelo();
        this.placa = autorizacionDTO.getPlaca();
        this.color = autorizacionDTO.getColor();
        this.ano = autorizacionDTO.getAno();
        this.hsalida = autorizacionDTO.getHsalida();
        this.firma = autorizacionDTO.getFirma();
        this.idUsuario = autorizacionDTO.getIdUsuario();
    }
}
