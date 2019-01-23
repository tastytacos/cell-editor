package com.company.editor;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class GraphBuilder {

    public static void displayXYLineGraph(DefaultTableModel tableModel) {
        JFrame graphFrame = new JFrame();
        graphFrame.setBounds(CellEditorTableConstants.x_coordinate, CellEditorTableConstants.y_coordinate, CellEditorTableConstants.graphBuilderWidth,
                CellEditorTableConstants.graphBuilderHeight);
        graphFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });
        ChartPanel chartPanel = new ChartPanel(getXYChart(tableModel));
        graphFrame.add(chartPanel);
        graphFrame.setVisible(true);
    }


    public static JFreeChart getXYChart(DefaultTableModel tableModel) {
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
        String chartTitle = "Visualize Time Series";
        String qAxisLabel = "Q";
        String hAxisLabel = "H";

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, qAxisLabel, hAxisLabel, dataset,
                PlotOrientation.VERTICAL, true, true, false);
        return chart;
    }

}
