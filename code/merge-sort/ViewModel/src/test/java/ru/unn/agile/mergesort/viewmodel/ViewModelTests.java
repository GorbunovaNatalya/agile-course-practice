package ru.unn.agile.mergesort.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
        assertEquals("", viewModel.getInput());
        assertEquals("", viewModel.getOutput());
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void canGetDefaultProperties() {
        assertEquals("", viewModel.inputProperty().get());
        assertEquals("", viewModel.outputProperty().get());
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
        assertTrue(viewModel.sortDisabledProperty().get());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyInput() {
        viewModel.calculate();

        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsWaitingWithInputWithSpacesOnly() {
        viewModel.setInput("   ");

        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsReadyWhenInputIsFilledByIntegers() {
        viewModel.setInput("15 4 42 8 16 23");

        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsWaitingAfterInputErase() {
        viewModel.setInput("15 4 42 8 16 23");
        viewModel.setInput("");

        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenInputHasWrongFormat() {
        viewModel.setInput("How this program works???");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenInputHasNotANumberWord() {
        viewModel.setInput("15 4 42 Oops 8 16 23");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenInputHasTooBigNumbers() {
        viewModel.setInput("15 4 42 853418926487349812743 8 16 23");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsReadyAfterBadFormatInputIsFixed() {
        viewModel.setInput("How this program works???");
        viewModel.setInput("15 4 42 8 16 23");

        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsSuccessAfterCalculation() {
        viewModel.setInput("15 4 42 8 16 23");
        viewModel.calculate();

        assertEquals(Status.SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void sortButtonIsDisabledInitially() {
        assertTrue(viewModel.isSortDisabled());
    }

    @Test
    public void sortButtonIsDisabledWhenFormatIsBad() {
        viewModel.setInput("15 4 42 Oops 8 16 23");

        assertTrue(viewModel.isSortDisabled());
    }

    @Test
    public void sortButtonIsEnabledWithCorrectInput() {
        viewModel.setInput("15 4 42 8 16 23");

        assertFalse(viewModel.isSortDisabled());
    }

    @Test
    public void outputIsCorrectAfterSortCorrectInput() {
        viewModel.setInput("15 4 42 8 16 23");
        viewModel.calculate();

        assertEquals("4 8 15 16 23 42", viewModel.getOutput());
    }

    @Test
    public void outputIsCorrectWithAbnormalSpacePlacingInInput() {
        viewModel.setInput("  15 4    42  8 16 23  ");
        viewModel.calculate();

        assertEquals("4 8 15 16 23 42", viewModel.getOutput());
    }

    @Test
    public void outputIsCorrectWithNegativeIntegersInInput() {
        viewModel.setInput("1 2 -3 4 -5");
        viewModel.calculate();

        assertEquals("-5 -3 1 2 4", viewModel.getOutput());
    }

    @Test
    public void outputIsCorrectWithIntegersWithPlusInInput() {
        viewModel.setInput("15 +4 42 8 +16 23");
        viewModel.calculate();

        assertEquals("4 8 15 16 23 42", viewModel.getOutput());
    }

    private ViewModel viewModel;
}
