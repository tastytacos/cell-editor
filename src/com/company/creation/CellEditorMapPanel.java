package com.company.creation;

import java.util.Map;

public abstract class CellEditorMapPanel extends CellEditorPanel{
    public abstract Map<Object, DoubleCellUnit> getMap();
    public abstract void setMapElement(Object key, DoubleCellUnit value);
}

