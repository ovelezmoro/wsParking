package com.parking.app.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class MathUtil {

    private BigDecimal value;

    public MathUtil() {
    }

    public MathUtil(Object number) {
        value = new BigDecimal(StrUtil.getString(MathUtil.getDouble(number)));
    }

    public MathUtil(BigDecimal value) {
        this.value = value;
    }

    public MathUtil add(Object number) {
        BigDecimal value2 = new BigDecimal(StrUtil.getString(MathUtil.getDouble(number)));
        value = value.add(value2);
        return this;
    }

    public MathUtil min(Object number) {
        BigDecimal value2 = new BigDecimal(StrUtil.getString(MathUtil.getDouble(number)));
        value = value.subtract(value2);
        return this;
    }

    public MathUtil mul(Object number) {
        BigDecimal value2 = new BigDecimal(StrUtil.getString(MathUtil.getDouble(number)));
        value = value.multiply(value2);
        return this;
    }

    public MathUtil div(Object number) {
        BigDecimal value2 = new BigDecimal(StrUtil.getString(MathUtil.getDouble(number)));
        value = value.divide(value2, 10, RoundingMode.UP);
        return this;
    }

    public double getDouble() {
        return value.doubleValue();
    }

    public double getRound(int decimal) {
        return round(value.doubleValue(), decimal);
    }

    public double getTrunc(int decimal) {
        return trunc(value.doubleValue(), decimal);
    }

    /**
     * Valida si la cadena ingresada es un numero entero
     *
     * @param str Cadena a validar
     * @return
     */
    public static boolean isInteger(String str) {
        return Pattern.matches("^?\\d+$", str);
    }

    /**
     * Valida si la cadena ingresada es un numero entero
     *
     * @param str Cadena a validar
     * @return
     */
    public static boolean isDouble(String str) {
        try {
            Double numero = Double.parseDouble(str);
            return !str.toLowerCase().contains("d") && !numero.isNaN() && !numero.isInfinite();
        } catch (Exception e) {
            return false;
        }
    }

    public static double round(double a, int decimal) {
        a = mul(a, Math.pow(10, decimal));
        a = Math.round(a);
        a = div(a, Math.pow(10, decimal));
        return a;
    }

    public static double trunc(double a, int decimal) {
        a = mul(a, Math.pow(10, decimal));
        a = new BigDecimal(StrUtil.getString(a)).intValue();
        a = div(a, Math.pow(10, decimal));
        return a;
    }

    public static double getNumber(Object data) {
        String number = StrUtil.getString(data);
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
        double a = 0;
        try {
            a = nf.parse(number).doubleValue();
        } catch (Exception e) {
        }
        return a;
    }

    public static double getDouble(Object data) {
        String number = StrUtil.getString(data);
        double a = 0;
        try {
            a = Double.parseDouble(number);
        } catch (Exception ex) {
            a = 0;
        } finally {
            if (Double.isInfinite(a) || Double.isNaN(a)) {
                a = 0;
            }
        }
        return a;
    }

    public static int getInt(Object data) {
        String number = StrUtil.getString(data);
        int a;
        try {
            a = (int) getDouble(number);
        } catch (Exception ex) {
            a = 0;
        }
        return a;
    }

    public static double add(Object a, Object b) {
        return add(getDouble(a), getDouble(b));
    }

    public static double min(Object a, Object b) {
        return min(getDouble(a), getDouble(b));
    }

    public static double mul(Object a, Object b) {
        return mul(getDouble(a), getDouble(b));
    }

    public static double div(Object a, Object b) {
        return div(getDouble(a), getDouble(b));
    }

    public static double add(double a, double b) {
        BigDecimal bigA = new BigDecimal(a + "");
        BigDecimal bigB = new BigDecimal(b + "");
        bigA = bigA.add(bigB);
        return bigA.doubleValue();
    }

    public static double min(double a, double b) {
        BigDecimal bigA = new BigDecimal(a + "");
        BigDecimal bigB = new BigDecimal(b + "");
        bigA = bigA.subtract(bigB);
        return bigA.doubleValue();
    }

    public static double mul(double a, double b) {
        BigDecimal bigA = new BigDecimal(a + "");
        BigDecimal bigB = new BigDecimal(b + "");
        bigA = bigA.multiply(bigB);
        return bigA.doubleValue();
    }

    public static double div(double a, double b) {
        BigDecimal bigA = new BigDecimal(a + "");
        BigDecimal bigB = new BigDecimal(b + "");
        bigA = bigA.divide(bigB, 10, RoundingMode.UP);
        return bigA.doubleValue();
    }
}
