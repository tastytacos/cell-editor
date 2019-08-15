package com.company.factory;


import com.company.editor.CellEditor;
import com.company.language_tools.EditorConfigs;

import javax.swing.*;
import java.util.Locale;

public class EditorFactory {
    private static EditorConfigs editorConfigs = new EditorConfigs(new String[]{"A1", "B1"}, "MyGraph",
            new String[]{"axis1", "axis2"});

    public static void main(String[] args) {
        new CellEditor();
    }

    public static void getCellEditor() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CellEditor();
            }
        });
    }

    public static void getCellEditor(Locale locale) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CellEditor(locale, editorConfigs);
            }
        });
    }

    public static void getCellEditor(final CellEditorMapPanel panel, final Object key) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CellEditor(panel, key);
            }
        });
    }


    public static void getCellEditor(final CellEditorMapPanel panel, final Object key, EditorConfigs editorConfigs) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CellEditor(panel, key, editorConfigs);
            }
        });
    }
}
