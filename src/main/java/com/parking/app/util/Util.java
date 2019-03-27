/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

/**
 *
 * @author Administrador
 */
public class Util {

    public static final AWSCredentials CREDENTIALS;

    static {
        CREDENTIALS = new ProfileCredentialsProvider().getCredentials();
    }

}
