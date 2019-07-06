package com.parking.app.entity;

import com.parking.app.dto.TParqueoDTO;
import lombok.*;

import java.util.Date;

/**
 * Created by LENOVO on 28/03/2019.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TParqueo {

    private Integer id;
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

    public TParqueo(TParqueoDTO parqueoDTO) {
        this.placa = parqueoDTO.getPlaca();
        this.marca = parqueoDTO.getMarca();
        this.color = parqueoDTO.getColor();
        this.modelo = parqueoDTO.getModelo();
        this.fecha = parqueoDTO.getFecha();
        this.entrada = parqueoDTO.getEntrada();
        this.zona = parqueoDTO.getZona();
        this.observacion = parqueoDTO.getObservaciones();
        this.image = parqueoDTO.getImage();
        this.idUsuario = parqueoDTO.getIdUsuario();
    }
}
