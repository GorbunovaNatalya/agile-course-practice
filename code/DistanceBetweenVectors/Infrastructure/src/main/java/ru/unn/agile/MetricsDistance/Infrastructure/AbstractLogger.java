package ru.unn.agile.MetricsDistance.Infrastructure;

import java.util.List;

import ru.unn.agile.MetricsDistance.viewmodel.ILogger;

public abstract class AbstractLogger implements ILogger {

    public void log(final String s) {
        this.log(s, "");
    }

    public abstract void log(String s, String logTag);
    public abstract List<String> getLog();

    protected String prepareMassageForLog(final String message, final String logTag) {
        String resultMessage = CurrentDate.getCurrentDate(DATE_FORMAT_NOW) + " > "
                + message + System.lineSeparator();
        if (!logTag.isEmpty()) {
            resultMessage = logTag + ": " + resultMessage;
        }
        return resultMessage;
    }

    protected static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
}
