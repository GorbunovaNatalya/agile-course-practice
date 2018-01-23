package ru.unn.agile.ConverterTemperature.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.ConverterTemperature.viewmodel.ViewModel;
import ru.unn.agile.ConverterTemperature.viewmodel.NameSystem;


public class ConverterTemperature {
    @FXML
    void initialize() {
        inputTemp.textProperty().bindBidirectional(viewModel.inputTemperatureProperty());
        cbOperation.valueProperty().bindBidirectional(viewModel.inputTypeProperty());
        cbOperation1.valueProperty().bindBidirectional(viewModel.outputTypeProperty());

        btnCalc.setOnAction(event -> viewModel.convert());
    }

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField inputTemp;
    @FXML
    private ComboBox<NameSystem> cbOperation;
    @FXML
    private ComboBox<NameSystem> cbOperation1;
    @FXML
    private Button btnCalc;
}
