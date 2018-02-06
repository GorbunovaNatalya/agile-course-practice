package ru.unn.agile.NumberSystemConverter.infrastructure;

import ru.unn.agile.NumberSystemConverter.viewmodel.NumberSystemConverterViewModel;
import ru.unn.agile.NumberSystemConverter.viewmodel.NumberSystemConverterViewModelTests;

public class ViewModelWithTextLoggerTests extends NumberSystemConverterViewModelTests {
    @Override
    public void setViewModel() {
        TextLogger realLogger =
            new TextLogger("./ViewModel_With_TextLogger_Tests_lab3.log");
        super.setExternalViewModel(new NumberSystemConverterViewModel(realLogger));
    }
}
