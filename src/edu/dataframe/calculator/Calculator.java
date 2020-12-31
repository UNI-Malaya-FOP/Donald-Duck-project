package edu.dataframe.calculator;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Calculator<T> {

    protected final List<T> list;
    protected final HashMap<T, List<Integer>> map;

    public Calculator(List<T> list, HashMap<T, List<Integer>> map) {
        this.list = list;
        this.map = map;
    }

    public List<T> mode() {
        final List<T> mode = new ArrayList<>();
        int tmpMax = 0;
        for (Map.Entry<T, List<Integer>> entry : map.entrySet()) {
            int num = entry.getValue().size();
            tmpMax = Math.max(tmpMax, num);
        }
        final int MAX = tmpMax;
        map.forEach((K, V) -> {
            if (V.size() == MAX)
                mode.add(K);
        });
        return mode;
    }
}
