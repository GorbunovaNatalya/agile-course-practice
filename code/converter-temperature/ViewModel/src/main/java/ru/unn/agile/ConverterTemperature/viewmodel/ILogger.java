package ru.unn.agile.ConverterTemperature.viewmodel;

import java.util.List;

public interface ILogger {
    void log(String s);

    List<String> getLog();
}
