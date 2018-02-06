package ru.unn.agile.NumberSystemConverter.infrastructure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import static ru.unn.agile.NumberSystemConverter.infrastructure.RegularExpressionMatcher.getMatcher;

public class TextLoggerTests {
    @Before
    public void setUp() {
        logger = new TextLogger(FILE_NAME);
    }

    @Test
    public void canCreateTextLoggerByFileName() {
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(FILE_NAME);
        } catch (FileNotFoundException exception) {
            fail(FILE_NAME + ": was not found.");
        }

        new BufferedReader(fileReader);
    }

    @Test
    public void canWriteTextLogMessage() {
        String message = "Message";

        logger.log(message);

        assertThat(logger.getLog().get(0), getMatcher(".*" + message + "$"));
    }

    @Test
    public void canWriteSeveralTextLogMessages() {
        String[] messages = {"Message 1", "Message 2"};

        logger.log(messages[0]);
        logger.log(messages[1]);

        List<String> actualMessages = logger.getLog();
        for (int message = 0; message < actualMessages.size(); message++) {
            assertThat(actualMessages.get(message), getMatcher(".*" + messages[message] + "$"));
        }
    }

    @Test
    public void doesLogContainDateAndTimeInProperFormat() {
        String message = "Message";

        logger.log(message);

        assertThat(logger.getLog().get(0), getMatcher(DATE_FORMAT));
    }

    @Test
    public void doesLogContainTag() {
        String message = "Message";
        String tag = "Tag";

        logger.log(message, tag);

        assertThat(logger.getLog().get(0), getMatcher(tag + ".*$"));
    }

    @Test
    public void doesLogContainDateAndTimeInProperFormatWithSeveralMessages() {
        String[] messages = {"Message 1", "Message 2"};

        logger.log(messages[0]);
        logger.log(messages[1]);

        for (String actualMessage : logger.getLog()) {
            assertThat(actualMessage, getMatcher(DATE_FORMAT));
        }
    }

    private static final String FILE_NAME = "./TextLogger_Tests_lab3.log";
    private static final String DATE_FORMAT = "^\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}:\\d{2}: .*";
    private TextLogger logger;
}
