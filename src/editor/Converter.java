package editor;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;


interface Converter {
    public DefaultTableModel convert() throws TextTransferException;
}


class StringConverter implements Converter {
    private String string;

    StringConverter(String string) {
        this.string = string;
    }


    @Override
    public DefaultTableModel convert() throws TextTransferException {
        ArrayList<double[]> doubles = ConverterUtils.getDoubleListFromString(string);
        DefaultTableModel newDefaultTableModel;

        if (doubles.get(0).length == 1) {
            newDefaultTableModel = setListValuesToFirstColumn(doubles);
        } else {
            newDefaultTableModel = setListValuesToModel(doubles);
        }

        return newDefaultTableModel;
    }

    private DefaultTableModel setListValuesToModel(ArrayList<double[]> list) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(list.size(), CellEditorTableConstants.DEFAULT_COLS_AMOUNT);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < CellEditorTableConstants.DEFAULT_COLS_AMOUNT; j++) {
                defaultTableModel.setValueAt(list.get(i)[j], i, j);
            }
        }
        return defaultTableModel;
    }

    private DefaultTableModel setListValuesToFirstColumn(ArrayList<double[]> list) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(list.size(), CellEditorTableConstants.DEFAULT_COLS_AMOUNT);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < CellEditorTableConstants.DEFAULT_COLS_AMOUNT; j++) {
                if (j == 0) {
                    defaultTableModel.setValueAt(list.get(i)[j], i, j);
                } else {
                    defaultTableModel.setValueAt(0.0, i, j);
                }
            }
        }
        return defaultTableModel;
    }

}

class StringFromIndexConverter implements Converter {
    private String string;
    private int rowIndex;
    private int columnIndex;
    private DefaultTableModel defaultTableModel;

    StringFromIndexConverter(String string, int selectedRow, int selectedColumn, DefaultTableModel newDefaultTableModel) {
        this.string = string;
        this.rowIndex = selectedRow;
        this.columnIndex = selectedColumn;
        this.defaultTableModel = newDefaultTableModel;
    }

    @Override
    public DefaultTableModel convert() throws TextTransferException {
        ArrayList<double[]> doubles = ConverterUtils.getDoubleListFromString(string);
        DefaultTableModel newDefaultTableModel;
        if (doubles.get(0).length == 0) {
            newDefaultTableModel = setListValuesToColumn(doubles, rowIndex, columnIndex, defaultTableModel);
        } else {
            newDefaultTableModel = setListValuesToModel(doubles, rowIndex, defaultTableModel);
        }

        return newDefaultTableModel;
    }

    private DefaultTableModel setListValuesToColumn(ArrayList<double[]> doubles, int rowIndex, int columnIndex, DefaultTableModel givenDefaultTableModel) {
        // todo method works incorrect. Fix it
        int newDefaultTableModelRowsSize;
        if (rowIndex + doubles.size() <= givenDefaultTableModel.getRowCount()) {
            newDefaultTableModelRowsSize = givenDefaultTableModel.getRowCount();
        } else {
            newDefaultTableModelRowsSize = rowIndex + doubles.size();
        }
        DefaultTableModel newDefaultTableModel = new DefaultTableModel(newDefaultTableModelRowsSize,
                CellEditorTableConstants.DEFAULT_COLS_AMOUNT);

        double element;
        for (int i = 0; i < rowIndex; i++) {
            for (int j = 0; j < CellEditorTableConstants.DEFAULT_COLS_AMOUNT; j++) {
                element = Double.parseDouble(givenDefaultTableModel.getValueAt(i, j).toString());
                newDefaultTableModel.setValueAt(element, i, j);
            }
        }

        for (int i = rowIndex, k = 0; i < newDefaultTableModelRowsSize; i++, k++) {
            for (int j = 0; j < CellEditorTableConstants.DEFAULT_COLS_AMOUNT; j++) {
                if (k >= doubles.size()) {
                    element = Double.parseDouble(givenDefaultTableModel.getValueAt(i, j).toString());
                    newDefaultTableModel.setValueAt(element, i, j);
                } else {
                    if (j == columnIndex) {
                        newDefaultTableModel.setValueAt(doubles.get(k)[0], i, j);
                    } else {
                        element = Double.parseDouble(givenDefaultTableModel.getValueAt(i, j).toString());
                        newDefaultTableModel.setValueAt(element, i, j);
                    }
                }
            }
        }
        return newDefaultTableModel;
    }

    private DefaultTableModel setListValuesToModel(ArrayList<double[]> doubles, int rowIndex,
                                                   DefaultTableModel givenDefaultTableModel) {
        int newDefaultTableModelRowsSize;
        if (rowIndex + doubles.size() <= givenDefaultTableModel.getRowCount()) {
            newDefaultTableModelRowsSize = givenDefaultTableModel.getRowCount();
        } else {
            newDefaultTableModelRowsSize = rowIndex + doubles.size();
        }
        DefaultTableModel newDefaultTableModel = new DefaultTableModel(newDefaultTableModelRowsSize,
                CellEditorTableConstants.DEFAULT_COLS_AMOUNT);

        double element;
        for (int i = 0; i < rowIndex; i++) {
            for (int j = 0; j < CellEditorTableConstants.DEFAULT_COLS_AMOUNT; j++) {
                element = Double.parseDouble(givenDefaultTableModel.getValueAt(i, j).toString());
                newDefaultTableModel.setValueAt(element, i, j);
            }
        }
        for (int i = rowIndex, k = 0; i < newDefaultTableModelRowsSize; i++, k++) {
            for (int j = 0; j < CellEditorTableConstants.DEFAULT_COLS_AMOUNT; j++) {
                if (k >= doubles.size()) {
                    element = Double.parseDouble(givenDefaultTableModel.getValueAt(i, j).toString());
                    newDefaultTableModel.setValueAt(element, i, j);
                    if (k == newDefaultTableModelRowsSize) break;
                } else
                    newDefaultTableModel.setValueAt(doubles.get(k)[j], i, j);
            }
        }

        return newDefaultTableModel;
    }
}


class ConverterUtils {
    static ArrayList<double[]> getDoubleListFromString(String string) throws TextTransferException {
        if (string.isEmpty()) {
            throw new WrongDataTypeException("The string is empty");
        }
        ArrayList<double[]> doubleList = new ArrayList<>();
        ArrayList<String[]> stringList = getStringList(string);
        int rowSize;
        for (int i = 0; i < stringList.size(); i++) {
            if (stringList.get(i).length > 2) {
                throw new WrongRowCellAmountException("Too many rows in copied table");
            }
            rowSize = stringList.get(i).length;
            double[] rowDoubleArray = new double[rowSize];
            for (int j = 0; j < rowSize; j++) {
                try {
                    rowDoubleArray[j] = Double.parseDouble(stringList.get(i)[j]);
                } catch (NumberFormatException e) {
                    throw new WrongCellDataTypeException(errorMessage(i, j), e.getCause());
                }
            }
            doubleList.add(rowDoubleArray);
        }
        return doubleList;
    }


    private static String errorMessage(int i, int j) {
        return ("The wrong format in " + Integer.toString(i + 1) + " column and "
                + Integer.toString(j + 1) + " row in copied table");
    }

    private static ArrayList<String[]> getStringList(String string) {
        ArrayList<String[]> list = new ArrayList<>();
        String[] row = string.split(System.lineSeparator());
        for (String element : row) {
            list.add(element.split("\t"));  // because in Excel the separator between neighbour rows is \t
        }
        return list;
    }

}

















