package com.company.language_tools.languageble;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Use this class if you are providing internationalization. If not, use {@link JLabel}
 */
public class LanguageLabel extends JLabel implements LanguageChangeable {
    private String key; // key must be the same as in your .properties file

    /**
     *  key must be the same as the appropriate value in your .properties file
     */
    public LanguageLabel(String propertyKey) {
        this.key = propertyKey;
    }

    @Override
    public void changeLanguage(String baseName, Locale locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
        setText(resourceBundle.getString(key));
    }
}
