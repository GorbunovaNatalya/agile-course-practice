package ru.unn.agile.StatisticalValues.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;

public class ViewModelTests {
    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUpStatisticalValuesTests() {
        if (viewModel == null) {
            viewModel = new ViewModel(new FakeStatisticalLogger());
        }
    }

    @After
    public void afterTests() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("0", viewModel.orderProperty().get());
        assertFalse(viewModel.isOrderVisible());

        assertFalse(viewModel.getBias());
        assertFalse(viewModel.isBiasVisible());

        assertEquals("0.0, 0.0", viewModel.getValues());
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

    @Test
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        try {
            new ViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger parameter can't be null", ex.getMessage());
        }
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsProperMessageAfterCalculation() {
        viewModel.statisticProperty().set(Statistic.MEAN);

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(getRefCalculateLog().toString()));
    }

    @Test
    public void logContainsProperMessageAfterSetBiasAndPressCalculation() {
        viewModel.statisticProperty().set(Statistic.STD);
        viewModel.valuesProperty().set("2.0, 1.0, 3.0, 4.0, 2.5");
        viewModel.isBiasedProperty().set(true);

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(getRefCalculateLog().toString()));
    }

    @Test
    public void logContainsInputArgumentsAfterCalculation() {
        setInputData();

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        StringBuilder reference = new StringBuilder(".*");
        reference.append(viewModel.getValues())
                 .append(".*");
        assertTrue(message.matches(reference.toString()));
    }

    @Test
    public void statisticTypeIsMentionedInTheLog() {
        setInputData();

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*Mean.*"));
    }

    @Test
    public void canUsegetLogs() {
        setInputData();
        viewModel.calculate();

        String message = viewModel.getLogs();
        assertNotNull(message);
    }

    @Test
    public void addSeveralLogMessage() {
        setInputData();
        viewModel.calculate();
        viewModel.valuesProperty().set("1.0, 2.0, 3.0, 4.0");
        viewModel.calculate();

        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void logContainsEmptyPushCalculate() {
        setInputData();

        viewModel.calculate();
        viewModel.calculate();
        viewModel.calculate();

        assertEquals(3, viewModel.getLog().size());
    }

    @Test
    public void canSeeStatisticChangeInLog() {
        setInputData();

        viewModel.onStatisticChanged(Statistic.MEAN, Statistic.STD);

        String message = viewModel.getLog().get(0);
        StringBuilder reference = new StringBuilder(".*");
        reference.append(LogMessages.OPERATION_WAS_CHANGED)
                 .append("Std.*");
        assertTrue(message.matches(reference.toString()));
    }

    @Test
    public void statisticIsLoggedIfChanged() {
        viewModel.onStatisticChanged(Statistic.MEAN, Statistic.STD);
        viewModel.onStatisticChanged(Statistic.STD, Statistic.MODE);

        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void argumentsAreCorrectlyLoggedWhenStatisticIsMean() {
        viewModel.statisticProperty().set(Statistic.MEAN);
        viewModel.valuesProperty().set("1.0, 2.0, 3.0");

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(0);
        StringBuilder reference = new StringBuilder(".*");
        reference.append(LogMessages.EDITING_FINISHED)
                 .append(correctInputArgument(viewModel.getValues()));
        assertTrue(message.matches(reference.toString()));
    }

    @Test
    public void argumentsAreCorrectlyLoggedWhenStatisticIsMoment5() {
        viewModel.statisticProperty().set(Statistic.MOMENT);
        viewModel.valuesProperty().set("2.0, 1.0, 3.0, 4.0, 2.5");
        viewModel.orderProperty().set("3");

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(0);
        StringBuilder reference = new StringBuilder(".*");
        reference.append(LogMessages.EDITING_FINISHED)
                 .append(correctInputWithOrder(viewModel.getValues(), viewModel.getOrder()));
        assertTrue(message.matches(reference.toString()));
    }

    @Test
    public void argumentsAreCorrectlyLoggedWhenStatisticIsStdWithBias() {
        viewModel.statisticProperty().set(Statistic.STD);
        viewModel.valuesProperty().set("2.0, 1.0, 3.0, 4.0, 2.5");
        viewModel.isBiasedProperty().set(true);

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(0);
        StringBuilder reference = new StringBuilder(".*");
        reference.append(LogMessages.EDITING_FINISHED)
                 .append(correctInputWithBias(viewModel.getValues(), viewModel.getBias()));
        assertTrue(message.matches(reference.toString()));
    }

    @Test
    public void logChangeIsCorrectWhenStatisticIsMomentAndChangeOrderTwice() {
        viewModel.statisticProperty().set(Statistic.MOMENT);
        viewModel.valuesProperty().set("2.0, 1.0, 3.0, 4.0, 2.5");
        viewModel.orderProperty().set("5");

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.orderProperty().set("2");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(1);
        StringBuilder reference = new StringBuilder(".*");
        reference.append(LogMessages.EDITING_FINISHED)
                 .append(correctInputWithOrder(viewModel.getValues(), viewModel.getOrder()));

        assertTrue(message.matches(reference.toString()));
        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void logIsEmptyWhenChangesStatisticIsSimilar() {
        setInputData();

        viewModel.onStatisticChanged(Statistic.MEAN, Statistic.MEAN);

        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void canCreateViewModelWithEmptyConstructor() {
        ViewModel viewModelTest = null;

        viewModelTest = new ViewModel();

        assertNotNull(viewModelTest);
    }

    @Test
    public void logIsEmptyWhenFocusChangesIsFalseAndTrue() {
        viewModel.statisticProperty().set(Statistic.STD);
        viewModel.valuesProperty().set("2.0, 1.0, 3.0, 4.0, 2.5");
        viewModel.isBiasedProperty().set(false);

        viewModel.onFocusChanged(Boolean.FALSE, Boolean.TRUE);

        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void argumentsAreCorrectlyLoggedWhenOrderIsInvisible() {
        viewModel.statisticProperty().set(Statistic.STD);
        viewModel.valuesProperty().set("2.0, 1.0, 5.0, 4.0, 22.5");
        viewModel.isBiasedProperty().set(false);
        viewModel.orderProperty().set("2");

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(0);
        StringBuilder reference = new StringBuilder(".*");
        String inValues = viewModel.getValues();
        boolean inBias = viewModel.getBias();
        String inOrder = viewModel.getOrder();
        String refInput = correctInputWithAllParams(inValues, inBias, inOrder);
        reference.append(LogMessages.EDITING_FINISHED)
                 .append(refInput);

        assertFalse(message.matches(reference.toString()));
        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void argumentsAreCorrectlyLoggedWhenChangeBias() {
        viewModel.statisticProperty().set(Statistic.STD);
        viewModel.valuesProperty().set("2.0, 1.0, 5.0, 4.0, 22.5");

        viewModel.isBiasedProperty().set(false);

        viewModel.onBiasChanged(false, true);
        viewModel.onBiasChanged(true, false);

        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void argumentsAreCorrectlyLoggedWhenChangeOrder() {
        viewModel.statisticProperty().set(Statistic.MOMENT);
        viewModel.valuesProperty().set("2.0, 1.0, 5.0, 4.0, 22.5");
        viewModel.orderProperty().set("5");

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.orderProperty().set("2");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(2, viewModel.getLog().size());
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

    private String correctInputArgument(final String values) {
        return "Input arguments are: \\[" + values + "; " + "\\]";
    }

    private String correctInputWithBias(final String values, final boolean bias) {
        String result =  correctInputArgument(values)
                + " Bias argument is: \\[" + String.valueOf(bias)  + "\\]";
         return result;
    }

    private String correctInputWithOrder(final String values, final String order) {
        String result =  correctInputArgument(values)
                + " Order argument is: \\[" + order  + "\\]";
        return result;
    }

    private String correctInputWithAllParams(final String values, final boolean bias,
                                         final String order) {
        String result =  correctInputArgument(values)
                + " Bias argument is: \\[" + String.valueOf(bias)  + "\\]"
                + " Order argument is: \\[" + order  + "\\]";
        return result;
    }

    private void setInputData() {
        viewModel.statisticProperty().set(Statistic.MEAN);
        viewModel.valuesProperty().set("1.0, 2.0, 3.0");
    }

    private StringBuilder getRefCalculateLog() {
        StringBuilder reference = new StringBuilder(".*");
        reference.append(LogMessages.CALCULATE_WAS_PRESSED)
                .append(".*");
        return reference;
    }

    private ViewModel viewModel;
}
