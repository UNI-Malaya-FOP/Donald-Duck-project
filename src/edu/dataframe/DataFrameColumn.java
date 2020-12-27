package edu.dataframe;

import java.util.*;

public class DataFrameColumn<T extends Comparable<T>> implements Iterable<T> {

    List<T> column = new ArrayList<>();
    LinkedHashMap<T, List<Integer>> columnMap = new LinkedHashMap<>();
    protected List<Integer> currIndices = new ArrayList<>();
    protected DataFrame dataFrame;
    protected String name;

    public DataFrameColumn(DataFrame dataFrame, String name) {
        this.dataFrame = dataFrame;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getIndices(T element) throws ElementNotFoundException {
        if (!columnMap.containsKey(element))
            throw new ElementNotFoundException("Element " + element + "doesn't exist");
        return columnMap.get(element);
    }

    protected List<Integer> getIndexOfNull() throws DataFrameException {
        return getIndices(null);
    }

    public T getElement(int index) throws DataFrameException {
        if (!(index < column.size()))
            throw new DataFrameException("Index " + index + " is out of bounds");
        return column.get(index);
    }

    protected List<Integer> getCurrIndices() {
        return currIndices;
    }

    protected DataFrameColumn<T> doAppend(T element) {
        column.add(element);
        currIndices.add(column.size()-1);
        mapPut(element, column.size()-1);
        return this;
    }

    protected DataFrameColumn<T> doAppendNull() {
        return doAppend(null);
    }

    //<editor-fold desc="Unused append">
    /**
     * @deprecated unused
     */
    public DataFrameColumn<T> append(T element) {
        doAppend(element);
        dataFrame.getColumnChange(this);
        return this;
    }
    //</editor-fold>


    public DataFrameColumn<T> deleteIndex(int index) throws DataFrameException {
        if (!(index < column.size()))
            throw new DataFrameException("Index " + index + " is out of bounds");
        column.remove(index);
        updateDeletedIndices(index);
        return reloadMap();
    }

    public DataFrameColumn<T> replaceIndex(int index, T element) throws DataFrameException {
        if (!(index < column.size()))
            throw new DataFrameException("Index " + index + " is out of bounds");
        List<Integer> indices = columnMap.get(getElement(index));
        if (indices.size() > 1)
            indices.remove((Object) index);
        else
            columnMap.remove(getElement(index));
        column.set(index, element);
        return mapPut(element, index);
    }

    //<editor-fold desc="Unused remove method">
    /**
     * @deprecated unused
     */
    public DataFrameColumn<T> deleteFirstElement(T element) throws DataFrameException {
        if (!columnMap.containsKey(element))
            throw new DataFrameException("Element " + element + " doesn't exist");
        Integer index = getIndices(element).get(0);
        return deleteIndex(index);
    }

    /**
     * @deprecated unused
     */
    public DataFrameColumn<T> deleteLastElement(T element) throws DataFrameException {
        if (!columnMap.containsKey(element))
            throw new DataFrameException("Element " + element + "doesn't exist");
        Integer index = getIndices(element).get(getIndices(element).size()-1);
        return deleteIndex(index);
    }

    /**
     * @deprecated unused
     */
    public DataFrameColumn<T> deleteAllElement(T element) throws DataFrameException {
        while (columnMap.containsKey(element)) {
            deleteFirstElement(element);
        }
        return this;
    }
    //</editor-fold>

    public DataFrameColumn<T> sort() {
        Collections.sort(column);
        updateSortedIndices();
        reloadMap();
        dataFrame.getColumnChange(this);
        return this;
    }

    //<editor-fold desc="Sort with comparator, to fix later">
    @SuppressWarnings({"unchecked", "rawtypes"})
    public DataFrameColumn<T> sort(Comparator comparator) {
        column.sort(comparator);
        updateSortedIndices();
        reloadMap();
        dataFrame.getColumnChange(this);
        return this;
    }
    //</editor-fold>

    public DataFrameColumn<T> sortBasedOn(List<Integer> indices) {
        List<T> newColumn = new ArrayList<>(column.size());
        for (Integer index : indices)
            newColumn.add(column.get(index));
        column = newColumn;
        updateIndices(indices);
        return reloadMap();
    }

    private void updateDeletedIndices(int index) {
        currIndices.remove(index);
        for (int i = 0; i < currIndices.size(); i++) {
            if (currIndices.get(i) > index)
                currIndices.set(i, currIndices.get(i) - 1);
        }
    }

    private void updateSortedIndices() {
        currIndices.clear();
        column.forEach(element -> {
            for (Integer index : columnMap.get(element)) {
                if (!currIndices.contains(index)) {
                    currIndices.add(index);
                    break;
                }
            }
        });
    }

    private void updateIndices(List<Integer> indices) {
        currIndices.clear();
        currIndices.addAll(indices);
    }

    protected void resetIndices() {
        currIndices.clear();
        for (int i = 0; i < column.size(); i++) {
            currIndices.add(i);
        }
    }

    private DataFrameColumn<T> reloadMap() {
        columnMap.clear();
        for (Integer index : currIndices) {
            mapPut(column.get(index), index);
        }
        return this;
    }

    private DataFrameColumn<T> mapPut(T element, int index) {
        List<Integer> indexes = new ArrayList<>();
        if(columnMap.containsKey(element))
            indexes = columnMap.get(element);
        if(!indexes.contains(index))
            indexes.add(index);
        columnMap.put(element, indexes);
        return this;
    }

    public void printDebug() {
        System.out.println(column);
        System.out.println(columnMap);
    }

    public DataFrameColumn<T> copy(String name) throws DataFrameException {
        DataFrame dataFrameCopy = DataFrame.create();
        DataFrameColumn<T> copy = new DataFrameColumn<T>(dataFrameCopy, name);
        copy.column = new ArrayList<T>();
        copy.column.addAll(column);
        copy.columnMap = new LinkedHashMap<>();
        columnMap.forEach((K, V) -> copy.columnMap.put(K,List.copyOf(V)));
        copy.currIndices = List.copyOf(currIndices);
        dataFrameCopy.addColumn(copy);
        return copy;
    }

    //<editor-fold desc="Iterator related">
    @Override
    public Iterator<T> iterator() {
        return this.column.iterator();
    }
    //</editor-fold>
}
