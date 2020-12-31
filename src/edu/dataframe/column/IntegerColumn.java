package edu.dataframe.column;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameColumn;
import edu.dataframe.calculator.IntegerCalculator;

public class IntegerColumn extends DataFrameColumn<Integer> {

    protected IntegerColumn(String name, DataFrame dataFrame) {
        super(name, dataFrame);
    }

    public IntegerColumn(String name) {
        super(name, DataFrame.create());
    }

    @Override
    public IntegerCalculator calculate() {
        return new IntegerCalculator(column, getTempMap());
    }
}
