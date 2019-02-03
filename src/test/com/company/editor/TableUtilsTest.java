package com.company.editor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.applet.Main;

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

}


















