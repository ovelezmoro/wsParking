/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.controller;

import com.parking.app.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author a_rkx
 */
@RestController
@RequestMapping("/mail")
public class MailController {
    
    
    @Autowired
    IEmailService iEmailService;

    @RequestMapping("enviar")
    @ResponseBody
    @GetMapping
    public ResponseEntity<?> enviar() {

        iEmailService.sendMail(
                new String[]{"aocampohidalgo@gmail.com"},
                "Prueba de correo",
                "PRUEBA RESERVA " );

        return new ResponseEntity<>(null, HttpStatus.OK);

    }
    
}
