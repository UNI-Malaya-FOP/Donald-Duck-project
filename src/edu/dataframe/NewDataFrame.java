package edu.dataframe;

import edu.dataframe.column.*;

import java.util.HashMap;

public class NewDataFrame implements DataFrame {

    int rowNum = 0;
    int columnNum = 0;
    DataFrameHeader header = new DataFrameHeader();
    DataFrameIndices indices = new DataFrameIndices();
    HashMap<String, DataFrameColumn<?>> column = new HashMap<>();

    @Override
    public DataFrame addColumn(DataFrameColumn<?> column) throws DataFrameException {
        if(column.size() != columnNum)
            throw new DataFrameException("");
        if(this.column.containsKey(column.getName()))
            throw new DataFrameException("");
        this.column.put(column.getName(), column);
        header.add(column.getName(), column.getClass());
        return this;
    }

    @Override
    public DataFrame addIntegerColumn(String name) throws DataFrameException {
        if(this.column.containsKey(name))
            throw new DataFrameException("");
        IntegerColumn column = new IntegerColumn(name);
        return addColumn(column);
    }

    @Override
    public DataFrame addFloatColumn(String name) throws DataFrameException {
        if(this.column.containsKey(name))
            throw new DataFrameException("");
        FloatColumn column = new FloatColumn(name);
        return addColumn(column);
    }

    @Override
    public DataFrame addStringColumn(String name) throws DataFrameException {
        if(this.column.containsKey(name))
            throw new DataFrameException("");
        StringColumn column = new StringColumn(name);
        return addColumn(column);
    }
}
