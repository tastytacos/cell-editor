package com.company.editor.utils;

import com.company.editor.*;
import com.company.editor.exceptions.FilenameContainingDotException;
import com.company.editor.exceptions.TextTransferException;
import com.company.language_tools.languageble.LanguageButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TableUtils {
    private static Component component;
    private static String[] allowedExtensions = {CellEditorTableConstants.TXT_FORMAT,
            CellEditorTableConstants.XLS_FORMAT,
            CellEditorTableConstants.XLSX_FORMAT};

    public static void setComponent(Component component) {
        TableUtils.component = component;
    }


    public static boolean validData(TableModel table_model) {
        String data_element;
        for (int i = 0; i < table_model.getRowCount(); i++) {
            for (int j = 0; j < table_model.getColumnCount(); j++) {
                data_element = table_model.getValueAt(i, j).toString();
                // todo replace this part of code by throwing Exception
                if (!containsDouble(data_element) && !data_element.equals("")) {
                    displayMessageOnScreen("The wrong value is detected in " + (j + 1) +
                            " column and " + (i + 1) + " row. " + System.lineSeparator() + " The table can not be fixed " +
                            "until the value will not be changed to double!");
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean containsDouble(String data) {
        boolean containsDouble;
        try {
            Double.parseDouble(data);
            containsDouble = true;
        } catch (NumberFormatException wrong_input_data) {
            containsDouble = false;
        }
        return containsDouble;
    }

    public static DefaultTableModel checkForBlankCells(JTable table) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
            for (int j = 0; j < defaultTableModel.getColumnCount(); j++) {
                String data_element = defaultTableModel.getValueAt(i, j).toString();
                // todo replace this part of code by throwing Exception
                if (data_element.equals("")) {
                    displayMessageOnScreen("The empty value is detected in " + (j + 1) +
                            " column" + " and " + (i + 1) + " row." + System.lineSeparator() + "  It will be filled with 0.0");
                    defaultTableModel.setValueAt(0.0, i, j);
                }
            }
        }
        return defaultTableModel;
    }

    public static DefaultTableModel sortTable(JTable table) {
        List<Double> doubles = new ArrayList<>();
        int rowsAmount = table.getRowCount();
        int colsAmount = table.getColumnCount();
        for (int i = 0; i < rowsAmount; i++) {
            for (int j = 0; j < colsAmount; j++) {
                doubles.add(Double.parseDouble(table.getValueAt(i, j).toString()));      // this complicated way is only one way out
            }
        }
        Collections.sort(doubles);
        return fillTableModel(rowsAmount, colsAmount, CellEditorTableConstants.getNameColumns(), doubles);
    }

    /**
     * This method creates and sets the {@link DefaultTableModel} table defaultTableModel which is filled with zero.
     *
     * @param rows         amount rows in the table
     * @param columns      amount columns in the table
     * @param columns_name {@link String} array names of the columns
     * @return {@link DefaultTableModel} defaultTableModel
     */
    public static DefaultTableModel fillTableModel(int rows, int columns, String[] columns_name) {
        DefaultTableModel model = new DefaultTableModel(rows, columns);
        model.setColumnIdentifiers(columns_name);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                model.setValueAt(0.0, i, j);
            }
        }
        return model;
    }


    /**
     * This method creates and sets the {@link DefaultTableModel} table defaultTableModel which is filled with the Double
     * {@link List} given as argument.
     *
     * @param rows         amount rows in the table
     * @param columns      amount columns in the table
     * @param columns_name {@link String} array names of the columns
     * @param valuesList   the {@link}
     * @return {@link DefaultTableModel} defaultTableModel
     */
    public static DefaultTableModel fillTableModel(int rows, int columns, String[] columns_name, List<Double> valuesList) {
        DefaultTableModel model = new DefaultTableModel(rows, columns);
        model.setColumnIdentifiers(columns_name);
        // assigning the data
        int list_index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (list_index == valuesList.size())
                    break;
                model.setValueAt(valuesList.get(list_index).toString(), i, j);
                list_index++;
            }
        }
        return model;
    }

    public static void displayMessageOnScreen(String message) {
        JOptionPane.showMessageDialog(component, message);
    }

    public static void displayMessageOnScreen(String message, String title, int MessageType) {
        JOptionPane.showMessageDialog(component, message, title, MessageType);
    }


    public static String getFileExtension(File file) throws IndexOutOfBoundsException, FilenameContainingDotException {
        String fileName = file.toString();
        String substring = fileName.substring(fileName.lastIndexOf('.'));
        for (String extension : allowedExtensions) {
            if (extension.equals(substring)) {
                return substring;
            }
        }
        if (!substring.equals(""))
            throw new FilenameContainingDotException("Non supportive extension");
        return substring;
    }


    public static DefaultTableModel paste(JTable table) throws TextTransferException {
        DefaultTableModel newDefaultTableModel = (DefaultTableModel) table.getModel();
        String copyString;
        try {
            copyString = TextTransfer.getClipboardContents();
        } catch (Exception e) {
            throw new TextTransferException(e.getMessage(), e.getCause());
        }

        Converter converter;
        if (table.getSelectedRowCount() == 0) {
            converter = new StringConverter(copyString);
        } else {
            converter = new StringFromIndexConverter(copyString, table.getSelectedRow(), table.getSelectedColumn(),
                    newDefaultTableModel);
        }

        try {
            newDefaultTableModel = converter.convert();
            newDefaultTableModel.setColumnIdentifiers(CellEditorTableConstants.getNameColumns());
        } catch (TextTransferException e) {
            throw new TextTransferException(e.getMessage(), e.getCause());
        }

        return newDefaultTableModel;
    }

    public static List<Double> getListFromModelColumn(DefaultTableModel defaultTableModel, int column) {
        List<Double> return_list = new ArrayList<>();
        for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
            return_list.add(Double.parseDouble(defaultTableModel.getValueAt(i, column).toString()));
        }
        return return_list;
    }


    /**
     * This method creates and returns a {@link JButton} object with the next parameters
     *
     * @param buttonText    text which will be on button
     * @param buttonCommand it's command name
     * @return JButton object equipped with attributes mentioned above
     */
    static JButton createButton(String buttonText, String buttonCommand, Font font) {
        JButton button = new JButton();
        button.setText(buttonText);
        button.setActionCommand(buttonCommand);
        button.setFont(font);
        return button;
    }

    public static LanguageButton createButton(String key, String buttonText, String buttonCommand, Font font) {
        LanguageButton button = new LanguageButton(key);
        button.setText(buttonText);
        button.setActionCommand(buttonCommand);
        button.setFont(font);
        return button;
    }
}

















