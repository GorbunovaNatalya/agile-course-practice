package ru.unn.agile.StatisticalValues.viewmodel;

import ru.unn.agile.StatisticalValues.model.DescriptiveStatistics;

class MomentEvaluator implements Computable {
    @Override
    public Printable compute() {
        return new ScalarStatistic<>(DescriptiveStatistics.moment(inputSample, order));
    }

    @Override
    public boolean isOrderUsing() {
        return true;
    }

    @Override
    public boolean isBiasUsing() {
        return false;
    }

    MomentEvaluator(final double[] inputSample, final int order) {
        this.inputSample = inputSample;
        this.order = order;
    }

    private final double[] inputSample;
    private final int order;
}
