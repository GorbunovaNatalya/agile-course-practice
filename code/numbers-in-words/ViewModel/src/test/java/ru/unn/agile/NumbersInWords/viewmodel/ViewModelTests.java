package ru.unn.agile.NumbersInWords.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.List;

public class ViewModelTests {

    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultNumber() {
        assertEquals("", viewModel.getInputNumber());
    }

    @Test
    public void canSetDefaultResultWord() {
        assertEquals("", viewModel.getResultWord());
    }

    @Test
    public void canSetDefaultStatus() {
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsWaitingWhenEmptyField() {
        viewModel.setNumber("");
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsWaitingWhenInputMinus() {
        viewModel.setNumber("-");
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsReadyWhenFieldAreFill() {
        viewModel.setNumber("123");
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenIncorrectInputNumber() {
        viewModel.setNumber("a");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenIncorrectSeparator() {
        viewModel.setNumber("65,7");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenInputTooBigNumber() {
        viewModel.setNumber("1000000000000");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenInputTooBigNumberThrowNumberProperty() {
        viewModel.numberProperty().set("1000000000000");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenInputTooBigNegativeNumber() {
        viewModel.setNumber("-1000000000000");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void translateButtonIsDisabledWhenInputIsEmpty() {
        viewModel.setNumber("");
        assertTrue(viewModel.isTranslateButtonDisabled());
    }

    @Test
    public void translateButtonIsDisabledWhenFormatIsBad() {
        viewModel.setNumber("ab");
        assertTrue(viewModel.isTranslateButtonDisabled());
    }

    @Test
    public void translateButtonIsDisabledWhenIncorrectSeparator() {
        viewModel.setNumber("54,7");
        assertTrue(viewModel.isTranslateButtonDisabled());
    }

    @Test
    public void translateButtonIsDisabledWhenInputTooBigNumber() {
        viewModel.setNumber("1000000000000");
        assertTrue(viewModel.isTranslateButtonDisabled());
    }

    @Test
    public void translateButtonIsDisabledWhenInputTooBigNegativeNumber() {
        viewModel.setNumber("-1000000000000");
        assertTrue(viewModel.isTranslateButtonDisabled());
    }

    @Test
    public void translateButtonIsEnabledWhenCorrectInputNumber() {
        viewModel.setNumber("2.2");
        assertFalse(viewModel.isTranslateButtonDisabled());
    }

    @Test
    public void translateButtonIsEnabledWhenNegativeNumber() {
        viewModel.setNumber("-89");
        assertFalse(viewModel.isTranslateButtonDisabled());
    }

    @Test
    public void canTranslateCorrectInputNumber() {
        viewModel.setNumber("922");
        viewModel.translate();
        assertEquals("nine hundred and twenty two", viewModel.getResultWord());
    }

    @Test
    public void statusIsSuccessWhenTranslateCorrectInputNumber() {
        viewModel.setNumber("922");
        viewModel.translate();
        assertEquals(Status.SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void canTranslateDoubleInputNumber() {
        viewModel.setNumber("0.123456789");
        viewModel.translate();
        assertEquals("zero point one two three four five six seven eight nine",
                viewModel.getResultWord());
    }

    @Test
    public void canTranslateNegativeInputNumber() {
        viewModel.setNumber("-9.6");
        viewModel.translate();
        assertEquals("negative nine point six", viewModel.getResultWord());
    }

    @Test
    public void statusIsWaitingAfterClearInputNumber() {
        viewModel.setNumber("894");
        viewModel.setNumber("");
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void translateButtonIsDisabledAfterClearInputNumber() {
        viewModel.setNumber("894");
        viewModel.setNumber("");
        assertTrue(viewModel.isTranslateButtonDisabled());
    }

    @Test
    public void statusIsBadFormatAfterInputIncorrectNumber() {
        viewModel.setNumber("894");
        viewModel.translate();
        viewModel.setNumber("894a");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void translateButtonIsDisabledAfterInputIncorrectNumber() {
        viewModel.setNumber("894");
        viewModel.translate();
        viewModel.setNumber("894a");
        assertTrue(viewModel.isTranslateButtonDisabled());
    }

    @Test
    public void statusIsReadyWhenInputNewNumber() {
        viewModel.setNumber("654");
        viewModel.translate();
        viewModel.setNumber("65");
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void resultIsClearWhenInputNewNumber() {
        viewModel.setNumber("654");
        viewModel.translate();
        viewModel.setNumber("2");
        assertEquals("", viewModel.getResultWord());
    }

    @Test
    public void canCreateViewModelWithoutLogger() {
        ViewModel viewModel = new ViewModel();
        assertNotNull(viewModel);
    }

    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModelLogged = new ViewModel(logger);
        assertNotNull(viewModelLogged);
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
            new ViewModel(null);
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsProperMessageAfterTranslation() {
        viewModel.setNumber("124");
        viewModel.translate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.TRANSLATE_WAS_PRESSED + ".*"));
    }

    @Test
    public void logContainsInputNumberAfterTranslation() {
        viewModel.setNumber("124");
        viewModel.translate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + viewModel.getInputNumber() + ".*"));
    }

    @Test
    public void logContainsResultAfterTranslation() {
        viewModel.setNumber("124");
        viewModel.translate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + viewModel.getResultWord() + ".*"));
    }

    @Test
    public void argumentsInfoIsProperlyFormatted() {
        viewModel.setNumber("124");
        viewModel.translate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*Input number: " + viewModel.getInputNumber()
                + "; Result = " + viewModel.getResultWord() + ".*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        viewModel.setNumber("124");
        viewModel.translate();
        viewModel.translate();
        viewModel.translate();
        assertEquals(3, viewModel.getLog().size());
    }

    @Test
    public void twoLogMessagesAfterChangingFocusAndTranslation() {
        viewModel.setNumber("124");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.translate();
        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void twoLogMessagesAfterTranslationAndChangingFocus() {
        viewModel.setNumber("124");
        viewModel.translate();
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void inputNumberIsCorrectlyLogged() {
        viewModel.setNumber("124");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.EDITING_FINISHED
                + "Input number: " + viewModel.getInputNumber()));
    }

    @Test
    public void doNotLogSameParametersTwiceWithPartialInput() {
        viewModel.setNumber("124");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.setNumber("124");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void logsAreEmptyByDefault() {
        assertEquals("", viewModel.getLogs());
    }

    @Test
    public void canChangeLogsWhenFocusChanged() {
        viewModel.setNumber("124");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        String message = viewModel.getLogs();
        assertTrue(message.matches(".*" + LogMessages.EDITING_FINISHED + "Input number: 124\n"));
    }

    @Test
    public void canChangeLogsAfterTranslation() {
        viewModel.setNumber("124");
        viewModel.translate();
        String message = viewModel.getLogs();
        assertTrue(message.matches(".*" + LogMessages.TRANSLATE_WAS_PRESSED + ".*\n"));
    }

    @Test
    public void logEmptyIfNothingChanged() {
        viewModel.setNumber("124");
        viewModel.onFocusChanged(Boolean.FALSE, Boolean.TRUE);
        String message = viewModel.getLogs();
        assertEquals("", message);
    }

    @Test
    public void logIsEmptyIfOnFocusChangedWithoutInput() {
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void logIsNotEmptyIfOnFocusChanged() {
        viewModel.setNumber("124");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        String message = viewModel.getLogs();
        assertFalse(message.isEmpty());
    }

    @Test
    public void canNotTranslateIfTranslateButtonDisabled() {
        viewModel.translate();
        assertEquals("", viewModel.getResultWord());
    }

    @Test
    public void logIsEmptyWhenTranslateCallAndTranslateButtonDisabled() {
        viewModel.translate();
        String message = viewModel.getLogs();
        assertEquals("", message);
    }

    @Test
    public void logContainsProperlyFirstMessageAfterSeveralTranslations() {
        viewModel.setNumber("124");
        viewModel.translate();
        viewModel.setNumber("125");
        viewModel.translate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.TRANSLATE_WAS_PRESSED
                + "Input number: " + "124" + ".*"));
    }

    @Test
    public void logContainsProperlySecondMessageAfterSeveralTranslations() {
        viewModel.setNumber("124");
        viewModel.translate();
        viewModel.setNumber("125");
        viewModel.translate();
        String message = viewModel.getLog().get(1);
        assertTrue(message.matches(".*" + LogMessages.TRANSLATE_WAS_PRESSED
                + "Input number: " + "125" + ".*"));
    }

    private ViewModel viewModel;
}
