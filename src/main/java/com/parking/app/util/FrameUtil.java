/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.util;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author SIS-Osmar
 */
public class FrameUtil {
    public static void setInternal(JDesktopPane jDesPan, JInternalFrame jIntFrm) {
        JInternalFrame jIntFrame;
        jIntFrame = getInternal(jDesPan, jIntFrm.getClass());
        if (jIntFrame == null || jIntFrame.isClosed()) {
            jIntFrame = jIntFrm;
            jIntFrame.setVisible(true);
            jDesPan.add(jIntFrame);
        } else {
            int a = JOptionPane.showConfirmDialog(null, "Existe una venta abierta\r\n¿Deseas recargarla?", "Sistema", JOptionPane.YES_NO_OPTION, 1);
            if (a == 0) {
                jIntFrame.dispose();
                jIntFrame = jIntFrm;
                jIntFrame.setVisible(true);
                jDesPan.add(jIntFrame);
            }
        }
        jIntFrame.moveToFront();
        jIntFrame.requestFocus();
    }

    public static void addInternal(JDesktopPane jDesPan, JInternalFrame jIntFrm) {
        JInternalFrame jIntFrame;
        jIntFrame = getInternal(jDesPan, jIntFrm.getClass());
        if (jIntFrame != jIntFrm) {
            if (jIntFrame == null || jIntFrame.isClosed()) {
                jIntFrame = jIntFrm;
                jIntFrame.setVisible(true);
                jDesPan.add(jIntFrame);
            }
        } else {
            jIntFrame.setVisible(true);
        }
        jIntFrame.moveToFront();
        jIntFrame.requestFocus();
    }

    public static void addMulInternal(JDesktopPane jDesPan, JInternalFrame jIntFrm) {
        JInternalFrame jIntFrame;
        jIntFrame = getInternal(jDesPan, jIntFrm.getClass());
        if (jIntFrame == null || jIntFrame.isClosed()) {
            jIntFrame = jIntFrm;
            jIntFrame.setVisible(true);
            jDesPan.add(jIntFrame);
            jIntFrame.moveToFront();
            jIntFrame.requestFocus();
        } else {
            if (!jIntFrame.getTitle().equals(jIntFrm.getTitle())) {
                jIntFrm.setVisible(true);
                jDesPan.add(jIntFrm);
                jIntFrm.moveToFront();
                jIntFrm.requestFocus();
            } else {
                jIntFrame.moveToFront();
                jIntFrame.requestFocus();
            }
        }
    }

    public static void newInternal(JDesktopPane jDesPan, JInternalFrame jIntFrm) {
        JInternalFrame jIntFrame;
        jIntFrame = getInternal(jDesPan, jIntFrm.getClass());
        if (jIntFrame != null && !jIntFrame.isClosed()) {
            jIntFrame.dispose();
        }
        jIntFrame = jIntFrm;
        jIntFrame.setVisible(true);
        jDesPan.add(jIntFrame);
        jIntFrame.moveToFront();
        jIntFrame.requestFocus();
    }

    public static JInternalFrame getInternal(JDesktopPane jDesPan, Class clase) {
        JInternalFrame[] internal = jDesPan.getAllFrames();
        for (JInternalFrame internal1 : internal) {
            if (clase.equals(internal1.getClass())) {
                return internal1;
            }
        }
        return null;
    }
}
