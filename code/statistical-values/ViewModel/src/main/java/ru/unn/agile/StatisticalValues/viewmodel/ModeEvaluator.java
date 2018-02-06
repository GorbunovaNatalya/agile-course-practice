package ru.unn.agile.StatisticalValues.viewmodel;

import ru.unn.agile.StatisticalValues.model.DescriptiveStatistics;

import java.util.Arrays;

class ModeEvaluator implements Computable {
    @Override
    public Printable compute() {
        Integer[] statistic = Arrays.stream(
                DescriptiveStatistics.mode(inputSample)
        ).boxed().toArray(Integer[]::new);
        return new VectorStatistic<>(statistic);
    }

    @Override
    public boolean isOrderUsing() {
        return false;
    }

    @Override
    public boolean isBiasUsing() {
        return false;
    }

    ModeEvaluator(final int[] inputSample) {
        this.inputSample = inputSample;
    }

    private final int[] inputSample;
}
