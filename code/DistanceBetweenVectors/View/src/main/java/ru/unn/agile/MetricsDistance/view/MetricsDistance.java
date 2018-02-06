package ru.unn.agile.MetricsDistance.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;

import ru.unn.agile.MetricsDistance.Model.Metric;
import ru.unn.agile.MetricsDistance.viewmodel.ViewModel;
import ru.unn.agile.MetricsDistance.Infrastructure.TxtLogger;
import ru.unn.agile.MetricsDistance.Infrastructure.CurrentDate;

public class MetricsDistance {
    @FXML
    void initialize() {
        String currentDate = CurrentDate.getCurrentDate(DATE_FORMAT);
        viewModel.setLogger(new TxtLogger("./TxtLogger-lab3-" + currentDate + ".log"));

        final ChangeListener<Boolean> focusChangeListener = (observable, oldValue, newValue)
                -> viewModel.onFocusChanged(oldValue, newValue);

        txtV1x.textProperty().bindBidirectional(viewModel.vec1XProperty());
        txtV1x.focusedProperty().addListener(focusChangeListener);

        txtV1y.textProperty().bindBidirectional(viewModel.vec1YProperty());
        txtV1y.focusedProperty().addListener(focusChangeListener);

        txtV2x.textProperty().bindBidirectional(viewModel.vec2XProperty());
        txtV2x.focusedProperty().addListener(focusChangeListener);

        txtV2y.textProperty().bindBidirectional(viewModel.vec2YProperty());
        txtV2y.focusedProperty().addListener(focusChangeListener);

        txtDim.textProperty().bindBidirectional(viewModel.dimProperty());
        txtDim.focusedProperty().addListener(focusChangeListener);

        cbMetric.valueProperty().bindBidirectional(viewModel.metricProperty());
        cbMetric.valueProperty().addListener((observable, oldValue, newValue)
                -> viewModel.onMetricChanged(oldValue, newValue));

        btnCalc.setOnAction(event -> viewModel.calculate());
    }

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtV1x;
    @FXML
    private TextField txtV1y;
    @FXML
    private TextField txtV2x;
    @FXML
    private TextField txtV2y;
    @FXML
    private TextField txtDim;
    @FXML
    private ComboBox<Metric> cbMetric;
    @FXML
    private Button btnCalc;

    private static final String DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss";
}
