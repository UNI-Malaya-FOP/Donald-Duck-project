package edu.dataframe;

import edu.dataframe.column.*;

import java.util.*;

@SuppressWarnings("rawtypes")
public class DataFrameHeader implements Iterable<String> {

    private final ArrayList<String> names = new ArrayList<>();
    private final HashMap<String, Integer> namesMap = new HashMap<>();
    private final HashMap<String, Class<? extends DataFrameColumn>> columnClassMap = new HashMap<>();
    private final HashMap<String, Class<? extends Comparable<?>>> typeClassMap = new HashMap<>();
    private int size = 0;

    public Set<String> getNames() {
        return new HashSet<>(names);
    }

    public int indexOf(String name) {
        return namesMap.get(name);
    }

    public void add(String name, Class<? extends DataFrameColumn> columnClass) {
        names.add(name);
        namesMap.put(name, size++);
        columnClassMap.put(name, columnClass);
        typeClassMap.put(name, getTyeClass(columnClass));
    }

    public void remove(String name) {
        names.remove(name);
        namesMap.remove(name);
        typeClassMap.remove(name);
        columnClassMap.remove(name);
    }

    public void remove(int index) {
        String name = names.get(index);
        remove(name);
    }

    public Class<? extends DataFrameColumn> getColumnClass (String name) {
        return columnClassMap.get(name);
    }

    public Class<? extends DataFrameColumn> getColumnClass (Integer index) {
        return columnClassMap.get(names.get(index));
    }

    private Class<? extends Comparable<?>> getTyeClass(Class<? extends DataFrameColumn> columnClass) {
        if (columnClass == IntegerColumn.class)
            return Integer.class;
        if (columnClass == FloatColumn.class)
            return Float.class;
        if (columnClass == StringColumn.class)
            return String.class;
        return null;
    }

    public Class<?> getTypeClass(String name) {
        return typeClassMap.get(name);
    }

    public Class<? extends Comparable<?>> getTypeClass(Integer index) {
        return typeClassMap.get(names.get(index));
    }

    public void print() {
        names.forEach(name -> System.out.print(name + "\t\t\t"));
        System.out.println();
        names.forEach(name -> System.out.print(typeClassMap.get(name).getSimpleName() + "\t\t\t"));
        System.out.println();
        names.forEach(name -> System.out.print(columnClassMap.get(name).getSimpleName() + "\t"));
        System.out.println();
    }

    @Override
    public Iterator<String> iterator() {
        return names.iterator();
    }
}
