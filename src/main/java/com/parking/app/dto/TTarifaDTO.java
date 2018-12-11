/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.dto;

import com.parking.app.util.StrUtil;
import lombok.Data;

/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Data
public class TTarifaDTO {

    Integer idplaya;
    Double tarifa;
    
    @Override
    public String toString(){
        return StrUtil.getString(idplaya) + " - " + StrUtil.getString(tarifa);
    }
    
}
