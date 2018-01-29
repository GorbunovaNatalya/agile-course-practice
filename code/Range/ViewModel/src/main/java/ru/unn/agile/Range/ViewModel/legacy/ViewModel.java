package ru.unn.agile.Range.ViewModel.legacy;

import ru.unn.agile.Range.Model.Boundary;
import ru.unn.agile.Range.Model.Range;

public class ViewModel {
    public Boolean isCalculateButtonEnabled() {
        return calculateButtonEnabled;
    }

    public Boolean isInputRangeOrSetTextFieldEnabled() {
        return inputRangeOrSetTextFieldEnabled;
    }

    public void setInputRange(final String inputRange) {
        this.inputRange = inputRange;
        this.calculateButtonEnabled = canParseAll();
    }

    public void setInputRangeOrSet(final String inputRangeOrSet) {
        this.inputRangeOrSet = inputRangeOrSet;
        this.calculateButtonEnabled = canParseAll();
    }

    public void setOperation(final Operation operation) {
        this.operation = operation;
        this.calculateButtonEnabled = canParseAll();
    }

    public Boolean canParseAll() {
        try {
            if (parseInputRange()) {
                if (operation == Operation.ARE_EQUAL_RANGES || operation == Operation.CONTAINS_RANGE
                        || operation == Operation.OVERLAPS_RANGE) {
                    inputRangeOrSetTextFieldEnabled = true;
                    return parseOtherInputRange();
                }
                if (operation == Operation.CONTAINS_POINTS) {
                    inputRangeOrSetTextFieldEnabled = true;
                    return parseInputSetOfNumbers();
                }
                return true;
            } else {
                return false;
            }
        } catch (IllegalArgumentException e) {
            result = "";
            messageText = Message.error(e);
            return false;
        }
    }

    public void calculate() {
        switch (this.operation) {
            case GET_ALL_POINTS:
                result = setOfNumbersToString(range.getAllPoints());
                break;
            case GET_END_POINTS:
                result = setOfNumbersToString(range.getEndPoints());
                break;
            case ARE_EQUAL_RANGES:
                if (range.equals(otherRange)) {
                    result = "These ranges are equal";
                } else {
                    result = "These ranges are not equal";
                }
                break;
            case CONTAINS_POINTS:
                if (range.containsValues(setOfNumbers)) {
                    result = "The range contains these values";
                } else {
                    result = "The range doesn't contain these values";
                }
                break;
            case CONTAINS_RANGE:
                if (range.containsRange(otherRange)) {
                    result = "The range contains this range";
                } else {
                    result = "The range doesn't contain this range";
                }
                break;
            case OVERLAPS_RANGE:
                if (range.overlapsRange(otherRange)) {
                    result = "These ranges overlap";
                } else {
                    result = "These ranges don't overlap";
                }
                break;
            default:
                break;
        }
    }

    public String getResult() {
        return result;
    }

    public String getMessageText() {
        return messageText;
    }

    private boolean parseInputRange() {
        if ("".equals(inputRange)) {
            messageText = Message.DEFAULT_RANGE;
            return false;
        }
        if (!inputRange.matches(REGEX_RANGES)) {
            messageText = Message.INCORRECT_INPUT_RANGE;
            return false;
        }
        range = parseRange(inputRange);
        if (range != null) {
            messageText = Message.CORRECT_INPUT_RANGE;
            return true;
        } else {
            return false;
        }
    }

    private boolean parseOtherInputRange() {
        if ("".equals(inputRangeOrSet)) {
            messageText = Message.DEFAULT_ANOTHER_RANGE;
            return false;
        }
        if (!inputRangeOrSet.matches(REGEX_RANGES)) {
            messageText = Message.INCORRECT_INPUT_ANOTHER_RANGE;
            return false;
        }
        otherRange = parseRange(inputRangeOrSet);
        if (otherRange != null) {
            messageText = Message.CORRECT_INPUT_ANOTHER_RANGE;
            return true;
        } else {
            return false;
        }
    }

    private boolean parseInputSetOfNumbers() {
        if ("".equals(inputRangeOrSet)) {
            messageText = Message.DEFAULT_SET_OF_NUMBERS;
            return false;
        }
        if (!inputRangeOrSet.matches(REGEX_SETS)) {
            messageText = Message.INCORRECT_INPUT_SET_OF_NUMBERS;
            return false;
        }
        setOfNumbers = parseSetOfNumbers(inputRangeOrSet);
        messageText = Message.CORRECT_INPUT_SET_OF_NUMBERS;
        return true;
    }

