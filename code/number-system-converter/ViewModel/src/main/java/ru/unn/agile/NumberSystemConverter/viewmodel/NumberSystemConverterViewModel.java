package ru.unn.agile.NumberSystemConverter.viewmodel;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import ru.unn.agile.NumberSystemConverter.model.NumberSystemConverter;
import ru.unn.agile.NumberSystemConverter.model.NumberSystemBase;

public class NumberSystemConverterViewModel {

    public StringProperty numberInBaseNumberSystemProperty() {
        return this.numberInBaseNumberSystem;
    }

    public StringProperty numberInTargetNumberSystemProperty() {
        return this.numberInTargetNumberSystem;
    }

    public ObjectProperty<NumberSystemBase> baseNumberSystemProperty() {
        return this.baseNumberSystem;
    }

    public ObjectProperty<NumberSystemBase> targetNumberSystemProperty() {
        return this.targetNumberSystem;
    }

    public ObjectProperty<ObservableList<NumberSystemBase>> availableNumberSystemsProperty() {
        return this.availableNumberSystems;
    }

    public final ObservableList<NumberSystemBase> getAvailableNumberSystems() {
        return this.availableNumberSystems.get();
    }
    public BooleanProperty conversionEnabledProperty() {
        return this.conversionEnabled;
    }

    public final boolean isConversionEnabled() {
        return this.conversionEnabled.get();
    }

    public BooleanProperty errorMessageIsShownProperty() {
        return this.errorMessageIsShown;
    }

    public final boolean isErrorMessageShown() {
        return this.errorMessageIsShown.get();
    }

    public final String getErrorMessage() {
        return this.errorMessage.get();
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final String getLogs() {
        return logs.get();
    }

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can not be null");
        }
        this.logger = logger;
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    public NumberSystemConverterViewModel() {
        init();
    }

    public NumberSystemConverterViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }

    public void convert() {
        if (!isConversionEnabled()) {
            return;
        }

        try {
            String result = NumberSystemConverter.convert(this.numberInBaseNumberSystem.get(),
                this.baseNumberSystem.get(), this.targetNumberSystem.get());
            this.numberInTargetNumberSystem.set(result);
        } catch (NumberFormatException numberFormatException) {
            this.errorMessage.set("Input contains invalid symbols for this number system");
            this.errorMessageIsShown.set(true);

            logger.log("Error: Input contains invalid symbols");
            updateLogs();

            return;
        }

        StringBuilder message = new StringBuilder(LogMessages.CONVERT_WAS_PRESSED);
        message.append("Arguments: NumberInBaseSystem = ")
               .append(numberInBaseNumberSystem.get())
               .append("; BaseNumberSystem = ")
               .append(baseNumberSystem.get().toString())
               .append("; TargetNumberSystem = ")
               .append(targetNumberSystem.get().toString());

        logger.log(message.toString());
        updateLogs();
    }

    public void closeErrorDialog() {
        if (this.isErrorMessageShown()) {
            this.errorMessageIsShown.set(false);
            this.errorMessage.set("");
        }
    }

    public void onBaseNumberSystemChanged(final NumberSystemBase oldValue,
                                          final NumberSystemBase newValue) {
        if (oldValue.equals(newValue)) {
            return;
        }

        logger.log(LogMessages.BASE_SYSTEM_WAS_CHANGED + newValue.toString());
        updateLogs();
    }

    public void onTargetNumberSystemChanged(final NumberSystemBase oldValue,
                                            final NumberSystemBase newValue) {
        if (oldValue.equals(newValue)) {
            return;
        }

        logger.log(LogMessages.TARGET_SYSTEM_WAS_CHANGED + newValue.toString());
        updateLogs();
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        PropertyChangeListener listener = valueChangedListeners.stream()
            .filter(PropertyChangeListener::isChanged).findFirst().orElse(null);

        if (listener == null) {
            return;
        }

        StringBuilder message = new StringBuilder(LogMessages.EDITING_FINISHED);
        message.append("Input arguments are: [")
               .append(numberInBaseNumberSystemProperty().get())
               .append("; ")
               .append(baseNumberSystemProperty().get())
               .append("; ")
               .append(targetNumberSystemProperty().get())
               .append("]");

        logger.log(message.toString());
        updateLogs();

        listener.cache();
    }

    private void init() {
        this.numberInBaseNumberSystem.set("");
        this.numberInTargetNumberSystem.set("");
        this.baseNumberSystem.set(NumberSystemBase.DEC);
        this.targetNumberSystem.set(NumberSystemBase.DEC);
        this.errorMessageIsShown.set(false);
        this.errorMessage.set("");

        this.bindConversionAvailability();

        valueChangedListeners = new ArrayList<>();
        addListener(numberInBaseNumberSystem);
    }

    @SuppressWarnings (value = "unchecked")
    private <T extends Property> void addListener(final T property) {
        PropertyChangeListener listener = new PropertyChangeListener<>();
        property.addListener(listener);
        valueChangedListeners.add(listener);
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        StringBuilder record = new StringBuilder();
        for (String log : fullLog) {
            record.append(log).append("\n");
        }
        logs.set(record.toString());
    }

    private void bindConversionAvailability() {
        BooleanBinding couldConvert = new BooleanBinding() {
            { super.bind(numberInBaseNumberSystem); }

            @Override
            protected boolean computeValue() {
                    return numberInBaseNumberSystem.get().length() > 0;
            }
        };
        this.conversionEnabled.bind(couldConvert);
    }

    private class PropertyChangeListener<T> implements ChangeListener<T> {
        @Override
        public void changed(final ObservableValue<? extends T> observable,
                            final T oldValue, final T newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }
            curValue = String.valueOf(newValue);
        }

        private boolean isChanged() {
            return !prevValue.equals(curValue);
        }
        private void cache() {
            prevValue = curValue;
        }

        private String prevValue = "";
        private String curValue = "";
    }

    private final StringProperty numberInBaseNumberSystem = new SimpleStringProperty();

    private final StringProperty numberInTargetNumberSystem = new SimpleStringProperty();

    private final ObjectProperty<ObservableList<NumberSystemBase>> availableNumberSystems =
        new SimpleObjectProperty<>(FXCollections.observableArrayList(NumberSystemBase.values()));

    private final ObjectProperty<NumberSystemBase> baseNumberSystem =
            new SimpleObjectProperty<>();

    private final ObjectProperty<NumberSystemBase> targetNumberSystem =
            new SimpleObjectProperty<>();

    private final BooleanProperty conversionEnabled = new SimpleBooleanProperty();

    private final BooleanProperty errorMessageIsShown = new SimpleBooleanProperty();

    private final StringProperty errorMessage = new SimpleStringProperty();

    private ILogger logger;
    private List<PropertyChangeListener> valueChangedListeners;

    private final StringProperty logs = new SimpleStringProperty();
}

final class LogMessages {
    public static final String CONVERT_WAS_PRESSED = "Convert. ";
    public static final String BASE_SYSTEM_WAS_CHANGED = "Base system was changed to ";
    public static final String TARGET_SYSTEM_WAS_CHANGED = "Target system was changed to ";
    public static final String EDITING_FINISHED = "Updated input. ";

    private LogMessages() {

    }
}
