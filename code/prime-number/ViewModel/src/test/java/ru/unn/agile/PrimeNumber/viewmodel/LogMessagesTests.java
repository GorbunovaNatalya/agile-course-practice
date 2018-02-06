package ru.unn.agile.PrimeNumber.viewmodel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertArrayEquals;

public class LogMessagesTests {
    @Test
    public void canGetLogMessageName() {
        String addName = LogMessages.CALCULATE_WAS_PRESSED.toString();
        assertEquals("Calculate was pressed with ", addName);
    }

    @Test
    public void canGetNumberOfLogMessages() {
        int nMessages = LogMessages.values().length;
        assertEquals(4, nMessages);
    }

    @Test
    public void canCompareLogMessagesByName() {
        assertEquals(LogMessages.RANGE_CHANGED, LogMessages.RANGE_CHANGED);
        assertNotEquals(LogMessages.RANGE_CHANGED, LogMessages.OPERATION_CHANGED);
    }

    @Test
    public void canGetListOfLogMessages() {
        LogMessages[] logMessages = LogMessages.values();
        LogMessages[] currentLogMessages = new LogMessages[] {
                LogMessages.CALCULATE_WAS_PRESSED,
                LogMessages.OPERATION_CHANGED,
                LogMessages.RANGE_CHANGED,
                LogMessages.NUM_PRIMES_CHANGED
        };
        assertArrayEquals(currentLogMessages, logMessages);
    }
}
