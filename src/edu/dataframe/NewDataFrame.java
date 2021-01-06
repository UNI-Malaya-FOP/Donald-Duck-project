package edu.dataframe;

import edu.dataframe.column.*;

import java.util.*;

public class NewDataFrame implements DataFrame, Iterable<DataFrameRow> {

    private int rowNum = 0, columnNum = 0;
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

    @Override
    public DataFrameHeader getHeader() {
        return header;
    }

    //<editor-fold desc="Get column">
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
                "(%s) is not valid for geIntegerColumn.",
                column.getClass().getSimpleName()));
    }

    @Override
    public FloatColumn getFloatColumn(String name) throws DataFrameException {
        var column = getColumn(name);
        if(column instanceof FloatColumn)
            return (FloatColumn) column;
        throw new DataFrameException(String.format(
                "(%s) is not valid for getFloatColumn.",
                column.getClass().getSimpleName()));
    }

    @Override
    public StringColumn getStringColumn(String name) throws DataFrameException {
        var column = getColumn(name);
        if(column instanceof StringColumn)
            return (StringColumn) column;
        throw new DataFrameException(String.format(
                "(%s) is not valid for getStringColumn.",
                column.getClass().getSimpleName()));
    }
    //</editor-fold>

    //<editor-fold desc="Add column">
    @Override
    public DataFrame addColumn(DataFrameColumn<?> column) throws DataFrameException {
        if (columnNum == 0) {
            column.getIndices().copyTo(indices);
            rowNum = column.size();
        }
        if(column.size() != rowNum)
            throw new DataFrameException(String.format(
                    "Column size (%d) does not match for (%d) row.",
                    column.size(), rowNum));
        if(columns.containsKey(column.getName()))
            throw new DataFrameException("Column (" + column.getName() + ") already exist.");
        column.reDataFrame(this);
        indices.copyTo(column);
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
    //</editor-fold>

    //<editor-fold desc="Delete column">
    private DataFrame deleteColumn(int index) throws DataFrameException {
        if(index > columnNum)
            throw new DataFrameException("Index (%d) out of bounds for (%d) column.".formatted(index, columnNum));
        String name = names.get(index);
        return deleteColumn(name);
    }

    @Override
    public DataFrame deleteColumn(String name) throws DataFrameException {
        if (!header.getNames().contains(name))
            throw new DataFrameException("Column (%s) does not exist.".formatted(name));
        columns.remove(name);
        header.remove(name);
        names.remove(name);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Get Row">
    @Override
    public DataFrameRow getRow(int position) throws DataFrameException {
        if (position > rowNum)
            throw new DataFrameException("Index (%d) of of bounds for (%d) row.".formatted(position, rowNum));
        return new DataFrameRow(this, position);
    }

    @Override
    public DataFrameRow getRowByIndex(int index) throws DataFrameException {
        int position = indices.indexOf(index);
        if (position == -1)
            throw new DataFrameException("Index (%d) does not exist".formatted(index));
        return getRow(position);
    }
    //</editor-fold>

    //<editor-fold desc="Append row">
    @SuppressWarnings("unchecked")
    @Override
    public DataFrame append(Object... elements) throws DataFrameException {
        if (elements.length != columnNum)
            throw new DataFrameException(String.format(
                    "Element length of (%d) does not match for (%d) column.",
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

    @Override
    public DataFrame appendRows(List<DataFrameRow> rows) throws DataFrameException {
        for (DataFrameRow row : rows)
            appendRow(row);
        return this;
    }

    //</editor-fold>

    //<editor-fold desc="Delete row">
    private DataFrame deleteRow(int index) throws DataFrameException {
        if (index > rowNum)
            throw new DataFrameException(
                    "Index (%d) is out of bounds for row number (%d)."
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
            throw new DataFrameException("Index (%d) does not exist.".formatted(index));
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
    //</editor-fold>

    //<editor-fold desc="Drop null">
    @Override
    public DataFrame dropNull(String... names) throws DataFrameException {
        for (String name : names)
            deleteAllRow(name, null);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Drop duplicate">
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
            throw new DataFrameException("Keep (%s) is not valid for (first) or (last).".formatted(keep));
        return this;
    }

    @Override
    public DataFrame dropDuplicate(String[] names, String keep) throws DataFrameException {
        for (String name : names)
            dropDuplicate(name, keep);
        return this;
    }
    //</editor-fold>

    @SuppressWarnings("unchecked")
    private DataFrame replace(String name, int index, Object element) throws DataFrameException {
        getColumn(name).replace(index, (Comparable<?>) element);
        return this;
    }

    @Override
    public DataFrame replaceByIndex(String name, int index, Object element) throws DataFrameException {
        int position = indices.indexOf(index);
        if (position == -1)
            throw new DataFrameException("Index %d does not exist".formatted(index));
        return replace(name, position, element);
    }

    @Override
    public DataFrame replaceNull(String name, Object element) throws DataFrameException {
        int position = getColumn(name).indexOf(null);
        if (position == -1)
            throw new DataFrameException("Null does not exist");
        return replace(name, position, element);
    }

    //<editor-fold desc="Concatenation">
    @Override
    public DataFrame concatX(DataFrame dataFrame) throws DataFrameException {
        final NewDataFrame newDataFrame;
        if (dataFrame instanceof NewDataFrame)
            newDataFrame = (NewDataFrame) dataFrame;
        else
            throw new DataFrameException("DataFrame does not created properly.");
        Set<String> newNames = newDataFrame.header.getNames();
        for (String name : names) {
            if (newNames.contains(name))
                throw new DataFrameException("Column name (%s) is duplicated.".formatted(name));
        }
        for (String name: newDataFrame.header)
            this.addColumn(newDataFrame.getColumn(name));

        return this;
    }

    @Override
    public DataFrame concatY(DataFrame dataFrame) throws DataFrameException {
        final NewDataFrame newDataFrame;
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
                        (header.getColumnClass(name).getSimpleName(),
                        this.header.getColumnClass(name).getSimpleName()));
        }
        for (DataFrameRow row : newDataFrame)
            this.appendRow(row);

        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Sorting">
    @SuppressWarnings("unchecked")
    @Override
    public DataFrame sortBy(String name, Comparator<?> comparator) throws DataFrameException {
        getColumn(name).sort(comparator);
        return this;
    }

    @Override
    public DataFrame sortBy(String name) throws DataFrameException {
        return sortBy(name, Comparator.naturalOrder());
    }

    @Override
    public DataFrame sortBy(String name, Boolean ascending) throws DataFrameException {
        if (ascending)
            return sortBy(name);
        else
            return sortBy(name, Collections.reverseOrder());
    }
    //</editor-fold>

    protected void getColumnChange(DataFrameColumn<?> changedColumn, List<Integer> howChange) {
        indices.sortBy(howChange);
        rowNum = indices.size();
        for (String name: names) {
            DataFrameColumn<?> column = columns.get(name);
            if (column != changedColumn)
                column.sortBy(howChange);
        }
    }

    @Override
    public DataFrame resetIndices() {
        indices.resetIndices();
        for (String name : names) {
            columns.get(name).resetIndices();
        }
        return this;
    }

    @Override
    public void print() throws DataFrameException {
        for (String name : names)
            System.out.print("\t" + name);
        for (int i = 0; i < rowNum; i++) {
            System.out.println();
            System.out.print(indices.get(i) + "\t" + getRow(i));
        }
        System.out.println();
    }

    @Override
    public void printDebug() {
        try {
            for (String name : names)
                System.out.println(getColumn(name));
        } catch ( DataFrameException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(indices);
    }

    @Override
    public Iterator<DataFrameRow> iterator() {
        return new DataFrameIterator(this);
    }
}
