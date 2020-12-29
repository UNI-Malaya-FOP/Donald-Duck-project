package edu.dataframe;

import edu.dataframe.column.*;

import java.util.ArrayList;
import java.util.HashMap;

public class NewDataFrame implements DataFrame {

    int rowNum = 0;
    int columnNum = 0;
    int customIndex = 1;
    DataFrameHeader header = new DataFrameHeader();
    DataFrameIndices indices = new DataFrameIndices();
    ArrayList<String> names = new ArrayList<>();
    HashMap<String, DataFrameColumn<?>> columns = new HashMap<>();

    @Override
    public DataFrame addColumn(DataFrameColumn<?> column) throws DataFrameException {
        if(column.size() != rowNum)
            throw new DataFrameException(String.format(
                    "Column size: %d does not match for row number: %d.",
                    column.size(),
                    rowNum));
        if(this.columns.containsKey(column.getName()))
            throw new DataFrameException("Column " + column.getName() + " already exist.");
        names.add(column.getName());
        columns.put(column.getName(), column);
        header.add(column.getName(), column.getClass());
        columnNum++;
        return this;
    }

    @Override
    public DataFrame addIntegerColumn(String name) throws DataFrameException {
        IntegerColumn column = new IntegerColumn(name);
        return addColumn(column);
    }

    @Override
    public DataFrame addFloatColumn(String name) throws DataFrameException {
        FloatColumn column = new FloatColumn(name);
        return addColumn(column);
    }

    @Override
    public DataFrame addStringColumn(String name) throws DataFrameException {
        StringColumn column = new StringColumn(name);
        return addColumn(column);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataFrame append(Object... elements) throws DataFrameException {
        if (elements.length != columnNum)
            throw new DataFrameException(String.format(
                    "Element length of %d does not match for length %d.",
                    elements.length, columnNum));
        for (int i = 0; i < elements.length; i++) {
            var columnClass = header.getColumnClass(i);
            var elementClass = header.getTypeClass(i);
            try {
                String name = names.get(i);
                var column = columnClass.cast(columns.get(name));
                var element = elementClass.cast(elements[i]);
                column.append(element);
            } catch (ClassCastException e) {
                throw new DataFrameException(String.format(
                        "Type %s does not match for %s.",
                        elements[i].getClass().getName(),
                        elementClass.getName()));
            }
        }
        indices.add(customIndex++);
        rowNum++;
        return this;
    }

    private DataFrame deleteRow(int index) throws DataFrameException {
        if (index > rowNum)
            throw new DataFrameException(String.format("Index: %d is out of bounds for row number: %d.", index, rowNum));
        names.forEach(name -> columns.get(name).remove(index));
        rowNum--;
        return this;
    }


    public DataFrame deleteRowByIndex(int index) throws DataFrameException {
        if (!indices.contains(index))
            throw new DataFrameException(String.format("Index: %d does not exist.", index));
        return deleteRow(indices.indexOf(index));
    }

    @Override
    public void printDebug() {
        names.forEach(name -> System.out.println(columns.get(name)));
    }
}
