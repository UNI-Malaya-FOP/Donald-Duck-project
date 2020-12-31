package edu.dataframe;

import java.util.ArrayList;
import java.util.List;

public class DataFrameIndices {

    private int index = 1;
    private final List<Integer> indices = new ArrayList<>();

    public int size() {
        return indices.size();
    }

    public void addIndex() {
        indices.add(index++);
    }

    public void add(int index) {
        indices.add(index);
    }

    public void remove(int index) {
        indices.remove(index);
    }

    public void clear() {
        indices.clear();
    }

    Integer get(int index) {
        return indices.get(index);
    }

    public int indexOf(int index) {
        return indices.indexOf(index);
    }

    public boolean contains(int index) {
        return indices.contains(index);
    }

    void sortBy(List<Integer> howChange) {
        List<Integer> newIndices = new ArrayList<>(indices.size());
        for (int i : howChange)
            newIndices.add(indices.get(i));
        indices.clear();
        indices.addAll(newIndices);
    }

    private void copyTo(List<Integer> indices) {
        indices.clear();
        indices.addAll(this.indices);
    }

    public void copyTo(DataFrameColumn<?> column) {
        copyTo(column.getIndices().indices);
    }

    protected void resetIndices() {
        this.index = 1;
        int oldSize = indices.size();
        indices.clear();
        for (int i = 0; i < oldSize; i++) {
            indices.add(index++);
        }
    }

    @Override
    public String toString() {
        return "indices = " + indices;
    }
}
