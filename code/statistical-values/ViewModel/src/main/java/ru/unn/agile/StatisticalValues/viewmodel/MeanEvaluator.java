package ru.unn.agile.StatisticalValues.viewmodel;

import ru.unn.agile.StatisticalValues.model.DescriptiveStatistics;

class MeanEvaluator implements Computable {
    @Override
    public Printable compute() {
        return new ScalarStatistic<>(DescriptiveStatistics.mean(inputSample));
    }

    @Override
    public boolean isOrderUsing() {
        return false;
    }

    @Override
    public boolean isBiasUsing() {
        return false;
    }

    MeanEvaluator(final double[] inputSample) {
        this.inputSample = inputSample;
    }

    private final double[] inputSample;
}
