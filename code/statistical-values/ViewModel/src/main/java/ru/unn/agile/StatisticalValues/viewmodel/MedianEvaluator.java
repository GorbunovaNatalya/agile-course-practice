package ru.unn.agile.StatisticalValues.viewmodel;

import ru.unn.agile.StatisticalValues.model.DescriptiveStatistics;

class MedianEvaluator implements Computable {
    @Override
    public Printable compute() {
        return new ScalarStatistic<>(DescriptiveStatistics.median(inputSample));
    }

    @Override
    public boolean isOrderUsing() {
        return false;
    }

    @Override
    public boolean isBiasUsing() {
        return false;
    }

    MedianEvaluator(final double[] inputSample) {
        this.inputSample = inputSample;
    }

    private final double[] inputSample;
}
