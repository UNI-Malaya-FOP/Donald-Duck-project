package edu.dataframe;

import edu.dataframe.column.*;
import edu.dataframe.csv.IntermediateTable;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.Reader;
import java.io.IOException;

public class DataFrameBuilder {

    private LinkedHashMap<String, Class<?>> headerMap = new LinkedHashMap<>();
    private List<DataFrameRow> content = new ArrayList<>();

    DataFrameBuilder load(Reader reader) throws IOException {
        IntermediateTable intermediateTable = new IntermediateTable();
        intermediateTable.load(reader);
        this.headerMap = intermediateTable.getHeaderMap();
        this.content = intermediateTable.getContent();
        return this;
    }

    DataFrame build() throws DataFrameException {
        DataFrame df = DataFrame.create();
        for (Map.Entry<String, Class<?>> entry : headerMap.entrySet()) {
            String K = entry.getKey();
            Class<?> V = entry.getValue();
            df.addColumn(getNewColumn(K, V));
        }
        df.appendRows(content);
        return df;
    }

    @SuppressWarnings("unchecked")
    private <C extends DataFrameColumn<?>> C getNewColumn(String name, Class<?> typeClass) throws DataFrameException{
        if (typeClass == Integer.class)
            return (C) new IntegerColumn(name);
        else if (typeClass == Float.class)
            return (C) new FloatColumn(name);
        else if (typeClass == String.class)
            return (C) new StringColumn(name);
        else
            return null;
    }
}