    private boolean isIncludedBoundary(final char bracket) {
        return bracket == '[' || bracket == ']';
    }

    private Range parseRange(final String strRange) {
        int indexOfLastSymbol = strRange.length() - 1;
        int leftValue = 0;
        int rightValue = 0;
        boolean leftIsIncluded = isIncludedBoundary(strRange.charAt(0));
        boolean rightIsIncluded = isIncludedBoundary(strRange.charAt(indexOfLastSymbol));

        String[] strValues = strRange.substring(1, indexOfLastSymbol).split(",");
        leftValue = Integer.parseInt(strValues[0]);
        rightValue = Integer.parseInt(strValues[1]);

        Boundary boundaryLeft = new Boundary(leftValue, leftIsIncluded);
        Boundary boundaryRight = new Boundary(rightValue, rightIsIncluded);
        try {
            return new Range(boundaryLeft, boundaryRight);
        } catch (IllegalArgumentException e) {
            result = "";
            messageText = Message.error(e);
            return null;
        }
    }

    private int[] parseSetOfNumbers(final String strSet) {
        int lengthOfSet = strSet.length();
        String[] strValues = strSet.substring(1, lengthOfSet - 1).split(",");
        int[] setOfNumbers = new int[strValues.length];
        for (int i = 0; i < strValues.length; i++) {
            setOfNumbers[i] = Integer.parseInt(strValues[i]);
        }
        return setOfNumbers;
    }

    private String setOfNumbersToString(final int[] setOfNumbers) {
        int lengthOfSet = setOfNumbers.length;
        String strValues = "";
        for (int i = 0; i < lengthOfSet - 1; i++) {
            strValues += setOfNumbers[i] + ", ";
        }
        strValues += setOfNumbers[lengthOfSet - 1];
        return strValues;
    }

    public enum Operation {
        GET_ALL_POINTS("Get all integer points of the range"),
        GET_END_POINTS("Get the boundary points of the range"),
        ARE_EQUAL_RANGES("Check the equivalence of ranges"),
        CONTAINS_POINTS("Check the occurrence of the set of points"),
        CONTAINS_RANGE("Check for range entry"),
        OVERLAPS_RANGE("Check the overlap of ranges");

        Operation(final String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }

        private final String name;
    }

    public static final class Message {
        public static final String DEFAULT_RANGE = "Please enter a range and select an action.\n";
        public static final String DEFAULT_ANOTHER_RANGE = "Please enter another range.\n";
        public static final String DEFAULT_SET_OF_NUMBERS = "Please enter set of numbers.\n";
        public static final String CORRECT_INPUT_RANGE = "You entered a correct range.\n";
        public static final String CORRECT_INPUT_ANOTHER_RANGE = "You entered a correct another "
                + "range.\n";
        public static final String CORRECT_INPUT_SET_OF_NUMBERS = "You entered a correct"
                + " set of numbers.\n";
        public static final String INCORRECT_INPUT_RANGE = "Incorrect input range:\n"
                + "the range must begin with a character '(' or '[' and end ')' or ']'.\n"
                + "Inside the parentheses there must be two integers\nseparated by a comma.";
        public static final String INCORRECT_INPUT_ANOTHER_RANGE = "Incorrect input another "
                + "range:\nthe range must begin with a character '(' or '[' and end ')' or ']'.\n"
                + "Inside the parentheses there must be two integers\nseparated by a comma.";
        public static final String INCORRECT_INPUT_SET_OF_NUMBERS = "Incorrect input set of "
                + "numbers:\nthe set of numbers must be separated by commas and enclosed in {}.\n";

        public static String error(final Exception e) {
            return "Error: " + e.getMessage();
        }

        private Message() {
        }
    }

    private Boolean calculateButtonEnabled = false;
    private Boolean inputRangeOrSetTextFieldEnabled = false;
    private String inputRange = "";
    private String inputRangeOrSet = "";
    private Operation operation = Operation.GET_ALL_POINTS;
    private String result = "";
    private String messageText = Message.DEFAULT_RANGE;
    private Range range;
    private Range otherRange;
    private int[] setOfNumbers;
    private static final String REGEX_RANGES = "[\\[\\(]\\-*\\d+,\\-*\\d+[\\]\\)]";
    private static final String REGEX_SETS = "\\{(\\-*\\d+,)*\\-*\\d+\\}";
}
