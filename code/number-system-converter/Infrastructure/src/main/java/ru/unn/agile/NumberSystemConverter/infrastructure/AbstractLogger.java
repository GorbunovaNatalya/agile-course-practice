package ru.unn.agile.NumberSystemConverter.infrastructure;

import java.util.List;

import ru.unn.agile.NumberSystemConverter.viewmodel.ILogger;

public abstract class AbstractLogger implements ILogger {
    public void log(final String message) {
        this.log(message, "");
    }

    public abstract void log(String message, String tag);
    public abstract List<String> getLog();

    protected String prepare(final String message, final String tag) {
        String prepared = Date.getDate(DATE_FORMAT) + ": "
            + message + System.lineSeparator();

        if (!tag.isEmpty()) {
            prepared = tag + ": " + prepared;
        }

        return prepared;
    }

    protected static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";
}
