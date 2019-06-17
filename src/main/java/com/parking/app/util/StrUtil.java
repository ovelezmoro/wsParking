package com.parking.app.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StrUtil {

    public static final String[] ENG_ABC = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    
    public static final Locale currentLocale = Locale.getDefault();
    
    public static String getString(Object data) {
        if (data != null) {
            String str;
            if (data instanceof byte[]) {
                str = new String((byte[]) data).trim();
            } else {
                str = (String.valueOf(data) + "").trim();
            }
            return str;
        } else {
            return "";
        }
    }

    public static String getString(Object data, int length) {
        if (data != null) {
            String str = String.valueOf(data);
            if (str.length() > length) {
                str = str.substring(0, length);
            }
            return str;
        } else {
            return "";
        }
    }

    public static String getString(Object data, int lenIni, int lenFin) {
        if (data != null) {
            String str = String.valueOf(data);
            int len = str.length();
            if (len < lenIni) {
                str = "";
            } else if (len < lenFin) {
                str = str.substring(lenIni, len);
            } else {
                str = str.substring(lenIni, lenFin);
            }
            return str;
        } else {
            return "";
        }
    }

    public static String getDate(Date fecha, SimpleDateFormat format) {
        if (fecha != null) {
            return format.format(fecha);
        } else {
            return "";
        }
    }

    public static String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return getDate(date, sdf);
    }

    public static String getDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return getDate(date, sdf);
    }

    public static String formatNumber(Object number, int decimal) {
        String pattern = "#0";
        StringBuilder sb = new StringBuilder(pattern);
        if (decimal > 0) {
            sb.append(".");
        }
        for (int i = 0; i < decimal; i++) {
            sb.append("0");
        }
        return formatNumber(MathUtil.getDouble(number), sb.toString());
    }

    public static String formatNumber(Object number, String pattern) {
        String format = "";
        try {
            format = formatNumber(MathUtil.getNumber(number), pattern);
        } catch (Exception e) {
        }
        return format;
    }

    public static String formatNumber(double number, String pattern) {
        String format = "";
        try {
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
            otherSymbols.setDecimalSeparator('.');
            otherSymbols.setGroupingSeparator(',');
            DecimalFormat df = new DecimalFormat(pattern, otherSymbols);
            format = df.format(number);
        } catch (Exception e) {
        }
        return format;
    }

    public static String getPrimerDiaDelMes(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("01/MM/yyyy");
        return sdf.format(fecha);
    }

    public static String quitarLetras(String str) {
        String newStr = "";
        for (int i = 0; i < str.length(); i++) {
            try {
                char a = str.charAt(i);
                Integer.parseInt(a + "");
                newStr += a;
            } finally {
            }
        }
        return newStr;
    }

    public static String toFecha(String str) {
        if (str.isEmpty()) {
            return "";
        } else {
            return toFecha(str, "dd/MM/yyyy");
        }
    }

    public static String toFecha(String str, String format) {
        SimpleDateFormat sdf;
        if (format != null) {
            sdf = new SimpleDateFormat(format);//"yyyy-MM-dd"
            Date fecha = DateUtil.getDate(str);
            if (fecha == null) {
                return "";
            } else {
                return sdf.format(fecha);
            }
        } else {
            return "";
        }
    }

    public static String hTrim(String s) {
        s = trim(s);
        s = s.replaceAll("\n", " ");
        s = s.replaceAll("\t", " ");
        s = s.replaceAll("\r", " ");
        s = s.replaceAll(" ", " ");
        return trim(s);
    }

    public static String NumTrim(String s) {
        s = trim(s);
        s = s.replaceAll(".", "");
        return trim(s);
    }

    public static String trim(String s) {
        if (s != null) {
            s = s.trim();
            while (s.contains(((char) 160) + "" + (char) 160)) {
                s = s.replace(((char) 160) + "" + (char) 160, " ");
            }
            while (s.contains("  ")) {
                s = s.replace("  ", " ");
            }
            if (s.startsWith(((char) 160) + "")) {
                s = s.substring(1, s.length());
            }
            if (s.endsWith(((char) 160) + "")) {
                s = s.substring(0, s.length() - 1);
            }
            s = s.trim();
        }
        return s;
    }

    public static String getYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year + "";
    }

    public static String getMonth() {
        Calendar calendar = Calendar.getInstance();
        String year = StrUtil.formatNumber(calendar.get(Calendar.MONTH) + 1, "00");
        return year;
    }

    public static String lPad(String word, int repeat, char val) {
        int len = word.length();
        for (int i = 0; i < repeat - len; i++) {
            word = val + word;
        }
        return word;
    }
    
    public static String reemplazarCaracteresEspeciales(String palabra) {
        String[] caracteresMalos = {" ","ñ","|","à","á","À","Á","è","é","È","É","ì","í","Ì","Í","ò","ó","Ò","Ó","ù","ú","Ù","Ú","\b","/",":","<","*","?",">","."};
        String[] caracteresBuenos = {"_","n","_","a","a","A","A","e","e","E","E","i","i","I","I","o","o","O","O","u","u","U","U","_","_","_","_","","_","_", "",""};

        for (String letraMala : caracteresMalos) {
            if(palabra.contains(letraMala)){
                palabra = palabra.replace(letraMala,caracteresBuenos[Arrays.asList(caracteresMalos).indexOf(letraMala)]);
            }
        }

        return palabra;

    }
    
}
