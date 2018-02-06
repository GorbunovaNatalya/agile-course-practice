package ru.unn.agile.StatisticalValues.viewmodel;

import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.Property;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    public ObjectProperty<Statistic> statisticProperty() {
        return statistic;
    }

    public ObservableList<Statistic> getAvailableStatistics() {
        return availableStatistics.get();
    }

    public StringProperty orderProperty() {
        return order;
    }

    public BooleanProperty orderVisibilityProperty() {
        return orderVisibility;
    }

    public boolean isOrderVisible() {
        return orderVisibility.get();
    }

    public BooleanProperty isBiasedProperty() {
        return isBiased;
    }

    public BooleanProperty isBiasedVisibilityProperty() {
        return isBiasedVisibility;
    }

    public boolean isBiasVisible() {
        return isBiasedVisibility.get();
    }

    public StringProperty valuesProperty() {
        return values;
    }

    public BooleanProperty valuesVisibilityProperty() {
        return valuesVisibility;
    }

    public boolean isValuesVisible() {
        return valuesVisibility.get();
    }

    public BooleanProperty calculateVisibilityProperty() {
        return calculateVisibility;
    }

    public boolean isCalculateVisible() {
        return calculateVisibility.get();
    }

    public StringProperty resultProperty() {
        return result;
    }

    public BooleanProperty resultVisibilityProperty() {
        return resultVisibility;
    }

    public boolean isResultVisible() {
        return resultVisibility.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getValues() {
        return valuesProperty().get();
    }

    public String getOrder() {
        return orderProperty().get();
    }

    public Boolean getBias() {
        return isBiasedProperty().get();
    }

    public Statistic getStatistic() {
        return statisticProperty().get();
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final String getLogs() {
        return logs.get();
    }

    public void calculate() {
        if (!isCalculateVisible())  {
            return;
        }

        StringBuilder message = new StringBuilder(LogMessages.CALCULATE_WAS_PRESSED);
        Printable value;
        try {
            value = evaluator.compute();
        } catch (IllegalArgumentException exception) {
            status.set(Status.BAD + ": " + exception.getMessage());
            return;
        }

        message.append(getInputsArguments())
                .append(" Operation: ")
                .append(getStatistic().toString())
                .append(".");
        logger.addLogText(message.toString());
        updateLogs();
        printResult(value.print());
        status.set(Status.SUCCESS.toString());
    }

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }

    public final List<String> getLog() {
        return logger.getLogText();
    }

    public void onStatisticChanged(final Statistic oldValue, final Statistic newValue) {
        if (newValue.equals(oldValue)) {
            return;
        }

        String message = LogMessages.OPERATION_WAS_CHANGED + newValue.toString();
        logger.addLogText(message);
        updateLogs();
    }

    public void onBiasChanged(final Boolean oldValue, final Boolean newValue) {
        if (newValue.equals(oldValue)) {
            return;
        }

        StringBuilder message = new StringBuilder(LogMessages.OPERATION_WAS_CHANGED);
        message.append(statistic.get().toString());

        if (newValue) {
            message.append(" Bias ");
        }

        logger.addLogText(message.toString());
        updateLogs();
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        PropertyChangeListener changed = valueChangedListeners.stream()
                .filter(PropertyChangeListener::isChanged)
                .findFirst().orElse(null);
        if (changed == null) {
            return;
        }

        String message = LogMessages.EDITING_FINISHED + getInputsArguments();

        logger.addLogText(message);
        updateLogs();

        changed.cache();
    }

    private void init() {
        order.set("0");
        orderVisibility.set(false);

        isBiased.set(false);
        isBiasedVisibility.set(false);

        values.set("0.0, 0.0");
        valuesVisibility.set(false);

        calculateVisibility.set(false);

        result.set("");
        resultVisibility.set(false);

        status.set(Status.WAIT.toString());

        valueChangedListeners = new ArrayList<>();
        final List<Property> allProperty = new ArrayList<Property>() {{
            add(values);
            add(order);
            add(isBiased);
            add(statistic);
        }};

        for (Property property : allProperty) {
            addListener(property);
        }
    }

    @SuppressWarnings (value = "unchecked")
    private <T extends Property> void addListener(final T property) {
        PropertyChangeListener listener = new PropertyChangeListener<>();
        property.addListener(listener);
        valueChangedListeners.add(listener);
    }

    private String getInputsArguments() {
        StringBuilder message = new StringBuilder("Input arguments are: [");
        message.append(values.get()).append("; ]");
        if (isOrderVisible()) {
            message.append(" Order argument is: [");
            message.append(order.get());
            message.append("]");
        }
        if (isBiasVisible()) {
            message.append(" Bias argument is: [");
            message.append(String.valueOf(isBiased.get()));
            message.append("]");
        }
        return message.toString();
    }

    private boolean isOrderEmpty() {
        return isOrderVisible() && orderProperty().get().isEmpty();
    }

    private boolean isValuesEmpty() {
        return isValuesVisible() && valuesProperty().get().isEmpty();
    }

    private void enableCalculation() {
        orderVisibility.set(evaluator.isOrderUsing());
        isBiasedVisibility.set(evaluator.isBiasUsing());

        calculateVisibility.set(true);

        status.set(Status.READY.toString());
    }

    private void disableCalculation() {
        calculateVisibility.set(false);
        resultVisibility.set(false);
    }

    private void printResult(final String resultText) {
        resultVisibility.set(true);
        result.set(resultText);
    }

    private final ObjectProperty<Statistic> statistic = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<Statistic>> availableStatistics =
            new SimpleObjectProperty<>(
                    FXCollections.observableArrayList(Statistic.values())
            );

    private final StringProperty  order = new SimpleStringProperty();
    private final BooleanProperty orderVisibility = new SimpleBooleanProperty();

    private final BooleanProperty isBiased = new SimpleBooleanProperty();
    private final BooleanProperty isBiasedVisibility = new SimpleBooleanProperty();

    private final StringProperty  values = new SimpleStringProperty();
    private final BooleanProperty valuesVisibility = new SimpleBooleanProperty();

    private final BooleanProperty calculateVisibility = new SimpleBooleanProperty();

    private final StringProperty  result = new SimpleStringProperty();
    private final BooleanProperty resultVisibility = new SimpleBooleanProperty();

    private final StringProperty status = new SimpleStringProperty();

    private Computable evaluator;

    private ILogger logger;

    private List<PropertyChangeListener> valueChangedListeners;

    private final StringProperty logs = new SimpleStringProperty();

    private void updateLogs() {
        List<String> fullLog = logger.getLogText();
        StringBuilder record = new StringBuilder("");
        for (String log : fullLog) {
            record.append(log)
                    .append("\n");
        }
        logs.set(record.toString());
    }

    private class PropertyChangeListener<T> implements ChangeListener<T> {
        @Override
        public void changed(final ObservableValue<? extends T> observable,
                            final T oldValue, final T newValue) {
            valuesVisibility.set(true);

            if (isOrderEmpty() || isValuesEmpty()) {
                status.set(Status.WAIT.toString());
                disableCalculation();
                return;
            }

            try {
                evaluator = EvaluatorsFabric.create(ViewModel.this);
                curValue = String.valueOf(newValue);
            } catch (Exception exception) {
                status.set(Status.BAD + ": " + exception.getMessage());
                disableCalculation();
                return;
            }

            enableCalculation();
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
}

enum Status {
    WAIT("Wait for input"),
    BAD("Bad format"),
    READY("Press 'Calculate'"),
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
    public static final String OPERATION_WAS_CHANGED = "Operation was changed to ";
    public static final String EDITING_FINISHED = "Updated input. ";

    private LogMessages() { }
}
