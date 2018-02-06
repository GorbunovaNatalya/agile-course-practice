package ru.unn.agile.StringCalculator.viewmodel;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ViewModelTests {
    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        FakeLogger logger = new FakeLogger();
        viewModel = new ViewModel(logger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getInputString());
        assertEquals("", viewModel.getResult());
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsDisabledInitially() {
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void statusIsReadyWhenCorrectInput() {
        viewModel.setInputString("2,2");
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsEnabledWhenCorrectInput() {
        viewModel.setInputString("2,2");
        assertFalse(viewModel.isCalculationDisabled());
    }

    @Test
    public void canReportBadFormatWhenIncorrectInput() {
        viewModel.setInputString("2,a");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsDisabledWhenIncorrectInput() {
        viewModel.setInputString("2,a");
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void canReportBadFormatWhenNegativeNumberInInput() {
        viewModel.setInputString("2,-2");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsDisabledWhenNegativeNumberInInput() {
        viewModel.setInputString("2,-2");
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void canReportBadFormatWhenDelimiterIsIncorrect() {
        viewModel.setInputString(";2,2,2");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsDisabledWhenDelimiterIsIncorrect() {
        viewModel.setInputString(";2,2,2");
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyFields() {
        viewModel.calculate();
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateReturnsCorrectResultWhenCorrectInputString() {
        viewModel.setInputString("2,2");
        viewModel.calculate();
        assertEquals("4", viewModel.getResult());
    }

    @Test
    public void canSetSuccessMessage() {
        viewModel.setInputString("2,2");
        viewModel.calculate();
        assertEquals(Status.SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void canReportBadFormatWhenDifferentDelimiters() {
        viewModel.inputStringProperty().set(";2,2;2");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsDisabledWhenDifferentDelimiters() {
        viewModel.setInputString(";2,2;2");
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void canReportBadFormatWhenBigInputNumber() {
        viewModel.setInputString("2147483648,1");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsDisabledWhenBigInputNumber() {
        viewModel.setInputString("2147483648,1");
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void statusIsWaitingWhenInputDelimiterOnly() {
        viewModel.setInputString(".");
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateReturnsCorrectResultWhenDelimiter() {
        viewModel.setInputString(".5.2.2");
        viewModel.calculate();
        assertEquals("9", viewModel.getResult());
    }

    @Test
    public void canSetSuccessMessageWithDelimiter() {
        viewModel.setInputString(".5.2.2");
        viewModel.calculate();
        assertEquals(Status.SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenDelimiterIsNotInput() {
        viewModel.setInputString("3;7");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsReadyWhenCorrectInputWithDelimiter() {
        viewModel.setInputString(";3;7");
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsEnabledWhenCorrectInputWithDelimiter() {
        viewModel.setInputString(";3;7");
        assertFalse(viewModel.isCalculationDisabled());
    }

    @Test
    public void statusIsWaitingWhenDeleteInput() {
        viewModel.setInputString("5");
        viewModel.setInputString("");
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsDisabledWhenDeleteInput() {
        viewModel.setInputString("5");
        viewModel.setInputString("");
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void statusIsReadyWhenCorrectInputAfterIncorrect() {
        viewModel.setInputString("2,2.");
        viewModel.setInputString("2,2,6");
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsEnabledWhenCorrectInputAfterIncorrect() {
        viewModel.setInputString("2,2.");
        viewModel.setInputString("2,2,6");
        assertFalse(viewModel.isCalculationDisabled());
    }

    @Test
    public void statusIsBadFormatWhenIncorrectInputAfterCorrect() {
        viewModel.setInputString("2,2");
        viewModel.setInputString("2,2,p");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsDisabledWhenIncorrectInputAfterCorrect() {
        viewModel.setInputString("2,2");
        viewModel.setInputString("2,2,p");
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void statusIsBadFormatWhenIncorrectInputAfterCalculate() {
        viewModel.setInputString("2,2");
        viewModel.calculate();
        viewModel.setInputString("2,2,p");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void canCreateViewModelWithEmptyConstructor() {
        ViewModel viewModelTest = new ViewModel();
        assertNotNull(viewModelTest);
    }

    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModelWithLogger = new ViewModel(logger);
        assertNotNull(viewModelWithLogger);
    }

    @Test
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        try {
            new ViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException exception) {
            assertEquals("Parameter Logger can not be null", exception.getMessage());
        } catch (Exception exception) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void logTagIsEmptyInTheBeginning() {
        String logTag = viewModel.getLogTag();
        assertTrue(logTag.isEmpty());
    }

    @Test
    public void logsAreEmptyByDefault() {
        assertEquals("", viewModel.getLogs());
    }

    @Test
    public void logIsEmptyIfOffFocusChanged() {
        viewModel.setInputString("2,5");
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void logIsEmptyIfOnFocusChangedWithoutInput() {
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void logsEmptyIfNothingChanged() {
        viewModel.setInputString("2,5");
        viewModel.onFocusChanged(Boolean.FALSE, Boolean.TRUE);
        String message = viewModel.getLogs();
        assertEquals("", message);
    }

    @Test
    public void logIsNotEmptyIfOnFocusChanged() {
        viewModel.setInputString("2,5");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        String message = viewModel.getLogs();
        assertFalse(message.isEmpty());
    }

    @Test
    public void logContainsLogTagAfterFocusChanged() {
        viewModel.setInputString("2,5");
        viewModel.setLogTag("0");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + viewModel.getLogTag() + ".*"));
    }

    @Test
    public void logContainsLogTagAfterCalculation() {
        viewModel.setInputString("2,5");
        viewModel.setLogTag("0");
        viewModel.calculate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + viewModel.getLogTag() + ".*"));
    }

    @Test
    public void logContainsProperMessageAfterCalculation() {
        viewModel.setInputString("2,5");
        viewModel.calculate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.CALCULATE_WAS_PRESSED + ".*"));
    }

    @Test
    public void logContainsProperMessageAfterInputString() {
        viewModel.setInputString("2,5");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.EDITING_FINISHED + ".*"));
    }

    @Test
    public void logContainsInputStringAfterCalculation() {
        viewModel.setInputString("2,5");
        viewModel.calculate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + viewModel.getInputString() + ".*"));
    }

    @Test
    public void logContainsInputStringAfterFocusChanged() {
        viewModel.setInputString("2,5");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + viewModel.getInputString() + ".*"));
    }

    @Test
    public void logContainsResultAfterCalculation() {
        viewModel.setInputString("2,5");
        viewModel.calculate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + viewModel.getResult() + ".*"));
    }

    @Test
    public void logContainsProperResultAfterCalculation() {
        viewModel.setInputString("2,5");
        viewModel.calculate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + "Result = " + ".*"));
    }

    @Test
    public void argumentInfoIsProperlyFormatted() {
        viewModel.setInputString("2,5");
        viewModel.calculate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*Input string = " + viewModel.getInputString() + ".*"));
    }

    @Test
    public void argumentIsCorrectlyLogged() {
        viewModel.setInputString("2,5");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.EDITING_FINISHED
                + "Input string = " + viewModel.getInputString() + ".*"));
    }

    @Test
    public void logContainsInputCorrectMessageAfterCalculation() {
        viewModel.setInputString("2,5");
        viewModel.calculate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.CALCULATE_WAS_PRESSED
                + "Input string = " + viewModel.getInputString() + ".*"));
    }

    @Test
    public void logContainsProperlyFirstMessageAfterSeveralCalculations() {
        viewModel.setInputString("2,7");
        viewModel.calculate();
        viewModel.setInputString("2,8,7");
        viewModel.calculate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.CALCULATE_WAS_PRESSED
                + "Input string = " + "2,7" + ".*"));
    }


    @Test
    public void logContainsProperlySecondMessageAfterSeveralCalculations() {
        viewModel.setInputString("2,7");
        viewModel.calculate();
        viewModel.setInputString("2,8,7");
        viewModel.calculate();
        String message = viewModel.getLog().get(1);
        assertTrue(message.matches(".*" + LogMessages.CALCULATE_WAS_PRESSED
                + "Input string = " + "2,8,7" + ".*"));
    }

    @Test
    public void logContainsTwoEqualsMessagesAfterTwoCalculations() {
        viewModel.setInputString("2,7");
        viewModel.calculate();
        viewModel.calculate();
        String message1 = viewModel.getLog().get(0);
        String message2 = viewModel.getLog().get(1);
        assertEquals(message1, message2);
    }

    @Test
    public void statusIsNotDisplayedInLogAfterInputString() {
        viewModel.setInputString("2,5");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        String message = viewModel.getLog().get(0);
        assertFalse(message.matches(".*" + viewModel.getStatus() + ".*"));
    }

    @Test
    public void statusIsNotDisplayedInLogAfterCalculate() {
        viewModel.setInputString("2,5");
        viewModel.calculate();
        String message = viewModel.getLog().get(0);
        assertFalse(message.matches(".*" + viewModel.getStatus() + ".*"));
    }

    @Test
    public void logContainsOneMessageIfCalculateAfterInput() {
        viewModel.setInputString("2,5");
        viewModel.calculate();
        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void logContainsOneMessageIfOnFocusChanged() {
        viewModel.setInputString("2,5");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void logContainsTwoMessagesIfCalculateAfterFocusChanged() {
        viewModel.setInputString("2,5");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.calculate();
        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void logContainsTwoMessagesIfFocusChangedAfterCalculate() {
        viewModel.setInputString("2,5");
        viewModel.calculate();
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void canPutSeveralLogMessagesOfCalculation() {
        viewModel.setInputString("2,5");
        viewModel.calculate();
        viewModel.calculate();
        viewModel.calculate();
        viewModel.calculate();
        assertEquals(4, viewModel.getLog().size());
    }

    @Test
    public void canPutSeveralLogMessagesOfInputStrings() {
        viewModel.setInputString("2,5");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.setInputString("7,5");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.setInputString("7,5,9");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        assertEquals(3, viewModel.getLog().size());
    }

    @Test
    public void doNotLogSameParametersTwiceWithPartialInput() {
        viewModel.setInputString("2,5");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.setInputString("2,5");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void canPutTwoLogMessagesIfInputStringAfterCalculate() {
        viewModel.setInputString("2,5");
        viewModel.calculate();
        viewModel.setInputString("7,5");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void canPutTwoLogMessagesIfCalculateAfterInputString() {
        viewModel.setInputString("7,5");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.calculate();
        assertEquals(2, viewModel.getLog().size());
    }

    private ViewModel viewModel;
}
