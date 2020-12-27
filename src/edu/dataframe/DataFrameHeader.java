package edu.dataframe;

import edu.dataframe.column.*;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@SuppressWarnings("rawtypes")
public class DataFrameHeader {

    List<String> header = new ArrayList<>();
    List<Class<? extends DataFrameColumn>> types = new ArrayList<>();
    LinkedHashMap<String, Integer> headerMap = new LinkedHashMap<>();
    LinkedHashMap<String, Class<? extends DataFrameColumn>> typesMap = new LinkedHashMap<>();

    public int size() {
        return header.size();
    }

    private DataFrameHeader add(String name, Class<? extends DataFrameColumn> type) {
        types.add(type);
        header.add(name);
        typesMap.put(name, type);
        headerMap.put(name, header.size()-1);
        return this;
    }

    public DataFrameHeader addHeader(String name, Class<? extends DataFrameColumn> type) throws DataFrameException {
        if (headerMap.containsKey(name))
            throw new DataFrameException("Column " + name + " already exist.");
        return add(name, type);
    }

    //<editor-fold desc="Get Column Class">
    public Class<? extends DataFrameColumn> getColumnClass(int index) throws DataFrameException {
        if (!(index < types.size()))
            throw new DataFrameException("Index " + index + "is out of bounds");
        return types.get(index);
    }

    public Class<? extends DataFrameColumn> getColumnClass(String name) throws DataFrameException {
        if(!typesMap.containsKey(name))
            throw new DataFrameException("Column " + name + " doesn't exist.");
        return typesMap.get(name);
    }
    //</editor-fold>

    //<editor-fold desc="Get Element Class">
    protected Class<?> getElementClass(Class<?extends DataFrameColumn> columnClass) throws DataFrameException {
        if (columnClass == IntegerColumn.class)
            return Integer.class;
        else if (columnClass == DoubleColumn.class)
            return Double.class;
        else if (columnClass == StringColumn.class)
            return String.class;
        throw new DataFrameException("Error checking type");
    }

    public Class<?> getElementClass(String name) throws DataFrameException {
        if(!typesMap.containsKey(name))
            throw new DataFrameException("Column " + name + " doesn't exist.");
        return getElementClass(typesMap.get(name));
    }

    public Class<?> getElementClass(int index) throws DataFrameException {
        if (!(index < types.size()))
            throw new DataFrameException("Index " + index + "is out of bounds");
        return getElementClass(types.get(index));
    }
    //</editor-fold>
}
