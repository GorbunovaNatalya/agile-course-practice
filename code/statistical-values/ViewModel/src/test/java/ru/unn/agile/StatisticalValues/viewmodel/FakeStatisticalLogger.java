package ru.unn.agile.StatisticalValues.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeStatisticalLogger implements ILogger {
    @Override
    public void addLogText(final String s) {
        log.add(s);
    }

    @Override
    public List<String> getLogText() {
        return log;
    }

    private final ArrayList<String> log = new ArrayList<>();
}
