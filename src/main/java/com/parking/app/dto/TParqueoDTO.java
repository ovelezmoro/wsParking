package com.parking.app.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by LENOVO on 28/03/2019.
 */
@Data
public class TParqueoDTO {

    private String placa;
    private String marca;
    private String color;
    private String modelo;
    private Date fecha;
    private String entrada;
    private String zona;
    private String observacion;
    private String image;
    private Integer idUsuario;

}
