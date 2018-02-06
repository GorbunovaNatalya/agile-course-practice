package ru.unn.agile.Triangle.Infrastructure;

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
        logger = new TxtLogger(NAMEOFFILE);
    }

    @Test
    public void canCreateLoggerWithNAMEOFFILE() {
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(NAMEOFFILE));
        } catch (FileNotFoundException e) {
            fail("File " + NAMEOFFILE + " wasn't found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";

        logger.log(testMessage);

        String message = logger.getLog().get(0);
        assertTrue(message.matches(".*" + testMessage));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] messages = {"Test message 1", "Test message 2"};

        logger.log(messages[0]);
        logger.log(messages[1]);

        List<String> actualMessages = logger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertTrue(actualMessages.get(i).matches(".*" + messages[i]));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";

        logger.log(testMessage);

        String message = logger.getLog().get(0);
        assertTrue(message.matches(PATTERN));
    }

    private static final String PATTERN = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*";
    private static final String NAMEOFFILE = "./TxtLoggerTests-lab3-legacy.log";
    private TxtLogger logger;
}
