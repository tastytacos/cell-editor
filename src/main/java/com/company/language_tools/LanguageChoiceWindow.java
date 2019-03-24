package com.company.language_tools;

import com.company.language_tools.languageble.LanguageChangeable;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageChoiceWindow {

    public static String displayWindow(JFrame frame, String[] languages, ResourceBundle resourceBundle, String windowTitleKey,
                                       String windowMessageKey) {
        String chosenLang;
        chosenLang = (String) JOptionPane.showInputDialog(
                frame,
                resourceBundle.getString(windowTitleKey),
                resourceBundle.getString(windowMessageKey),
                JOptionPane.PLAIN_MESSAGE,null,
                languages,
                languages[0]);
        //If a string was returned, say so.
        if ((chosenLang != null) && (chosenLang.length() > 0)) {
            return chosenLang;
        }
        return chosenLang;
    }

    public static String displayWindow(JFrame frame, String[] languages){
        String chosenLang;
        chosenLang = (String) JOptionPane.showInputDialog(
                frame,
                "Localization",
                "Choose the language",
                JOptionPane.PLAIN_MESSAGE,null,
                languages,
                languages[0]);
        //If a string was returned, say so.
        if ((chosenLang != null) && (chosenLang.length() > 0)) {
            return chosenLang;
        }
        return chosenLang;
    }

}
