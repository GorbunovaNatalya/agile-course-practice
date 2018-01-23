package ru.unn.agile.StringCalculator.viewmodel;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;

import ru.unn.agile.StringCalculator.Model.StringCalculator;

public class ViewModel {
    public ViewModel() {
        initialize();
    }

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        initialize();
    }

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Parameter Logger can not be null");
        }
        this.logger = logger;
    }

    public void calculate() {
        if (isCalculationDisabled()) {
            return;
        }

        String inputString = getInputString();
        String correctInputString = getCorrectString(inputString);
        String summa = Integer.toString(StringCalculator.add(correctInputString));
        result.set(summa);
        status.set(Status.SUCCESS.toString());

        StringBuilder message = new StringBuilder(LogMessages.CALCULATE_WAS_PRESSED);
        message.append("Input string = ").append(inputString)
                .append("; Result = ").append(getResult());
        String tag = getLogTag();
        logger.log(tag, message.toString());
        updateLogs();
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        for (ValueChangeListener listener : changedListeners) {
            if (listener.isChanged()) {
                StringBuilder message = new StringBuilder(LogMessages.EDITING_FINISHED);
                message.append("Input string = ").append(getInputString());
                String tag = getLogTag();
                logger.log(tag, message.toString());
                updateLogs();
                listener.cache();
                break;
            }
        }
    }

    public void setInputString(final String inputString) {
        this.inputString.set(inputString);
    }

    public final String getInputString() {
        return inputString.get();
    }

    public StringProperty inputStringProperty() {
        return inputString;
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

    public StringProperty logsProperty() {
        return logs;
    }

    public void setLogs(final String record) {
        this.logs.set(record);
    }

    public final String getLogs() {
        return logsProperty().get();
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    public final boolean isCalculationDisabled() {
        return calculationDisabled.get();
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    public void setLogTag(final String tag) {
        this.logTag = tag;
    }

    public final String getLogTag() {
        return logTag;
    }

    private final StringProperty inputString = new SimpleStringProperty();
    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty logs = new SimpleStringProperty();
    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();
    private List<ValueChangeListener> changedListeners;
    private String logTag;
    private ILogger logger;

    private void initialize() {
        inputString.set("");
        result.set("");
        status.set(Status.WAITING.toString());
        logs.set("");
        logTag = "";

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(inputString);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calculationDisabled.bind(couldCalculate.not());

        final List<StringProperty> values = new ArrayList<StringProperty>() { {
            add(inputString);
        } };

        changedListeners = new ArrayList<>();
        for (StringProperty value : values) {
            final ValueChangeListener listener = new ValueChangeListener();
            value.addListener(listener);
            changedListeners.add(listener);
        }
    }

    private Status getInputStatus() {
        String inputString = getInputString();
        Status inputStatus = Status.READY;
        if (inputString.isEmpty() || inputString.matches("[,.:;]")) {
            inputStatus = Status.WAITING;
        } else if (StringCalculator.isBadFormat(getCorrectString(inputString))) {
            inputStatus = Status.BAD_FORMAT;
        }

        return inputStatus;
    }

    private String getCorrectString(final String input) {
        if (input.substring(0, 1).matches("[,.:;]")) {
            return input.substring(0, 1) + "\n" + input.substring(1);
        } else {
            return input;
        }
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String record = new String("");

        for (String log : fullLog) {
            record += log + "\n";
        }
        setLogs(record);
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }
            String currentStatus = getInputStatus().toString();
            status.set(currentStatus);
            currentValue = newValue;
        }

        public boolean isChanged() {
            return !previousValue.equals(currentValue);
        }

        public void cache() {
            previousValue = currentValue;
        }

        private String previousValue = new String("");
        private String currentValue = new String("");
    }
}

enum Status {
    WAITING("Please provide input data"),
    READY("Press 'Calculate' or Enter"),
    BAD_FORMAT("Bad format"),
    SUCCESS("Success");

    Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    private final String name;
}

final class LogMessages {
    public static final String CALCULATE_WAS_PRESSED = "Calculate. ";
    public static final String EDITING_FINISHED = "Update input. ";

    private LogMessages() { }
}
