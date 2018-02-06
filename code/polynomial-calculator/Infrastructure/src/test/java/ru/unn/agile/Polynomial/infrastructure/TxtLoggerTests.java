package ru.unn.agile.Polynomial.infrastructure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class TxtLoggerTests {
    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILE_NAME);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(FILE_NAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILE_NAME + " wasn't found!");
        }
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";

        txtLogger.addInfo(testMessage);

        String messageInLog = txtLogger.getLog().get(0);
        assertTrue(messageInLog.matches(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] messages = {"Test message 1", "Test message 2"};

        txtLogger.addInfo(messages[0]);
        txtLogger.addInfo(messages[1]);

        List<String> listLogger = txtLogger.getLog();
        for (int i = 0; i < listLogger.size(); i++) {
            assertTrue(listLogger.get(i).matches(".*" + messages[i] + "$"));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";

        txtLogger.addInfo(testMessage);

        String message = txtLogger.getLog().get(0);
        assertTrue(message.matches(REGEX_DATA + " " + REGEX_TIME + " > .*"));
    }
    @Test
    public void notFailConstructTxtLoggerWithBadFileName() {
         TxtLogger txtLogger = new TxtLogger(EMPTY_STRING);
    }

    @Test
    public void notFailLoggingWithNullPointerWriter() {
        TxtLogger badLogger = new TxtLogger(EMPTY_STRING);
        badLogger.addInfo("");
    }

    private static final String EMPTY_STRING = "";
    private static final String FILE_NAME = "./TxtLogger_Tests-lab3.log";
    private static final String REGEX_DATA = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
    private static final String REGEX_TIME = "(0?[0-9]|(1?[0-9]|2[0-3])):([0-5][0-9]):([0-5][0-9])";
    private TxtLogger txtLogger;
}
