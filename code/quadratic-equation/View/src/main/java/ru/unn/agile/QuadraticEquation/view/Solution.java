package ru.unn.agile.QuadraticEquation.view;

import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import ru.unn.agile.QuadraticEquation.viewmodel.ViewModel;

public class Solution {

    @FXML
    void initialize() {
        final List<TextField> textFields = new ArrayList<TextField>() {
            {
                add(first);
                add(second);
                add(third);
            }
        };
        first.textProperty().bindBidirectional(viewModel.aProperty());
        second.textProperty().bindBidirectional(viewModel.bProperty());
        third.textProperty().bindBidirectional(viewModel.cProperty());

        Pattern p = Pattern.compile(LEGAL_INPUT);
        for (TextField textField : textFields) {
            TextFormatter<String> formatter = new TextFormatter<>(
                    (UnaryOperator<TextFormatter.Change>) change -> {
                        return p.matcher(change.getControlNewText()).matches() ? change : null;
                    });
            textField.setTextFormatter(formatter);
        }

        btnSolve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.solve();
            }
        });
    }

    private static final String LEGAL_INPUT = "[-+]?[0-9]{0,7}(\\.[0-9]{0,7})?([0-9]{0,3})?";
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField first;
    @FXML
    private TextField second;
    @FXML
    private TextField third;
    @FXML
    private Button btnSolve;
}


