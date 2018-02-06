package ru.unn.agile.ConvertNumeral.infrastructure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

public class FileLoggerTests {
    @Before
    public void setUp() {
        fileLogger = new FileLogger(FILENAME);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(fileLogger);
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";

        fileLogger.log(testMessage);

        String message = fileLogger.getLog().get(0);
        assertTrue(message.matches(".*" + testMessage + "$"));
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";

        fileLogger.log(testMessage);

        String message = fileLogger.getLog().get(0);
        assertTrue(message.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] testMessages = {"Test message 1", "Test message 2"};

        fileLogger.log(testMessages[0]);
        fileLogger.log(testMessages[1]);

        List<String> messages = fileLogger.getLog();
        for (int i = 0; i < messages.size(); i++) {
            assertTrue(messages.get(i).matches(".*" + testMessages[i] + "$"));
        }
    }

    @Test
    public void canCreateLoggerWithEmptyFileName() {
        assertNotNull(new FileLogger(""));
    }

    @Test
    public void canWriteLogMessageWhenEmptyFileName() {
        fileLogger = new FileLogger("");
        String testMessage = "Test message";

        fileLogger.log(testMessage);
        List<String> messages = fileLogger.getLog();

        assertEquals(0, messages.size());
    }

    private static final String FILENAME = "./FileLoggerTests.log";
    private FileLogger fileLogger;
}
