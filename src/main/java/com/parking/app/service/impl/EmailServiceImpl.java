/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.service.impl;

import com.parking.app.service.IEmailService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author Osmar Velezmoro <SIS-SINTAD>
 */
@Data
@Slf4j
@Service
public class EmailServiceImpl implements IEmailService {
    
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public JavaMailSender sender;

    @Override
    public void sendMail(String[] to, String subject, String text) {
        new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            sender.send(message);
        });
    }
    
}
