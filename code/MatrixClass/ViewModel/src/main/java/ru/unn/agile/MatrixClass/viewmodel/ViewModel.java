package ru.unn.agile.MatrixClass.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.ClassMatrix.Model.Matrix;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    public ViewModel() {
        matrixSize.set("");
        determinant.set("");
        fieldForMatrix.set("");
        status.set(Status.WAITING.toString());

        BooleanBinding couldSetMatrixSize = new BooleanBinding() {
            {
                super.bind(matrixSize, fieldForMatrix);
            }

            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calculateButtonDisabled.bind(couldSetMatrixSize.not());

        final List<StringProperty> fields = new ArrayList<StringProperty>() {
            {
                add(matrixSize);
                add(fieldForMatrix);
            }
        };

        valueChangedListeners = new ArrayList<>();
        for (StringProperty field : fields) {
            final ValueCachingChangeListener listener = new ValueCachingChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public final void setLogger(final ILogg logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public ViewModel(final ILogg logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        matrixSize.set("");
        fieldForMatrix.set("");
        status.set(Status.WAITING.toString());
        determinant.set("");

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(matrixSize, fieldForMatrix);
            }
            @Override
            protected boolean computeValue() {
                if (getInputStatus() == Status.READY) {
                    return true;
                }
                return false;
            }
        };
        calculateButtonDisabled.bind(couldCalculate.not());

        final List<StringProperty> vals = new ArrayList<StringProperty>() { {
            add(matrixSize);
            add(fieldForMatrix);
        } };
        valueChangedListeners = new ArrayList<>();
        for (StringProperty val : vals) {
            final ValueCachingChangeListener listener = new ValueCachingChangeListener();
            val.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty determinantProperty() {
        return determinant;
    }

    public BooleanProperty calculateButtonDisabledProperty() {
        return calculateButtonDisabled;
    }

    public StringProperty matrixSizeProperty() {
        return matrixSize;
    }

    public StringProperty fieldForMatrixProperty() {
        return fieldForMatrix;
    }

    public final String getInputMatrixSize() {
        return matrixSize.get();
    }

    public final String getDeterminant() {
        return determinant.get();
    }

    public final String getFieldForMatrix() {
        return fieldForMatrix.get();
    }

    public final String getStatus() {
        return status.get();
    }

    public final void setMatrixSize(final String inputNumber) {
        matrixSize.set(inputNumber);
    }

    public final void setFieldForMatrix(final String inputNumber) {
        fieldForMatrix.set(inputNumber);
    }

    public final boolean isCalculateButtonDisabled() {
        return calculateButtonDisabled.get();
    }

    public void calculate() {
        if (isCalculateButtonDisabled()) {
            return;
        }
        int inputSize = Integer.parseInt(getInputMatrixSize());
        float[][] matrixA = new float[inputSize][inputSize];
        String inputMatrix = getFieldForMatrix().toString();
        String[] mas = inputMatrix.split("/");
        for (int i = 0; i < mas.length; i++) {
            String[] rowMatrix = mas[i].split(",");
            for (int j = 0; j < inputSize; j++) {
                matrixA[i][j] = Float.parseFloat(rowMatrix[j]);
            }
        }
        Matrix matrix = new Matrix(matrixA);
        determinant.set(Float.toString(matrix.calculateDeterminant()));
        status.set(Status.SUCCESS.toString());

        StringBuilder message = new StringBuilder(LogMessages.CALCULATE_WAS_PRESSED);
        message.append("Arguments: MatrixSize = ").append(matrixSize.get())
                .append("; Matrix = ").append(fieldForMatrix.get());
        logger.log(message.toString());
        updateLogs();
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            status.set(getInputStatus().toString());
            determinant.set("");
        }
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (newValue && !oldValue) {
            return;
        }
        for (ValueCachingChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                StringBuilder message = new StringBuilder(LogMessages.EDITING_FINISHED);
                message.append("Input args are: [")
                        .append(fieldForMatrix.get()).append("; ");
                logger.log(message.toString());
                updateLogs();
                listener.cache();
                break;
            }
        }
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    private Status getInputStatus() {
        if (getInputMatrixSize().isEmpty()) {
            return Status.WAITING;
        }
        try {
            int inputSize = Integer.parseInt(getInputMatrixSize());
            if ((inputSize <= 1)) {
                return Status.BAD_FORMAT;
            }
            if ((getFieldForMatrix().isEmpty())) {
                return Status.WAITING_MATRIX;
            }
            String[] mas = getFieldForMatrix().split("/");
            for (int i = 0; i < mas.length; i++) {
                if (mas[i].equals("")) {
                    return Status.BAD_FORMAT;
                }
                String[] rowMatrix = mas[i].split(",");
                if ((rowMatrix.length != mas.length)
                        || (rowMatrix.length * mas.length != inputSize * inputSize)) {
                    return Status.BAD_FORMAT;
                }
                for (int j = 0; j < rowMatrix.length; j++) {
                    if (!rowMatrix[j].matches((FLOAT_NUMBER_REGEX))) {
                        return Status.BAD_FORMAT;
                    }
                }
            }
        } catch (NumberFormatException nfe) {
            return Status.BAD_FORMAT;
        }
        return Status.READY;
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String record = new String("");
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }

    private class ValueCachingChangeListener implements ChangeListener<String> {
        private String prevValue = new String("");
        private String curValue = new String("");

        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }
            status.set(getInputStatus().toString());
            curValue = newValue;
        }

        public boolean isChanged() {
            return !prevValue.equals(curValue);
        }

        public void cache() {
            prevValue = curValue;
        }
    }

    private final StringProperty matrixSize = new SimpleStringProperty();
    private final StringProperty determinant = new SimpleStringProperty();
    private final StringProperty fieldForMatrix = new SimpleStringProperty();
    private final StringProperty logs = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final BooleanProperty calculateButtonDisabled = new SimpleBooleanProperty();
    private List<ValueCachingChangeListener> valueChangedListeners = new ArrayList<>();
    private static final String FLOAT_NUMBER_REGEX = "(^[-]?\\d+[.]\\d+$)|(^[-]?\\d+$)";

    private ILogg logger;

    final class LogMessages {
        public static final String CALCULATE_WAS_PRESSED = "Calculate. ";
        public static final String EDITING_FINISHED = "Updated input. ";

        private LogMessages() {
        }
    }

}
