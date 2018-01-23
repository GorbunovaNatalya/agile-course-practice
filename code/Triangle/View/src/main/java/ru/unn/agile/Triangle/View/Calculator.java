package ru.unn.agile.Triangle.View;

import ru.unn.agile.Triangle.ViewModel.ViewModel;
import javafx.fxml.FXML;


import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class Calculator {
    @FXML
    void initialize() {
        pointAX.textProperty().bindBidirectional(viewModel.coordAxProperty());
        pointAY.textProperty().bindBidirectional(viewModel.coordAyProperty());
        pointBX.textProperty().bindBidirectional(viewModel.coordBxProperty());
        pointBY.textProperty().bindBidirectional(viewModel.coordByProperty());
        pointCX.textProperty().bindBidirectional(viewModel.coordCxProperty());
        pointCY.textProperty().bindBidirectional(viewModel.coordCyProperty());
        btnCalc.setOnAction(event -> viewModel.calculate());
    }

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField pointAX;
    @FXML
    private TextField pointAY;
    @FXML
    private TextField pointBX;
    @FXML
    private TextField pointBY;
    @FXML
    private TextField pointCX;
    @FXML
    private TextField pointCY;
    @FXML
    private Button btnCalc;
}
