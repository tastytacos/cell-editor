package com.company.language_tools.languageble;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Use this class if you are providing internationalization. If not, use {@link JFrame}
 */
public class LanguageFrame extends JFrame implements LanguageChangeable {
    private String titleKey;


    public LanguageFrame(String titleKey) {
        this.titleKey = titleKey;
    }


    @Override
    public void changeLanguage(String baseName, Locale locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
        setTitle(resourceBundle.getString(titleKey));
    }
}
