package ru.unn.agile.Polynomial.viewmodel;

import java.util.LinkedList;
import java.util.List;

public class ListLogger implements ILogger {
    @Override
    public void addInfo(final String str) {
        log.add(str);
    }

    @Override
    public List<String> getLog() {
        return log;
    }

    private final List<String> log = new LinkedList<>();
}
