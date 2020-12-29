package edu.dataframe;

import edu.dataframe.column.*;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("rawtypes")
public class DataFrameHeader {

    ArrayList<String> names = new ArrayList<>();
    HashMap<String, Class<? extends DataFrameColumn>> columnClassMap = new HashMap<>();
    HashMap<String, Class<? extends Comparable<?>>> typeClassMap = new HashMap<>();

    public void add(String name, Class<? extends DataFrameColumn> columnClass) {
        names.add(name);
        columnClassMap.put(name, columnClass);
        typeClassMap.put(name, getTyeClass(columnClass));
    }

    public Class<? extends DataFrameColumn> getColumnClass (String name) {
        return columnClassMap.get(name);
    }

    public Class<? extends DataFrameColumn> getColumnClass (Integer index) {
        return columnClassMap.get(names.get(index));
    }

    private Class<? extends Comparable<?>> getTyeClass(Class<? extends DataFrameColumn> columnClass) {
        if (columnClass == IntegerColumn.class)
            return Integer.class;
        if (columnClass == FloatColumn.class)
            return Float.class;
        if (columnClass == StringColumn.class)
            return String.class;
        return null;
    }

    public Class<?> getTypeClass(String name) {
        return typeClassMap.get(name);
    }

    public Class<? extends Comparable<?>> getTypeClass(Integer index) {
        return typeClassMap.get(names.get(index));
    }
}
