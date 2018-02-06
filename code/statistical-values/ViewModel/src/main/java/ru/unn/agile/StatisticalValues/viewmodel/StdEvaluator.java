package ru.unn.agile.StatisticalValues.viewmodel;

import ru.unn.agile.StatisticalValues.model.DescriptiveStatistics;

class StdEvaluator implements Computable {
    @Override
    public Printable compute() {
        return new ScalarStatistic<>(
                DescriptiveStatistics.standardDeviation(inputSample, isBiased)
        );
    }

    @Override
    public boolean isOrderUsing() {
        return false;
    }

    @Override
    public boolean isBiasUsing() {
        return true;
    }

    StdEvaluator(final double[] inputSample, final boolean isBiased) {
        this.inputSample = inputSample;
        this.isBiased = isBiased;
    }

    private final double[] inputSample;
    private final boolean isBiased;
}
