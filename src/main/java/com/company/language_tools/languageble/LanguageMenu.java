package com.company.language_tools.languageble;

import javax.swing.*;
import java.lang.annotation.Native;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageMenu extends JMenu implements LanguageChangeable{
    private String key;

    public LanguageMenu(String key){
        this.key = key;
    }

    @Override
    public void changeLanguage(String baseName, Locale locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
        setText(resourceBundle.getString(key));
    }
}
