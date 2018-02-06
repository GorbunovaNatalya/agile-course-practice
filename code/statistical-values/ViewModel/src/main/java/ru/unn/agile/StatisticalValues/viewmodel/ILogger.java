package ru.unn.agile.StatisticalValues.viewmodel;

import java.util.List;

public interface ILogger {
    void addLogText(String message);

    List<String> getLogText();
}
