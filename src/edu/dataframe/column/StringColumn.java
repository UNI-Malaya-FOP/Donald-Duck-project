package edu.dataframe.column;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameColumn;
import edu.dataframe.DataFrameException;

public class StringColumn extends DataFrameColumn<String> {

    protected StringColumn(String name, DataFrame dataFrame) {
        super(name, dataFrame);
    }

    public StringColumn(String name) throws DataFrameException {
        super(name, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected  StringColumn getNewSubColumn(String name) throws DataFrameException {
        return new StringColumn(name);
    }
}
