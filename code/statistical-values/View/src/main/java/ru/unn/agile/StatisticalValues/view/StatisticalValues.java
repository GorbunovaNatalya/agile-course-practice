package ru.unn.agile.StatisticalValues.view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import ru.unn.agile.StatisticalValues.viewmodel.Statistic;
import ru.unn.agile.StatisticalValues.viewmodel.ViewModel;

public class StatisticalValues {
    @FXML
    void initialize() {
        cbStatistic.valueProperty().bindBidirectional(viewModel.statisticProperty());

        tfOrder.textProperty().bindBidirectional(viewModel.orderProperty());
        tfOrder.visibleProperty().bindBidirectional(viewModel.orderVisibilityProperty());
        lbOrder.visibleProperty().bindBidirectional(viewModel.orderVisibilityProperty());

        cbIsBiased.selectedProperty().bindBidirectional(viewModel.isBiasedProperty());
        cbIsBiased.visibleProperty().bindBidirectional(viewModel.isBiasedVisibilityProperty());

        tfValues.textProperty().bindBidirectional(viewModel.valuesProperty());
        tfValues.visibleProperty().bindBidirectional(viewModel.valuesVisibilityProperty());
        lbValues.visibleProperty().bindBidirectional(viewModel.valuesVisibilityProperty());

        lbResultValue.textProperty().bindBidirectional(viewModel.resultProperty());
        lbResultValue.visibleProperty().bindBidirectional(viewModel.resultVisibilityProperty());
        lbResult.visibleProperty().bindBidirectional(viewModel.resultVisibilityProperty());

        lbStatusValue.textProperty().bindBidirectional(viewModel.statusProperty());

        btCalculate.setOnAction(actionEvent -> viewModel.calculate());
        btCalculate.visibleProperty().bindBidirectional(viewModel.calculateVisibilityProperty());
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
}
