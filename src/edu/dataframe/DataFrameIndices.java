package edu.dataframe;

import java.util.ArrayList;
import java.util.List;

public class DataFrameIndices {

    private int index = 1;
    private final List<Integer> indices = new ArrayList<>();

    public void addIndex() {
        indices.add(index++);
    }

    public void add(int index) {
        indices.add(index);
    }

    public void remove(int index) {
        indices.remove(index);
    }

    public int indexOf(int index) {
        return indices.indexOf(index);
    }

    public boolean contains(int index) {
        return indices.contains(index);
    }

    private void copyTo(List<Integer> indices) {
        indices.clear();
        indices.addAll(this.indices);
    }

    public void copyTo(DataFrameColumn<?> column) {
        copyTo(column.getIndices().indices);
    }

    @Override
    public String toString() {
        return "indices = " + indices;
    }
}
