package src.edu.dataframe;

import java.util.ArrayList;
import java.util.List;

public class DataFrameColumn<T extends Comparable<T>, C extends DataFrameColumn<T,C>> {

    String name;
    List<T> column = new ArrayList<>();
    DataFrameIndices indices = new DataFrameIndices();

    public DataFrameColumn(String name) {
        this.name = name;
    }

    public void rename(String name) {
        this.name = name;
    }

    public int indexOf(T element) {
        return column.indexOf(element);
    }

    public T get(int index) {
        return column.get(index);
    }

    public int size() {
        return column.size();
    }

    public void append(T element) {
        column.add(element);
        indices.add(column.size());
    }
}
