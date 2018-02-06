package ru.unn.agile.StringCalculator.infrastructure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class TxtLoggerTests {
    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILE_NAME);
    }

    @Test
    public void canCreateLogger() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            new BufferedReader(fileReader);
        } catch (FileNotFoundException excp) {
            fail(FILE_NAME + " was not found");
        }
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";
        String logTag = "Tag";

        txtLogger.log(logTag, testMessage);

        String message = txtLogger.getLog().get(0);
        assertTrue(message.matches(".*" + logTag + ": " + testMessage + ".*"));
    }

    @Test
    public void logDoesNotContainLogTagIfTagIsEmpty() {
        String testMessage = "Test message";
        String logTag = "";

        txtLogger.log(logTag, testMessage);

        String message = txtLogger.getLog().get(0);
        assertFalse(message.matches(".*" + logTag + ": " + ".*"));
    }

    @Test
    public void doesLogContainProperMessages() {
        String[] messages = {"Test message 1", "Test message 2"};
        String[] logTags = {"Tag1", "Tag2"};

        txtLogger.log(logTags[0], messages[0]);
        txtLogger.log(logTags[1], messages[1]);

        List<String> actualMessages = txtLogger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertTrue(getActualMessage(actualMessages, i).matches(".*" + logTags[i]
                    + ": " + messages[i]  + ".*"));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";
        String logTag = "Tag";
        String dateAndTimeFormat = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";

        txtLogger.log(logTag, testMessage);

        String message = txtLogger.getLog().get(0);
        assertTrue(message.matches(dateAndTimeFormat + " > .*"));
    }

    private static final String FILE_NAME = "./TxtLogger_Tests.log";
    private TxtLogger txtLogger;

    private String getActualMessage(final List<String> actualMessages, final int i) {
        return actualMessages.get(i);
    }
}
