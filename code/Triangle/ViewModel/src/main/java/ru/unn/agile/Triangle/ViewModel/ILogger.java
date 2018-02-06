package ru.unn.agile.Triangle.ViewModel;

import java.util.List;

public interface ILogger {
    void log(String s);

    List<String> getLog();
}
