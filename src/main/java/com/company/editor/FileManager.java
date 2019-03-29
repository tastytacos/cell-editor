package com.company.editor;

import com.company.editor.utils.TableUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;


abstract class FileManager {
    public abstract void save(File file, DefaultTableModel tableModel) throws IOException;

    public abstract List<Double> load(File file) throws Exception;

    int findWorkSheet(Workbook workBook) {
        int index = 0;
        for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
            Sheet sheet = workBook.getSheetAt(i);
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                index = i;
                break;
            }
        }
        return index;
    }
}

class XlsFile extends FileManager {

    @Override
    public void save(File file, DefaultTableModel tableModel) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row firstRow = sheet.createRow(0);
        Cell q = firstRow.createCell(0);
        q.setCellValue("Q");
        Cell h = firstRow.createCell(1);
        h.setCellValue("H");
        for (int i = 1; i < tableModel.getRowCount() + 1; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue((Double.parseDouble(tableModel.getValueAt(i - 1, j).toString())));
            }
        }

        workbook.write(new FileOutputStream(file));
        workbook.close();
    }

    @Override
    public List<Double> load(File file) throws Exception {
        // for older versions of exel file
        List<Double> returnList = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        // Finds the workbook instance for XLS file
        HSSFWorkbook myWorkBook = new HSSFWorkbook(fis);
        // Return first sheet from the XLS workbook
        HSSFSheet mySheet = myWorkBook.getSheetAt(findWorkSheet(myWorkBook));
        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator();
        int columnCounter = 0;
        // Traversing over each row of XLS file
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            CellType cellType;
            while (cellIterator.hasNext()) {
                columnCounter++;
                if (columnCounter > CellEditorTableConstants.DEFAULT_COLS_AMOUNT) {
                    break;
                }
                Cell cell = cellIterator.next();
                cellType = cell.getCellTypeEnum();
                if (row.getRowNum() > 1 && cellType.toString().equals("STRING")) {      // means if the row is not header row and if it has incorrect type
                    TableUtils.displayMessageOnScreen("Incorrect cell format in your table in " + Integer.toString(cell.getColumnIndex() + 1) + " column " +
                            "and " + Integer.toString(cell.getRowIndex() + 1) + " row. " + System.lineSeparator() +
                            " Check your table and fix it!");
                    return null;
                }
                if (cellType.toString().equals("NUMERIC")) {
                    returnList.add(cell.getNumericCellValue());
                }
            }
            columnCounter = 0;
        }
        fis.close();
        return returnList;
    }
}

class XlsxFile extends FileManager {
    @Override
    public void save(File file, DefaultTableModel tableModel) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet();

        Row firstRow = sheet.createRow(0);
        Cell q = firstRow.createCell(0);
        q.setCellValue("Q");
        Cell h = firstRow.createCell(1);
        h.setCellValue("H");
        for (int i = 1; i < tableModel.getRowCount() + 1; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue((Double.parseDouble(tableModel.getValueAt(i - 1, j).toString())));
            }
        }

        workbook.write(new FileOutputStream(file));
        workbook.close();
    }

    @Override
    public List<Double> load(File file) throws Exception {
        List<Double> returnList = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        // Finds the workbook instance for XLSX file
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
        // Return first sheet from the XLSX workbook
        XSSFSheet mySheet = myWorkBook.getSheetAt(findWorkSheet(myWorkBook));
        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator();
        int columnCounter = 0;
        // Traversing over each row of XLSX file
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            CellType cellType;
            while (cellIterator.hasNext()) {
                columnCounter++;
                if (columnCounter > CellEditorTableConstants.DEFAULT_COLS_AMOUNT) {
                    break;
                }
                Cell cell = cellIterator.next();
                cellType = cell.getCellTypeEnum();
                if (row.getRowNum() > 1 && cellType.toString().equals("STRING")) {      // means if the row is not header row and if it has incorrect type
                    TableUtils.displayMessageOnScreen("Incorrect cell format in your table in " + Integer.toString(cell.getColumnIndex() + 1) + " column " +
                            "and " + Integer.toString(cell.getRowIndex() + 1) + " row. " + System.lineSeparator() +
                            " Check your table and fix it!");
                    return null;
                }
                if (cellType.toString().equals("NUMERIC")) {
                    returnList.add(cell.getNumericCellValue());
                }
            }
            columnCounter = 0;
        }
        fis.close();
        return returnList;
    }
}

class TXTFile extends FileManager {
    @Override
    public void save(File file, DefaultTableModel tableModel) throws IOException {
        RandomAccessFile rand = new RandomAccessFile(file, "rw");
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                rand.writeBytes(tableModel.getValueAt(i, j).toString());
                rand.writeBytes(" ");
            }
            rand.writeBytes(System.lineSeparator());
        }
        rand.close();
    }

    @Override
    public List<Double> load(File file) throws Exception {
        RandomAccessFile rand = new RandomAccessFile(file, "r");
        String line;
        Scanner scan;
        List<Double> returnList = new ArrayList<>();
        while ((line = rand.readLine()) != null) {
            scan = new Scanner(line);
            scan.useLocale(Locale.CANADA);
            returnList.add(scan.nextDouble());
            returnList.add(scan.nextDouble());
        }
        rand.close();
        return returnList;
    }
}



