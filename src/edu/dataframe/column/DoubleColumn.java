package edu.dataframe.column;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameColumn;

public class DoubleColumn extends DataFrameColumn<Double> {

    public DoubleColumn(DataFrame dataFrame, String name) {
        super(dataFrame, name);
    }
}