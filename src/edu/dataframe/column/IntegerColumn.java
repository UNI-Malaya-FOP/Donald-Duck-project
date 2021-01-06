package edu.dataframe.column;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameColumn;
import edu.dataframe.DataFrameException;
import edu.dataframe.calculator.IntegerCalculator;

public class IntegerColumn extends DataFrameColumn<Integer> {

    protected IntegerColumn(String name, DataFrame dataFrame) {
        super(name, dataFrame);
    }

    public IntegerColumn(String name) throws DataFrameException {
        super(name, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected  IntegerColumn getNewSubColumn(String name) throws DataFrameException {
        return new IntegerColumn(name);
    }

    @Override
    public IntegerCalculator calculate() {
        return new IntegerCalculator(column, getTempMap());
    }
}
