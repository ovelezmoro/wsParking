package com.parking.app.util;

import java.awt.Color;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class EventUtil {

    private static final Color color = Color.CYAN;
    private static final Color defCo = UIManager.getColor("TextField.background");
    private static JTextField help;

    public static void focusGained(JTextField field) {
        if (field.isEditable() && field.isEnabled()) {
            field.selectAll();
            field.setBackground(color);
        }
    }

    public static void resetStyle(JTextField field) {
        JTextField reset = new JTextField();
        reset.setEditable(field.isEditable());
        reset.setEnabled(field.isEnabled());
        field.setBackground(reset.getBackground());
    }

    public static void focusLost(JTextField field) {
        if (field.isEditable() && field.isEnabled()) {
            field.setBackground(defCo);
        } else {
            resetStyle(field);
        }
    }

    public static void isFecha(JTextField field) {
        if (!field.getText().isEmpty()) {
            if (field.getText().length() <= 10) {
                String str = StrUtil.toFecha(field.getText());
                field.setText(str);
            }
        }
    }

    public static void isHour(JTextField field) {
        if (!field.getText().isEmpty()) {
            String[] hours = field.getText().split(":");
            if (hours.length > 1) {
                String hra = StrUtil.formatNumber(hours[0], "00");
                String minute = StrUtil.formatNumber(hours[1], "00");
                field.setText(hra + ":" + minute);
            } else {
                if (field.getText().length() < 5) {
                    String str = field.getText();
                    try {
                        String hra = StrUtil.formatNumber(str.substring(0, 2), "00");
                        String minute = StrUtil.formatNumber(str.substring(2, 4), "00");
                        field.setText(hra + ":" + minute);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void setHelp(JTextField help) {
        EventUtil.help = help;
    }

    public static void setHelp(String txt) {
        EventUtil.help.setText(txt);
    }

    public static void clearHelp() {
        EventUtil.help.setText("");
    }
}
