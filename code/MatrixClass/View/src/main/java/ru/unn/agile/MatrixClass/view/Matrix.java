package ru.unn.agile.MatrixClass.view;

//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.MatrixClass.Infrastructure.TxtLogger;
import ru.unn.agile.MatrixClass.viewmodel.ViewModel;

public class Matrix {
    @FXML
    void initialize() {
        viewModel.setLogger(new TxtLogger("./TxtLogger-lab3.log"));
        matrixSize.textProperty().bindBidirectional(viewModel.matrixSizeProperty());
        fieldForMatrix.textProperty().bindBidirectional(viewModel.fieldForMatrixProperty());

        /*final ChangeListener<Boolean> focusChngListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };*/

        calculate.setOnAction(event -> viewModel.calculate());
    }

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField matrixSize;
    @FXML
    private TextField fieldForMatrix;
    @FXML
    private Button calculate;
}
