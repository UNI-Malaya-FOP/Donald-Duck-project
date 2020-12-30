package edu.dataframe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataFrameColumn<T extends Comparable<T>> implements Iterable<T>{

    String name;
    List<T> column = new ArrayList<>();
    DataFrameIndices indices = new DataFrameIndices();

    protected DataFrameColumn(String name) {
        this.name = name;
    }

    private void rename(String name) {
        this.name = name;
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

    public int size() {
        return column.size();
    }

    public void append(T element) {
        column.add(element);
        indices.addIndex();
    }

    public void remove(int index) {
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
