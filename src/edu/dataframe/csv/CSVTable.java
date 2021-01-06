package edu.dataframe.csv;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class CSVTable implements Iterable<List<String>>{

    private final CSVReader csvr;
    private final List<String> header = new ArrayList<>();
    private final List<List<String>> content = new ArrayList<>();

    public CSVTable(Reader reader) throws IOException {
        csvr = new CSVReader(reader);
        readHeader();
        readContent();
        csvr.close();
    }

    public List<String> getHeader() {
        return header;
    }

    public List<List<String>> getContent() {
        return content;
    }

    private void readHeader() throws IOException {
        header.addAll(csvr.readLine());
    }

    private void readContent() throws IOException {
        List<String> line = csvr.readLine();
        while (line != null) {
            content.add(line);
            line = csvr.readLine();
        }
        csvr.close();
    }

    @Override
    public Iterator<List<String>> iterator() {

        return new Iterator<List<String>>() {

            private int counter = 0;

            @Override
            public boolean hasNext() {
                return counter < content.get(0).size();
            }

            @Override
            public List<String> next() {
                List<String> column = new ArrayList<>();
                content.forEach(e -> column.add(e.get(counter)));
                counter++;
                return column;
            }
        };
    }
}
