package ru.unn.agile.LeftSidedHeap.infrastructure;

import ru.unn.agile.LeftSidedHeap.viewmodel.legacy.ViewModel;
import ru.unn.agile.LeftSidedHeap.viewmodel.legacy.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
            new TxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}

