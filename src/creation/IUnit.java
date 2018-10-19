package creation;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IUnit {
    public DefaultTableModel getUnitDefaultTableModel();
    public void setUnitFirstValue(List<Double> firstList);
    public void setUnitSecondValue(List<Double> secondList);
    public void setUnitTableModel(DefaultTableModel defaultTableModel);
}
