package edu.dataframe;

import edu.dataframe.column.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class NewDataFrame implements DataFrame, Iterable<DataFrameRow> {

    int rowNum = 0;
    int columnNum = 0;
    private final DataFrameHeader header = new DataFrameHeader();
    private final DataFrameIndices indices = new DataFrameIndices();
    private final ArrayList<String> names = new ArrayList<>();
    private final HashMap<String, DataFrameColumn<?>> columns = new HashMap<>();

    @Override
    public int getRowNumber() {
        return rowNum;
    }

    @Override
    public int getColumnNumber() {
        return columnNum;
    }

    public DataFrameHeader getHeader() {
        return header;
    }

    @SuppressWarnings("rawtypes")
    private DataFrameColumn getColumn(String name) throws DataFrameException {
        if (!columns.containsKey(name))
            throw new DataFrameException("Column " + name + " does not exist.");
        return columns.get(name);
    }

    protected DataFrameColumn<?> getColumn(int index) throws DataFrameException {
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
                    "Column size (%d) does not match for (%d).",
                    column.size(), rowNum));
        if(columns.containsKey(column.getName()))
            throw new DataFrameException("Column " + column.getName() + " already exist.");
        indices.copyTo(column);
        names.add(column.getName());
        columns.put(column.getName(), column);
        header.add(column.getName(), column.getClass());
        columnNum++;
        return this;
    }

    @Override
    public DataFrame addIntegerColumn(String name) throws DataFrameException {
        IntegerColumn column = new IntegerColumn(name, this);
        return addColumn(column);
    }

    @Override
    public DataFrame addFloatColumn(String name) throws DataFrameException {
        FloatColumn column = new FloatColumn(name, this);
        return addColumn(column);
    }

    @Override
    public DataFrame addStringColumn(String name) throws DataFrameException {
        StringColumn column = new StringColumn(name, this);
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

    public DataFrame appendRow(DataFrameRow row) throws DataFrameException {
        append(row.toArray());
        return this;
    }

    private DataFrame deleteRow(int index) throws DataFrameException {
        if (index > rowNum)
            throw new DataFrameException(
                    "Index: %d is out of bounds for row number: %d."
                            .formatted(index, rowNum));
        for (String name : names)
            getColumn(name).remove(index);
        indices.remove(index);
        rowNum--;
        return this;
    }

    @Override
    public DataFrame deleteRowByIndex(int index) throws DataFrameException {
        if (!indices.contains(index))
            throw new DataFrameException("Index: %d does not exist.".formatted(index));
        return deleteRow(indices.indexOf(index));
    }

    @Override
    public DataFrame deleteFirstRow(String name, Object element) throws DataFrameException {
        int index = getColumn(name).indexOf(element);
        if (index == -1)
            throw new DataFrameException("Element (%s) does not exist".formatted(element));
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
    public DataFrame dropNull(String... names) throws DataFrameException {
        for (String name : names)
            deleteAllRow(name, null);
        return this;
    }

    @Override
    public DataFrame dropDuplicate(String... names) throws DataFrameException {
        for (String name : names) {
            DataFrameColumn<?> column = getColumn(name);
            Object element = column.getFirstDuplicate();
            deleteAllRow(name, element);
        }
        return this;
    }

    @Override
    public DataFrame dropDuplicate(String name, int keep) throws DataFrameException {
        try {
            DataFrameColumn<?> column = getColumn(name);
            Object[] indices = column.getFirstDuplicateIndices().toArray();
            for (int i = indices.length - 1; i >= 0; i--) {
                if (i != keep)
                    deleteRow((Integer) indices[i]);
            }
            return this;
        } catch (NullPointerException e) {
            throw new DataFrameException("Column (%s) does not contain duplicate.".formatted(name));
        }
    }

    @Override
    public DataFrame dropDuplicate(String[] names, int keep) throws DataFrameException {
        for (String name : names)
            dropDuplicate(name, keep);
        return this;
    }

    @Override
    public DataFrame dropDuplicate(String name, String keep) throws DataFrameException {
        DataFrameColumn<?> column = getColumn(name);
        int length = column.getFirstDuplicateIndices().size();
        if (keep.equalsIgnoreCase("first"))
            dropDuplicate(name, 0);
        else if (keep.equalsIgnoreCase("last"))
            dropDuplicate(name, length - 1);
        else
            throw new DataFrameException("Keep %s is not valid for first or last.".formatted(keep));
        return this;
    }

    @Override
    public DataFrame dropDuplicate(String[] names, String keep) throws DataFrameException {
        for (String name : names)
            dropDuplicate(name, keep);
        return this;
    }

    @Override
    public DataFrame concatX(DataFrame dataFrame) throws DataFrameException {
        NewDataFrame newDataFrame;
        if (dataFrame instanceof NewDataFrame)
            newDataFrame = (NewDataFrame) dataFrame;
        else
            throw new DataFrameException("DataFrame does not created properly.");
        Set<String> newNames = newDataFrame.header.getNames();
        for (String name : names) {
            if (newNames.contains(name))
                throw new DataFrameException("Column name (%s) is duplicated.".formatted(name));
        }
        for (String name: newDataFrame.header) {
            this.addColumn(newDataFrame.getColumn(name));
        }
        return this;
    }

    @Override
    public DataFrame concatY(DataFrame dataFrame) throws DataFrameException {
        NewDataFrame newDataFrame;
        if (dataFrame instanceof NewDataFrame)
            newDataFrame = (NewDataFrame) dataFrame;
        else
            throw new DataFrameException("DataFrame does not created properly.");
        Set<String> newNames = newDataFrame.header.getNames();
        for (String name : names) {
            if (!newNames.contains(name))
                throw new DataFrameException("Column name (%s) does not exist in DataFrame to concat.".formatted(name));
        }
        DataFrameHeader header = newDataFrame.header;
        for (String name: newDataFrame.header) {
            if (header.getColumnClass(name) != this.header.getColumnClass(name))
                throw new DataFrameException("Column class (%s) does not match (%s).".formatted
                        (header.getColumnClass(name).getSimpleName(), this.header.getColumnClass(name).getSimpleName()));
        }
        for (DataFrameRow row : newDataFrame) {
            this.appendRow(row);
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

    @Override
    public Iterator<DataFrameRow> iterator() {
        return new DataFrameIterator(this);
    }
}
