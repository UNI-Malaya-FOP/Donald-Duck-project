package edu.dataframe.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IntegerCalculator extends ComparableCalculator<Integer> {

    private final FloatCalculator floatCalculator;

    public IntegerCalculator(List<Integer> list, HashMap<Integer, List<Integer>> map) {
        super(list, map);
        List<Float> floatList = new ArrayList<>();
        list.forEach(n -> floatList.add((float)n));
        HashMap<Float, List<Integer>> floatMap = new HashMap<>();
        map.forEach((K, V) -> floatMap.put((float)K,V));
        this.floatCalculator = new FloatCalculator(floatList, floatMap);
    }

    public int range() {
        return max() - min();
    }

    public float median() {
        return floatCalculator.median();
    }

    public float mean() {
        return floatCalculator.mean();
    }

    public float variance() {
        return floatCalculator.variance();
    }

    public float standardDeviation() {
        return (float) Math.sqrt(variance());
    }
}
