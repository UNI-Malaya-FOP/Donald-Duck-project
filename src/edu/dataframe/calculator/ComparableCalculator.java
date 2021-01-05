package edu.dataframe.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ComparableCalculator<T extends Comparable<T>> extends Calculator<T> {

    protected final List<T> sorted ;

    public ComparableCalculator(List<T> list, HashMap<T, List<Integer>> map) {
        super(list, map);
        sorted = new ArrayList<>(list);
        Collections.sort(sorted);
    }

    public T min() {
        return sorted.get(0);
    }

    public T max() {
        return sorted.get(list.size()-1);
    }
}
