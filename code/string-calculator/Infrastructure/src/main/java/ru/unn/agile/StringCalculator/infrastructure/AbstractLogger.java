package ru.unn.agile.StringCalculator.infrastructure;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ru.unn.agile.StringCalculator.viewmodel.ILogger;

public abstract class AbstractLogger implements ILogger {
    protected static String prepareLogMessage(final String logTag, final String message) {
        String resultMessage = now() + " > ";
        if (!logTag.isEmpty()) {
            resultMessage = resultMessage + logTag + ": "
                    + message + System.lineSeparator();
        } else {
            resultMessage = resultMessage + message + System.lineSeparator();
        }

        return resultMessage;
    }

    protected static String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return dateFormat.format(calendar.getTime());
    }

    protected static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
}
