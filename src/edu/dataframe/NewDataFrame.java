package edu.dataframe;

import edu.dataframe.column.*;

import java.util.ArrayList;
import java.util.HashMap;

public class NewDataFrame implements DataFrame {

    int rowNum = 0;
    int columnNum = 0;
    DataFrameHeader header = new DataFrameHeader();
    DataFrameIndices indices = new DataFrameIndices();
    ArrayList<String> names = new ArrayList<>();
    HashMap<String, DataFrameColumn<?>> columns = new HashMap<>();

    public DataFrameHeader getHeader() {
        return header;
    }

    @SuppressWarnings("rawtypes")
    private DataFrameColumn getColumn(String name) throws DataFrameException {
        if (!columns.containsKey(name))
            throw new DataFrameException("Column " + name + " does not exist.");
        return columns.get(name);
    }

    private DataFrameColumn<?> getColumn(int index) throws DataFrameException {
        return getColumn(names.get(index));
    }

    @Override
    public IntegerColumn getIntegerColumn(String name) throws DataFrameException {
        var column = getColumn(name);
        if(column instanceof IntegerColumn)
            return (IntegerColumn) column;
        throw new DataFrameException(String.format(
                "%s is not valid for geIntegerColumn.",
                column.getClass().getSimpleName()));
    }

    @Override
    public FloatColumn getFloatColumn(String name) throws DataFrameException {
        var column = getColumn(name);
        if(column instanceof FloatColumn)
            return (FloatColumn) column;
        throw new DataFrameException(String.format(
                "%s is not valid for getFloatColumn.",
                column.getClass().getSimpleName()));
    }

    @Override
    public StringColumn getStringColumn(String name) throws DataFrameException {
        var column = getColumn(name);
        if(column instanceof StringColumn)
            return (StringColumn) column;
        throw new DataFrameException(String.format(
                "%s is not valid for getStringColumn.",
                column.getClass().getSimpleName()));
    }

    @Override
    public DataFrame addColumn(DataFrameColumn<?> column) throws DataFrameException {
        if(column.size() != rowNum)
            throw new DataFrameException(String.format(
                    "Column size: %d does not match for row number: %d.",
                    column.size(), rowNum));
        if(columns.containsKey(column.getName()))
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
                var column = columnClass.cast(getColumn(i));
                var element = elementClass.cast(elements[i]);
                column.append(element);
            } catch (ClassCastException e) {
                throw new DataFrameException(String.format(
                        "Type %s does not match for %s.",
                        elements[i].getClass().getName(),
                        elementClass.getName()));
            }
        }
        indices.addIndex();
        rowNum++;
        return this;
    }

    private DataFrame deleteRow(int index) throws DataFrameException {
        if (index > rowNum)
            throw new DataFrameException(String.format("Index: %d is out of bounds for row number: %d.", index, rowNum));
        for (String name : names)
            getColumn(name).remove(index);
        rowNum--;
        return this;
    }

    public DataFrame deleteRowByIndex(int index) throws DataFrameException {
        if (!indices.contains(index))
            throw new DataFrameException(String.format("Index: %d does not exist.", index));
        return deleteRow(indices.indexOf(index));
    }

    @Override
    public DataFrame deleteFirstRow(String name, Object element) throws DataFrameException {
        int index = getColumn(name).indexOf(element);
        if (index == -1)
            throw new DataFrameException("Element " + element + " does not exist");
        return deleteRow(index);
    }

    @Override
    public DataFrame deleteAllRow(String name, Object element) throws DataFrameException {
        deleteFirstRow(name,element);
        DataFrameColumn<?> column = getColumn(name);
        int index = column.indexOf(element);
        while (index != -1) {
            deleteRow(index);
            index = column.indexOf(element);
        }
        return this;
    }

    @Override
    public void printDebug() {
        try {
            for (String name : names)
                System.out.println(getColumn(name));
        } catch ( DataFrameException e) {
            System.out.println(e.getMessage());
        }
    }
}
