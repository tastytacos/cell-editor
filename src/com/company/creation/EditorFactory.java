package com.company.creation;

import com.company.editor.CellEditor;

import javax.swing.*;

public class EditorFactory {
    public static void getCellEditor(){
         SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new CellEditor();
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
