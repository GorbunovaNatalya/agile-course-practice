package ru.unn.agile.Triangle.ViewModel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import ru.unn.agile.Triangle.Model.Triangle;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {

    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        setToEmptyCoordinates();
        setToDefault();
        status.set(Status.WAITING.toString());
        logs.set("");
        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(coordAx, coordAy, coordBx, coordBy, coordCx, coordCy);
            }

            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calculationDisabled.bind(couldCalculate.not());

        final List<StringProperty> fields = new ArrayList<StringProperty>() {
            {
                add(coordAx);
                add(coordAy);
                add(coordBx);
                add(coordBy);
                add(coordCx);
                add(coordCy);
            }
        };

        for (StringProperty field : fields) {
            final TriangleChangesListener listener = new TriangleChangesListener();
            field.addListener(listener);
            changesListeners.add(listener);
        }
    }

    public void calculate() {
        if (calculationDisabled.get()) {
            return;
        }
        try {
            Triangle triangle = new Triangle(Double.parseDouble(coordAx.get()),
                    Double.parseDouble(coordAy.get()),
                    Double.parseDouble(coordBx.get()),
                    Double.parseDouble(coordBy.get()),
                    Double.parseDouble(coordCx.get()),
                    Double.parseDouble(coordCy.get()));
            updateFields(triangle);
            status.set(Status.SUCCESS.toString());
            String message = calculateLogMessage();
            logger.log(message);
            updateLogs();
        } catch (IllegalArgumentException illeg) {
            setToDefault();
            status.set(Status.DEGENERATED.toString());
        }
    }

    public final String getSideAB() {
        return sideAB.get();
    }

    public final String getSideAC() {
        return sideAC.get();
    }

    public final String getSideBC() {
        return sideBC.get();
    }

    public final String getCornerABC() {
        return cornerABC.get();
    }

    public final String getCornerBAC() {
        return cornerBAC.get();
    }

    public final String getCornerACB() {
        return cornerACB.get();
    }

    public final String getPerimeter() {
        return perimeterValue.get();
    }

    public final String getSurfaceArea() {
        return surfaceArea.get();
    }

    public String getStatus() {
        return status.get();
    }

    public final boolean isCalculationDisabled() {
        return calculationDisabled.get();
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    public StringProperty coordAxProperty() {
        return coordAx;
    }

    public StringProperty coordAyProperty() {
        return coordAy;
    }

    public StringProperty coordBxProperty() {
        return coordBx;
    }

    public StringProperty coordByProperty() {
        return coordBy;
    }

    public StringProperty coordCxProperty() {
        return coordCx;
    }

    public StringProperty coordCyProperty() {
        return coordCy;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final StringProperty sideABProperty() {
        return sideAB;
    }

    public final StringProperty sideACProperty() {
        return sideAC;
    }

    public final StringProperty sideBCProperty() {
        return sideBC;
    }

    public final StringProperty cornerABCProperty() {
        return cornerABC;
    }

    public final StringProperty cornerBACProperty() {
        return cornerBAC;
    }

    public final StringProperty cornerACBProperty() {
        return cornerACB;
    }

    public final StringProperty perimeterProperty() {
        return perimeterValue;
    }

    public final StringProperty surfaceAreaProperty() {
        return surfaceArea;
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final String getLogs() {
        return logs.get();
    }

    public boolean isCalculateButtonEnabled() {
        return isCalculateButtonEnabled;
    }


    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    private final StringProperty logs = new SimpleStringProperty();
    private final StringProperty coordAx = new SimpleStringProperty();
    private final StringProperty coordAy = new SimpleStringProperty();
    private final StringProperty coordBx = new SimpleStringProperty();
    private final StringProperty coordBy = new SimpleStringProperty();
    private final StringProperty coordCx = new SimpleStringProperty();
    private final StringProperty coordCy = new SimpleStringProperty();

    private final StringProperty sideAB = new SimpleStringProperty();
    private final StringProperty sideBC = new SimpleStringProperty();
    private final StringProperty sideAC = new SimpleStringProperty();

    private final StringProperty cornerABC = new SimpleStringProperty();
    private final StringProperty cornerACB = new SimpleStringProperty();
    private final StringProperty cornerBAC = new SimpleStringProperty();

    private final StringProperty perimeterValue = new SimpleStringProperty();
    private final StringProperty surfaceArea = new SimpleStringProperty();
    private final List<TriangleChangesListener> changesListeners = new ArrayList<>();
    private final StringProperty status = new SimpleStringProperty();
    private boolean isCalculateButtonEnabled;
    private ILogger logger;

    private class TriangleChangesListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldNum, final String newNum) {
            if (oldNum.equals(newNum)) {
                return;
            }
            status.set(getInputStatus().toString());
            newInput = newNum;
        }

        public boolean isChanged() {
            return !prevInput.equals(newInput);
        }

        public void saveNewInputAsPrevious() {
            prevInput = newInput;
        }

        private String prevInput = new String("");
        private String newInput = new String("");
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (hasEmptyCoordinates()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!coordAx.get().isEmpty()) {
                parseToDouble(coordAx);
            }
            if (!coordAy.get().isEmpty()) {
                parseToDouble(coordAy);
            }
            if (!coordBx.get().isEmpty()) {
                parseToDouble(coordBx);
            }
            if (!coordBy.get().isEmpty()) {
                parseToDouble(coordBy);
            }
            if (!coordCx.get().isEmpty()) {
                parseToDouble(coordCx);
            }
            if (!coordCy.get().isEmpty()) {
                parseToDouble(coordCy);
            }
        } catch (NumberFormatException nfe) {
            setToDefault();
            inputStatus = Status.BAD_FORMAT;
        }
        return inputStatus;
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }
        for (TriangleChangesListener listener : changesListeners) {
            if (listener.isChanged()) {
                String message = LogMessages.EDITING_FINISHED + "Input arguments are: A = ("
                        + coordAx.get() + "," + coordAy.get() + "); B = ("
                        + coordBx.get() + "," + coordBy.get() + "); C = ("
                        + coordCx.get() + "," + coordCy.get() + ").";
                logger.log(message);

                listener.saveNewInputAsPrevious();
                break;
            }
        }
        updateLogs();
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String record = new String("");
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }

    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();

    private double parseToDouble(final StringProperty property) {
        return Double.parseDouble(property.get());
    }

    private void updateFields(final Triangle triangle) {
        sideAB.set(String.format("|AB| = %.2f", triangle.getLengthAB()).replace(',', '.'));
        sideAC.set(String.format("|AC| = %.2f", triangle.getLengthAC()).replace(',', '.'));
        sideBC.set(String.format("|BC| = %.2f", triangle.getLengthBC()).replace(',', '.'));

        cornerABC.set(String.format("ABC = %.2f rad", triangle.getABCAngle()).replace(',', '.'));
        cornerACB.set(String.format("ACB = %.2f rad", triangle.getBCAAngle()).replace(',', '.'));
        cornerBAC.set(String.format("BAC = %.2f rad", triangle.getCABAngle()).replace(',', '.'));

        perimeterValue.set(String.format("P = %.2f", triangle.getPerimeter()).replace(',', '.'));
        surfaceArea.set(String.format("S = %.2f", triangle.getSurfaceArea()).replace(',', '.'));
    }

    private boolean hasEmptyCoordinates() {
        return coordAx.get().isEmpty()
                || coordAy.get().isEmpty()
                || coordBx.get().isEmpty()
                || coordBy.get().isEmpty()
                || coordCx.get().isEmpty()
                || coordCy.get().isEmpty();
    }

    private void setToEmptyCoordinates() {
        coordAx.set("");
        coordAy.set("");
        coordBx.set("");
        coordBy.set("");
        coordCx.set("");
        coordCy.set("");
    }

    private void setToDefault() {
        perimeterValue.set("P = N/A");
        sideAB.set("|AB| = N/A");
        sideAC.set("|AC| = N/A");
        sideBC.set("|BC| = N/A");
        cornerABC.set("ABC = N/A");
        cornerACB.set("ACB = N/A");
        cornerBAC.set("BAC = N/A");
        surfaceArea.set("S = N/A");
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    private String calculateLogMessage() {
        return LogMessages.CALCULATE_WAS_PRESSED + "Arguments: "
                + allInputCoordinates();
    }

    public final class LogMessages {
        public static final String CALCULATE_WAS_PRESSED = "Calculate. ";
        public static final String EDITING_FINISHED = "Updated input. ";

        private LogMessages() {
        }
    }

    private String allInputCoordinates() {
        return String.format("A = (%s, %s); B = (%s, %s); C = (%s, %s).",
                coordAx.get(), coordAy.get(),
                coordBx.get(), coordBy.get(),
                coordCx.get(), coordCy.get());
    }
}

enum Status {
    WAITING("Enter the coordinates of the triangle."),
    READY("Press 'Calculate'!"),
    BAD_FORMAT("Incorrect data!"),
    DEGENERATED("Incorrect data: degenerated triangle!"),
    SUCCESS("Success! Look at results.");

    Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    private final String name;
}
