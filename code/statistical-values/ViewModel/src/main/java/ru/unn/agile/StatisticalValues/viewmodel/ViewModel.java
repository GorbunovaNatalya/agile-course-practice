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

    public void calculate() {
        if (!statusProperty().get().equals(Status.READY.toString())) {
            return;
        }

        Printable value;
        try {
            value = evaluator.compute();
        } catch (IllegalArgumentException exception) {
            status.set(Status.BAD + ": " + exception.getMessage());
            return;
        }

        printResult(value.print());
        status.set(Status.SUCCESS.toString());
    }

    public ViewModel() {
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

        statistic.addListener(new PropertyChangeListener<>());
        order.addListener(new PropertyChangeListener<>());
        isBiased.addListener(new PropertyChangeListener<>());
        values.addListener(new PropertyChangeListener<>());
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
            } catch (Exception exception) {
                status.set(Status.BAD + ": " + exception.getMessage());
                disableCalculation();
                return;
            }

            enableCalculation();
        }
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

