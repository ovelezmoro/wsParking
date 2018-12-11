/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.util;

import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;
import java.awt.Rectangle;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

/**
 *
 * @author CPizarro
 */
public class JComboBoxExpandUI extends WindowsComboBoxUI {

    @Override
    protected ComboPopup createPopup() {
        BasicComboPopup comboPopup = new BasicComboPopup(comboBox) {
            @Override
            protected Rectangle computePopupBounds(int px, int py, int pw, int ph) {
                return super.computePopupBounds(px, py, Math.max(comboBox.getPreferredSize().width, pw), ph);
            }
        };
        comboPopup.getAccessibleContext().setAccessibleParent(comboBox);
        return comboPopup;
    }

}
