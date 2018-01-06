package ru.unn.agile.MetricsDistance.viewmodel;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import ru.unn.agile.MetricsDistance.Model.Metric;

public class ViewModelTests {

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getVec1X());
        assertEquals("", viewModel.getVec1Y());
        assertEquals("", viewModel.getVec2X());
        assertEquals("", viewModel.getVec2Y());
        assertEquals("", viewModel.getDim());
        assertEquals(Metric.Chebyshev, viewModel.getMetric());
        assertEquals("", viewModel.getResult());
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyFields() {
        viewModel.calculate();
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void canReportBadFormat() {
        viewModel.setVec1X("a");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsWaitingIfNotEnoughCorrectData() {
        viewModel.setVec1X("1");
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsDisabledInitially() {
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void calculateButtonIsDisabledWhenFormatIsBad() {
        setInputData("1", "2", "3", "4", "");
        viewModel.setVec1X("trash");
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void calculateButtonIsDisabledWithIncompleteInput() {
        viewModel.setVec1X("1");
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void calculateButtonIsEnabledWithCorrectInput() {
        setInputData("1", "2", "3", "4", "");
        assertFalse(viewModel.isCalculationDisabled());
    }

    @Test
    public void canSetMinkowskiL1Metric() {
        viewModel.setMetric(Metric.Minkowski);
        assertEquals(Metric.Minkowski, viewModel.getMetric());
    }

    @Test
    public void canSetChebyshevMetric() {
        viewModel.setMetric(Metric.Chebyshev);
        assertEquals(Metric.Chebyshev, viewModel.getMetric());
    }

    @Test
    public void chebyshevIsDefaultOperation() {
        assertEquals(Metric.Chebyshev, viewModel.getMetric());
    }

    @Test
    public void chebyshevMetricHasCorrectResult() {
        setInputData("2", "2", "1", "3", "");
        viewModel.setMetric(Metric.Chebyshev);
        viewModel.calculate();
        assertEquals("1.0", viewModel.getResult());
    }

    @Test
    public void canSetSuccessMessage() {
        setInputData("1", "2", "3", "4", "");
        viewModel.calculate();
        assertEquals(Status.SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void canSetBadFormatMessage() {
        viewModel.setVec1X("#selfie");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsReadyWhenSetProperData() {
        setInputData("1", "2", "3", "4", "");
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void minkowskiL1MetircHasCorrectResult() {
        setInputData("3", "3", "1", "3", "1");
        viewModel.setMetric(Metric.Minkowski);
        viewModel.calculate();
        assertEquals("2.0", viewModel.resultProperty().get());
    }

    @Test
    public void minkowskiL2MetircHasCorrectResult() {
        setInputData("3", "3", "1", "3", "2");
        viewModel.setMetric(Metric.Minkowski);
        viewModel.calculate();
        assertEquals("2.0", viewModel.resultProperty().get());
    }
    @Test
    public void minkowskiL3MetircHasCorrectResult() {
        setInputData("3", "3", "1", "3", "3");
        viewModel.setMetric(Metric.Minkowski);
        viewModel.calculate();
        assertEquals("2.0", viewModel.resultProperty().get());
    }

    @Test
    public void chebyshevMetricWithNegativeNumbersHasCorrectResult() {
        setInputData("1.2", "2.3", "-1.4", "-2.5", "");
        viewModel.setMetric(Metric.Chebyshev);
        viewModel.calculate();
        assertEquals("4.8", viewModel.resultProperty().get());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModelLogged = new ViewModel(logger);

        assertNotNull(viewModelLogged);
    }

    @Test
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        try {
            new ViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger parameter can't be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsProperMessageAfterCalculation() {
        setInputData("3", "3", "1", "3", "1");

        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.CALCULATE_WAS_PRESSED + ".*"));
    }

    @Test
    public void logContainsInputArgumentsAfterCalculation() {
        setInputData("3", "3", "1", "3", "1");
        viewModel.setMetric(Metric.Chebyshev);

        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + viewModel.getVec1X()
                + ".*" + viewModel.getVec1Y()
                + ".*" + viewModel.getVec2X()
                + ".*" + viewModel.getVec2Y()
                + ".*" + viewModel.getMetric().toString() + ".*"));
    }

    @Test
    public void logContainsResultAfterCalculation() {
        setInputData("3", "3", "1", "3", "1");

        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + viewModel.getResult() + ".*"));
    }

    @Test
    public void argumentsInfoIsProperlyFormatted() {
        setInputData("3", "3", "1", "3", "1");
        viewModel.setMetric(Metric.Chebyshev);

        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*Arguments"
                + ": Vec1X = " + viewModel.getVec1X()
                + "; Vec1Y = " + viewModel.getVec1Y()
                + "; Vec2X = " + viewModel.getVec2X()
                + "; Vec2Y = " + viewModel.getVec2Y()
                + "; Metric = " + viewModel.getMetric()
                + "; Result = " + viewModel.getResult() + ".*"));
    }

    @Test
    public void logContainsDimAfterCalculationWhenMinkowskiMetric() {
        setInputData("3", "3", "1", "3", "5");
        viewModel.setMetric(Metric.Minkowski);

        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + viewModel.getVec1X()
                + ".*" + viewModel.getMetric().toString()
                + ".*" + viewModel.getDim() + ".*"));
    }

    @Test
    public void argumentsInfoIsProperlyFormattedWhenMinkowskiMetric() {
        setInputData("3", "3", "1", "3", "1");
        viewModel.setMetric(Metric.Minkowski);

        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*Arguments"
                + ": Vec1X = " + viewModel.getVec1X()
                + "; Vec1Y = " + viewModel.getVec1Y()
                + "; Vec2X = " + viewModel.getVec2X()
                + "; Vec2Y = " + viewModel.getVec2Y()
                + "; Metric = " + viewModel.getMetric()
                + "; Dim = " + viewModel.getDim()
                + "; Result = " + viewModel.getResult() + ".*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        setInputData("3", "3", "1", "3", "1");

        viewModel.calculate();
        viewModel.calculate();
        viewModel.calculate();

        assertEquals(3, viewModel.getLog().size());
    }

    @Test
    public void logContainsProperMessageAfterChangingMetric() {
        setInputData("3", "3", "1", "3", "1");

        viewModel.onMetricChanged(Metric.Chebyshev, Metric.Minkowski);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.METRIC_WAS_CHANGED + "Minkowski.*"));
    }

    @Test
    public void metricIsNotLoggedIfNotChanged() {
        viewModel.onMetricChanged(Metric.Chebyshev, Metric.Minkowski);

        viewModel.onMetricChanged(Metric.Chebyshev, Metric.Chebyshev);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void argumentsAreCorrectlyLogged() {
        setInputData("3", "3", "1", "3", "1");

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.EDITING_FINISHED
                + "Input arguments are: \\["
                + viewModel.getVec1X() + "; "
                + viewModel.getVec1Y() + "; "
                + viewModel.getVec2X() + "; "
                + viewModel.getVec2Y() + "; "
                + viewModel.getDim()+ "\\]"));
    }

    @Test
    public void doNotLogSameParametersTwiceWithPartialInput() {
        viewModel.setVec1X("1");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.setVec1X("1");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void logsAreEmptyByDefault() {
        assertEquals("", viewModel.getLogs());
    }

    @Test
    public void canChangeLogsWhenMetricChanged() {
        viewModel.onMetricChanged(Metric.Chebyshev, Metric.Minkowski);
        String message = viewModel.getLogs();

        assertEquals(LogMessages.METRIC_WAS_CHANGED + "Minkowski\n", message);
    }

    @Test
    public void canChangeLogsWhenFocusChanged() {
        viewModel.setVec1X("1");

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        String message = viewModel.getLogs();

        assertEquals(LogMessages.EDITING_FINISHED + "Input arguments are: [1; ; ; ; ]\n", message);
    }

    @Test
    public void canChangeLogsAfterCalculation() {
        setInputData("3", "3", "1", "3", "1");

        viewModel.calculate();
        String message = viewModel.getLogs();

        assertTrue(message.matches(".*" + LogMessages.CALCULATE_WAS_PRESSED + ".*\n"));
    }

    private ViewModel viewModel;

    private void setInputData(final String vec1X, final String vec1Y, final String vec2X,
                              final String vec2Y, final String dim) {
        viewModel.setVec1X(vec1X);
        viewModel.setVec1Y(vec1Y);
        viewModel.setVec2X(vec2X);
        viewModel.setVec2Y(vec2Y);
        viewModel.setDim(dim);
    }

}
