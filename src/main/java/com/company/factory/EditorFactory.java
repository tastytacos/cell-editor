package com.company.factory;


import com.company.editor.CellEditor;

import javax.swing.*;
import java.util.Locale;

public class EditorFactory {
    public static void main(String[] args){
        new CellEditor(new Locale("ru", "RU"));
    }

    public static void getCellEditor(){
         SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new CellEditor();
            }
        });
    }

    public static void getCellEditor(Locale locale){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CellEditor(locale);
            }
        });
    }
    public static void getCellEditor(final CellEditorMapPanel panel, final Object key){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CellEditor(panel, key);
            }
        });
    }
}
