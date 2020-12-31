package com.company;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameColumn;
import edu.dataframe.DataFrameException;

import java.util.Collections;

public class Main {

    public static void main(String[] args) throws DataFrameException {
        DataFrame dt = DataFrame.create()
                                .addStringColumn("Name")
                                .addIntegerColumn("Age")
                                .addFloatColumn("Point")
                                .append("Lily", 17, 13.7f)
                                .append(null, 24, 91.9f)
                                .append("Iris", 21, 49.9f)
                                .append("Earl", 24, 94.9f)
                                .append("Earl", 27, 95.9f)
                                .append("Lily", 17, 73.7f)
                                .append("Lily", 14, 71.7f);
        dt.dropNull("Name");
        dt.sortBy("Name", Collections.reverseOrder());
        dt.getFloatColumn("Point").doMap(n -> n + 100);
        dt.getIntegerColumn("Age").doFilter(n -> n > 20);
        dt.resetIndices();
        dt.print();
    }
}
