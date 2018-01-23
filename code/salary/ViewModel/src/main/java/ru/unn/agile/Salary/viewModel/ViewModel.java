package ru.unn.agile.Salary.viewModel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.Salary.model.Salary;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final StringProperty pay = new SimpleStringProperty();
    private final StringProperty workedHours = new SimpleStringProperty();
    private final StringProperty overHours = new SimpleStringProperty();
    private final StringProperty adminLeave = new SimpleStringProperty();

    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();

    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    // FXML needs default c-tor for binding
    public ViewModel() {
        pay.set("");
        workedHours.set("");
        overHours.set("");
        adminLeave.set("");
        result.set("");
        status.set(Status.WAITING.toString());

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(pay, workedHours, overHours, adminLeave);
            }

            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calculationDisabled.bind(couldCalculate.not());

        // Add listeners to the input text fields
        final List<StringProperty> fields = new ArrayList<StringProperty>() {
            {
                add(pay);
                add(workedHours);
                add(overHours);
                add(adminLeave);
            }
        };

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void calculate() {
        if (calculationDisabled.get()) {
            return;
        }

        Salary salary = new Salary(Integer.parseInt(pay.get()), Integer.parseInt(workedHours.get()),
                Integer.parseInt(overHours.get()), Integer.parseInt(adminLeave.get()));

        result.set(Double.toString(salary.calculateSalary()));
        status.set(Status.SUCCESS.toString());
    }

    public StringProperty payProperty() {
        return pay;
    }

    public StringProperty workProperty() {
        return workedHours;
    }

    public StringProperty overProperty() {
        return overHours;
    }

    public StringProperty adminProperty() {
        return adminLeave;
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    public final boolean isCalculationDisabled() {
        return calculationDisabled.get();
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

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (pay.get().isEmpty() || workedHours.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        if (overHours.get().isEmpty()) {
            overHours.set("0");
        }
        if (adminLeave.get().isEmpty()) {
            adminLeave.set("0");
        }
        try {
            if (!pay.get().isEmpty()) {
                Double.parseDouble(pay.get());
            }
            if (!workedHours.get().isEmpty()) {
                Double.parseDouble(workedHours.get());
            }
            if (!overHours.get().isEmpty()) {
                Double.parseDouble(overHours.get());
            }
            if (!adminLeave.get().isEmpty()) {
                Double.parseDouble(adminLeave.get());
            }
        } catch (NumberFormatException nfe) {
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
}

enum Status {
    WAITING("Please provide input data"),
    READY("Press 'Calculate' or Enter"),
    BAD_FORMAT("Bad format"),
    SUCCESS("Success");

    private final String name;

    Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
