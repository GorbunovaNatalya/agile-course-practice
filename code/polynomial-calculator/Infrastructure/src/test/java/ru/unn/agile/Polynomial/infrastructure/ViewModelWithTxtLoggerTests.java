package ru.unn.agile.Polynomial.infrastructure;

import ru.unn.agile.Polynomial.viewmodel.ViewModel;
import ru.unn.agile.Polynomial.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setBefore() {
        TxtLogger realLogger =
            new TxtLogger("./ViewModelWithTxtLoggerTests.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
