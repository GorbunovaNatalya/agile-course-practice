package ru.unn.agile.NumbersInWords.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class NumbersInWordsConverter {

    public static String convert(final String number) throws IllegalArgumentException {
        StringBuilder integerConvertedNumber = new StringBuilder("");
        StringBuilder decimalConvertedNumber = new StringBuilder();
        String positiveNumber;

        if (number == null) {
            throw new IllegalArgumentException("Input number is null");
        } else if (number.isEmpty()) {
            throw new IllegalArgumentException("Input number is empty");
        }

        if (number.charAt(0) == '-') {
            integerConvertedNumber.append("negative ");
            positiveNumber = number.substring(1);
        } else {
            positiveNumber = number;
        }

        String[] numberParts = positiveNumber.split("\\.");
        if (numberParts.length == 1) {
            integerConvertedNumber.append(convertIntegerPart(numberParts[0]));
        } else if (numberParts.length == 2) {
            integerConvertedNumber.append(convertIntegerPart(numberParts[0]));
            decimalConvertedNumber.append(convertDecimalPart(numberParts[1]));
        } else {
            throw new IllegalArgumentException("Input number must have no more than one point");
        }

        return integerConvertedNumber.append(decimalConvertedNumber).toString();
    }

    public static double getBorderTop() {
        return BORDER_TOP;
    }

    public static double getBorderBottom() {
        return BORDER_BOTTOM;
    }

    private static String convertIntegerPart(final String number) throws IllegalArgumentException {
        StringBuilder convertedNumber = new StringBuilder();

        if (number.length() == 1) {
            return convertOneDigitNumber(number);
        } else if (number.length() == 2) {
            return convertTwoDigitNumber(number);
        }

        String processedNumber = addZerosToBeginningString(number, SUB_NUMBER_LENGTH);

        int startSubNumber = processedNumber.length() - SUB_NUMBER_LENGTH;
        String subNumber = processedNumber.substring(startSubNumber, processedNumber.length());
        convertedNumber.append(convertThreeDigitNumber(subNumber));
        if (isOneOrTwoDigitSubNumber(convertedNumber.toString(), subNumber)) {
            convertedNumber.insert(0, "and ");
        }

        int position = 1;
        int startSecondSubNumber = processedNumber.length() - 2 * SUB_NUMBER_LENGTH;
        for (int i = startSecondSubNumber; i >= 0; i -= SUB_NUMBER_LENGTH) {
            subNumber = processedNumber.substring(i, i + SUB_NUMBER_LENGTH);
            String convertedThreeDigitNumber = convertThreeDigitNumber(subNumber);
            String convertedSubNumber = concatWithPosition(convertedNumber.toString(),
                    convertedThreeDigitNumber, position);
            convertedNumber.insert(0, convertedSubNumber);
            position++;
        }
        return convertedNumber.toString();
    }

    private static String convertDecimalPart(final String number) throws IllegalArgumentException {
        StringBuilder convertedNumber = new StringBuilder(" point");
        for (int i = 0; i < number.length(); i++) {
            convertedNumber.append(" " + convertOneDigitNumber(number.substring(i, i + 1)));
        }
        return convertedNumber.toString();
    }

    private static String addZerosToBeginningString(final String string,
                                                    final int subStringLength) {
        String newString;
        int remainder = subStringLength - string.length() % subStringLength;
        if (remainder != subStringLength) {
            newString = String.join("", Collections.nCopies(remainder, "0")) + string;
        } else {
            newString = string;
        }
        return newString;
    }

    private static String convertOneDigitNumber(final String digit) {
        String convertedDigit = digits.get(digit);
        if (convertedDigit == null) {
            throw new IllegalArgumentException("Input number contains incorrect symbols");
        }
        return convertedDigit;
    }

    private static String convertTwoDigitNumber(final String number)
            throws IllegalArgumentException {
        String convertedNumber;
        String firstDigit = number.substring(0, 1);
        String secondDigit = number.substring(1, 2);

        if (isStringsEqual(firstDigit, "1")) {
            convertedNumber = convertTwoDigitNumberBeforeTwenty(secondDigit);
        } else if (!isStringsEqual(firstDigit, "0")) {
            String twoDigitNumber = twoDigitNumbersAfterTwenty.get(firstDigit);
            if (twoDigitNumber == null) {
                throw new IllegalArgumentException("Input number contains incorrect symbols");
            }
            convertedNumber = twoDigitNumber + " " + convertOneDigitNumber(secondDigit);
        } else if (isStringsEqual(firstDigit, "0") && !isStringsEqual(secondDigit, "0")) {
            convertedNumber = convertOneDigitNumber(secondDigit);
        } else {
            convertedNumber = "";
        }

        return convertedNumber;
    }

    private static String convertTwoDigitNumberBeforeTwenty(final String secondDigit)
            throws IllegalArgumentException {
        String convertedNumber = twoDigitNumbersBeforeTwenty.get(secondDigit);
        if (convertedNumber == null) {
            throw new IllegalArgumentException("Input number contains incorrect symbols");
        }
        return convertedNumber;
    }

    private static String convertThreeDigitNumber(final String number)
            throws IllegalArgumentException {
        String convertedNumber;

        String firstDigit = number.substring(START_OF_FIRST_DIGIT, END_OF_FIRST_DIGIT);
        String secondDigit = number.substring(START_OF_SECOND_DIGIT, END_OF_SECOND_DIGIT);
        String thirdDigit = number.substring(START_OF_THIRD_DIGIT, END_OF_THIRD_DIGIT);

        String convertedTwoDigitNumber = convertTwoDigitNumber(secondDigit + thirdDigit);
        if (!isStringsEqual(firstDigit, "0")) {
            if (isStringsEqual(convertedTwoDigitNumber, "")) {
                convertedNumber = convertOneDigitNumber(firstDigit) + " hundred";
            } else {
                convertedNumber = convertOneDigitNumber(firstDigit)
                        + " hundred and " + convertedTwoDigitNumber;
            }
        } else {
            convertedNumber = convertedTwoDigitNumber;
        }
        return convertedNumber;
    }

    private static String convertDigitPosition(final int position) throws IllegalArgumentException {
        String convertedPosition = digitPosition.get(position);
        if (convertedPosition == null) {
            throw new IllegalArgumentException("Input number is too big or too small");
        }
        return convertedPosition;
    }

    private static String concatWithPosition(final String convertedNumber,
                                             final String convertedThreeDigitNumber,
                                             final int position) {
        String concatenatedNumber = "";
        if (!isStringsEqual(convertedThreeDigitNumber, "")) {
            if (!isStringsEqual(convertedNumber, "")) {
                concatenatedNumber = convertedThreeDigitNumber + " "
                        + convertDigitPosition(position) + " ";
            } else {
                concatenatedNumber = convertedThreeDigitNumber + " "
                        + convertDigitPosition(position);
            }
        }
        return concatenatedNumber;
    }

    private static boolean isStringsEqual(final String str, final String expected) {
        return str == null && expected == null || str != null && str.equals(expected);
    }

    private static boolean isOneOrTwoDigitSubNumber(final String convertedNumber,
                                                    final String subNumber) {
        return !isStringsEqual(convertedNumber, "") && (subNumber.charAt(0) == '0');
    }

    private static Map<String, String> digits;
    private static Map<String, String> twoDigitNumbersBeforeTwenty;
    private static Map<String, String> twoDigitNumbersAfterTwenty;
    private static Map<Integer, String> digitPosition;
    private static final int SUB_NUMBER_LENGTH = 3;
    private static final int START_OF_FIRST_DIGIT = 0;
    private static final int START_OF_SECOND_DIGIT = 1;
    private static final int START_OF_THIRD_DIGIT = 2;
    private static final int END_OF_FIRST_DIGIT = 1;
    private static final int END_OF_SECOND_DIGIT = 2;
    private static final int END_OF_THIRD_DIGIT = 3;
    private static final int MILLIARD_POSITION = 3;
    private static final int MILLION_POSITION = 2;
    private static final int THOUSAND_POSITION = 1;
    private static final double BORDER_TOP = 1000000000000.0;
    private static final double BORDER_BOTTOM = -1000000000000.0;

    private NumbersInWordsConverter() {
    }

    static {
        digits = new HashMap<String, String>();
        digits.put("0", "zero");
        digits.put("1", "one");
        digits.put("2", "two");
        digits.put("3", "three");
        digits.put("4", "four");
        digits.put("5", "five");
        digits.put("6", "six");
        digits.put("7", "seven");
        digits.put("8", "eight");
        digits.put("9", "nine");

        twoDigitNumbersBeforeTwenty = new HashMap<String, String>();
        twoDigitNumbersBeforeTwenty.put("0", "ten");
        twoDigitNumbersBeforeTwenty.put("1", "eleven");
        twoDigitNumbersBeforeTwenty.put("2", "twelve");
        twoDigitNumbersBeforeTwenty.put("3", "thirteen");
        twoDigitNumbersBeforeTwenty.put("4", "fourteen");
        twoDigitNumbersBeforeTwenty.put("5", "fifteen");
        twoDigitNumbersBeforeTwenty.put("6", "sixteen");
        twoDigitNumbersBeforeTwenty.put("7", "seventeen");
        twoDigitNumbersBeforeTwenty.put("8", "eighteen");
        twoDigitNumbersBeforeTwenty.put("9", "nineteen");

        twoDigitNumbersAfterTwenty = new HashMap<String, String>();
        twoDigitNumbersAfterTwenty.put("2", "twenty");
        twoDigitNumbersAfterTwenty.put("3", "thirty");
        twoDigitNumbersAfterTwenty.put("4", "forty");
        twoDigitNumbersAfterTwenty.put("5", "fifty");
        twoDigitNumbersAfterTwenty.put("6", "sixty");
        twoDigitNumbersAfterTwenty.put("7", "seventy");
        twoDigitNumbersAfterTwenty.put("8", "eighty");
        twoDigitNumbersAfterTwenty.put("9", "ninety");

        digitPosition = new HashMap<Integer, String>();
        digitPosition.put(THOUSAND_POSITION, "thousand");
        digitPosition.put(MILLION_POSITION, "million");
        digitPosition.put(MILLIARD_POSITION, "milliard");
    }

}
