/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.service;

/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
public interface IEmailService {
    
    void sendMail(String[] to, String subject, String text);
    
}
