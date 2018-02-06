package ru.unn.agile.Triangle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {

    @Override
    public void log(final String str) {
        logger.add(str);
    }

    @Override
    public List<String> getLog() {
        return logger;
    }

    private ArrayList<String> logger = new ArrayList<String>();
}
