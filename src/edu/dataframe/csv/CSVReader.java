package edu.dataframe.csv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    private final BufferedReader br;
    private int cur, next;

    /**
     * Create a reader from csv formatted text file
     * @param file with csv format
     */
    public CSVReader(File file) throws IOException {
        this.br = new BufferedReader(new FileReader(file));
    }

    public CSVReader(Reader reader) throws IOException {
        this.br = new BufferedReader(reader);
    }

    /**
     * Create a reader for csv formatted string
     * @param string with csv format
     */
    public CSVReader(String string) {
        this.br = new BufferedReader(new StringReader(string));
    }

    /**
     * Move the reader to the next column if exists
     * and return next Column as a string. If reach
     * end of column this return null.
     * @return String of next column or null.
     * @throws IOException if I/O error occur.
     */
    public String next() throws IOException {
        if(next == '\r') {
            br.readLine();
            return null;
        }
        if(next == '\n')
            return null;
        if(!hasNext())
            return null;

        StringBuilder betweenQuotes = new StringBuilder();

        cur = next;
        next = br.read();
        int quoteCounter = 0;

        while (quoteCounter % 2 != 0 || next == '"') {

            cur = next;
            next = br.read();

            if (cur == '"')
                quoteCounter++;

            if (!((cur == '"' && next == '"') ||
                    (quoteCounter % 2 == 0 && next != '"') ||
                    (quoteCounter == 1 && cur == '"')))
                betweenQuotes.append((char) cur);
        }

        StringBuilder wholeString = new StringBuilder().append(betweenQuotes);

        while (!(next == ',' || next == '\r' || next == '\n' || next == -1)) {
            cur = next;
            next = br.read();
            wholeString.append((char) cur);
        }
        return wholeString.toString();
    }

    /**
     * Read a single line and move the reader to the next row.
     * @return list of string.
     * @throws IOException if I/O error occur.
     */
    public List<String> readLine() throws IOException {
        List<String> line = new ArrayList<>();
        String str = next();
        while (str != null) {
            line.add(str);
            str = next();
        }
        next = -1;
        return line.size() != 0? line:null;
    }

    /**
     * check whether the next column of current
     * row exist or not.
     * @return true if next column exist and vice versa.
     * @throws IOException if I/O error occur.
     */
    public boolean hasNext() throws IOException {
        br.mark(1);
        int tmpNext = br.read();
        br.reset();
        return tmpNext != -1;
    }

    /**
     * Close the reader. Once the reader is closed any
     * method used will throw an IOException. Closing
     * a closed reader will not have any effect.
     * @throws IOException if I/O error occur
     */
    public void close() throws IOException{
        br.close();
    }
}