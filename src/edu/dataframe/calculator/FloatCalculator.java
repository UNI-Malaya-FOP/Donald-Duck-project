package edu.dataframe.calculator;

import java.util.HashMap;
import java.util.List;

public class FloatCalculator extends ComparableCalculator<Float> {

    public FloatCalculator(List<Float> list, HashMap<Float, List<Integer>> map) {
        super(list, map);
    }

    public float range() {
        return max() - min();
    }

    public float median() {
        int size = list.size();
        if (size % 2 == 1)
            return list.get((size-1)/2);
        return (sorted.get((size-1)/2) + sorted.get((size-1)/2+1))/2;
    }

    public float mean() {
        float mean = 0f;
        for (Float n : list)
            mean += n;
        return mean / list.size();
    }

    public float variance() {
        float variance = 0f;
        for (Float n : list)
            variance += Math.pow(n - mean(), 2);
        return variance / list.size();
    }

    public float standardDeviation() {
        return (float) Math.sqrt(variance());
    }
}
