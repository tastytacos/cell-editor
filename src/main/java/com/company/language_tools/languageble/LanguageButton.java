package com.company.language_tools.languageble;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageButton extends JButton implements LanguageChangeable {
    private String key;

    public LanguageButton(String key) {
        this.key = key;
    }

    @Override
    public void changeLanguage(String baseName, Locale locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
        setText(resourceBundle.getString(key));
    }
}
