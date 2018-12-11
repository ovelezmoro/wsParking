package com.parking.app.util;

import javax.swing.JTable;

public class JTableUtil {

    public static Object getValueAt(JTable jTable, int row, int column) {
        Object obj;
        if (jTable.getClass() == JTable.class) {
            obj = jTable.getValueAt(row, column);
//            DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
//            List lst = new ArrayList(dtm.getDataVector());
//            lst = new ArrayList((Collection) lst.get(row));
//            obj = lst.get(column);
        } else {
            obj = jTable.getValueAt(jTable.convertRowIndexToView(row), jTable.convertColumnIndexToView(column));
        }
        return obj;
    }

    public static String getColumnName(JTable table, int column) {
        return table.getColumnName(column);
    }

}
