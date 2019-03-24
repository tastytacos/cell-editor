package com.company.language_tools;

import com.company.language_tools.languageble.LanguageChangeable;
import org.jfree.chart.ChartPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LanguageManager {

    private List<LanguageChangeable> elements = new ArrayList<>();

    public void subscribeElement(LanguageChangeable element) {
        elements.add(element);
    }

    public void subscribeElement(LanguageChangeable ... args){
        elements.addAll(Arrays.asList(args));
    }

    public void unsubscribeElement(LanguageChangeable element) {
        elements.remove(element);
    }

    public void changeLanguage(String baseName, Locale locale) {
        for (LanguageChangeable element : elements) {
            element.changeLanguage(baseName, locale);
        }
    }
}
