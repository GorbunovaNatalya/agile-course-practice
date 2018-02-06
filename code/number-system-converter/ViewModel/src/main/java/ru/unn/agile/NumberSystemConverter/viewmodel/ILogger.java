package ru.unn.agile.NumberSystemConverter.viewmodel;

import java.util.List;

public interface ILogger {
    void log(String message, String tag);
    void log(String message);

    List<String> getLog();
}
