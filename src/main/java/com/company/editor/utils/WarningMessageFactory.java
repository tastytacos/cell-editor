package com.company.editor.utils;

import java.io.StringReader;
import java.util.ResourceBundle;

public class WarningMessageFactory {


    public static String getWarningFromRowColsMessage(int rowNumber, int colsNumber, ResourceBundle resourceBundle) {
        return resourceBundle.getString("language_warning_message") + System.lineSeparator() +
                resourceBundle.getString("language_wrong_data_message1") +
                " " + rowNumber + " " +
                resourceBundle.getString("language_wrong_data_message2") +
                " " + colsNumber + " " +
                resourceBundle.getString("language_wrong_data_message3");
    }

    public static String getDeletingImpossibleMessage(ResourceBundle resourceBundle){
        return resourceBundle.getString("language_empty_table_message") +
                System.lineSeparator() +
                resourceBundle.getString("language_impossible_delete_message");
    }

    public static String getFileNotFoundMessage(ResourceBundle resourceBundle){
        return resourceBundle.getString("language_file_not_found_message");
    }

    public static String getDamagedFileMessage(ResourceBundle resourceBundle){
        return resourceBundle.getString("language_damaged_file_message");
    }


}
