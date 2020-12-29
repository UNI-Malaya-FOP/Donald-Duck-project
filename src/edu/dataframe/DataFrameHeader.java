package edu.dataframe;

import edu.dataframe.column.*;

import java.util.HashMap;

@SuppressWarnings("rawtypes")
public class DataFrameHeader {

    HashMap<String, Class<? extends DataFrameColumn>> columnClassMap = new HashMap<>();
    HashMap<String, Class<? extends Comparable<?>>> typeClassMap = new HashMap<>();

    public void add(String name, Class<? extends DataFrameColumn> columnClass) {
        columnClassMap.put(name, columnClass);
        typeClassMap.put(name, getTyeClass(columnClass));
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
}
