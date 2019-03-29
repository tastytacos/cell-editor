package com.company.language_tools.languageble;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageChartPanel extends ChartPanel implements LanguageChangeable{
    private String titleKey;
    public LanguageChartPanel(JFreeChart chart, String titleKey) {
        super(chart);
        this.titleKey = titleKey;
    }

    @Override
    public void changeLanguage(String baseName, Locale locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
        getChart().setTitle(resourceBundle.getString(titleKey));
    }

    public LanguageChartPanel(JFreeChart chart) {
        super(chart);
    }

    public LanguageChartPanel(JFreeChart chart, boolean useBuffer) {
        super(chart, useBuffer);
    }

    public LanguageChartPanel(JFreeChart chart, boolean properties, boolean save, boolean print, boolean zoom, boolean tooltips) {
        super(chart, properties, save, print, zoom, tooltips);
    }

    public LanguageChartPanel(JFreeChart chart, int width, int height, int minimumDrawWidth, int minimumDrawHeight, int maximumDrawWidth, int maximumDrawHeight, boolean useBuffer, boolean properties, boolean save, boolean print, boolean zoom, boolean tooltips) {
        super(chart, width, height, minimumDrawWidth, minimumDrawHeight, maximumDrawWidth, maximumDrawHeight, useBuffer, properties, save, print, zoom, tooltips);
    }

    public LanguageChartPanel(JFreeChart chart, int width, int height, int minimumDrawWidth, int minimumDrawHeight, int maximumDrawWidth, int maximumDrawHeight, boolean useBuffer, boolean properties, boolean copy, boolean save, boolean print, boolean zoom, boolean tooltips) {
        super(chart, width, height, minimumDrawWidth, minimumDrawHeight, maximumDrawWidth, maximumDrawHeight, useBuffer, properties, copy, save, print, zoom, tooltips);
    }
}
