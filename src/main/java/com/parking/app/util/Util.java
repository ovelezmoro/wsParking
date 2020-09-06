/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

/**
 *
 * @author Administrador
 */
public class Util {

    public static final AWSCredentials CREDENTIALS;
    public static final BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIRXYNGOUDEHYNEGQ","qVvest24gGn6SWndU1AEFIstF+Wt9+0MHM44yX95");


    static {
        CREDENTIALS = new AWSStaticCredentialsProvider(awsCreds).getCredentials(); //new ProfileCredentialsProvider().getCredentials();
    }

}
