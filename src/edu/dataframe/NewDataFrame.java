package edu.dataframe;

import edu.dataframe.column.*;

import java.util.Arrays;
import java.util.Comparator;

public class NewDataFrame implements DataFrame {

    //<editor-fold desc="Object Instance">
    DataFrameHeader header = new DataFrameHeader();
    DataFrameColumns columns = new DataFrameColumns(header);

    //</editor-fold>

    //<editor-fold desc="Get Column Class">
    @SuppressWarnings("rawtypes")
    @Override
    public Class<? extends DataFrameColumn> getColumnClass(String name) throws DataFrameException {
        return header.getColumnClass(name);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class<? extends DataFrameColumn> getColumnClass(int index) throws DataFrameException {
        return header.getColumnClass(index);
    }
    //</editor-fold>

    //<editor-fold desc="Get Element Class">
    @Override
    public Class<?> getElementClass(String name) throws DataFrameException {
        return header.getElementClass(name);
    }

    @Override
    public Class<?> getElementClass(int index) throws DataFrameException {
        return header.getElementClass(index);
    }
    //</editor-fold>


    @Override
    public DataFrameHeader getHeader() {
        return header;
    }

    @Override
    public String[] getHeaderName() {
        return header.header.toArray(String[]::new);
    }

    @Override
    public int getColumnNumber() {
        return header.size();
    }

    @Override
    public int getRowNumber() {
        return columns.getRowNumber();
    }

    @Override
    public Object[] getRow(int index) throws DataFrameException {
        return columns.getElements(index);
    }

    //<editor-fold desc="Get column">
    @Override
    public DataFrameColumn<?> getColumn(String name) throws DataFrameException {
        return columns.getColumn(name);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public <T extends DataFrameColumn> T getColumn(String name, Class<T> type) throws DataFrameException {
        if (!(type.isInstance(getColumn(name))))
            throw new DataFrameException("Invalid type of column " + name + ".");
        return type.cast(getColumn(name));
    }

    @Override
    public IntegerColumn getIntegerColumn(String name) throws DataFrameException {
        return getColumn(name, IntegerColumn.class);
    }

    @Override
    public DoubleColumn getDoubleColumn(String name) throws DataFrameException {
        return getColumn(name, DoubleColumn.class);
    }

    @Override
    public StringColumn getStringColumn(String name) throws DataFrameException {
        return getColumn(name, StringColumn.class);
    }
    //</editor-fold>

    //<editor-fold desc="Add Column">
    @Override
    public DataFrame addColumn(DataFrameColumn<?> column) throws DataFrameException{
        header.addHeader(column.getName(), column.getClass());
        columns.addColumn(column.getName(), column);
        return this;
    }

    @Override
    public DataFrame addIntegerColumn(String name) throws DataFrameException {
        IntegerColumn column = new IntegerColumn(this, name);
        return addColumn(column);
    }

    @Override
    public DataFrame addDoubleColumn(String name) throws DataFrameException {
        DoubleColumn column = new DoubleColumn(this, name);
        return addColumn(column);
    }

    @Override
    public DataFrame addStringColumn(String name) throws DataFrameException {
        StringColumn column = new StringColumn(this, name);
        return addColumn(column);
    }
    //</editor-fold>

    //<editor-fold desc="Append Row">
    @Override
    public DataFrame appendRow(Object... elements) throws DataFrameException {
        columns.appendAll(elements);
        return this;
    }

    @Override
    public DataFrame appendMatchingRow(Object... elements) throws DataFrameException {
        return null;
    }

    @Override
    public DataFrame appendRow(DataFrameRow row) throws DataFrameException {
        return null;
    }
    //</editor-fold>

    //<editor-fold desc="Delete Row">
    @Override
    public DataFrame deleteRow(int index) throws DataFrameException {
        columns.deleteRow(index);
        return this;
    }

    @Override
    public DataFrame deleteFirstRowWith(String name, Object element) throws DataFrameException {
        columns.deleteFirstRowWith(name, element);
        return this;
    }

    @Override
    public DataFrame deleteAllRowWith(String name, Object element) throws DataFrameException {
        columns.deleteAllRowWith(name, element);
        return this;
    }

    @Override
    public DataFrame dropNull(String name) throws DataFrameException {
        columns.deleteAllRowWith(name, null);
        return this;
    }
    //</editor-fold>

    @Override
    public DataFrame sortBy(String name) throws DataFrameException {
        columns.sortBy(name);
        return this;
    }

    //<editor-fold desc="Sort with comparator, to fix later">
    @Override
    public DataFrame sortBy(String name, Comparator<?> comparator) throws DataFrameException {
        columns.sortBy(name, comparator);
        return this;
    }
    //</editor-fold>

    @Override
    public void printDebug() {
        columns.printDebug();
    }

    @Override
    public void print() {
        System.out.println(Arrays.toString(getHeaderName()));
        this.forEach(System.out::println);
    }

    @Override
    public DataFrame getColumnChange(DataFrameColumn<?> baseColumn) {
        columns.reloadColumns(baseColumn);
        return this;
    }
}
