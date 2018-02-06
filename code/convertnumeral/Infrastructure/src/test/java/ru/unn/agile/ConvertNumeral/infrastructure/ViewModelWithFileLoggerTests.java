package ru.unn.agile.ConvertNumeral.infrastructure;

import ru.unn.agile.ConvertNumeral.ViewModel.legacy.ViewModel;
import ru.unn.agile.ConvertNumeral.ViewModel.legacy.ViewModelTests;

public class ViewModelWithFileLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        FileLogger realLogger =
                new FileLogger("./ViewModelWithFileLoggerTests.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
