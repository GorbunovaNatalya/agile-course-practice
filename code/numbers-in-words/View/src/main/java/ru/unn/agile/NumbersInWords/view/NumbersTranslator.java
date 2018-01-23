package ru.unn.agile.NumbersInWords.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import ru.unn.agile.NumbersInWords.viewmodel.ViewModel;
import ru.unn.agile.NumbersInWords.infrastructure.TxtLogger;

import java.time.LocalDate;

public class NumbersTranslator {
    @FXML
    void initialize() {
        viewModel.setLogger(new TxtLogger("./TxtLogger-lab3_" + LocalDate.now() + ".log"));

        final ChangeListener<Boolean> focusChangeListener = (observable, oldValue, newValue)
                -> viewModel.onFocusChanged(oldValue, newValue);

        txtNumber.textProperty().bindBidirectional(viewModel.numberProperty());
        txtNumber.focusedProperty().addListener(focusChangeListener);
        btnTranslate.setOnAction(event -> viewModel.translate());
    }

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtNumber;
    @FXML
    private Button btnTranslate;
}
