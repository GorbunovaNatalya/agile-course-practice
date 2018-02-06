package ru.unn.agile.Triangle.Infrastructure;

import ru.unn.agile.Triangle.ViewModel.ViewModel;
import ru.unn.agile.Triangle.ViewModel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setViewModel() {
        TxtLogger realLogger =
                new TxtLogger("./ViewModelWithTxtLoggerTests-lab3-legacy.log");
        super.setTheOtherViewModel(new ViewModel(realLogger));
    }
}
