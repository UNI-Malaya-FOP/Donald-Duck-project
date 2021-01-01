package com.company;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameException;

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
                                .append("Lily", 21, 73.7f)
                                .append("Lily", 21, 71.7f);
        dt.replaceNull("Name", "Lulu");
        dt.sortBy("Name", true);
        dt.getIntegerColumn("Age").doFilter(n -> n > 20);
        dt.getFloatColumn("Point").doMap(n -> n+100);
        dt.replaceByIndex("Name", 4, "Aron");
        dt.replaceByIndex("Name", 7, "Mary");
        dt.print();
    }
}
