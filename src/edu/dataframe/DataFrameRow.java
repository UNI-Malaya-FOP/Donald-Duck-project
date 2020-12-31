package edu.dataframe;

import java.util.ArrayList;

public class DataFrameRow extends ArrayList<Object> {

    private final Integer index;
    private final NewDataFrame dataFrame;

    DataFrameRow(DataFrame dataFrame, int index) {
        this.dataFrame = (NewDataFrame) dataFrame;
        this.index = index;
        addAll(index);
    }

    public DataFrameRow() {
        this.dataFrame = null;
        this.index = null;
    }

    private void addAll(Object o) {
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
}
