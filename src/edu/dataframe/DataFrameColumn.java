package edu.dataframe;

import java.util.*;

public class DataFrameColumn<T extends Comparable<T>> implements Iterable<T>{

    private String name;
    private final DataFrame dataFrame;
    private final List<T> column = new ArrayList<>();
    private final DataFrameIndices indices = new DataFrameIndices();

    protected DataFrameColumn(String name, DataFrame dataFrame) {
        this.dataFrame = dataFrame;
        this.name = name;
    }

    public void rename(String name) throws DataFrameException {
        Set<String> names = dataFrame.getHeader().getNames();
        if (!names.contains(name))
            this.name = name;
        throw new DataFrameException("Name " + name + " already exist.");
    }

    public String getName() {
        return name;
    }

    public DataFrameIndices getIndices() {
        return indices;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public int indexOf(Object element) {
        return column.indexOf(element);
    }

    public T get(int index) {
        return column.get(index);
    }

    private Set<T> getDuplicate() {
        final Set<T> testAdd = new HashSet<>();
        final Set<T> duplicate = new HashSet<>();
        column.forEach(e -> {
            if (!testAdd.add(e)) {
                duplicate.add(e);
            }
        });
        return duplicate;
    }

    public T getFirstDuplicate() {
        Set<T> duplicate = getDuplicate();
        for (T e : column) {
            if (duplicate.contains(e))
                return e;
        }
        return null;
    }

    public List<Integer> getFirstDuplicateIndices() {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < column.size(); i++) {
            T tmp = column.get(i);
            for (int j = i + 1; j < column.size(); j++) {
                if (tmp.equals(column.get(j)) && indices.size() == 0)
                    indices.add(i);
                if (tmp.equals(column.get(j)))
                    indices.add(j);
            }
            if (indices.size() != 0)
                return indices;
        }
        return null;
    }

    public int size() {
        return column.size();
    }

    public void doAppend(T element) throws DataFrameException {
        if (dataFrame == null)
            append(element);
        else
            throw new DataFrameException("doAppend only valid if column is not in the DataFrame");
    }

    protected void append(T element) {
        column.add(element);
        indices.addIndex();
    }

    public void doRemove(int index) throws DataFrameException {
        if (dataFrame == null)
            remove(index);
        else
            throw new DataFrameException("doRemove only valid if column is not in the DataFrame");
    }

    protected void remove(int index) {
        column.remove(index);
        indices.remove(index);
    }

    public void replace(int index, T element) {
        column.set(index, element);
    }

    @Override
    public String toString() {
        return name + "\t= " + column + "\n" + indices;
    }

    @Override
    public Iterator<T> iterator() {
        return column.listIterator();
    }
}
