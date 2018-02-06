package ru.unn.agile.MetricsDistance.viewmodel;

import java.util.List;

public interface ILogger {
    void log(String s, String logTag);
    void log(String s);

    List<String> getLog();
}
