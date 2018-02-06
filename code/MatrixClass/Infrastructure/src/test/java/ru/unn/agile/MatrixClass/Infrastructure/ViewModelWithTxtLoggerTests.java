package ru.unn.agile.MatrixClass.Infrastructure;

import ru.unn.agile.MatrixClass.viewmodel.ViewModel;
import ru.unn.agile.MatrixClass.viewmodel.ViewModelTest;

public class ViewModelWithTxtLoggerTests extends ViewModelTest {
    @Override
    public void setUp() {
        TxtLogger realLogger =
                new TxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
