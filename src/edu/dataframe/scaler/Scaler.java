package edu.dataframe.scaler;

import edu.dataframe.calculator.FloatCalculator;

import java.util.List;
import java.util.stream.Collectors;

public class Scaler {

    private final FloatCalculator calculator;
    private final List<Float> list;

    public Scaler(List<Float> list) {
        calculator = new FloatCalculator(list, null);
        this.list = list;
    }

    public List<Float> standardScaling() {
        float avg = calculator.mean();
        float standardDeviation = calculator.standardDeviation();
        return list.stream()
                .map(n -> (n - avg) / standardDeviation)
                .collect(Collectors.toList());
    }

    public List<Float> minMaxScaling() {
        float min = calculator.min();
        float range = calculator.range();
        return list.stream()
                .map(n -> (n - min) / range)
                .collect(Collectors.toList());
    }
}
