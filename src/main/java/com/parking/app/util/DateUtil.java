package com.parking.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static boolean isFecha(String fecha) {
        return DateUtil.getDate(fecha) != null;
    }

    public static Date getLastDayMonth(Date fec_ini) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fec_ini);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static enum DateTipe {

        HORA(1), DIAS(5), MESES(8), AÑOS(10);
        private final int datetipe;

        DateTipe(int datetipe) {
            this.datetipe = datetipe;
        }

        public int getDateTipe() {
            return this.datetipe;
        }
    }

    public static Date getDate(Object obj) {
        return getDate(StrUtil.getString(obj), null);
    }

    public static Date getDate(String str) {
        return getDate(str, null);
    }

    public static Date getDate(String str, String format) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat sdf = null;
            if (format != null) {
                try {
                    sdf = new SimpleDateFormat(format);
                    date = sdf.parse(str);
                } catch (Exception e) {
                }
            } else {
                if (str.length() == 2) {
                    str += StrUtil.getMonth();
                    str += StrUtil.getYear();
                    sdf = new SimpleDateFormat("ddMMyyyy");
                    try {
                        date = sdf.parse(str);
                    } catch (ParseException ex) {
                    }
                } else if (str.length() == 4) {
                    str += StrUtil.getYear();
                    sdf = new SimpleDateFormat("ddMMyyyy");
                    try {
                        date = sdf.parse(str);
                    } catch (ParseException ex) {
                    }
                } else if (str.length() == 6) {
                    sdf = new SimpleDateFormat("ddMMyy");
                    try {
                        date = sdf.parse(str);
                    } catch (ParseException ex) {
                    }
                } else if (str.length() == 8) {
                    if (str.contains("/")) {
                        sdf = new SimpleDateFormat("dd/MM/yy");
                    } else if (str.contains("-")) {
                        sdf = new SimpleDateFormat("dd-MM-yy");
                    } else if (str.contains("-")) {
                        sdf = new SimpleDateFormat("ddMMyyyy");
                    } else {
                        sdf = new SimpleDateFormat("yyyyMMdd");
                    }
                    if (sdf != null) {
                        try {
                            date = sdf.parse(str);
                        } catch (ParseException ex) {
                        }
                    }

                } else if (str.length() == 10) {
                    int index;
                    if (str.contains("/")) {
                        index = str.indexOf("/");
                        if (index == 2) {
                            sdf = new SimpleDateFormat("dd/MM/yyyy");
                        } else if (index == 4) {
                            sdf = new SimpleDateFormat("yyyy/MM/dd");
                        }
                    } else if (str.contains("-")) {
                        index = str.indexOf("-");
                        if (index == 2) {
                            sdf = new SimpleDateFormat("dd-MM-yyyy");
                        } else if (index == 4) {
                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                        }
                    }
                } else if (str.length() == 16) {
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                } else if (str.length() == 19) {
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                } else if (str.length() == 14) {
                    sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                }
                try {
                    if (sdf != null) {
                        date = sdf.parse(str);
                    }
                } catch (ParseException ex) {
                }
            }
        }
        return date;
    }

    public static Date addDate(Date fecha, DateTipe tipo, int valor) {
        String formato = StrUtil.getDate(fecha, "dd-MM-yyyy-HH-mm-ss");
        String[] sector = formato.split("-");
        if (tipo == DateTipe.HORA) {
            sector[3] = StrUtil.formatNumber(MathUtil.add(sector[3], valor), "00");
        } else if (tipo == DateTipe.DIAS) {
            sector[0] = StrUtil.formatNumber(MathUtil.add(sector[0], valor), "00");
        } else if (tipo == DateTipe.MESES) {
            sector[1] = StrUtil.formatNumber(MathUtil.add(sector[1], valor), "00");
        } else if (tipo == DateTipe.AÑOS) {
            sector[2] = StrUtil.formatNumber(MathUtil.add(sector[2], valor), "0000");
        }
        formato = new StringBuilder(sector[0]).append("-").append(sector[1]).append("-").append(sector[2])
                .append("-").append(sector[3]).append("-").append(sector[4]).append("-").append(sector[5]).toString();
        return DateUtil.getDate(formato, "dd-MM-yyyy-HH-mm-ss");
    }

    public static int getDiference(Date fecFin, Date fecIni, DateTipe tipo) {
        GregorianCalendar date1 = new GregorianCalendar();
        date1.setTime(fecIni);
        GregorianCalendar date2 = new GregorianCalendar();
        date2.setTime(fecFin);
        int rango = 0;
        if (tipo == DateTipe.HORA) {

        } else if (tipo == DateTipe.DIAS) {
            if (date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR)) {
                rango = date2.get(Calendar.DAY_OF_YEAR) - date1.get(Calendar.DAY_OF_YEAR);
            } else {
                int diasAnyo = date1.isLeapYear(date1.get(Calendar.YEAR)) ? 366 : 365;
                int rangoAnyos = date2.get(Calendar.YEAR) - date1.get(Calendar.YEAR);
                rango = (rangoAnyos * diasAnyo) + (date2.get(Calendar.DAY_OF_YEAR) - date1.get(Calendar.DAY_OF_YEAR));
            }
        } else if (tipo == DateTipe.MESES) {
        } else if (tipo == DateTipe.AÑOS) {
            rango = date2.get(Calendar.YEAR) - date1.get(Calendar.YEAR);
        }
        return rango;
    }
}
