package ru.unn.agile.StringCalculator.infrastructure;

import ru.unn.agile.StringCalculator.viewmodel.ViewModel;
import ru.unn.agile.StringCalculator.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
                new TxtLogger("./ViewModel_with_TxtLogger_Tests.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
