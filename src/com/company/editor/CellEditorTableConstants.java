package com.company.editor;
import java.awt.*;
import java.io.File;

class CellEditorTableConstants {

         static final String PASTE_BUTTON_COMMAND = "paste";
    // the screen constants
        private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        static final int height = (int)(screenSize.height * 0.6);
        static final int width = (int)(screenSize.width * 0.7);
        static final int graphBuilderHeight = (int) (screenSize.height * 0.6);
        static final int graphBuilderWidth = (int)(screenSize.width * 0.4);
        static final int x_coordinate = 100;
        static final int y_coordinate = 200;

        // data constants
        // for Table class only
        static final String[] NAME_COLUMNS = {
                "Q",
                "H"
        };
        static final int DEFAULT_COLS_AMOUNT = 2;
        static final int DEFAULT_ROWS_AMOUNT = 3;
        static final int DEFAULT_FONT_SIZE = 16;
        // The PATH constants
        // used to open the explorer in the appropriate path
        static final String CURRENT_DIRECTORY_PATH = System.getProperty("user.dir") + File.separator + "res";
        //the buttons constants
        // used in multiply classes
        static final String SAVE_BUTTON_COMMAND = "save";
        static final String SAVE_AS_BUTTON_COMMAND = "save as";
        static final String LOAD_BUTTON_COMMAND = "load";
        static final String BUILD_XY_GRAPH = "build graph";
        // for Table class only
        static final String ADD_ROW_BUTTON_COMMAND = "add_row";
        static final String DELETE_ROW_BUTTON_COMMAND = "delete_row";
        // formats
        static final String XLS_FORMAT = ".xls";
        static final String XLSX_FORMAT = ".xlsx";
        static final String TXT_FORMAT = ".txt";
        //names
        static final String DEFAULT_NAME = " New Table";
        static final String QH_EDITOR = " - Cell-Editor";
}
