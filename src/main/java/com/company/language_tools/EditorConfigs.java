package com.company.language_tools;

/**
 * This class contains all information about the name of the title of the graph, rows of editor and graph's axises.
 * It is created in order to decrease the parameters of constructor of the {@link com.company.editor.CellEditor} class
 */
public class EditorConfigs {
    private String[] rowsName;
    private String graphTitle;
    private String[] graphAxis;

    public EditorConfigs() {
    }

    public EditorConfigs(String[] rowsName, String graphTitle, String[] graphAxis) {
        this.rowsName = rowsName;
        this.graphTitle = graphTitle;
        this.graphAxis = graphAxis;
    }

    public String[] getRowsName() {
        return rowsName;
    }

    public void setRowsName(String[] rowsName) {
        this.rowsName = rowsName;
    }

    public String getGraphTitle() {
        return graphTitle;
    }

    public void setGraphTitle(String graphTitle) {
        this.graphTitle = graphTitle;
    }

    public String[] getGraphAxis() {
        return graphAxis;
    }

    public void setGraphAxis(String[] graphAxis) {
        this.graphAxis = graphAxis;
    }

}
