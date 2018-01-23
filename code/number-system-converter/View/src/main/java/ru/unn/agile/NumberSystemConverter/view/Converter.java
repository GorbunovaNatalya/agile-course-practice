package ru.unn.agile.NumberSystemConverter.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import ru.unn.agile.NumberSystemConverter.model.NumberSystemBase;
import ru.unn.agile.NumberSystemConverter.viewmodel.NumberSystemConverterViewModel;


public class Converter {
    @FXML
    void initialize() {
        this.textAreaBaseNumber.textProperty().bindBidirectional(
                this.viewModel.numberInBaseNumberSystemProperty()
        );

        this.textAreaTargetNumber.textProperty().bindBidirectional(
                this.viewModel.numberInTargetNumberSystemProperty()
        );

        this.comboBoxBase.valueProperty().bindBidirectional(
                this.viewModel.baseNumberSystemProperty()
        );

        this.comboBoxTarget.valueProperty().bindBidirectional(
                this.viewModel.targetNumberSystemProperty()
        );

        this.buttonConvert.disableProperty().bind(
                this.viewModel.conversionEnabledProperty().not()
        );

        this.buttonConvert.setOnAction(event -> viewModel.convert());

        this.viewModel.errorMessageIsShownProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(this.viewModel.getErrorMessage());
                    alert.showAndWait()
                            .filter(response -> response == ButtonType.OK)
                            .ifPresent(response -> this.viewModel.closeErrorDialog());
                }
            }
        );
    }

    @FXML
    private NumberSystemConverterViewModel viewModel;

    @FXML
    private TextArea textAreaBaseNumber;

    @FXML
    private TextArea textAreaTargetNumber;

    @FXML
    private ComboBox<NumberSystemBase> comboBoxBase;

    @FXML
    private ComboBox<NumberSystemBase> comboBoxTarget;

    @FXML
    private Button buttonConvert;
}
