package ru.unn.agile.MatrixClass.Infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static ru.unn.agile.MatrixClass.Infrastructure.RegexMatcher.matchesPattern;

public class TxtLoggerTests {
    @Before
    public void setUp() {
        textLogger = new TxtLogger(FILENAME);
    }

    @Test
    public void canCreateLoggerWithFilename() {
        assertNotNull(textLogger);
    }

    @Test
    public void canCreateLogFileOnDevice() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " was not found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";

        textLogger.log(testMessage);

        String message = textLogger.getLog().get(0);
        assertThat(message, matchesPattern(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] messages = {"Test message 1", "Test message 2"};

        textLogger.log(messages[0]);
        textLogger.log(messages[1]);

        List<String> actualMessages = textLogger.getLog();
        int i = 0;
        for (i = 0; i < actualMessages.size(); i++) {
            assertThat(actualMessages.get(i), matchesPattern(".*" + messages[i] + "$"));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test";
        textLogger.log(testMessage);
        String message = textLogger.getLog().get(0);
        assertThat(message, matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }

    private static final String FILENAME = "./TxtLogger_Tests-lab3.log";
    private TxtLogger textLogger;
}
