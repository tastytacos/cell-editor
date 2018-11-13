package com.company.factory;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface DoubleCellUnit {
    public DefaultTableModel getUnitDefaultTableModel();
    public void setUnitFirstValue(List<Double> firstList);
    public List<Double> getUnitFirstValue();
    public void setUnitSecondValue(List<Double> secondList);
    public List<Double> getUnitSecondValue();
    public void setUnitTableModel(DefaultTableModel defaultTableModel);
}
