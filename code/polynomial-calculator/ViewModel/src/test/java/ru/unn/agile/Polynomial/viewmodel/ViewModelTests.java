package ru.unn.agile.Polynomial.viewmodel;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ViewModelTests {
    @Before
    public void setBefore() {
        viewModel = new ViewModel(new ListLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getFirstPolynomial());
        assertEquals("", viewModel.getSecondPolynomial());
        assertEquals(Operation.ADD, viewModel.getOperation());
        assertEquals("", viewModel.getResult());
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingWhenCalculateWithEmptyFields() {
        viewModel.calculate();
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsAreFilledCorrectly() {
        fillInputFieldsWithCorrectData();
        viewModel.processTextChanged();
        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void canReportBadFormat() {
        fillInputFieldsWithInvalidData();
        viewModel.processTextChanged();
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canGetReadyStatusAfterBadFormat() {
        fillInputFieldsWithInvalidData();
        viewModel.processTextChanged();
        fillInputFieldsWithCorrectData();
        viewModel.processTextChanged();
        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isCalculateButtonDisabledInitially() {
        assertEquals(false, viewModel.isButtonCalculateEnabled());
    }

    @Test
    public void isCalculateButtonEnabledWhenInputCorrect() {
        fillInputFieldsWithCorrectData();
        viewModel.processTextChanged();
        assertEquals(true, viewModel.isButtonCalculateEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWhenFormatIsBad() {
        fillInputFieldsWithInvalidData();
        viewModel.processTextChanged();
        assertEquals(false, viewModel.isButtonCalculateEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWithIncompleteInput() {
        viewModel.setSecondPolynomial("1x^2-14.3x^4");
        viewModel.processTextChanged();
        assertEquals(false, viewModel.isButtonCalculateEnabled());
    }

    @Test
    public void canSetAddOperation() {
        viewModel.setOperation(Operation.ADD);
        assertEquals(Operation.ADD, viewModel.getOperation());
    }

    @Test
    public void canSetSubOperation() {
        viewModel.setOperation(Operation.SUB);
        assertEquals(Operation.SUB, viewModel.getOperation());
    }

    @Test
    public void canSetMulOperation() {
        viewModel.setOperation(Operation.MULTIPLY);
        assertEquals(Operation.MULTIPLY, viewModel.getOperation());
    }

    @Test
    public void canSetSuccessMessage() {
        fillInputFieldsWithCorrectData();
        viewModel.calculate();
        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void canSetBadFormatMessage() {
        fillInputFieldsWithInvalidData();
        viewModel.calculate();
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canPerformCalculationAfterBadFormat() {
        fillInputFieldsWithInvalidData();
        viewModel.calculate();
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
        fillInputFieldsWithCorrectData();
        viewModel.calculate();
        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void canGetWaitingStatusAfterSuccess() {
        fillInputFieldsWithCorrectData();
        viewModel.calculate();
        assertEquals(Status.SUCCESS, viewModel.getStatus());
        viewModel.setSecondPolynomial("");
        viewModel.processTextChanged();
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void canAddPolynomials() {
        viewModel.setFirstPolynomial("1.0x^(3)-2.132x^(-1)-0.2x^(10)");
        viewModel.setSecondPolynomial("-5.1x^(5)+0.9x^(10)");
        viewModel.setOperation(Operation.ADD);
        viewModel.calculate();
        assertEquals("-2.132x^(-1)+1.0x^(3)-5.1x^(5)+0.7x^(10)", viewModel.getResult());
    }

    @Test
    public void canSubPolynomials() {
        viewModel.setFirstPolynomial("1.0x^(3)-2.132x^(-1)-0.2x^(10)");
        viewModel.setSecondPolynomial("-5.1x^(5)+0.9x^(10)");
        viewModel.setOperation(Operation.SUB);
        viewModel.calculate();
        assertEquals("-2.132x^(-1)+1.0x^(3)+5.1x^(5)-1.1x^(10)", viewModel.getResult());
    }

    @Test
    public void canMultiplyPolynomials() {
        viewModel.setFirstPolynomial("1.0x^(6)+2.13x^(-1)");
        viewModel.setSecondPolynomial("-5.1x^(5)+0.9x^(10)");
        viewModel.setOperation(Operation.MULTIPLY);
        viewModel.calculate();
        assertEquals("-10.863x^(4)+1.917x^(9)-5.1x^(11)+0.9x^(16)", viewModel.getResult());
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
    public void logIsEmptyAfterConstruction() {
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void isCalculatePuttingSomething() {
        viewModel.calculate();
        List<String> log = viewModel.getLog();
        assertNotEquals(0, log.size());
    }

    @Test
    public void logContainsInputArgumentsAfterEditFirstPolynomial() {
        viewModel.setFirstPolynomial("8x^(5)-0.1x^(4)");
        viewModel.focusLost();
        String message = viewModel.getLog().get(0);
        String correctMessage = messageFirstPolinomial();
        assertTrue(message.contains(correctMessage));
    }

    @Test
    public void logContainsInputArgumentsAfterEditSecondPolynomial() {
        viewModel.setSecondPolynomial("8x^(5)-0.1x^(4)");
        viewModel.focusLost();
        String message = viewModel.getLog().get(0);
        String correctMessage = messageSecondPolinomial();
        assertTrue(message.contains(correctMessage));
    }

    @Test
    public void logContainsInputArgumentsAfterResetSecondPolynomial() {
        viewModel.setSecondPolynomial("8x^(5)-0.1x^(4)");
        viewModel.focusLost();
        viewModel.setSecondPolynomial("");
        viewModel.focusLost();
        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void logContainsCorrectMessageAfterCalculate() {
        fillInputFieldsWithCorrectData();
        viewModel.calculate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.CALCULATE_WAS_PRESSED + ".*"));
    }

    @Test
    public void isMulOperationMentionedInTheLog() {
        viewModel.setOperation(Operation.MULTIPLY);
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*Mul.*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        fillInputFieldsWithCorrectData();
        viewModel.focusLost();
        viewModel.calculate();
        viewModel.setOperation(Operation.SUB);
        assertEquals(4, viewModel.getLog().size());
    }

    @Test
    public void doNotLogSameParametersTwice() {
        viewModel.setFirstPolynomial("7x^(55)-0.1x^(-14)");
        viewModel.focusLost();
        viewModel.setFirstPolynomial("7x^(55)-0.1x^(-14)");
        viewModel.focusLost();
        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void canSeeOperationChangeInLog() {
        viewModel.setOperation(Operation.MULTIPLY);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.OPERATION_WAS_CHANGED + "Mul.*"));
    }

    @Test
    public void isOperationNotLoggedWhenNotChanged() {
        viewModel.setOperation(Operation.MULTIPLY);
        viewModel.setOperation(Operation.MULTIPLY);

        assertEquals(1, viewModel.getLog().size());
    }

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private void fillInputFieldsWithCorrectData() {
        viewModel.setFirstPolynomial("7x^(55)-0.1x^(-14)");
        viewModel.setSecondPolynomial("-99x^(-4)+0.411x^(1)");
    }

    private void fillInputFieldsWithInvalidData() {
        viewModel.setFirstPolynomial("ax^2+by+z");
        viewModel.setSecondPolynomial("2p");
    }

    private String messageFirstPolinomial() {
        return LogMessages.EDITING_HAPPENED
                + String.format("First polynomial: [%s].", viewModel.getFirstPolynomial());
    }

    private String messageSecondPolinomial() {
        return LogMessages.EDITING_HAPPENED
                + String.format("Second polynomial: [%s].", viewModel.getSecondPolynomial());
    }

    private ViewModel viewModel;
}
