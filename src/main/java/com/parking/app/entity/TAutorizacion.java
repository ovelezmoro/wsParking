package com.parking.app.entity;

import com.parking.app.dto.AutorizacionDTO;

/**
 * Created by LENOVO on 13/04/2019.
 */
public class TAutorizacion {

    private Integer id;
    private String dni;
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
    private String hsalida;
    private String firma;

    public TAutorizacion(AutorizacionDTO autorizacionDTO) {
        this.dni = autorizacionDTO.getDni();
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
    }
}
