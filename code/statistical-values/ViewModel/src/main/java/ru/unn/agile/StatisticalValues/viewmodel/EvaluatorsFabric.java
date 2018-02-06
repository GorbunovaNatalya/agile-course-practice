package ru.unn.agile.StatisticalValues.viewmodel;

import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public final class EvaluatorsFabric {

    public static Computable create(final ViewModel viewModel) {
        Statistic statistic = viewModel.statisticProperty().get();

        Computable evaluator;
        switch (statistic) {
            case CENTRAL_MOMENT:
                evaluator = createCentralMomentEvaluator(viewModel);
            break;

            case MEAN:
                evaluator = createMeanEvaluator(viewModel);
            break;

            case MEDIAN:
                evaluator = createMedianEvaluator(viewModel);
            break;

            case MODE:
                evaluator = createModeEvaluator(viewModel);
            break;

            case MOMENT:
                evaluator = createMomentEvaluator(viewModel);
            break;

            case STD:
                evaluator = createStdEvaluator(viewModel);
            break;

            case VARIANCE:
                evaluator = createVarianceEvaluator(viewModel);
            break;

            default:
                throw new IllegalArgumentException("Undefined statistic type");
        }

        return evaluator;
    }

    private static Computable createMeanEvaluator(final ViewModel viewModel) {
        final double[] inputSample = StringPropertyConverter.convertToDoubles(
            viewModel.valuesProperty()
        );
        return new MeanEvaluator(inputSample);
    }

    private static Computable createMedianEvaluator(final ViewModel viewModel) {
        final double[] inputSample = StringPropertyConverter.convertToDoubles(
            viewModel.valuesProperty()
        );
        return new MedianEvaluator(inputSample);
    }

    private static Computable createModeEvaluator(final ViewModel viewModel) {
        final int[] inputSample = StringPropertyConverter.convertToIntegers(
            viewModel.valuesProperty()
        );
        return new ModeEvaluator(inputSample);
    }

    private static Computable createVarianceEvaluator(final ViewModel viewModel) {
        final double[] inputSample = StringPropertyConverter.convertToDoubles(
            viewModel.valuesProperty()
        );
        final boolean isBiased = viewModel.isBiasedProperty().get();
        return new VarianceEvaluator(inputSample, isBiased);
    }

    private static Computable createStdEvaluator(final ViewModel viewModel) {
        final double[] inputSample = StringPropertyConverter.convertToDoubles(
            viewModel.valuesProperty()
        );
        final boolean isBiased = viewModel.isBiasedProperty().get();
        return new StdEvaluator(inputSample, isBiased);
    }

    private static Computable createMomentEvaluator(final ViewModel viewModel) {
        final double[] inputSample = StringPropertyConverter.convertToDoubles(
            viewModel.valuesProperty()
        );
        final int order = StringPropertyConverter.convertToInteger(
            viewModel.orderProperty()
        );
        return new MomentEvaluator(inputSample, order);
    }

    private static Computable createCentralMomentEvaluator(final ViewModel viewModel) {
        final double[] inputSample = StringPropertyConverter.convertToDoubles(
            viewModel.valuesProperty()
        );
        final int order = StringPropertyConverter.convertToInteger(viewModel.orderProperty());
        return new CentralMomentEvaluator(inputSample, order);
    }

    private static class StringPropertyConverter {
        private static double[] convertToDoubles(final StringProperty inputSample) {
            return convertToDoubles(inputSample, ",");
        }

        private static double[] convertToDoubles(final StringProperty inputSample,
                                                 final String sep) {
            final String input = inputSample.get();
            final String[] values = input.replaceAll("\\s+", "").split(sep);
            final ArrayList<Double> sample = new ArrayList<>();
            for (String value : values) {
                sample.add(Double.valueOf(value));
            }

            final double[] result = new double[sample.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = sample.get(i);
            }
            return result;
        }

        private static int[] convertToIntegers(final StringProperty inputSample) {
            return convertToIntegers(inputSample, ",");
        }

        private static int[] convertToIntegers(final StringProperty inputSample,
                                               final String sep) {
            final String input = inputSample.get();
            final String[] values = input.replaceAll("\\s+", "").split(sep);
            final ArrayList<Integer> sample = new ArrayList<>();
            for (String value : values) {
                sample.add(Integer.valueOf(value));
            }

            final int[] result = new int[sample.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = sample.get(i);
            }
            return result;
        }

        private static int convertToInteger(final StringProperty stringProperty) {
            return Integer.valueOf(stringProperty.get());
        }
    }

    private EvaluatorsFabric() {

    }
}
