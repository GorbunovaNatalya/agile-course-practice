package ru.unn.agile.LeftSidedHeap.infrastructure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import static junit.framework.TestCase.assertNotNull;

public class TxtLoggerTests {
    @Before
    public void setUp() {
        txtLogger = new TxtLogger(LOGGERFILENAME);
    }

    @Test
    public void canConstructLoggerWithFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLogFile() {
        try {
            new BufferedReader(new FileReader(LOGGERFILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + LOGGERFILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Log message";

        txtLogger.addInfo(testMessage);

        String message = txtLogger.getFullLog().get(0);
        assertTrue(message.matches(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] messages = {"Log message 1", "Log message 2"};

        txtLogger.addInfo(messages[0]);
        txtLogger.addInfo(messages[1]);

        List<String> actualLog = txtLogger.getFullLog();
        for (int i = 0; i < actualLog.size(); i++) {
            assertTrue(actualLog.get(i).matches(".*" + messages[i] + "$"));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";

        txtLogger.addInfo(testMessage);

        String message = txtLogger.getFullLog().get(0);
        String dateRegex = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
        String timeRegex = "(0?[0-9]|(1?[0-9]|2[0-3])):([0-5][0-9]):([0-5][0-9])";
        assertTrue(message.matches(dateRegex + " " + timeRegex + " > .*"));
    }

    @Test
    public void notFailConstructTxtLoggerWithBadFileName() {
        new TxtLogger(FILENAME_ACCESS_DENIED);
    }

    @Test
    public void notFailLoggingWithNullPointerWriter() {
        TxtLogger badLogger = new TxtLogger(FILENAME_ACCESS_DENIED);
        badLogger.addInfo("");
    }

    @Test
    public void notFailGetFullLogWithNullPointerWriter() {
        TxtLogger badLogger = new TxtLogger(FILENAME_ACCESS_DENIED);
        badLogger.getFullLog();
    }

    private static final String FILENAME_ACCESS_DENIED = "";
    private static final String LOGGERFILENAME = "./TxtLogger_Tests-lab3.log";
    private TxtLogger txtLogger;
}
