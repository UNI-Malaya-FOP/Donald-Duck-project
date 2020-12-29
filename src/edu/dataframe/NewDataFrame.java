package edu.dataframe;

import edu.dataframe.column.*;

import java.util.ArrayList;
import java.util.HashMap;

public class NewDataFrame implements DataFrame {

    int rowNum = 0;
    int columnNum = 0;
    DataFrameHeader header = new DataFrameHeader();
    DataFrameIndices indices = new DataFrameIndices();
    ArrayList<DataFrameColumn<?>> columns = new ArrayList<>();
    HashMap<String, DataFrameColumn<?>> columnsMap = new HashMap<>();

    @Override
    public DataFrame addColumn(DataFrameColumn<?> column) throws DataFrameException {
        if(column.size() != rowNum)
            throw new DataFrameException("");
        if(this.columnsMap.containsKey(column.getName()))
            throw new DataFrameException("");
        columns.add(column);
        columnsMap.put(column.getName(), column);
        header.add(column.getName(), column.getClass());
        columnNum++;
        return this;
    }

    @Override
    public DataFrame addIntegerColumn(String name) throws DataFrameException {
        if(this.columnsMap.containsKey(name))
            throw new DataFrameException("");
        IntegerColumn column = new IntegerColumn(name);
        return addColumn(column);
    }

    @Override
    public DataFrame addFloatColumn(String name) throws DataFrameException {
        if(this.columnsMap.containsKey(name))
            throw new DataFrameException("");
        FloatColumn column = new FloatColumn(name);
        return addColumn(column);
    }

    @Override
    public DataFrame addStringColumn(String name) throws DataFrameException {
        if(this.columnsMap.containsKey(name))
            throw new DataFrameException("");
        StringColumn column = new StringColumn(name);
        return addColumn(column);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataFrame append(Object... elements) throws DataFrameException {
        if (elements.length != columnNum)
            throw new DataFrameException("");
        for (int i = 0; i < elements.length; i++) {
            try {
                var element = header.getTypeClass(i).cast(elements[i]);
                var column = header.getColumnClass(i).cast(columns.get(i));
                column.append(element);
            } catch (ClassCastException e) {
                throw new DataFrameException("");
            }
        }
        return this;
    }


















}
