/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.service;

/**
 *
 * @author Administrador
 */
public interface INotificationService {
    
    void sendMessageToAllUsers(String title, String message);
    
    Boolean sendMessageToUser(String title, String message, String userId);
    
}
