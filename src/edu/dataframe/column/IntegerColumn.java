package edu.dataframe.column;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameColumn;

public class IntegerColumn extends DataFrameColumn<Integer> {

    protected IntegerColumn(String name, DataFrame dataFrame) {
        super(name, dataFrame);
    }

    public IntegerColumn(String name) {
        super(name, null);
    }
}
