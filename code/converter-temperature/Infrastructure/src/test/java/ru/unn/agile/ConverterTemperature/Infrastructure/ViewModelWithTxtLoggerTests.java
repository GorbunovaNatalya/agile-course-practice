package ru.unn.agile.ConverterTemperature.Infrastructure;

import ru.unn.agile.ConverterTemperature.viewmodel.ViewModel;
import ru.unn.agile.ConverterTemperature.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
                new TxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
