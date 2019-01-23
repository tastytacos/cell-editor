package com.company.factory;

import java.util.Map;

public abstract class CellEditorMapPanel<Key> extends CellEditorPanel{
    public abstract Map<Key, DoubleCellUnit> getMap();
    public abstract void setMapElement(Key key, DoubleCellUnit value);
}

