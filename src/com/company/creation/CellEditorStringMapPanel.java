package com.company.creation;

import java.util.Map;

public abstract class CellEditorStringMapPanel {
    public abstract Map<String, DoubleCellUnit> getMap();
    public abstract void setMapElement(String key, DoubleCellUnit value);
}
