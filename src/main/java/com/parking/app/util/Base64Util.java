/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Edgar
 */
public class Base64Util {

    public static String BytesToBase64(byte[] bytes) {
        byte[] encoded = Base64.encodeBase64(bytes);
        return new String(encoded);
    }

    public static String FileToBase64(File file) {
        String base64;
        try {
            InputStream is = new FileInputStream(file);
            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                throw new RuntimeException("Error Limite exedido");
            }
            byte[] bytes = new byte[(int) length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            if (offset < bytes.length) {
                throw new RuntimeException("Could not completely read file " + file.getName());
            }
            is.close();
            byte[] encoded = Base64.encodeBase64(bytes);
            base64 = new String(encoded);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return base64;
    }

    public static File Base64ToFile(String base64, String name) {
        File file = new File(name);
        try {
            byte[] decoded = Base64.decodeBase64(base64);
            OutputStream output = new FileOutputStream(name);
            output.write(decoded);
            output.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return file;
    }

    public static InputStream Base64ToInputStream(String base64) {
        try {
            byte[] decoded = Base64.decodeBase64(base64);
            return new ByteArrayInputStream(decoded);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
