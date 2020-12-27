package edu.dataframe.column;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameColumn;

public class StringColumn extends DataFrameColumn<String> {

    public StringColumn(DataFrame dataFrame, String name){
        super(dataFrame, name);
    }

}
