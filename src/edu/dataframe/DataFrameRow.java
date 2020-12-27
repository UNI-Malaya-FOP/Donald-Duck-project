
package edu.dataframe;

import java.util.Arrays;
import java.util.ArrayList;

public class DataFrameRow extends ArrayList<Object> {

    private final DataFrame dataFrame;
    private final int index;

    public DataFrameRow(DataFrame dataFrame, int index) {
        this.dataFrame = dataFrame;
        this.index = index;
        try {
            addAll();
        } catch (DataFrameException e) {
            e.printStackTrace();
        }
    }

    public DataFrameRow () {
        this.dataFrame = null;
        this.index = 0;
    }

    private void addElement(Object element) {
        super.add(element);
    }

    private void addAll() throws DataFrameException {
        Arrays.stream(dataFrame.getRow(index)).forEach(this::addElement);
    }
}
