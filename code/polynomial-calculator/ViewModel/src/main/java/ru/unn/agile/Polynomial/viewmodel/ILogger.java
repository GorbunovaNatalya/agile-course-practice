package ru.unn.agile.Polynomial.viewmodel;

import java.util.List;

public interface ILogger {
    void addInfo(String message);

    List<String> getLog();
}
