package com.parking.app.util;

import java.security.MessageDigest;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncryptUtil {

    private static final SecretKeySpec KEY_SPEC = new SecretKeySpec("MyKey".getBytes(), "Blowfish");

    /**
     * Takes a single String as an argument and returns an Encrypted version of
     * that String.
     *
     * @param str String to be encrypted
     * @return <code>String</code> Encrypted version of the provided String
     */
    public static String encrypt(String str) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, KEY_SPEC);
            byte[] encrypted = cipher.doFinal(str.getBytes());
            StringBuilder cadena = new StringBuilder("");
            for (int i = 0; i < encrypted.length; i++) {
                cadena.append(encrypted[i]);
                if (i < encrypted.length - 1) {
                    cadena.append(".");
                }
            }
            return cadena.toString();
        } catch (Exception ex) {
            log.error("Exception: " + "\n\r\tMessage:" + ex.getMessage() + "\n\r\tCause:" + ex.getCause(), ex);
            throw ex;
        }
    }

    /**
     * Takes a encrypted String as an argument, decrypts and returns the
     * decrypted String.
     *
     * @param str Encrypted String to be decrypted
     * @return <code>String</code> Decrypted version of the provided String
     */
    public static String decrypt(String str) throws Exception {
        try {
            String[] row = str.split("\\.");
            byte[] myBytes = new byte[8];
            for (int i = 0; i < myBytes.length; i++) {
                myBytes[i] = (byte) MathUtil.getInt(row[i]);
            }
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, KEY_SPEC);
            byte[] decrypted = cipher.doFinal(myBytes);
            return new String(decrypted);
        } catch (Exception ex) {
            log.error("Exception: " + "\n\r\tMessage:" + ex.getMessage() + "\n\r\tCause:" + ex.getCause(), ex);
            throw ex;
        }
    }

    public static String getMD5(String cadena) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(cadena.getBytes());
        int size = b.length;
        StringBuilder h = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255;
            if (u < 16) {
                h.append("0").append(Integer.toHexString(u));
            } else {
                h.append(Integer.toHexString(u));
            }
        }
        return h.toString();
    }

    /**
     * Devuelve el password encriptado
     *
     * @param cadena(password)
     * @param control
     * @return String
     */
    public static String encrypt(String cadena, boolean control) {
        int i, iFin, iChr;
        iFin = cadena.trim().length();
        String cripta = "";
        for (i = 0; i < iFin; i++) {
            if (control) {
                iChr = (((int) (cadena.charAt(i))) + 123 + ((i + 1) * 4)) % 255;
            } else {
                iChr = (((int) (cadena.charAt(i))) - 123 - ((i + 1) * 4)) % 255;
            }
            cripta += "" + (char) iChr;
        }
        return cripta;
    }
}
