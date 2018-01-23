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

import java.awt.geom.Point2D;

public class ViewModel {
    public ViewModel() {
        setToEmptyCoordinates();
        setToDefault();
        status.set(Status.WAITING.toString());

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
        Point2D dotA = makePoint(coordAx, coordAy);
        Point2D dotB = makePoint(coordBx, coordBy);
        Point2D dotC = makePoint(coordCx, coordCy);

        try {
            Triangle triangle = new Triangle(dotA, dotB, dotC);
            updateFields(triangle);
            status.set(Status.SUCCESS.toString());
        } catch (IllegalArgumentException illeg) {
            setToDefault();
            status.set(Status.DEGENERATED.toString());
        }

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

    public final boolean isCalculationDisabled() {
        return calculationDisabled.get();
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    public String getStatus() {
        return statusProperty().get();
    }
    public String getSideAB() {
        return sideABProperty().get();
    }
    public String getSideBC() {
        return sideBCProperty().get();
    }
    public String getSideAC() {
        return sideACProperty().get();
    }
    public String getCornerABC() {
        return cornerABCProperty().get();
    }
    public String getCornerACB() {
        return cornerACBProperty().get();
    }
    public String getCornerBAC() {
        return cornerBACProperty().get();
    }
    public final String getPerimeter() {
        return perimeterProperty().get();
    }
    public final String getSurfaceArea() {
        return surfaceAreaProperty().get();
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
    private final StringProperty status = new SimpleStringProperty();

    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();

    private final List<TriangleChangesListener> changesListeners = new ArrayList<>();

    private double parseToDouble(final StringProperty property) {
        return Double.parseDouble(property.get());
    }

    private Point2D makePoint(final StringProperty propertyCoordX,
                              final StringProperty propertyCoordy) {
        return new Point2D.Double(parseToDouble(propertyCoordX), parseToDouble(propertyCoordy));
    }

    private boolean hasEmptyCoordinates() {
        return coordAx.get().isEmpty()
                || coordAy.get().isEmpty()
                || coordBx.get().isEmpty()
                || coordBy.get().isEmpty()
                || coordCx.get().isEmpty()
                || coordCy.get().isEmpty();
    }

    private class TriangleChangesListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String prevValue, final String newValue) {
            status.set(getInputStatus().toString());
        }
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
