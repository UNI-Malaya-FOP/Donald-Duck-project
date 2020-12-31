package edu.dataframe;

import java.util.ArrayList;

public class DataFrameRow extends ArrayList<Object> {

    private final Integer index;
    private final NewDataFrame dataFrame;

    DataFrameRow(DataFrame dataFrame, int index) {
        this.dataFrame = (NewDataFrame) dataFrame;
        this.index = index;
        addAll();
    }

    public DataFrameRow() {
        this.dataFrame = null;
        this.index = null;
    }

    private void addAll() {
        try {
            int size = dataFrame.getColumnNumber();
            for (int i = 0; i < size; i++) {
                Object element = dataFrame.getColumn(i).get(index);
                super.add(element);
            }
        } catch (DataFrameException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < super.size(); i++) {
            s.append(super.get(i)).append("\t");
        }
        return s.toString();
    }
}
