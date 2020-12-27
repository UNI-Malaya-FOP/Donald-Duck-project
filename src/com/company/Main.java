package com.company;

import edu.dataframe.*;

public class Main {

    public static void main(String[] args) throws DataFrameException {
        DataFrame dt = DataFrame.create()
                                .addIntegerColumn("ID")
                                .addStringColumn("Name")
                                .addIntegerColumn("Age")
                                .addDoubleColumn("Point")
                                .appendRow(1, "Elly", 19, 131.0)
                                .appendRow(2, "Iris", 23, 109.7)
                                .appendRow(3, "Mona", 18, 143.5)
                                .appendRow(4, "Adam", 21, 71.03)
                                .appendRow(5, "Wade", 25, 51.07)
                                .appendRow(6, "Elly", 25, 177.0)
                                .appendRow(7, null, 10, 999.9)
                                .appendRow(8, null, 99, 999.9)
                                .appendRow(9, null, 50, 999.9);
        dt.deleteAllRowWith("Name", "Elly").print();
        System.out.println();
        dt.dropNull("Name").print();
    }
}