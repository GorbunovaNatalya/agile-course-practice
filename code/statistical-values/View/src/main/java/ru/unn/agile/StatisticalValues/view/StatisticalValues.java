package ru.unn.agile.StatisticalValues.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;

import ru.unn.agile.StatisticalValues.viewmodel.Statistic;
import ru.unn.agile.StatisticalValues.viewmodel.ViewModel;
import ru.unn.agile.StatisticalValues.infrastructure.StatisticalLogger;

public class StatisticalValues {
    @FXML
    void initialize() {
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        String currentDate = sdf.format(cal.getTime());
        viewModel.setLogger(new StatisticalLogger("./TxtLogger-lab3_" + currentDate + ".log"));

        final ChangeListener<Boolean> focusChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };

        cbStatistic.valueProperty().bindBidirectional(viewModel.statisticProperty());

        tfOrder.textProperty().bindBidirectional(viewModel.orderProperty());
        tfOrder.visibleProperty().bindBidirectional(viewModel.orderVisibilityProperty());
        lbOrder.visibleProperty().bindBidirectional(viewModel.orderVisibilityProperty());
        tfOrder.focusedProperty().addListener(focusChangeListener);

        cbIsBiased.selectedProperty().bindBidirectional(viewModel.isBiasedProperty());
        cbIsBiased.visibleProperty().bindBidirectional(viewModel.isBiasedVisibilityProperty());
        cbIsBiased.focusedProperty().addListener(focusChangeListener);

        tfValues.textProperty().bindBidirectional(viewModel.valuesProperty());
        tfValues.visibleProperty().bindBidirectional(viewModel.valuesVisibilityProperty());
        lbValues.visibleProperty().bindBidirectional(viewModel.valuesVisibilityProperty());

        lbResultValue.textProperty().bindBidirectional(viewModel.resultProperty());
        lbResultValue.visibleProperty().bindBidirectional(viewModel.resultVisibilityProperty());
        lbResultValue.focusedProperty().addListener(focusChangeListener);

        lbResult.visibleProperty().bindBidirectional(viewModel.resultVisibilityProperty());

        lbStatusValue.textProperty().bindBidirectional(viewModel.statusProperty());

        btCalculate.setOnAction(actionEvent -> viewModel.calculate());
        btCalculate.visibleProperty().bindBidirectional(viewModel.calculateVisibilityProperty());

        cbStatistic.valueProperty().addListener(new ChangeListener<Statistic>() {
            @Override
            public void changed(final ObservableValue<? extends Statistic> observable,
                                final Statistic oldValue,
                                final Statistic newValue) {
                viewModel.onStatisticChanged(oldValue, newValue);
            }
        });

        cbIsBiased.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue,
                                final Boolean newValue) {
                viewModel.onBiasChanged(oldValue, newValue);
            }
        });
    }

    @FXML
    private ViewModel viewModel;
    @FXML
    private ComboBox<Statistic> cbStatistic;
    @FXML
    private TextField tfOrder;
    @FXML
    private Label lbOrder;
    @FXML
    private CheckBox cbIsBiased;
    @FXML
    private TextField tfValues;
    @FXML
    private Label lbValues;
    @FXML
    private Label lbResultValue;
    @FXML
    private Label lbResult;
    @FXML
    private Label lbStatusValue;
    @FXML
    private Button btCalculate;

    private static final String DATE_FORMAT = "yyyy-MM-dd";
}
