package ru.unn.agile.ConverterTemperature.view;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import ru.unn.agile.ConverterTemperature.viewmodel.ViewModel;
import ru.unn.agile.ConverterTemperature.viewmodel.NameSystem;
import ru.unn.agile.ConverterTemperature.Infrastructure.TxtLogger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ConverterTemperature {
    @FXML
    void initialize() {
        viewModel.setLogger(new TxtLogger("./ConverterTemperature_Log_" + now() + ".log"));

        final ChangeListener<Boolean> focusChangeListener = (observable, oldValue, newValue)
                -> viewModel.onFocusChanged(oldValue, newValue);

        final ChangeListener<NameSystem> inputTypeChangeListener = (observable, oldValue, newValue)
                -> viewModel.onInputTypeChanged(oldValue, newValue);

        final ChangeListener<NameSystem> outputTypeChangeListener = (observable, oldValue, newValue)
                -> viewModel.onOutputTypeChanged(oldValue, newValue);

        inputTemperature.textProperty().bindBidirectional(viewModel.inputTemperatureProperty());
        inputTemperature.focusedProperty().addListener(focusChangeListener);

        inputTypeSelector.valueProperty().bindBidirectional(viewModel.inputTypeProperty());
        inputTypeSelector.focusedProperty().addListener(focusChangeListener);
        inputTypeSelector.valueProperty().addListener(inputTypeChangeListener);

        outputTypeSelector.valueProperty().bindBidirectional(viewModel.outputTypeProperty());
        outputTypeSelector.focusedProperty().addListener(focusChangeListener);
        outputTypeSelector.valueProperty().addListener(outputTypeChangeListener);

        convertButton.setOnAction(event -> viewModel.convert());
        convertButton.focusedProperty().addListener(focusChangeListener);
    }

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField inputTemperature;
    @FXML
    private ComboBox<NameSystem> inputTypeSelector;
    @FXML
    private ComboBox<NameSystem> outputTypeSelector;
    @FXML
    private Button convertButton;
}
