package ru.unn.agile.NumberSystemConverter.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    @Override
    public void log(final String message) {
        logs.add(message);
    }

    @Override
    public void log(final String message, final String tag) {
        logs.add(tag + ": " + message);
    }

    @Override
    public List<String> getLog() {
        return logs;
    }

    private final ArrayList<String> logs = new ArrayList<>();
}
