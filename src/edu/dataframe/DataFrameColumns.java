package edu.dataframe;

import java.util.*;

public class DataFrameColumns implements Iterable<DataFrameColumn<?>> {

    DataFrameHeader header;
    List<DataFrameColumn<?>> columns = new ArrayList<>();
    LinkedHashMap<String, DataFrameColumn<?>> columnsMap = new LinkedHashMap<>();
    private int rowNumber = 0;

    public DataFrameColumns(DataFrameHeader header) {
        this.header = header;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public Object[] getElements(int index) throws DataFrameException {
        Object[] obj = new Object[columns.size()];
        for (int i = 0; i < columns.size(); i++) {
            obj[i] = columns.get(i).getElement(index);
        }
        return obj;
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getIndices(String name, Object element) throws DataFrameException {
        if (!columnsMap.containsKey(name))
            throw new DataFrameException("Column " + name + " doesn't exist");
        var x = header.getColumnClass(name).cast(columnsMap.get(name));
        var y = header.getElementClass(name).cast(element);
        return x.getIndices((Comparable<?>) y);
    }

    public DataFrameColumn<?> getColumn(String name) throws DataFrameException {
        if (!columnsMap.containsKey(name))
            throw new DataFrameException("Column " + name + " does not exist.");
        return columnsMap.get(name);
    }

    public DataFrameColumns addColumn(String name, DataFrameColumn<?> column) throws DataFrameException {
        if (columnsMap.containsKey(name))
            throw new DataFrameException("Column " + name + " already exist.");
        columns.add(column);
        columnsMap.put(name, column);
        return this;
    }

    @SuppressWarnings("unchecked")
    public DataFrameColumns appendAll(Object... row) throws DataFrameException{
        if (columns.size() != row.length)
            throw new DataFrameException("DataFrame length doesn't match");
        for (int i = 0; i < columns.size(); i++) {
            try {
                var x = header.getColumnClass(i).cast(columns.get(i));
                var y = header.getElementClass(i).cast(row[i]);
                x.doAppend((Comparable<?>) y);
            } catch (ClassCastException e) {
                throw new DataFrameException("Type doesn't match");
            }
        }
        rowNumber++;
        return this;
    }

    public DataFrameColumns deleteRow(int index) throws DataFrameException {
        for (DataFrameColumn<?> column : columns)
            column.deleteIndex(index);
        rowNumber--;
        return this;
    }

    public DataFrameColumns deleteFirstRowWith(String name, Object element) throws DataFrameException {
        return deleteRow(getIndices(name, element).get(0));
    }

    public DataFrameColumns deleteAllRowWith(String name, Object element) throws DataFrameException {
        List<Integer> indices = getIndices(name, element);
        while (indices.size() > 0) {
            deleteRow(Collections.max(indices));
            indices.remove(Collections.max(indices));
        }
        return this;
    }

    //<editor-fold desc="Sort with comparator, to fix later">
    @SuppressWarnings("rawtypes")
    public DataFrameColumns sortBy(String name, Comparator comparator) throws DataFrameException {
        if (!columnsMap.containsKey(name))
            throw new DataFrameException("Column " + name + " does not exist.");
        columnsMap.get(name).sort(comparator);
        return reloadColumns(columnsMap.get(name));
    }
    //</editor-fold>

    public DataFrameColumns sortBy(String name) throws DataFrameException {
        if (!columnsMap.containsKey(name))
            throw new DataFrameException("Column " + name + " does not exist.");
        columnsMap.get(name).sort();
        return this;
    }

    public DataFrameColumns reloadColumns(DataFrameColumn<?> baseColumn) {
        columns.forEach(column -> {
            if (column != baseColumn) {
                column.sortBasedOn(baseColumn.getCurrIndices());
                column.resetIndices();
            }
        });
        baseColumn.resetIndices();
        return this;
    }

    public void printDebug() {
        columns.forEach(DataFrameColumn::printDebug);
    }

    //<editor-fold desc="Iterator related">
    @Override
    public Iterator<DataFrameColumn<?>> iterator() {
        return this.columns.iterator();
    }
    //</editor-fold>
}
