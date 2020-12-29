package edu.dataframe.column;

import edu.dataframe.DataFrameColumn;

public class StringColumn extends DataFrameColumn<String> {

    protected StringColumn getSubClass() {
        return this;
    }

    public StringColumn(String name) {
        super(name);
    }

    public Class<String> getElementClass() {
        return String.class;
    }
}
