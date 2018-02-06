package ru.unn.agile.PrimeNumber.viewmodel;

import javafx.collections.FXCollections;
import org.junit.Rule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import ru.unn.agile.PrimeNumber.Model.PrimeNumber.Methods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ViewModelTests {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    public void setViewModel(final ViewModel externalViewModel) {
        viewModel = externalViewModel;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getRangeFrom());
        assertEquals("", viewModel.getRangeTo());
        assertEquals("", viewModel.getMaxCountPrimes());
        assertEquals("", viewModel.getCurrentAnswer());
        assertEquals("", viewModel.getCurrentAnswer());
    }

    @Test
    public void canGetDefaultStatus() {
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void canCalculate() {
        viewModel.calculate();
    }

    @Test
    public void checkBadFormatStatusWithNonIntegerInput() {
        setInvalidData();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void checkBadFormatStatusWithNegativeCount() {
        setNegativeIntegers();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void checkBadFormatStatusWithBigIntegers() {
        setBigIntegers();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void checkWaitingStatusWithCorrectIncompleteRange() {
        viewModel.rangeFromProperty().set("2");
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void checkWaitingStatusWithCorrectIncompleteData() {
        viewModel.rangeFromProperty().set("2");
        viewModel.rangeToProperty().set("7");
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void checkBadFormatStatusWithInvalidIncompleteRange() {
        viewModel.rangeFromProperty().set("a");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void checkBadFormatStatusWithInvalidRange() {
        viewModel.rangeFromProperty().set("a");
        viewModel.rangeToProperty().set("b");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void checkBadFormatStatusWithInvalidCount() {
        viewModel.maxCountPrimesProperty().set("a");
        viewModel.rangeFromProperty().set("-10");
        viewModel.rangeToProperty().set("10");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void checkReadyStatusWithZeroCount() {
        viewModel.maxCountPrimesProperty().set("0");
        viewModel.rangeFromProperty().set("-10");
        viewModel.rangeToProperty().set("10");
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void checkReadyStatusWithReverseRange() {
        viewModel.maxCountPrimesProperty().set("10");
        viewModel.rangeFromProperty().set("10");
        viewModel.rangeToProperty().set("-10");
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void checkReadyStatusWithCorrectData() {
        setCorrectData();
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsDisabledInitially() {
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenBadFormat() {
        setCorrectData();
        viewModel.rangeFromProperty().set("sdf");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWithIncompleteRange() {
        viewModel.rangeFromProperty().set("1");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithCorrectInput() {
        setCorrectData();
        assertFalse(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void canSetEratosthenesMethod() {
        viewModel.methodProperty().set(Methods.ERATOSTHENES);
        assertEquals(Methods.ERATOSTHENES, viewModel.methodProperty().get());
    }

    @Test
    public void simpleIsDefaultMethod() {
        assertEquals(Methods.SIMPLE, viewModel.methodProperty().get());
    }

    @Test
    public void methodSimpleHasCorrectResult() {
        viewModel.rangeFromProperty().set("2");
        viewModel.rangeToProperty().set("10");
        viewModel.maxCountPrimesProperty().set("3");
        viewModel.methodProperty().set(Methods.SIMPLE);

        viewModel.calculate();

        assertEquals("Found 4 primes in the range from 2 to 10 in  seconds.\n"
                + "Here are 3 of them:\n2, 3, ..., 7\n",
                getAnswerWithoutTime(viewModel.currentAnswerProperty().get()));
    }

    @Test
    public void canPrintOnePrimeNumber() {
        viewModel.rangeFromProperty().set("2");
        viewModel.rangeToProperty().set("10");
        viewModel.maxCountPrimesProperty().set("1");
        viewModel.methodProperty().set(Methods.SIMPLE);

        viewModel.calculate();

        assertEquals("Found 4 primes in the range from 2 to 10 in  seconds.\n"
                + "Here are 1 of them:\n2\n",
                getAnswerWithoutTime(viewModel.currentAnswerProperty().get()));
    }

    @Test
    public void canPrintZeroPrimeNumbers() {
        viewModel.rangeFromProperty().set("2");
        viewModel.rangeToProperty().set("10");
        viewModel.maxCountPrimesProperty().set("0");
        viewModel.methodProperty().set(Methods.SIMPLE);

        viewModel.calculate();

        assertEquals("Found 4 primes in the range from 2 to 10 in  seconds.\n",
                getAnswerWithoutTime(viewModel.currentAnswerProperty().get()));
    }

    @Test
    public void canPrintTwoPrimeNumbers() {
        viewModel.rangeFromProperty().set("2");
        viewModel.rangeToProperty().set("10000");
        viewModel.maxCountPrimesProperty().set("2");
        viewModel.methodProperty().set(Methods.SIMPLE);

        viewModel.calculate();

        assertEquals("Found 1229 primes in the range from 2 to 10000 in  seconds.\n"
                + "Here are 2 of them:\n2, ..., 9973\n",
                getAnswerWithoutTime(viewModel.currentAnswerProperty().get()));
    }

    @Test
    public void canFindNoPrimes() {
        viewModel.rangeFromProperty().set("9980");
        viewModel.rangeToProperty().set("10000");
        viewModel.maxCountPrimesProperty().set("2");
        viewModel.methodProperty().set(Methods.SIMPLE);

        viewModel.calculate();

        assertEquals("There are no primes in the range from 9980 to 10000 in  seconds.\n",
                getAnswerWithoutTime(viewModel.currentAnswerProperty().get()));
    }

    @Test
    public void canFindPrimesWithNegativeBound() {
        viewModel.rangeFromProperty().set("-100");
        viewModel.rangeToProperty().set("100");
        viewModel.maxCountPrimesProperty().set("2");
        viewModel.methodProperty().set(Methods.SIMPLE);

        viewModel.calculate();

        assertEquals("Found 25 primes in the range from -100 to 100 in  seconds.\n"
                + "Here are 2 of them:\n2, ..., 97\n",
                getAnswerWithoutTime(viewModel.currentAnswerProperty().get()));
    }

    @Test
    public void canFindPrimesWithReverseRange() {
        viewModel.rangeFromProperty().set("10");
        viewModel.rangeToProperty().set("2");
        viewModel.maxCountPrimesProperty().set("2");
        viewModel.methodProperty().set(Methods.SIMPLE);

        viewModel.calculate();

        assertEquals("Found 4 primes in the range from 10 to 2 in  seconds.\n"
                + "Here are 2 of them:\n2, ..., 7\n",
                getAnswerWithoutTime(viewModel.currentAnswerProperty().get()));
    }

    @Test
    public void canFindPrimesWithEqualBounds() {
        viewModel.rangeFromProperty().set("13");
        viewModel.rangeToProperty().set("13");
        viewModel.maxCountPrimesProperty().set("10");
        viewModel.methodProperty().set(Methods.SIMPLE);

        viewModel.calculate();

        assertEquals("Found 1 primes in the range from 13 to 13 in  seconds.\n"
                + "Here are 1 of them:\n13\n",
                getAnswerWithoutTime(viewModel.currentAnswerProperty().get()));
    }

    @Test
    public void canFindPrimesWithMaxCountMoreThanPrimes() {
        viewModel.rangeFromProperty().set("2");
        viewModel.rangeToProperty().set("10");
        viewModel.maxCountPrimesProperty().set("9");
        viewModel.methodProperty().set(Methods.SIMPLE);

        viewModel.calculate();

        assertEquals("Found 4 primes in the range from 2 to 10 in  seconds.\n"
                + "Here are 4 of them:\n2, 3, 5, 7\n",
                getAnswerWithoutTime(viewModel.currentAnswerProperty().get()));
    }

    @Test
    public void canFindPrimesWithEratosthenesMethod() {
        viewModel.rangeFromProperty().set("2");
        viewModel.rangeToProperty().set("10000");
        viewModel.maxCountPrimesProperty().set("9");
        viewModel.methodProperty().set(Methods.ERATOSTHENES);

        viewModel.calculate();

        assertEquals("Found 1229 primes in the range from 2 to 10000 in  seconds.\n"
                + "Here are 9 of them:\n2, 3, 5, 7, 11, ..., 9941, 9949, 9967, 9973\n",
                getAnswerWithoutTime(viewModel.currentAnswerProperty().get()));
    }

    @Test
    public void canSetSuccessMessage() {
        setCorrectData();

        viewModel.calculate();

        assertEquals(Status.SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void canGetAnswersLists() {
        assertEquals(new ArrayList<Query>(), viewModel.answersListProperty().get());
    }

    @Test
    public void canAddAnswerToAnswersList() {
        setCorrectData();

        viewModel.calculate();

        assertEquals(new ArrayList<Query>() {{
            add(new Query("1. 1 primes in [2; 2] and printed 1",
                    "Found 1 primes in the range from 2 to 2 in  seconds.\n"
                       + "Here are 1 of them:\n2\n")); }}, viewModel.answersListProperty().get());
    }

    @Test
    public void canSetCurrentAnswer() {
        viewModel.answersListProperty().set(FXCollections.observableArrayList(
            new ArrayList<Query>() {{
                add(new Query("a", "aaa"));
                add(new Query("b", "bbb"));
            }}
        ));
        viewModel.chooseAnswerById(0);
        assertEquals("aaa", viewModel.getCurrentAnswer());
    }

    @Test
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Logger can't be null");
        new ViewModel(null);
    }

    @Test
    public void canLoggerBeSetAfterObjectIsCreated() {
        ViewModel viewModel = new ViewModel();
        viewModel.setLogger(new FakeLogger());
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsProperMessageAfterCalculation() {
        setCorrectData();
        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.CALCULATE_WAS_PRESSED + ".*"));
    }

    @Test
    public void logContainsInputRangeAfterCalculation() {
        setCorrectData();

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*"
                + viewModel.rangeFromProperty().get() + ".*"
                + viewModel.rangeToProperty().get() + ".*"));
    }

    @Test
    public void rangeIsProperlyFormattedInLogAfterCalculation() {
        setCorrectData();

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*for range \\["
                + viewModel.rangeFromProperty().get() + ", "
                + viewModel.rangeToProperty().get() + "\\].*"));
    }

    @Test
    public void logContainsMaxCountOfPrimesAfterCalculation() {
        setCorrectData();

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*"
                + viewModel.maxCountPrimesProperty().get() + ".*"));
    }

    @Test
    public void maxCountOfPrimesIsProperlyFormattedInLogAfterCalculation() {
        setCorrectData();

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*maximum count of primes was = "
                + viewModel.maxCountPrimesProperty().get() + ".*"));
    }

    @Test
    public void methodIsMentionedInTheLogAfterCalculation() {
        setCorrectData();

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*method " + Methods.SIMPLE + ".*"));
    }

    @Test
    public void canBeSeveralLogMessagesInLog() {
        setCorrectData();

        viewModel.calculate();
        viewModel.onMethodChanged(Methods.SIMPLE, Methods.ERATOSTHENES);
        viewModel.calculate();

        assertEquals(3, viewModel.getLog().size());
    }

    @Test
    public void canSeeMethodChangeInLog() {
        setCorrectData();

        viewModel.onMethodChanged(Methods.SIMPLE, Methods.ERATOSTHENES);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.OPERATION_CHANGED
                + Methods.ERATOSTHENES + ".*"));
    }


    @Test
    public void methodChangeIsNotLoggedIfNotChanged() {
        viewModel.onMethodChanged(Methods.SIMPLE, Methods.ERATOSTHENES);

        viewModel.onMethodChanged(Methods.ERATOSTHENES, Methods.ERATOSTHENES);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void rangeIsCorrectlyLoggedIfItsFocusChanged() {
        setCorrectData();

        viewModel.onRangeFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.RANGE_CHANGED
                + "\\[" + viewModel.rangeFromProperty().get() + ", "
                + viewModel.rangeToProperty().get() + "\\]."));
    }

    @Test
    public void rangeIsNotLoggedIfSelectedFirst() {
        setCorrectData();

        viewModel.onRangeFocusChanged(Boolean.FALSE, Boolean.TRUE);

        assertEquals(0, viewModel.getLog().size());
    }

    @Test
    public void numberOfPrimesIsCorrectlyLoggedIfItsFocusChanged() {
        setCorrectData();

        viewModel.onMaxCountPrimesFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.NUM_PRIMES_CHANGED
                + viewModel.maxCountPrimesProperty().get() + "."));
    }

    @Test
    public void numberOfPrimesIsNotLoggedIfSelectedFirst() {
        setCorrectData();

        viewModel.onMaxCountPrimesFocusChanged(Boolean.FALSE, Boolean.TRUE);

        assertEquals(0, viewModel.getLog().size());
    }

    @Test
    public void calculateIsNotCalledWhenButtonIsDisabled() {
        viewModel.calculate();

        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void doNotLogRangeFocusChangingForSameParamsWhenPartialInput() {
        viewModel.rangeFromProperty().set("3");
        viewModel.onRangeFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.rangeFromProperty().set("3");
        viewModel.onRangeFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void logRangeFocusChangingForDifferentParamsWhenPartialInput() {
        viewModel.rangeFromProperty().set("2");
        viewModel.onRangeFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.rangeFromProperty().set("3");
        viewModel.onRangeFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void canLogAllEvents() {
        setCorrectData();

        viewModel.onRangeFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.onMethodChanged(Methods.SIMPLE, Methods.ERATOSTHENES);
        viewModel.onMaxCountPrimesFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.calculate();

        assertEquals(4, viewModel.getLog().size());
    }

    @Test
    public void isLogTextEmptyInTheBeginning() {
        assertEquals("", viewModel.getLogs());
    }

    @Test
    public void canLogTextContainSeveralLogMessages() {
        setCorrectData();

        viewModel.calculate();
        viewModel.onMethodChanged(Methods.SIMPLE, Methods.ERATOSTHENES);
        viewModel.calculate();

        String logText = viewModel.getLogs();
        int numberOfEventsHappened = logText.split("\n").length;
        assertEquals(3, numberOfEventsHappened);
    }

    @Test
    public void doesLogTextContainEntriesFromOriginalLog() {
        setCorrectData();

        viewModel.calculate();
        viewModel.onMaxCountPrimesFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String[] splitLogText = viewModel.getLogs().split("\n");
        assertEquals(viewModel.getLog().get(0), splitLogText[0]);
        assertEquals(viewModel.getLog().get(1), splitLogText[1]);
    }

    private void setCorrectData() {
        viewModel.rangeFromProperty().set("2");
        viewModel.rangeToProperty().set("2");
        viewModel.maxCountPrimesProperty().set("10");
    }

    private void setInvalidData() {
        viewModel.rangeFromProperty().set("ajfla");
        viewModel.rangeToProperty().set("bdsf");
        viewModel.maxCountPrimesProperty().set("bsf");
    }

    private void setNegativeIntegers() {
        viewModel.rangeFromProperty().set("-1");
        viewModel.rangeToProperty().set("-1");
        viewModel.maxCountPrimesProperty().set("-1");
    }

    private void setBigIntegers() {
        viewModel.rangeFromProperty().set("9999999999");
        viewModel.rangeToProperty().set("9999999999");
        viewModel.maxCountPrimesProperty().set("9999999999");
    }

    private String getAnswerWithoutTime(final String answer) {
        String beginStr = answer.substring(0, answer.indexOf(" seconds."));
        return beginStr.substring(0, beginStr.lastIndexOf(" ") + 1)
                + answer.substring(answer.indexOf(" seconds."));
    }

    private ViewModel viewModel;
}
