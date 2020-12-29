package src.edu.dataframe;

import java.util.HashMap;

public class DataFrameHeader {

    HashMap<String, Class<? extends DataFrameColumn<?,?>>> colClassMap = new HashMap<>();
    HashMap<String, Class<? extends Comparable<?>>> typeClassMap = new HashMap<>();
}
