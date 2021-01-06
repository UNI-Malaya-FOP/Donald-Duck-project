package edu.dataframe.csv;

import edu.dataframe.DataFrameRow;

import java.io.Reader;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.LinkedHashMap;

public class IntermediateTable {


    private final List<Class<?>> typeClass = new ArrayList<>();
    private final LinkedHashMap<String, Class<?>> headerMap = new LinkedHashMap<>();
    private final List<DataFrameRow> content = new ArrayList<>();

    public void load(Reader reader) throws IOException{
        CSVTable csvt = new CSVTable(reader);
        csvt.forEach(column -> typeClass.add(getTypeClass(column)));

        List<String> header = csvt.getHeader();
        for (int i = 0; i < header.size(); i++)
            headerMap.put(header.get(i), typeClass.get(i));

        parseContent(csvt.getContent());
    }

    public LinkedHashMap<String, Class<?>> getHeaderMap() {
        return headerMap;
    }

    public List<DataFrameRow> getContent() {
        return content;
    }

    private void parseContent(List<List<String>> content) {
        for (List<String> row : content) {
            DataFrameRow dtRow = new DataFrameRow();
            for (int j = 0; j < typeClass.size(); j++) {
                Object obj = stringParse(row.get(j), typeClass.get(j));
                dtRow.add(obj);
            }
            this.content.add(dtRow);
        }
    }

    private Object stringParse(String str, Class<?> type) {
        if (type == Integer.class)
            return Integer.parseInt(str);
        if (type == Float.class)
            return Float.parseFloat(str);
        return str;
    }

    private Class<?> getTypeClass(List<String> list) {
        Class<?>[] typeClass =  {Integer.class, Float.class, String.class};
        int index = 0;
        for (String e : list) {
            int tmpIndex = 2;
            if (isFloat(e))
                tmpIndex = 1;
            if (isInteger(e))
                tmpIndex = 0;
            index = Math.max(tmpIndex, index);
        }
        return typeClass[index];
    }

    private boolean isFloat(String str) {
        try {
            float n = Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isInteger(String str) {
        try {
            int n = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
