package edu.dataframe;

import java.util.ArrayList;
import java.util.List;

public class DataFrameIndices {

    List<Integer> indices = new ArrayList<>();

    public void add(Integer i) {
        indices.add(i);
    }

    @Override
    public String toString() {
        return "indices = " + indices;
    }
}
