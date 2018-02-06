package ru.unn.agile.StringCalculator.Model;

public final class StringCalculator {
    public static int add(final String input) throws IllegalArgumentException {
        checkBadFormat(input);

        String delimiter = extractDelimiter(input);
        String[] numbers = split(input, delimiter);

        int sum = 0;
        for (String number : numbers) {
            sum += Integer.parseInt(number);
        }
        return sum;
    }

    public static Boolean isBadFormat(final String input) {
        try {
            checkBadFormat(input);
        } catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }

    private static void checkBadFormat(final String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Incorrect data");
        }
        String delimiter = extractDelimiter(input);
        String[] numbers = split(input, delimiter);

        checkIncorrectData(numbers);
        checkNegatives(numbers);
    }

    private static String extractDelimiter(final String input) {
        String delimiter = ",\n";
        String firstSymbol = input.substring(0, 1);
        if (firstSymbol.matches("[,.:;]")) {
            delimiter = firstSymbol + "\n";
        }
        return delimiter;
    }

    private static String[] split(final String input, final String delimiter) {
        String str = input;
        String firstSymbol = str.substring(0, 1);
        if (firstSymbol.matches("[,.:;]")) {
            str = str.substring(2);
        }
        return str.split("[" + delimiter + "]");
    }

    private static void checkNegatives(final String[] numbers) {
        StringBuilder error = new StringBuilder(ERROR_BUFFER_SIZE);
        error.append("Negatives ");
        for (String number: numbers) {
            if (Integer.parseInt(number) < 0) {
                error.append(number).append(" ");
            }
        }
        if (!"Negatives ".equals(error.toString())) {
            error.append("not allowed");
            throw new IllegalArgumentException(error.toString());
        }
    }

    private static void checkIncorrectData(final String[] numbers) {
        for (String number: numbers) {
            if (number.isEmpty() || !isNumeric(number)) {
                throw new IllegalArgumentException("Incorrect data");
            }
        }
    }

    private static boolean isNumeric(final String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private StringCalculator() {
    }

    private static final int ERROR_BUFFER_SIZE = 50;
}
