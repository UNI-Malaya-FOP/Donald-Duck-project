package com.company;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameException;

public class Main {

    public static void main(String[] args) throws DataFrameException {
        DataFrame dt = DataFrame.create()
                                .addStringColumn("Name")
                                .addIntegerColumn("Age")
                                .addFloatColumn("Point")
                                .append("Iris", 21, 99.9f)
                                .append("Lily", 17, 73.7f);
        dt.printDebug();
    }
}
