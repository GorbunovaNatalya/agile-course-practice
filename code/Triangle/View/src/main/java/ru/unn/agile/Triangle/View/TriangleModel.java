package ru.unn.agile.Triangle.View;

import ru.unn.agile.Triangle.Infrastructure.TxtLogger;
import ru.unn.agile.Triangle.ViewModel.ViewModel;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TextFormatter;

import java.util.List;
import java.util.ArrayList;

public class TriangleModel {
    @FXML
    void initialize() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH", Locale.ENGLISH);
        String currentDate = sdf.format(cal.getTime());
        viewModel.setLogger(new TxtLogger("./TxtLogger-lab3_" + currentDate + ".log"));

        final ChangeListener<Boolean> focusChangeListener = (observable, oldValue, newValue)
                -> viewModel.onFocusChanged(oldValue, newValue);

        final List<TextField> textFields = new ArrayList<TextField>() {
            {
                add(pointAX);
                add(pointAY);
                add(pointBX);
                add(pointBY);
                add(pointCX);
                add(pointCY);
            }
        };

        pointAX.textProperty().bindBidirectional(viewModel.coordAxProperty());
        pointAX.focusedProperty().addListener(focusChangeListener);
        pointAY.textProperty().bindBidirectional(viewModel.coordAyProperty());
        pointAY.focusedProperty().addListener(focusChangeListener);
        pointBX.textProperty().bindBidirectional(viewModel.coordBxProperty());
        pointBX.focusedProperty().addListener(focusChangeListener);
        pointBY.textProperty().bindBidirectional(viewModel.coordByProperty());
        pointBY.focusedProperty().addListener(focusChangeListener);
        pointCX.textProperty().bindBidirectional(viewModel.coordCxProperty());
        pointCX.focusedProperty().addListener(focusChangeListener);
        pointCY.textProperty().bindBidirectional(viewModel.coordCyProperty());
        pointCY.focusedProperty().addListener(focusChangeListener);

        Pattern p = Pattern.compile(LEGAL_INPUT);
        for (TextField textField : textFields) {
            TextFormatter<String> formatter = new TextFormatter<>(change ->
                    p.matcher(change.getControlNewText()).matches() ? change : null);
            textField.setTextFormatter(formatter);
        }
        btnCalc.setOnAction(event -> viewModel.calculate());
    }

    private static final String LEGAL_INPUT = "[-+]?[0-9]{0,7}(\\.[0-9]{0,7})?([0-9]{0,3})?";
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
