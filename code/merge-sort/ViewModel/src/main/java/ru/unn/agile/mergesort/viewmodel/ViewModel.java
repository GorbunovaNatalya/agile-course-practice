package ru.unn.agile.mergesort.viewmodel;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import ru.unn.agile.mergesort.model.MergeSort;

public class ViewModel {
    public ViewModel() {
        input.set("");
        output.set("");
        status.set(Status.WAITING.toString());

        BooleanBinding couldSort = new BooleanBinding() {
            {
                super.bind(input);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        sortDisabled.bind(couldSort.not());

        final InputChangeListener listener = new InputChangeListener();
        input.addListener(listener);
    }

    public void calculate() {
        if (isSortDisabled()) {
            return;
        }

        String inputStr = input.get();
        inputStr = inputStr.trim();
        String[] words = inputStr.split("[ ]+");
        List<Integer> numbers = new LinkedList<>();
        for (String word : words) {
            numbers.add(Integer.parseInt(word));
        }
        Collection<Integer> sortNumbers = MergeSort.sort(numbers);
        setOutput(sortNumbers);

        status.set(Status.SUCCESS.toString());
    }

    public StringProperty inputProperty() {
        return input;
    }

    public void setInput(final String input) {
        inputProperty().set(input);
    }

    public String getInput() {
        return input.get();
    }

    public BooleanProperty sortDisabledProperty() {
        return sortDisabled;
    }

    public boolean isSortDisabled() {
        return sortDisabled.get();
    }

    public StringProperty outputProperty() {
        return output;
    }

    public String getOutput() {
        return output.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getStatus() {
        return status.get();
    }

    private Status getInputStatus() {
        String inputStr = input.get();
        inputStr = inputStr.trim();
        if (inputStr.isEmpty()) {
            return Status.WAITING;
        }

        String[] words = inputStr.split("[ ]+");
        for (String word : words) {
            try {
                Integer.parseInt(word);
            } catch (NumberFormatException exception) {
                return Status.BAD_FORMAT;
            }
        }

        return Status.READY;
    }

    private void setOutput(final Collection<Integer> outputNumbers) {
        String result = "";
        for (Integer number : outputNumbers) {
            result += number.toString() + " ";
        }
        output.set(result.substring(0, result.length() - 1));
    }

    private final StringProperty input = new SimpleStringProperty();

    private final BooleanProperty sortDisabled = new SimpleBooleanProperty();

    private final StringProperty output = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    private class InputChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldVal, final String newVal) {
            status.set(getInputStatus().toString());
        }
    }

}
