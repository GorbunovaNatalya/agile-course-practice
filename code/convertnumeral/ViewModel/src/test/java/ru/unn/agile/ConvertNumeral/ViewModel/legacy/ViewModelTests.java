package ru.unn.agile.ConvertNumeral.ViewModel.legacy;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ViewModelTests {
    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
    }

    @After
    public void clean() {
        viewModel = null;
    }

    @Test
    public void canCreateViewModelWithLogger() {
        assertNotNull(viewModel);
    }

    @Test
    public void convertButtonIsDisabledWhenInputIsEmpty() {
        assertFalse(viewModel.isConvertButtonEnabled());
    }

    @Test
    public void showMessageWhenInputIsEmpty() {
        assertEquals(ViewModel.Message.DEFAULT, viewModel.getMessageText());
    }

    @Test
    public void convertButtonIsEnabledWhenEnterArabicNumber() {
        viewModel.setInputNumber("1");

        assertTrue(viewModel.isConvertButtonEnabled());
    }

    @Test
    public void convertButtonIsEnabledWhenEnterRomanNumber() {
        viewModel.setInputNumber("I");

        assertTrue(viewModel.isConvertButtonEnabled());
    }

    @Test
    public void convertButtonIsDisabledWhenClearInputNumber() {
        viewModel.setInputNumber("1");
        viewModel.setInputNumber("");

        assertFalse(viewModel.isConvertButtonEnabled());
    }

    @Test
    public void whenConvertArabicNumber10DisplayRomanNumberX() {
        viewModel.setInputNumber("10");
        viewModel.convert();

        assertEquals("X", viewModel.getOutputNumber());
    }

    @Test
    public void whenConvertRomanNumberVDisplayArabicNumber5() {
        viewModel.setInputNumber("V");
        viewModel.convert();

        assertEquals("5", viewModel.getOutputNumber());
    }

    @Test
    public void convertButtonIsDisabledWhenEnterInvalidInputNumber() {
        viewModel.setInputNumber("abc");

        assertFalse(viewModel.isConvertButtonEnabled());
    }

    @Test
    public void showErrorMessageWhenEnterInvalidInputNumber() {
        viewModel.setInputNumber("abc");

        assertEquals(ViewModel.Message.INCORRECT_INPUT, viewModel.getMessageText());
    }

    @Test
    public void showErrorMessageWhenConvertInvalidRomanNumber() {
        viewModel.setInputNumber("VXC");
        viewModel.convert();

        assertEquals("Error: Incorrectly entered Roman number!", viewModel.getMessageText());
    }

    @Test
    public void showErrorMessageWhenConvertArabicNumberMoreThen3999() {
        viewModel.setInputNumber("4000");
        String errorMessage = "Error: Incorrectly entered Arabic number."
                + " It should be between 0 and 3999!";
        viewModel.convert();

        assertEquals(errorMessage, viewModel.getMessageText());
    }

    @Test
    public void showMessageWhenCorrectInvalidArabicNumber() {
        viewModel.setInputNumber("abc");
        viewModel.setInputNumber("5");

        assertEquals(ViewModel.Message.ENTER_ARABIC, viewModel.getMessageText());
    }

    @Test
    public void showMessageWhenCorrectInvalidRomanNumber() {
        viewModel.setInputNumber("abc");
        viewModel.setInputNumber("XX");

        assertEquals(ViewModel.Message.ENTER_ROMAN, viewModel.getMessageText());
    }

    @Test
    public void showMessageWhenClearInputNumber() {
        viewModel.setInputNumber("a");
        viewModel.setInputNumber("");

        assertEquals(ViewModel.Message.DEFAULT, viewModel.getMessageText());
    }

    @Test
    public void canEnterNumericSymbolInInput() {
        assertTrue(viewModel.isAvailableInsertInput(0, "2"));
    }

    @Test
    public void canEnterIVXLCDMSymbolInInput() {
        assertTrue(viewModel.isAvailableInsertInput(0, "X"));
    }

    @Test
    public void canEnterSomeSymbolExcludingNumericAndRomansNumericInInput() {
        assertFalse(viewModel.isAvailableInsertInput(0, "a"));
    }

    @Test
    public void canEnterArabicWithCountOfSymbolsMore4InInput() {
        assertFalse(viewModel.isAvailableInsertInput(4, "2"));
    }

    @Test
    public void canInsertArabicWithCountOfSymbolsMore4InInput() {
        assertFalse(viewModel.isAvailableInsertInput(0, "12345"));
    }

    @Test
    public void canEnterNumericSymbolAfterRomansNumericSymbolInInput() {
        viewModel.setInputNumber("I");
        assertFalse(viewModel.isAvailableInsertInput(1, "2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void canThrowExceptionWhenLoggerIsNull() {
        new ViewModel(null);
    }

    @Test
    public void isLogEmptyAfterStart() {
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void canAddLogWhenEnterInput() {
        viewModel.setInputNumber("a");
        List<String> log = viewModel.getLog();

        assertEquals(1, log.size());
    }

    @Test
    public void canAddLogWhenChangeInput() {
        viewModel.setInputNumber("a");
        viewModel.setInputNumber("2");
        List<String> log = viewModel.getLog();

        assertEquals(2, log.size());
    }

    @Test
    public void doesNotLogWhenInputWasNotChanged() {
        viewModel.setInputNumber("CM");
        viewModel.setInputNumber("CM");
        List<String> log = viewModel.getLog();

        assertEquals(1, log.size());
    }

    @Test
    public void doesLogContainInputArgumentWhenChangeInput() {
        String inputString = "1";
        viewModel.setInputNumber(inputString);
        List<String> log = viewModel.getLog();

        assertTrue(log.get(0).matches(".*" + inputString + ".*"));
    }

    @Test
    public void doesLogContainLogMessageWhenChangeInput() {
        viewModel.setInputNumber("II");
        List<String> log = viewModel.getLog();

        assertTrue(log.get(0).matches(".*" + ViewModel.LogMessage.CHANGE_INPUT + ".*"));
    }

    @Test
    public void doesLogContainArgumentWhenConvertWasClicked() {
        String inputString = "10";
        viewModel.setInputNumber(inputString);
        viewModel.convert();
        List<String> log = viewModel.getLog();

        assertTrue(log.get(1).matches(".*"
                + ViewModel.NumberType.ARABIC.toString() + ": "
                + inputString
                + " to "
                + ViewModel.NumberType.ROMAN.toString() + ": X"
                + ".*"));
    }

    @Test
    public void doesLogContainLogMessageWhenConvertWasClicked() {
        viewModel.setInputNumber("II");
        viewModel.convert();
        List<String> log = viewModel.getLog();

        assertTrue(log.get(1).matches(".*" + ViewModel.LogMessage.CONVERT_WAS_PRESSED + ".*"));
    }

    @Test
    public void addMessageWhenConvertWasClickedAndInputIsInvalid() {
        viewModel.convert();
        List<String> log = viewModel.getLog();

        assertTrue(log.get(0).matches(".*"
                + ViewModel.LogMessage.CONVERT_WAS_PRESSED
                + "failed: "
                + ViewModel.Message.UNKNOWN_TYPE));
    }

    @Test
    public void addMessageToLogWhenConvertingIncorrectRomanNumber() {
        viewModel.setInputNumber("IXI");
        viewModel.convert();
        List<String> log = viewModel.getLog();

        assertTrue(log.get(1).matches(".*"
                + ViewModel.LogMessage.CONVERT_WAS_PRESSED
                + "failed: " + ".*"));
    }

    private ViewModel viewModel;
}
