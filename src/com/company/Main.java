package com.company;

import edu.dataframe.DataFrame;
import edu.dataframe.DataFrameException;

import java.io.*;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws DataFrameException, IOException {

        File file1 = new File("src\\resources\\test.csv");
        DataFrame dt = DataFrame.load(file1);
        dt.print();

        URL url = new URL("https://raw.githubusercontent.com/UNI-Malaya-FOP/Donald-Duck-project/main/src/resources/Data.csv");
        DataFrame dt2 = DataFrame.load(url);
        dt2.print();

        File file2 = new File("src\\resources\\CAvideos.csv");
        DataFrame dt3 = DataFrame.load(file2);
        dt3.print();

        String str = "Item_name,Price,Quantity\nHeadphone,111.11,10\nSmartphone, 555.55,7\nGaming rig, 999.99,1";
        DataFrame dt4 = DataFrame.load(str);
        dt4.print();
    }
}

