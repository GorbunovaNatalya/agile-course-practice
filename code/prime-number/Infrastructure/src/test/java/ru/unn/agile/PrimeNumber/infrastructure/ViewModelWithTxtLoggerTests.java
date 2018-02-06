package ru.unn.agile.PrimeNumber.infrastructure;

import ru.unn.agile.PrimeNumber.viewmodel.ViewModel;
import ru.unn.agile.PrimeNumber.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
            new TxtLogger("./TestingViewModelWithTxtLogger.log");
        ViewModel viewModel = new ViewModel(realLogger);
        super.setViewModel(viewModel);
    }
}
