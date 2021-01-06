package edu.dataframe.column;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameException;
import edu.dataframe.scaler.Scaler;
import edu.dataframe.DataFrameColumn;
import edu.dataframe.calculator.FloatCalculator;

public class FloatColumn extends DataFrameColumn<Float> {

    protected FloatColumn(String name, DataFrame dataFrame) {
        super(name, dataFrame);
    }

    public FloatColumn(String name) throws DataFrameException {
        super(name, null);
    }

    @Override
    public FloatCalculator calculate() {
        return new FloatCalculator(column, getTempMap());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected  FloatColumn getNewSubColumn(String name) throws DataFrameException {
        return new FloatColumn(name);
    }

    public Scaler scaler() {
        return new Scaler(column);
    }
}
