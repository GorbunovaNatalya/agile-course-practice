package ru.unn.agile.Salary.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.Salary.viewModel.ViewModel;

public class Calculator {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtPay;
    @FXML
    private TextField txtWorked;
    @FXML
    private TextField txtOver;
    @FXML
    private TextField txtAdmin;
    @FXML
    private Button btnCalc;

    @FXML
    void initialize() {
        // Two-way binding hasn't supported by FXML yet, so place it in code-behind
        txtPay.textProperty().bindBidirectional(viewModel.payProperty());
        txtWorked.textProperty().bindBidirectional(viewModel.workProperty());
        txtOver.textProperty().bindBidirectional(viewModel.overProperty());
        txtAdmin.textProperty().bindBidirectional(viewModel.adminProperty());

        btnCalc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }
}
