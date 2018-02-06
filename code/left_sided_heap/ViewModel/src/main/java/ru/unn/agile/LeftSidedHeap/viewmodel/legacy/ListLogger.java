package ru.unn.agile.LeftSidedHeap.viewmodel.legacy;

import java.util.ArrayList;
import java.util.List;

public class ListLogger implements ILogger {
    @Override
    public void addInfo(final String s) {
        fullLog.add(s);
    }

    @Override
    public List<String> getFullLog() {
        return fullLog;
    }

    private final List<String> fullLog = new ArrayList<>();
}
