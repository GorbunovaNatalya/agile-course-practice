package ru.unn.agile.LeftSidedHeap.viewmodel.legacy;

import ru.unn.agile.LeftSidedHeap.Model.LeftSidedHeap;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ViewModel {

    public ViewModel() {
        this.logger = new ListLogger();
        initialize();
    }

    public ViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
        initialize();
    }

    public List<String> getFullLog() {
        return logger.getFullLog();
    }

    public boolean isButtonAddEnabled() {
        return isButtonAddEnabled;
    }

    public boolean isButtonRemoveEnabled() {
        return isButtonRemoveEnabled;
    }

    public void processKeyInTextField() {
        parseInput();
    }

    public void processKeyInAddField() {
        parseInput();
        logTextAdd();
    }

    public void processKeyInRemoveField() {
        parseInput();
        logTextRemove();
    }

    public void add() {
        logger.addInfo(addLogMessage());

        if (!parseInput()) {
            return;
        }

        LeftSidedHeap<Double> secondHeap = new LeftSidedHeap<>(addCollection);

        heap.merge(secondHeap);

        textSizeHeap = formatTextSizeHeap();
        textMinInHeap = formatTextMinInHeap();
        textRemoveFromHeap = formatTextRemoveFromHeap();

        isButtonAddEnabled = false;
        textAdd = "";
        status = Status.SUCCESS;

        isTextAddChanged = false;
        isTextRemoveChanged = false;
    }

    public void remove() {
        logger.addInfo(removeLogMessage());

        if (!parseInput()) {
            return;
        }

        removedCollection.clear();

        for (Integer keyValue : removeCollection) {
            if (!heap.isEmpty()) {
                removedCollection.addAll(heap.removeAll(keyValue));
            }
        }

        textSizeHeap = formatTextSizeHeap();
        textMinInHeap = formatTextMinInHeap();
        textRemoveFromHeap = formatTextRemoveFromHeap();

        isButtonRemoveEnabled = false;
        textRemove = "";
        status = Status.SUCCESS;
    }

    public String getTextSizeHeap() {
        return this.textSizeHeap;
    }

    public String getTextMinInHeap() {
        return this.textMinInHeap;
    }

    public String getTextRemoveFromHeap() {
        return this.textRemoveFromHeap;
    }

    public String getStatus() {
        return status;
    }

    public String getTextAdd() {
        return this.textAdd;
    }
    public String getTextRemove() {
        return this.textRemove;
    }

    public void setTextAdd(final String textAdd) {
        if (textAdd.equals(this.textAdd)) {
            return;
        }

        this.textAdd = textAdd;
        isTextAddChanged = true;
    }

    public void setTextRemove(final String textRemove) {
        if (textRemove.equals(this.textRemove)) {
            return;
        }

        this.textRemove = textRemove;
        isTextRemoveChanged = true;
    }

    public void focusLost() {
        logTextAdd(); logTextRemove();
    }

    public final class LogMessages {
        public static final String ADD_WAS_PRESSED = "Add.";
        public static final String REMOVE_WAS_PRESSED = "Remove.";
        public static final String EDITING_HAPPENED = "Updated input.";

        private LogMessages() { }
    }

    private void initialize() {
        textRemove = "";
        textAdd = "";
        heap = new LeftSidedHeap<>();

        addCollection = new ArrayList<>();
        removeCollection = new ArrayList<>();
        removedCollection = new ArrayList<>();

        isButtonAddEnabled = false;
        isButtonAddEnabled = false;
        isTextAddChanged = false;
        isTextRemoveChanged = false;
        status = Status.WAITING;

        textSizeHeap = formatTextSizeHeap();
        textMinInHeap = formatTextMinInHeap();
        textRemoveFromHeap = formatTextRemoveFromHeap();
    }

    private void logTextAdd() {
        if (!isTextAddChanged) {
            return;
        }

        logger.addInfo(editingTextAddHappenedLogMessage());
        isTextAddChanged = false;
    }

    private void logTextRemove() {
        if (!isTextRemoveChanged) {
            return;
        }

        logger.addInfo(editingTextRemoveHappenedLogMessage());
        isTextRemoveChanged = false;
    }

    private String addLogMessage() {
        return String.format("%s Arguments: %s.", LogMessages.ADD_WAS_PRESSED, textAdd);
    }

    private String removeLogMessage() {
        return String.format("%s Arguments: %s.", LogMessages.REMOVE_WAS_PRESSED, textRemove);
    }

    private String editingTextAddHappenedLogMessage() {
        return String.format("%s \"Add\" field changed to: %s", LogMessages.EDITING_HAPPENED,
                textAdd);
    }

    private String editingTextRemoveHappenedLogMessage() {
        return String.format("%s \"Remove\" field changed to: %s", LogMessages.EDITING_HAPPENED,
                textRemove);
    }

    private boolean correctAdd() {
        try {
            if (!textAdd.isEmpty()) {
                parseTextAdd(textAdd);
            }
        } catch (Exception e) {
            if  (status.equals(Status.BAD_FORMAT_IN_REMOVE)) {
                status = Status.BAD_FORMAT;
            } else {
                status = Status.BAD_FORMAT_IN_ADD;
            }
            return false;
        }
        return true;
    }

    private boolean correctRemove() {
        try {
            if (!textRemove.isEmpty()) {
                parseTextRemove(textRemove);
            }
        } catch (Exception e) {
            if (status.equals(Status.BAD_FORMAT_IN_ADD)) {
                status = Status.BAD_FORMAT;
            } else {
                status = Status.BAD_FORMAT_IN_REMOVE;
            }
            return false;
        }
        return true;
    }

    private boolean parseInput() {
        boolean isCorrectTextAdd = correctAdd();
        boolean isCorrectTextRemove = correctRemove();
        boolean isCorrectInput = isCorrectTextAdd && isCorrectTextRemove;

        isButtonAddEnabled = !textAdd.isEmpty() && isCorrectInput;
        isButtonRemoveEnabled = !textRemove.isEmpty() && isCorrectInput;

        if (isCorrectInput) {
            if (isButtonAddEnabled || isButtonRemoveEnabled) {
                status = Status.READY;
            } else {
                status = Status.WAITING;
            }
        }
        return (isButtonAddEnabled || isButtonRemoveEnabled);
    }

    private void parseTextAdd(final String input) throws IllegalStateException {
        addCollection.clear();

        String inputWithoutSpace = input.replaceAll("\\s+", "");

        String[] elements = inputWithoutSpace.split(";");

        for (String element: elements) {
            String[] partsElement = element.split("_");
            if (partsElement.length != 2) {
                throw new IllegalStateException("textAdd contains an element not of 2 parts!");
            } else {
                Integer key = Integer.parseInt(partsElement[0]);
                Double value = Double.parseDouble(partsElement[1]);
                addCollection.add(new SimpleEntry<>(key, value));
            }
        }
    }

    private void parseTextRemove(final String input) throws IllegalStateException {
        removeCollection.clear();

        String inputWithoutSpace = input.replaceAll("\\s+", "");

        String[] elements = inputWithoutSpace.split(";");

        for (String element: elements) {
            Integer key = Integer.parseInt(element);
            removeCollection.add(key);
        }
    }

    private String formatTextSizeHeap() {
        Integer size = heap.size();

        return String.format("size: %d", size);
    }

    private String formatTextMinInHeap() {
        if (heap.isEmpty()) {
            return "min: -";
        } else {
            SimpleEntry<Integer, Double> min = heap.getMin();
            return String.format("min: %s", min.toString());
        }
    }

    private String formatTextRemoveFromHeap() {
        if (removedCollection.isEmpty()) {
            return "remove: -";
        } else {
            String formatRes = "remove:";
            for (SimpleEntry<Integer, Double> pairKeyValue: removedCollection) {
                formatRes += String.format(" %s", pairKeyValue.toString());
            }

            removedCollection.clear();
            return formatRes;
        }
    }

    private String textAdd;
    private String textRemove;
    private String status;
    private String textSizeHeap;
    private String textMinInHeap;
    private String textRemoveFromHeap;

    private boolean isButtonAddEnabled;
    private boolean isButtonRemoveEnabled;
    private boolean isTextAddChanged;
    private boolean isTextRemoveChanged;

    private ILogger logger;

    private LeftSidedHeap<Double> heap;
    private Collection<SimpleEntry<Integer, Double>> addCollection;
    private Collection<Integer> removeCollection;
    private Collection<SimpleEntry<Integer, Double>> removedCollection;
}

