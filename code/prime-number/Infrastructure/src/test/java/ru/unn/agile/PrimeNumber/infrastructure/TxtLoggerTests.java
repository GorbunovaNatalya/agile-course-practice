package ru.unn.agile.PrimeNumber.infrastructure;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertNotNull;

public class TxtLoggerTests {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void canLoggerBeCreated() {
        assertNotNull(new TxtLogger(LOGFILE_NAME));
    }

    @Test
    public void cantLoggerBeCreatedWithNullArgument() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Name of file can't be null or empty!");
        assertNull(new TxtLogger(null));
    }

    @Test
    public void cantLoggerBeCreatedWithEmptyFileName() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Name of file can't be null or empty!");
        assertNull(new TxtLogger(""));
    }

    @Test
    public void canLogFileBeCreatedOnDisk() {
        File logFile = new File(LOGFILE_NAME);
        assertTrue(logFile.exists());
    }

    @Test
    public void canAddMessageToLog() {
        String addedMessage = "Test log";

        TxtLogger txtLogger = new TxtLogger(LOGFILE_NAME);
        txtLogger.log(addedMessage);

        String actualMessage = txtLogger.getLog().get(0);
        assertTrue(Pattern.matches(".*" + addedMessage + "$", actualMessage));
    }

    @Test
    public void canAddSeveralMessagesToLog() {
        String[] addedMessages = {"Test log 1", "Test log 2"};

        TxtLogger txtLogger = new TxtLogger(LOGFILE_NAME);
        txtLogger.log(addedMessages[0]);
        txtLogger.log(addedMessages[1]);

        List<String> log = txtLogger.getLog();
        for (int i = 0; i < log.size(); i++) {
            String actualMessage = log.get(i);
            assertTrue(Pattern.matches(".*" + addedMessages[i] + "$", actualMessage));
        }
    }

    @Test
    public void doesLogContainDateAndTimeInTheBeginning() {
        String addedMessage = "Test log";

        TxtLogger txtLogger = new TxtLogger(LOGFILE_NAME);
        txtLogger.log(addedMessage);

        String actualMessage = txtLogger.getLog().get(0);
        assertTrue(Pattern.matches(
                "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.*", actualMessage));
    }

    private static final String LOGFILE_NAME = "./TestingTxtLogger.log";
    private TxtLogger txtLogger;
}
