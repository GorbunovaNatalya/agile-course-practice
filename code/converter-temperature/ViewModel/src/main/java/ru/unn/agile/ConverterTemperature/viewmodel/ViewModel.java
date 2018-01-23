package ru.unn.agile.ConverterTemperature.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.ConverterTemperature.Model.Conversion;
import ru.unn.agile.ConverterTemperature.Model.ConverterTemperature;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;


public class ViewModel {
    public ViewModel() {
        inputTemperature.set("");
        inputType.set(NameSystem.CELSIUS);
        outputType.set(NameSystem.FAHRENHEIT);
        result.set("");
        status.set(Status.WAITING.toString());

        BooleanBinding canCalculate = new BooleanBinding() {
            {
                super.bind(inputTemperature);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calculationDisabled.bind(canCalculate.not());

        final List<StringProperty> fields = new ArrayList<StringProperty>() {{
            add(inputTemperature);
        }};

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public StringProperty inputTemperatureProperty() {
        return inputTemperature;
    }

    public StringProperty resultProperty() {
        return result;
    }

    public final String getResult() {
        return result.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getStatus() {
        return status.get();
    }

    public ObjectProperty<ObservableList<NameSystem>> inputTypesProperty() {
        return inputTypes;
    }

    public final ObservableList<NameSystem> getInputTypes() {
        return inputTypes.get();
    }

    public ObjectProperty<NameSystem> inputTypeProperty() {
        return inputType;
    }

    public ObjectProperty<ObservableList<NameSystem>> outputTypesProperty() {
        return outputTypes;
    }

    public final ObservableList<NameSystem> getOutputTypes() {
        return outputTypes.get();
    }

    public ObjectProperty<NameSystem> outputTypeProperty() {
        return outputType;
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    public final boolean isCalculationDisabled() {
        return calculationDisabled.get();
    }

    public void convert() {
        if (calculationDisabled.get()) {
            return;
        }
        Double resultDouble = 0.0;
        String in = inputType.get().toString();
        String out = outputType.get().toString();
        String typeConvertion = in + "_TO_" + out;
        try {
            if (!MATCHCONVERTER.containsKey(typeConvertion)) {
                if (in.equals(out)) {
                    status.set(Status.SUCCESS.toString());
                    result.set(inputTemperature.get());
                } else {
                    status.set(Status.BAD_FORMAT.toString());
                }
            } else {
                resultDouble =
                        ConverterTemperature.convert(input, MATCHCONVERTER.get(typeConvertion));
                status.set(Status.SUCCESS.toString());
                String formattedDouble =  String.format("%.2f", resultDouble).replace(',', '.');
                result.set(formattedDouble);
            }
        } catch (IllegalArgumentException excep) {
            status.set(Status.IMPOSSIBLE.toString());
        }
    }

    private final StringProperty inputTemperature = new SimpleStringProperty();

    private final ObjectProperty<ObservableList<NameSystem>> inputTypes =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(NameSystem.values()));

    private final ObjectProperty<NameSystem> inputType = new SimpleObjectProperty<>();

    private final ObjectProperty<ObservableList<NameSystem>> outputTypes =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(NameSystem.values()));

    private final ObjectProperty<NameSystem> outputType = new SimpleObjectProperty<>();

    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();

    private final StringProperty result = new SimpleStringProperty();

    private final StringProperty status = new SimpleStringProperty();

    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (inputTemperature.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!inputTemperature.get().isEmpty()) {
               input = Double.parseDouble(inputTemperature.get());
            }
        } catch (NumberFormatException excep) {
            inputStatus = Status.BAD_FORMAT;
        }

        return inputStatus;
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            status.set(getInputStatus().toString());
        }
    }

    private static final Map<String, Conversion> MATCHCONVERTER = Collections.unmodifiableMap(
        new HashMap<String, Conversion>() {{
            put("CELSIUS_TO_FAHRENHEIT", Conversion.CELSIUS_TO_FAHRENHEIT);
            put("CELSIUS_TO_KELVIN", Conversion.CELSIUS_TO_KELVIN);
            put("CELSIUS_TO_NEWTON", Conversion.CELSIUS_TO_NEWTON);

            put("FAHRENHEIT_TO_CELSIUS", Conversion.FAHRENHEIT_TO_CELSIUS);
            put("FAHRENHEIT_TO_KELVIN", Conversion.FAHRENHEIT_TO_KELVIN);
            put("FAHRENHEIT_TO_NEWTON", Conversion.FAHRENHEIT_TO_NEWTON);

            put("KELVIN_TO_CELSIUS", Conversion.KELVIN_TO_CELSIUS);
            put("KELVIN_TO_FAHRENHEIT", Conversion.KELVIN_TO_FAHRENHEIT);
            put("KELVIN_TO_NEWTON", Conversion.KELVIN_TO_NEWTON);

            put("NEWTON_TO_CELSIUS", Conversion.NEWTON_TO_CELSIUS);
            put("NEWTON_TO_FAHRENHEIT", Conversion.NEWTON_TO_FAHRENHEIT);
            put("NEWTON_TO_KELVIN", Conversion.NEWTON_TO_KELVIN);
        }}
    );

    private double input;
}

enum Status {
    WAITING("Please provide input data"),
    READY("Press 'Convert' or Enter"),
    BAD_FORMAT("Bad format"),
    SUCCESS("Success"),
    IMPOSSIBLE("Your input data is colder than heart of ex-girlfriend");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
