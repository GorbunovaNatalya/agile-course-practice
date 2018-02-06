package ru.unn.agile.LeftSidedHeap.viewmodel.legacy;

import java.util.List;

public interface ILogger {
    void addInfo(String s);

    List<String> getFullLog();
}
