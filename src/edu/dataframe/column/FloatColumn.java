package edu.dataframe.column;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameColumn;
import edu.dataframe.calculator.FloatCalculator;

public class FloatColumn extends DataFrameColumn<Float> {

    protected FloatColumn(String name, DataFrame dataFrame) {
        super(name, dataFrame);
    }

    public FloatColumn(String name) {
        super(name, DataFrame.create());
    }

    @Override
    public FloatCalculator calculate() {
        return new FloatCalculator(column, getTempMap());
    }
}
