package ru.unn.agile.StatisticalValues.model;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class DescriptiveStatisticsTests {
    @Test
    public void meanIsCalculatedCorrectly() {
        final double[] inputSample = {1.0, 2.0, 3.0};

        final double mean = DescriptiveStatistics.mean(inputSample);

        assertEquals(2.0, mean, 10e-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void meanForNullThrowsIllegalArgumentException() {
        final double[] nullSample = null;

        DescriptiveStatistics.mean(nullSample);
    }

    @Test(expected = IllegalArgumentException.class)
    public void meanForEmptySampleThrowsIllegalArgumentException() {
        final double[] emptySample = {};

        DescriptiveStatistics.mean(emptySample);
    }

    @Test
    public void varianceIsOverZero() {
        final double[] inputSample = {1.0, 2.0, 3.0};

        final double variance = DescriptiveStatistics.variance(inputSample);

        assertTrue(variance > 0.0);
    }

    @Test
    public void varianceIsCalculatedCorrectly() {
        final double[] inputSample = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};

        final double variance = DescriptiveStatistics.variance(inputSample);

        assertEquals(8.25, variance, 10e-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void varianceForNullThrowsIllegalArgumentException() {
        final double[] nullSample = null;

        DescriptiveStatistics.variance(nullSample);
    }

    @Test(expected = IllegalArgumentException.class)
    public void varianceForEmptySampleThrowsIllegalArgumentException() {
        final double[] emptySample = {};

        DescriptiveStatistics.variance(emptySample);
    }

    @Test
    public void unbiasedVarianceIsCalculatedCorrectly() {
        final double[] inputSample = {0.0, 1.0, 2.0, 3.0, 4.0};

        final double unbiasedVariance = DescriptiveStatistics.variance(inputSample, false);

        assertEquals(2.5, unbiasedVariance, 10e-5);
    }

    @Test
    public void unbiasedVarianceForSampleWithSingleElementIsZero() {
        final double[] sampleWithOneElement = {2.0};

        final double unbiasedVariance = DescriptiveStatistics.variance(sampleWithOneElement, false);

        assertEquals(0.0, unbiasedVariance, 10e-5);
    }

    @Test
    public void medianIsCalculatedCorrectlyForSampleWithOddLength() {
        final double[] inputSample = {10.0, 5.0, 6.0, 7.0, 3.0, 1.0, 2.0, 4.0, 8.0, 9.0, 0.0};

        final double median = DescriptiveStatistics.median(inputSample);

        assertEquals(5.0, median, 10e-5);
    }

    @Test
    public void medianIsCalculatedCorrectlyForSampleWithEvenLength() {
        final double[] inputSample = {1.0, 3.0, 0.0, 2.0, 4.0, 9.0, 7.0, 8.0, 6.0, 5.0};

        final double median = DescriptiveStatistics.median(inputSample);

        assertEquals(4.5, median, 10e-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void medianForEmptySampleThrowsIllegalArgumentException() {
        final double[] emptySample = {};

        DescriptiveStatistics.median(emptySample);
    }

    @Test(expected = IllegalArgumentException.class)
    public void medianForNullThrowsIllegalArgumentException() {
        final double[] nullSample = null;

        DescriptiveStatistics.median(nullSample);
    }

    @Test
    public void medianForSampleWithSingleElementIsCalculatedCorrectly() {
        final double[] sampleWithOneElement = {3.5};

        final double median = DescriptiveStatistics.median(sampleWithOneElement);

        assertEquals(3.5, median, 10e-5);
    }

    @Test
    public void modeForIntegerUnimodalSampleIsCalculatedCorrect() {
        final int[] unimodalSample = {4, 5, 3, 2, 1, 1};

        final int[] modes = DescriptiveStatistics.mode(unimodalSample);

        assertArrayEquals(new int[]{1}, modes);
    }

    @Test
    public void modeForIntegerBimodalSampleIsCalculatedCorrect() {
        final int[] bimodalSample = {3, 2, 2, 1, 4, 5, 4, 10};

        final int[] modes = DescriptiveStatistics.mode(bimodalSample);

        assertArrayEquals(new int[]{2, 4}, modes);
    }

    @Test
    public void modeForIntegerGeneralSampleIsCalculatedCorrect() {
        final int[] inputSample = {3, 4, 3, 1, 2, 1, 2, 8, 6, 8, 4};

        final int[] modes = DescriptiveStatistics.mode(inputSample);

        assertArrayEquals(new int[]{1, 2, 3, 4, 8}, modes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void modeForEmptySampleThrowsIllegalArgumentException() {
        final int[] emptySample = {};

        DescriptiveStatistics.mode(emptySample);
    }

    @Test(expected = IllegalArgumentException.class)
    public void modeForNullThrowsIllegalArgumentException() {
        final int[] nullSample = null;

        DescriptiveStatistics.mode(nullSample);
    }

    @Test
    public void momentForPositiveOrderIsCalculatedCorrect() {
        final double[] inputSample = {2.0, 1.0, 3.0, 4.0, 2.5};
        final int momentOrder = 5;

        final double moment = DescriptiveStatistics.moment(inputSample, momentOrder);

        assertEquals(279.53125, moment, 10e-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void momentForNegativeOrderThrowsIllegalArgumentException() {
        final double[] inputSample = {2.0, 1.0};
        final int negativeMomentOrder = -4;

        DescriptiveStatistics.moment(inputSample, negativeMomentOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void momentForNullThrowsIllegalArgumentException() {
        final double[] nullSample = null;
        final int order = 4;

        DescriptiveStatistics.moment(nullSample, order);
    }

    @Test(expected = IllegalArgumentException.class)
    public void momentForEmptySampleThrowsIllegalArgumentException() {
        final double[] emptySample = {};
        final int order = 4;

        DescriptiveStatistics.moment(emptySample, order);
    }

    @Test
    public void centralMomentForPositiveOrderIsCalculatedCorrect() {
        final double[] inputSample = {2.0, 1.0, 3.0, 4.0, 4.0};
        final int momentOrder = 5;

        final double moment = DescriptiveStatistics.centralMoment(inputSample, momentOrder);

        assertEquals(-3.5616, moment, 10e-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void centralMomentForNegativeOrderThrowsIllegalArgumentException() {
        final double[] inputSample = {2.0, 1.0};
        final int negativeMomentOrder = -4;

        DescriptiveStatistics.centralMoment(inputSample, negativeMomentOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void centralMomentForNullThrowsIllegalArgumentException() {
        final double[] nullSample = null;
        final int order = 4;

        DescriptiveStatistics.moment(nullSample, order);
    }

    @Test(expected = IllegalArgumentException.class)
    public void centralMomentForEmptySampleThrowsIllegalArgumentException() {
        final double[] emptySample = {};
        final int order = 4;

        DescriptiveStatistics.centralMoment(emptySample, order);
    }

    @Test
    public void biasedStandardDeviationIsCalculatedCorrectly() {
        final double[] inputSample = {2.0, 4.0, 4.0, 5.0, 4.0, 5.0, 7.0, 9.0};
        final boolean isBiased = true;

        final double standardDeviation = DescriptiveStatistics.standardDeviation(inputSample,
                isBiased);

        assertEquals(2.0, standardDeviation, 10e-5);
    }

    @Test
    public void unBiasedStandardDeviationIsCalculatedCorrectly() {
        final double[] inputSample = {2.0, 4.0, 4.0, 5.0, 4.0, 5.0, 7.0, 9.0};
        final boolean isBiased = false;

        final double standardDeviation = DescriptiveStatistics.standardDeviation(inputSample,
                isBiased);

        assertEquals(2.1380899, standardDeviation, 10e-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void standardDeviationForNullThrowsIllegalArgumentException() {
        final double[] nullSample = null;
        final boolean isBiased = false;

        DescriptiveStatistics.standardDeviation(nullSample, isBiased);
    }

    @Test(expected = IllegalArgumentException.class)
    public void standardDeviationForEmptySampleThrowsIllegalArgumentException() {
        final double[] emptySample = {};
        final boolean isBiased = true;

        DescriptiveStatistics.standardDeviation(emptySample, isBiased);
    }
}
