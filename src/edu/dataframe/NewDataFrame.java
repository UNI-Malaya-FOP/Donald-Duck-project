package src.edu.dataframe;

import java.util.HashMap;

public class NewDataFrame implements DataFrame {

    DataFrameHeader header = new DataFrameHeader();
    DataFrameIndices indices = new DataFrameIndices();
    HashMap<String, DataFrameColumn<?,?>> column = new HashMap<>();
}
