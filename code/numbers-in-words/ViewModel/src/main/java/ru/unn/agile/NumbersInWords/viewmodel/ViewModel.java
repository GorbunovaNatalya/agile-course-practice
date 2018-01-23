package ru.unn.agile.NumbersInWords.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.List;

import ru.unn.agile.NumbersInWords.model.NumbersInWordsConverter;

public class ViewModel {

    public ViewModel() {
       initialize();
    }

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        initialize();
    }

    public void translate() {
        if (isTranslateButtonDisabled()) {
            return;
        }
        resultWord.set(NumbersInWordsConverter.convert(getInputNumber()));
        status.set(Status.SUCCESS.toString());

        String message = createMessageAfterTranslation();
        logger.log(message);
        updateLogs();
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        if (inputNumberChangedListener.isChanged()) {
            StringBuilder message = new StringBuilder(LogMessages.EDITING_FINISHED);
            message.append("Input number: ").append(getInputNumber());
            logger.log(message.toString());
            updateLogs();
            inputNumberChangedListener.cache();
        }
    }

    public StringProperty numberProperty() {
        return number;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty resultWordProperty() {
        return resultWord;
    }

    public BooleanProperty translateButtonDisabledProperty() {
        return translateButtonDisabled;
    }

    public final void setNumber(final String inputNumber) {
        number.set(inputNumber);
    }

    public final String getInputNumber() {
        return number.get();
    }

    public final String getResultWord() {
        return resultWord.get();
    }

    public final String getStatus() {
        return status.get();
    }

    public final boolean isTranslateButtonDisabled() {
        return translateButtonDisabled.get();
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final String getLogs() {
        return logs.get();
    }

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    private void initialize() {
        number.set("");
        resultWord.set("");
        status.set(Status.WAITING.toString());
        logs.set("");

        BooleanBinding couldTranslate = new BooleanBinding() {
            {
                super.bind(number);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        translateButtonDisabled.bind(couldTranslate.not());

        number.addListener(inputNumberChangedListener);
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;

        if ((getInputNumber().isEmpty()) || (getInputNumber().equals("-"))) {
            inputStatus = Status.WAITING;
        } else {
            try {
                double inputNumber = Double.parseDouble(getInputNumber());
                double borderTop = NumbersInWordsConverter.getBorderTop();
                double borderBottom = NumbersInWordsConverter.getBorderBottom();
                if ((inputNumber <= borderBottom) || (inputNumber >= borderTop)) {
                    inputStatus = Status.BAD_FORMAT;
                }
            } catch (NumberFormatException nfe) {
                inputStatus = Status.BAD_FORMAT;
            }
        }
        return inputStatus;
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String record = new String("");
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }

    private String createMessageAfterTranslation() {
        StringBuilder message = new StringBuilder(LogMessages.TRANSLATE_WAS_PRESSED);
        message.append("Input number: ")
                .append(getInputNumber())
                .append("; Result = ")
                .append(getResultWord()).append(".");
        return message.toString();
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }
            status.set(getInputStatus().toString());
            resultWord.set("");
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

    private ILogger logger;
    private final StringProperty logs = new SimpleStringProperty();
    private final StringProperty number = new SimpleStringProperty();
    private final StringProperty resultWord = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final BooleanProperty translateButtonDisabled = new SimpleBooleanProperty();
    private final ValueChangeListener inputNumberChangedListener = new ValueChangeListener();
}
