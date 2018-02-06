package ru.unn.agile.PrimeNumber.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    @Override
    public void log(final String s) {
        log.add(s);
    }

    @Override
    public List<String> getLog() {
        return log;
    }

    private final ArrayList<String> log = new ArrayList<>();
}
