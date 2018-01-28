package ru.unn.agile.Salary.viewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewModelTest {
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
        assertEquals("", viewModel.payProperty().get());
        assertEquals("", viewModel.workProperty().get());
        assertEquals("", viewModel.overProperty().get());
        assertEquals("", viewModel.adminProperty().get());
        assertEquals("", viewModel.resultProperty().get());
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyFields() {
        viewModel.calculate();
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormat() {
        viewModel.workProperty().set("abcd");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfNotEnoughCorrectData() {
        viewModel.payProperty().set("1223");
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledInitially() {
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenFormatIsBad() {
        setInputData();
        viewModel.workProperty().set("abcd");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFill() {
        setInputData();
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWithIncompleteInput() {
        viewModel.payProperty().set("1");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void statusIsBadFormatWhenCalculateSalaryWithNegativePay() {
        viewModel.payProperty().set("-10");
        viewModel.workProperty().set("5");
        viewModel.overProperty().set("5");
        viewModel.adminProperty().set("1");
        viewModel.calculate();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithCorrectInput() {
        setInputData();
        assertFalse(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void canSetSuccessMessage() {
        setInputData();
        viewModel.calculate();
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenSetProperData() {
        setInputData();
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void salaryCalculationHasCorrectResultWhenAllDataIsFull() {
        viewModel.payProperty().set("10");
        viewModel.workProperty().set("5");
        viewModel.overProperty().set("2");
        viewModel.adminProperty().set("1");
        viewModel.calculate();
        assertEquals("69.6", viewModel.resultProperty().get());
    }

    @Test
    public void salaryCalculationHasCorrectResultWhenOverHoursIsEmpty() {
        viewModel.payProperty().set("10");
        viewModel.workProperty().set("5");
        viewModel.adminProperty().set("1");
        viewModel.calculate();
        assertEquals("34.8", viewModel.resultProperty().get());
    }

    @Test
    public void salaryCalculationHasCorrectResultWhenAdminLeaveHoursIsEmpty() {
        viewModel.payProperty().set("10");
        viewModel.workProperty().set("5");
        viewModel.overProperty().set("2");
        viewModel.calculate();
        assertEquals("78.3", viewModel.resultProperty().get());
    }

    @Test
    public void salaryCalculationHasCorrectResultWhenAdminLeaveAndOverHoursIsEmpty() {
        viewModel.payProperty().set("10");
        viewModel.workProperty().set("5");
        viewModel.calculate();
        assertEquals("43.5", viewModel.resultProperty().get());
    }

    private void setInputData() {
        viewModel.payProperty().set("10");
        viewModel.workProperty().set("5");
        viewModel.overProperty().set("2");
        viewModel.adminProperty().set("1");
    }

    private ViewModel viewModel;
}
