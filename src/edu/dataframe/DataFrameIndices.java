package edu.dataframe;

import java.util.ArrayList;
import java.util.List;

public class DataFrameIndices {

    private int index = 1;
    List<Integer> indices = new ArrayList<>();

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

    @Override
    public String toString() {
        return "indices = " + indices;
    }
}
