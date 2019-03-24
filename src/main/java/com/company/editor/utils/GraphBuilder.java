package com.company.editor.utils;

import com.company.editor.CellEditorTableConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GraphBuilder {

    public static void displayXYLineGraph(DefaultTableModel tableModel, Rectangle windowParams, String chartTitle,
                                          String qAxisLabel, String hAxisLAbel) {
        JFrame graphFrame = new JFrame();
//        graphFrame.setBounds(CellEditorTableConstants.x_coordinate, CellEditorTableConstants.y_coordinate, CellEditorTableConstants.graphBuilderWidth, CellEditorTableConstants.graphBuilderHeight);
        graphFrame.setBounds(windowParams);
        graphFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });
        ChartPanel chartPanel = new ChartPanel(getXYChart(tableModel, chartTitle, qAxisLabel, hAxisLAbel));
        graphFrame.add(chartPanel);
        graphFrame.setVisible(true);
    }


    public static JFreeChart getXYChart(DefaultTableModel tableModel, String chartTitle, String xAxisLabel, String yAxisLabel) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries tsSeries = new XYSeries("Time Series");
        double x = 0, z = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++){
            for (int j = 0; j < tableModel.getColumnCount() - 1; j++){
                x = Double.parseDouble(tableModel.getValueAt(i, j).toString());
                z = Double.parseDouble(tableModel.getValueAt(i, j + 1).toString());
            }
            tsSeries.add(x, z);
        }
        dataset.addSeries(tsSeries);
//        String chartTitle = "Visualize Time Series";
//        String xAxisLabel = "Q";
//        String yAxisLabel = "H";

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset,
                PlotOrientation.VERTICAL, true, true, false);
        return chart;
    }

}
