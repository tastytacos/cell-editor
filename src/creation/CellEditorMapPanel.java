package creation;

import java.util.Map;

public abstract class CellEditorMapPanel extends CellEditorPanel{
    public abstract Map<Object, IUnit> getMap();
    public abstract void setMapElement(Object key, IUnit value);
}
