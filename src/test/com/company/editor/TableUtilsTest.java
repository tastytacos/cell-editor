package com.company.editor;

import com.company.editor.exceptions.FilenameContainingDotException;
import com.company.editor.utils.TableUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;

import static org.junit.Assert.*;

public class TableUtilsTest {

    @Test
    public void testGetFileExtension() throws FilenameContainingDotException {
        assertEquals(".txt", TableUtils.getFileExtension(new File("file.txt")));
        assertEquals(".xls", TableUtils.getFileExtension(new File("file.xls")));
        assertEquals(".xlsx", TableUtils.getFileExtension(new File("file.xlsx")));
        assertEquals(".txt", TableUtils.getFileExtension(new File("123.42.txt")));
        assertEquals(".xls", TableUtils.getFileExtension(new File("123.42.xls")));
        assertEquals(".xlsx", TableUtils.getFileExtension(new File("123.42.xlsx")));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFileExtensionWithoutDotExtension() throws IndexOutOfBoundsException, FilenameContainingDotException {
        TableUtils.getFileExtension(new File("filename"));
    }

    @Test(expected = FilenameContainingDotException.class)
    public void testGetFileExtensionWithDotWithoutExtension() throws FilenameContainingDotException {
        TableUtils.getFileExtension(new File("21.02.2010"));
    }


    @Test
    public void sortTable() {
        JTable table = new JTable();

        TableModel tableModel = new DefaultTableModel(2, 3);

    }


    @Test
    public void fillTableModelByZeros() {
    }

    @Test
    public void fillTableModelByGivenValues() {


    }

    @Test
    public void displayMessageOnScreen() {
    }

    @Test
    public void displayMessageOnScreen1() {
    }

    @Test
    public void getFileExtension() {
    }

    @Test
    public void paste() {
    }

    @Test
    public void getListFromModelColumn() {
    }

    @Test
    public void createButton() {
    }

    @Test
    public void createButton1() {
    }
}


















