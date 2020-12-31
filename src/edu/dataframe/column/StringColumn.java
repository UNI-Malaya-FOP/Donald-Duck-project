package edu.dataframe.column;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameColumn;

public class StringColumn extends DataFrameColumn<String> {

    protected StringColumn(String name, DataFrame dataFrame) {
        super(name, dataFrame);
    }

    public StringColumn(String name) {
        super(name, DataFrame.create());
    }
}
