package ru.unn.agile.mergesort.view;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.mergesort.viewmodel.ViewModel;

public class MergeSort {
    @FXML
    void initialize() {
        StringProperty inputProperty = txtInput.textProperty();
        inputProperty.bindBidirectional(viewModel.inputProperty());
        btnSort.setOnAction(event -> viewModel.calculate());
    }

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtInput;
    @FXML
    private Button btnSort;
}
