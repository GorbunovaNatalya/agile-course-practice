package ru.unn.agile.StatisticalValues.model;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.stream.DoubleStream;

public final class DescriptiveStatistics {

    public static double mean(final double[] inputSample) throws IllegalArgumentException {
        validateSample(inputSample);
        if (inputSample.length == 1) {
            return inputSample[0];
        } else {
            return moment(inputSample, 1);
        }
    }

    public static double variance(final double[] inputSample) throws IllegalArgumentException {
        return variance(inputSample, true);
    }

    public static double variance(final double[] inputSample,
                                  final boolean isBiased) throws IllegalArgumentException {
        validateSample(inputSample);
        int sampleLength = inputSample.length;
        if (sampleLength == 1) {
            return 0.0;
        }
        if (isBiased) {
            return centralMoment(inputSample, 2) * (sampleLength - 1) / sampleLength;
        } else {
            return centralMoment(inputSample, 2);
        }
    }

    public static double standardDeviation(final double[] inputSample,
                                           final boolean isBiased) throws IllegalArgumentException {
        return Math.sqrt(variance(inputSample, isBiased));
    }

    public static double median(final double[] inputSample) throws IllegalArgumentException {
        validateSample(inputSample);
        if (inputSample.length == 1) {
            return inputSample[0];
        }
        final double[] workingSample = inputSample.clone();
        Arrays.sort(workingSample);
        final int medianIndex = workingSample.length / 2;
        if (isEven(workingSample.length)) {
            return (workingSample[medianIndex] + workingSample[medianIndex - 1]) / 2;
        } else {
            return workingSample[medianIndex];
        }
    }

    public static int[] mode(final int[] inputSample) throws IllegalArgumentException {
        validateSample(inputSample);
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int element : inputSample) {
            if (frequencyMap.containsKey(element)) {
                frequencyMap.put(element, frequencyMap.get(element) + 1);
            } else {
                frequencyMap.put(element, 1);
            }
        }
        final int highestFrequency = Collections.max(frequencyMap.values());
        List<Integer> modes = new LinkedList<>();
        for (int element : frequencyMap.keySet()) {
            if (frequencyMap.get(element) == highestFrequency) {
                modes.add(element);
            }
        }
        return modes.stream().mapToInt(i -> i).toArray();
    }

    public static double moment(final double[] inputSample,
                                final int order) throws IllegalArgumentException {
        validateSample(inputSample);
        if (order < 0) {
            throw new IllegalArgumentException("Moment order should be over zero");
        }
        final DoubleStream sampleStream = Arrays.stream(inputSample);
        return sampleStream.map(element -> Math.pow(element, order)).average().orElse(0.0);
    }

    public static double centralMoment(final double[] inputSample,
                                       final int order) throws IllegalArgumentException {
        validateSample(inputSample);
        if (order < 0) {
            throw new IllegalArgumentException("Moment order should be over zero");
        }
        final double[] centralizedSample = centralizeSample(inputSample);
        return moment(centralizedSample, order) * inputSample.length / (inputSample.length - 1);
    }

    private static double[] centralizeSample(final double[] inputSample) {
        final double sampleMean = mean(inputSample);
        final DoubleStream sampleStream = Arrays.stream(inputSample);
        return sampleStream.map(element -> element - sampleMean).toArray();
    }

    private static boolean isEven(final int number) {
        return number % 2 == 0;
    }

    private static void validateSample(final int[] inputSample) {
        if (inputSample == null) {
            throw new IllegalArgumentException("Input sample can't be null");
        }
        if (inputSample.length == 0) {
            throw new IllegalArgumentException("Input sample must contain at least one element");
        }
    }

    private static void validateSample(final double[] inputSample) {
        if (inputSample == null) {
            throw new IllegalArgumentException("Input sample can't be null");
        }
        if (inputSample.length == 0) {
            throw new IllegalArgumentException("Input sample must contain at least one element");
        }
    }

    private DescriptiveStatistics() {
    }
}
