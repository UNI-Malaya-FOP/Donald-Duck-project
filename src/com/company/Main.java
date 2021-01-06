package com.company;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameException;

import java.io.*;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws DataFrameException, IOException {

        File file = new File("src\\resources\\test.csv");
        URL url = new URL("https://raw.githubusercontent.com/UNI-Malaya-FOP/Donald-Duck-project/main/src/resources/Data.csv");

        DataFrame dt = DataFrame.load(file);
        dt.print();
        DataFrame dt2 = DataFrame.load(url);
        dt2.print();
    }
}

