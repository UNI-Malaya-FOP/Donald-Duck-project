package edu.dataframe.column;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameColumn;

public class FloatColumn extends DataFrameColumn<Float> {

    protected FloatColumn(String name, DataFrame dataFrame) {
        super(name, dataFrame);
    }

    public FloatColumn(String name) {
        super(name, null);
    }
}
