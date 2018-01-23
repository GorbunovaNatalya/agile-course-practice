package ru.unn.agile.StatisticalValues.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ViewModelTests {
    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("0", viewModel.orderProperty().get());
        assertFalse(viewModel.isOrderVisible());

        assertFalse(viewModel.isBiasedProperty().get());
        assertFalse(viewModel.isBiasVisible());

        assertEquals("0.0, 0.0", viewModel.valuesProperty().get());
        assertFalse(viewModel.isValuesVisible());

        assertEquals("", viewModel.resultProperty().get());
        assertFalse(viewModel.isResultVisible());

        assertFalse(viewModel.isCalculateVisible());

        assertEquals(Status.WAIT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitWhenStatisticHasNotBeenChosen() {
        viewModel.calculate();
        assertEquals(Status.WAIT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void orderIsInvisibleWhenStatisticHasNotBeenChosen() {
        viewModel.calculate();

        assertFalse(viewModel.isOrderVisible());
    }

    @Test
    public void biasIsInvisibleWhenStatisticHasNotBeenChosen() {
        viewModel.calculate();

        assertFalse(viewModel.isBiasVisible());
    }

    @Test
    public void valuesAreInvisibleWhenStatisticHasNotBeenChosen() {
        viewModel.calculate();

        assertFalse(viewModel.isValuesVisible());
    }

    @Test
    public void calculateIsInvisibleWhenStatisticHasNotBeenChosen() {
        viewModel.calculate();

        assertFalse(viewModel.isResultVisible());
    }

    @Test
    public void resultIsInvisibleWhenStatisticHasNotBeenChosen() {
        viewModel.calculate();

        assertFalse(viewModel.isResultVisible());
    }

    @Test
    public void calculationIsInvisibleByInvalidOrder() {
        viewModel.statisticProperty().set(Statistic.MOMENT);

        viewModel.orderProperty().set("Invalid");

        assertFalse(viewModel.isCalculateVisible());
    }

    @Test
    public void calculationIsInvisibleByInvalidValues() {
        viewModel.statisticProperty().set(Statistic.MEAN);

        viewModel.valuesProperty().set("Invalid");

        assertFalse(viewModel.isCalculateVisible());
    }

    @Test
    public void statusIsBadFormatByNegativeOrder() {
        viewModel.statisticProperty().set(Statistic.MOMENT);
        viewModel.orderProperty().set("-1");

        viewModel.calculate();

        assertTrue(viewModel.statusProperty().get().contains(Status.BAD.toString()));
    }

    @Test
    public void calculationIsVisibleBySettingStatistic() {
        viewModel.statisticProperty().set(Statistic.MEAN);

        assertTrue(viewModel.isCalculateVisible());
    }

    @Test
    public void orderIsVisibleBySettingStatisticUsingOrder() {
        viewModel.statisticProperty().set(Statistic.MOMENT);

        assertTrue(viewModel.isOrderVisible());
    }

    @Test
    public void orderIsInvisibleBySettingStatisticWithoutOrder() {
        viewModel.statisticProperty().set(Statistic.STD);

        assertFalse(viewModel.isOrderVisible());
    }

    @Test
    public void orderIsHiddenBySettingStatisticWithoutOrder() {
        viewModel.statisticProperty().set(Statistic.MOMENT);

        viewModel.statisticProperty().set(Statistic.MEAN);

        assertFalse(viewModel.isOrderVisible());
    }

    @Test
    public void orderIsShownBySettingStatisticUsingOrder() {
        viewModel.statisticProperty().set(Statistic.MEAN);

        viewModel.statisticProperty().set(Statistic.MOMENT);

        assertTrue(viewModel.isOrderVisible());
    }

    @Test
    public void biasIsVisibleBySettingStatisticUsingBias() {
        viewModel.statisticProperty().set(Statistic.VARIANCE);

        assertTrue(viewModel.isBiasVisible());
    }

    @Test
    public void biasIsInvisibleBySettingStatisticWithoutBias() {
        viewModel.statisticProperty().set(Statistic.MEAN);

        assertFalse(viewModel.isBiasVisible());
    }

    @Test
    public void biasIsHiddenBySettingStatisticWithoutBias() {
        viewModel.statisticProperty().set(Statistic.VARIANCE);

        viewModel.statisticProperty().set(Statistic.MEAN);

        assertFalse(viewModel.isBiasVisible());
    }

    @Test
    public void biasIsShownBySettingStatisticUsingBias() {
        viewModel.statisticProperty().set(Statistic.MEAN);

        viewModel.statisticProperty().set(Statistic.VARIANCE);

        assertTrue(viewModel.isBiasVisible());
    }

    @Test
    public void resultIsInvisibleBeforeCalculate() {
        viewModel.statisticProperty().set(Statistic.MEAN);

        assertFalse(viewModel.isResultVisible());
    }

    @Test
    public void resultIsVisibleAfterCalculate() {
        viewModel.statisticProperty().set(Statistic.MEAN);

        viewModel.calculate();

        assertTrue(viewModel.isResultVisible());
    }

    @Test
    public void resultIsHiddenByInvalidInput() {
        viewModel.statisticProperty().set(Statistic.MOMENT);
        viewModel.calculate();

        viewModel.orderProperty().set("");

        assertFalse(viewModel.isResultVisible());
    }

    @Test
    public void statusIsWaitByEmptyInput() {
        viewModel.statisticProperty().set(Statistic.MEAN);
        viewModel.calculate();

        viewModel.valuesProperty().set("");

        assertEquals(Status.WAIT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyBySettingStatistic() {
        viewModel.statisticProperty().set(Statistic.MEAN);

        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsSuccessByCalculation() {
        viewModel.statisticProperty().set(Statistic.MEAN);

        viewModel.calculate();

        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateIsHiddenByNotNumbersInValues() {
        viewModel.statisticProperty().set(Statistic.MEAN);

        viewModel.valuesProperty().set("0.0,;");

        assertFalse(viewModel.isCalculateVisible());
    }

    @Test
    public void calculateIsHiddenByRealNumbersInValuesForMode() {
        viewModel.statisticProperty().set(Statistic.MODE);

        viewModel.valuesProperty().set("0.0, 1.0");

        assertFalse(viewModel.isCalculateVisible());
    }

    @Test
    public void resultIsHiddenByRealNumbersInValuesForMode() {
        viewModel.statisticProperty().set(Statistic.MODE);

        viewModel.valuesProperty().set("0.0, 1.0");

        assertFalse(viewModel.isResultVisible());
    }

    @Test
    public void resultHasSeveralNumbersForMode() {
        viewModel.statisticProperty().set(Statistic.MODE);
        viewModel.valuesProperty().set("1, 2, 3, 4, 5, 6");

        viewModel.calculate();

        String result = viewModel.resultProperty().get();
        assertTrue(0 < getNumberOfOccurrences(result));
    }


    @Test
    public void meanIsCalculatedCorrectly() {
        viewModel.statisticProperty().set(Statistic.MEAN);
        viewModel.valuesProperty().set("1.0, 2.0, 3.0");

        viewModel.calculate();

        assertEquals("2.0", viewModel.resultProperty().get());
    }


    @Test
    public void varianceIsCalculatedCorrectly() {
        viewModel.statisticProperty().set(Statistic.VARIANCE);
        viewModel.valuesProperty().set("0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0");
        viewModel.isBiasedProperty().set(true);

        viewModel.calculate();

        assertEquals("8.25", viewModel.resultProperty().get());
    }

    @Test
    public void unbiasedVarianceIsCalculatedCorrectly() {
        viewModel.statisticProperty().set(Statistic.VARIANCE);
        viewModel.valuesProperty().set("0.0, 1.0, 2.0, 3.0, 4.0");

        viewModel.calculate();

        assertEquals("2.5", viewModel.resultProperty().get());
    }

    @Test
    public void modeIsCalculatedCorrectly() {
        viewModel.statisticProperty().set(Statistic.MODE);
        viewModel.valuesProperty().set("4, 5, 3, 2, 1, 1");

        viewModel.calculate();

        assertEquals("1", viewModel.resultProperty().get());
    }

    @Test
    public void modeForBimodalSampleIsCalculatedCorrect() {
        viewModel.statisticProperty().set(Statistic.MODE);
        viewModel.valuesProperty().set("3, 2, 2, 1, 4, 5, 4, 10");

        viewModel.calculate();

        assertEquals("2, 4", viewModel.resultProperty().get());
    }

    @Test
    public void momentForPositiveOrderIsCalculatedCorrect() {
        viewModel.statisticProperty().set(Statistic.MOMENT);
        viewModel.valuesProperty().set("2.0, 1.0, 3.0, 4.0, 2.5");
        viewModel.orderProperty().set("5");

        viewModel.calculate();

        assertEquals("279.53125", viewModel.resultProperty().get());
    }


    @Test
    public void centralMomentForPositiveOrderIsCalculatedCorrect() {
        viewModel.statisticProperty().set(Statistic.CENTRAL_MOMENT);
        viewModel.valuesProperty().set("2.0, 1.0, 3.0, 4.0, 4.0");
        viewModel.orderProperty().set("5");

        viewModel.calculate();

        assertEquals(-3.5616, Double.valueOf(viewModel.resultProperty().get()), 10e-6);
    }


    @Test
    public void biasedStandardDeviationIsCalculatedCorrectly() {
        viewModel.statisticProperty().set(Statistic.STD);
        viewModel.valuesProperty().set("2.0, 4.0, 4.0, 5.0, 4.0, 5.0, 7.0, 9.0");
        viewModel.isBiasedProperty().set(true);

        viewModel.calculate();

        assertEquals(2.0, Double.valueOf(viewModel.resultProperty().get()), 10e-6);
    }

    @Test
    public void unBiasedStandardDeviationIsCalculatedCorrectly() {
        viewModel.statisticProperty().set(Statistic.STD);
        viewModel.valuesProperty().set("2.0, 4.0, 4.0, 5.0, 4.0, 5.0, 7.0, 9.0");
        viewModel.isBiasedProperty().set(false);

        viewModel.calculate();

        assertEquals(2.1380899, Double.valueOf(viewModel.resultProperty().get()), 10e-6);
    }

    @Test
    public void medianIsCalculatedCorrectlyForSampleWithOddLength() {
        viewModel.statisticProperty().set(Statistic.MEDIAN);
        viewModel.valuesProperty().set("10.0, 5.0, 6.0, 7.0, 3.0, 1.0, 2.0, 4.0, 8.0, 9.0, 0.0");

        viewModel.calculate();

        assertEquals("5.0", viewModel.resultProperty().get());
    }

    @Test
    public void medianIsCalculatedCorrectlyForSampleWithEvenLength() {
        viewModel.statisticProperty().set(Statistic.MEDIAN);
        viewModel.valuesProperty().set("1.0, 3.0, 0.0, 2.0, 4.0, 9.0, 7.0, 8.0, 6.0, 5.0");

        viewModel.calculate();

        assertEquals("4.5", viewModel.resultProperty().get());
    }

    private int getNumberOfOccurrences(final String src) {
        int numberOfOccurrences = 0;
        for (int position = 0; position < src.length(); position++) {
            if (src.charAt(position) == ',') {
                numberOfOccurrences++;
            }
        }

        return numberOfOccurrences;
    }

    private ViewModel viewModel;
}
