package ru.unn.agile.ConverterTemperature.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    @Override
    public void log(final String message) {
        log.add(message);
    }

    @Override
    public List<String> getLog() {
        return log;
    }

    private final ArrayList<String> log = new ArrayList<>();
}
