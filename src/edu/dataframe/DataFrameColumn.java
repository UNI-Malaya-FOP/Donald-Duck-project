package edu.dataframe;

import java.util.ArrayList;
import java.util.List;

public class DataFrameColumn<T extends Comparable<T>> {

    String name;
    List<T> column = new ArrayList<>();
    DataFrameIndices indices = new DataFrameIndices();

    protected DataFrameColumn(String name) {
        this.name = name;
    }

    public void rename(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int indexOf(T element) {
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
        indices.add(column.size());
    }

    @Override
    public String toString() {
        return name + " \t= " + column + "\n" + indices;
    }
}
