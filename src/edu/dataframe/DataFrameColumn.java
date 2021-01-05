package edu.dataframe;

import edu.dataframe.column.*;
import edu.dataframe.calculator.Calculator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.function.Predicate;

public class DataFrameColumn<T extends Comparable<T>> implements Iterable<T>{

    private String name;
    private NewDataFrame dataFrame;
    protected final List<T> column = new ArrayList<>();
    private final DataFrameIndices indices = new DataFrameIndices();

    protected DataFrameColumn(String name, DataFrame dataFrame) {
        this.dataFrame = (NewDataFrame) dataFrame;
        this.name = name;
    }

    protected void reDataFrame(DataFrame dataFrame) {
        this.dataFrame = (NewDataFrame) dataFrame;
    }

    public void rename(String name) throws DataFrameException {
        Set<String> names = dataFrame.getHeader().getNames();
        if (!dataFrame.getHeader().getNames().contains(name))
            this.name = name;
        else
            throw new DataFrameException("Name " + name + " already exist.");
    }

    public String getName() {
        return name;
    }

    public DataFrameIndices getIndices() {
        return indices;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public int indexOf(Object element) {
        return column.indexOf(element);
    }

    public T get(int index) {
        return column.get(index);
    }

    private Set<T> getDuplicate() {
        final Set<T> testAdd = new HashSet<>();
        final Set<T> duplicate = new HashSet<>();
        column.forEach(e -> {
            if (!testAdd.add(e)) {
                duplicate.add(e);
            }
        });
        return duplicate;
    }

    public T getFirstDuplicate() {
        Set<T> duplicate = getDuplicate();
        for (T e : column) {
            if (duplicate.contains(e))
                return e;
        }
        return null;
    }

    public List<Integer> getFirstDuplicateIndices() {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < column.size(); i++) {
            T tmp = column.get(i);
            for (int j = i + 1; j < column.size(); j++) {
                if (tmp.equals(column.get(j)) && indices.size() == 0)
                    indices.add(i);
                if (tmp.equals(column.get(j)))
                    indices.add(j);
            }
            if (indices.size() != 0)
                return indices;
        }
        return null;
    }

    public int size() {
        return column.size();
    }

    public void doAppend(T element) throws DataFrameException {
        if (dataFrame == null)
            append(element);
        else
            throw new DataFrameException("doAppend only valid if column is not in the DataFrame");
    }

    protected void append(T element) {
        column.add(element);
        indices.addIndex();
    }

    public void doRemove(int index) throws DataFrameException {
        if (dataFrame == null)
            remove(index);
        else
            throw new DataFrameException("doRemove only valid if column is not in the DataFrame");
    }

    protected void remove(int index) {
        column.remove(index);
        indices.remove(index);
    }

    public void replace(int index, T element) {
        column.set(index, element);
    }

    public void replaceNull(T element) {
        int index = column.indexOf(null);
        column.set(index, element);
    }

    public DataFrameColumn<T> replaceAll(Collection<T> elements) throws DataFrameException {
        if (elements.size() != column.size())
            throw new DataFrameException("(%d) element is not valid for (%d)".formatted(elements.size(),column.size()));
        column.clear();
        column.addAll(elements);
        return this;
    }

    public void sort(Comparator<T> comparator) {
        HashMap<T, List<Integer>> map = getTempMap();
        column.sort(comparator);
        List<Integer> howChange = getHowChange(map);
        this.indices.sortBy(howChange);
        dataFrame.getColumnChange(this, howChange);
    }

    protected HashMap<T, List<Integer>> getTempMap() {
        HashMap<T, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < column.size(); i++) {
            if (!map.containsKey(column.get(i))) {
                List<Integer> tmp = new ArrayList<>();
                tmp.add(i);
                map.put(column.get(i), tmp);
            } else
                map.get(column.get(i)).add(i);
        }
        return map;
    }

    private List<Integer> getHowChange(HashMap<T, List<Integer>> map) {
        List<Integer> howChange = new ArrayList<>();
        HashSet<Integer> changeSet = new HashSet<>();
        for (T element : column) {
            for (Integer index : map.get(element)) {
                if (changeSet.add(index)) {
                    howChange.add(index);
                    break;
                }
            }
        }
        return howChange;
    }

    protected void sortBy(List<Integer> howChange) {
        List<T> newColumn = new ArrayList<>(column.size());
        for (int i : howChange)
            newColumn.add(column.get(i));
        column.clear();
        column.addAll(newColumn);
        this.indices.sortBy(howChange);
    }

    @SuppressWarnings("unchecked")
    public <C extends DataFrameColumn<?>> C getNewSubColumn(String name) throws DataFrameException {
        var columnClass = dataFrame.getHeader().getColumnClass(this.name);
        if (columnClass == IntegerColumn.class) {
            return (C) new IntegerColumn(name);
        } else if (columnClass == FloatColumn.class) {
            return (C) new FloatColumn(name);
        } else if (columnClass == StringColumn.class) {
            return (C) new StringColumn(name);
        } else
            throw new DataFrameException("This Column is not subclass of DataFrameColumn");
    }

    public DataFrameColumn<T> map(Function<T, T> function) throws DataFrameException {
        List<T> mapped = column.stream().map(function).collect(Collectors.toList());
        DataFrameColumn<T> col = getNewSubColumn("mapped" + name);
        mapped.forEach(col::append);
        return col;
    }

    public void doMap(Function<T,T> function) {
        List<T> mapped = column.stream().map(function).collect(Collectors.toList());
        column.clear();
        column.addAll(mapped);
    }

    public DataFrameColumn<T> filter(Predicate<T> predicate) throws DataFrameException {
        List<T> filtered = column.stream().filter(predicate).collect(Collectors.toList());
        DataFrameColumn<T> col = getNewSubColumn("filtered" + name);
        filtered.forEach(col::append);
        return col;
    }

    public void doFilter(Predicate<T> predicate) throws DataFrameException {
        List<T> filtered = column.stream().filter(predicate).collect(Collectors.toList());
        HashMap<T, List<Integer>> map = getTempMap();
        column.clear();
        column.addAll(filtered);
        List<Integer> howChange = getHowChange(map);
        indices.sortBy(howChange);
        dataFrame.getColumnChange(this, howChange);
    }

    protected void resetIndices() {
        indices.resetIndices();
    }

    public Calculator<T> calculate() {
        return new Calculator<>(column, getTempMap());
    }

    public List<T> toList() {
        return column;
    }

    @Override
    public String toString() {
        return name + "\t= " + column + "\n" + indices;
    }

    @Override
    public Iterator<T> iterator() {
        return column.listIterator();
    }
}
