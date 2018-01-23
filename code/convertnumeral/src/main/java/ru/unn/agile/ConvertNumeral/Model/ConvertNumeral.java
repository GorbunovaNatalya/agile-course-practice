package ru.unn.agile.ConvertNumeral.Model;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

public class ConvertNumeral {

    public Integer convert(final String romanNumber) {
        return getArabicNumber(romanNumber);
    }

    public String convert(final int arabicNumber) {
        return getRomanNumber(arabicNumber);
    }

    private Integer getArabicNumber(final String romanNumber) {
        Integer returnArabicNumber = 0;
        String cutRomanNumber = romanNumber;
        String previousRoman = "";
        String currentRoman = "";
        int countOfRepeatedSymbols = 0;
        Iterator<String> romanIterator = ConformityRomanAndArabic.getRomanIterator();

        while (cutRomanNumber.length() > 0) {
            countOfRepeatedSymbols = 0;
            currentRoman = romanIterator.next();
            while (isCurrentRomanEqualFirstOfString(currentRoman, cutRomanNumber)) {
                countOfRepeatedSymbols++;
                if (isCurrentValueNotAcceptable(previousRoman,
                        currentRoman,
                        countOfRepeatedSymbols)) {
                    throw new InvalidParameterException("Incorrectly entered number!");
                }
                previousRoman = currentRoman;
                cutRomanNumber = cutRomanNumber.substring(currentRoman.length());
                returnArabicNumber += ConformityRomanAndArabic.getArabicNumber(currentRoman);
            }
            if (!romanIterator.hasNext() && cutRomanNumber.length() != 0) {
                throw new InvalidParameterException("Incorrectly entered number!");
            }
        }
        return returnArabicNumber;
    }

    private boolean isCurrentRomanEqualFirstOfString(final String currentNumber,
                                                     final String cutRomanNumber) {
        return cutRomanNumber.length() >= currentNumber.length()
                && currentNumber.equals(cutRomanNumber.substring(0, currentNumber.length()));
    }

    private boolean isCurrentValueNotAcceptable(final String previous,
                                                final String current,
                                                final int countOfRepeatedSymbols) {
        if (countOfRepeatedSymbols > MAX_COUNT_OF_REPEATED_SYMBOLS) {
            return true;
        }
        if (ROMAN_NUMBERS_CONTAINS_ONE.contains(previous) || previous.length() == 0) {
            return false;
        }
        if (ROMAN_NUMBERS_CONTAINS_FIVE.contains(previous)) {
            return isDigitsOfRomansEqual(previous, current)
                    && !ROMAN_NUMBERS_CONTAINS_ONE.contains(current);
        }
        return isDigitsOfRomansEqual(previous, current);
    }

    private boolean isDigitsOfRomansEqual(final String firstRoman,
                                          final String secondRoman) {
        return NUMBER_OF_DIGIT_OF_ROMAN.get(firstRoman)
                .equals(NUMBER_OF_DIGIT_OF_ROMAN.get(secondRoman));
    }

    private String getRomanNumber(final int arabicNumber) {
        if (arabicNumber > MAX_VALUE || arabicNumber < 0) {
            throw new InvalidParameterException("Incorrectly entered number."
                    + " Exceeding the allowed values!");
        }
        int arabicNumberUse = arabicNumber;
        StringBuilder returnRomanNumber = new StringBuilder();
        Iterator<Integer> arabicIterator = ConformityRomanAndArabic.getArabicIterator();
        Integer currentArabic;
        while (arabicNumberUse > 0) {
            currentArabic = arabicIterator.next();
            while (currentArabic <= arabicNumberUse) {
                returnRomanNumber.append(ConformityRomanAndArabic.getRomanNumber(currentArabic));
                arabicNumberUse -= currentArabic;
            }
        }
        return returnRomanNumber.toString();
    }

    private static final List<String> ROMAN_NUMBERS_CONTAINS_FIVE
            = Arrays.asList("V", "L", "D");
    private static final List<String> ROMAN_NUMBERS_CONTAINS_ONE
            = Arrays.asList("M", "C", "X", "I");
    private static final Map<String, DigitOfNumber> NUMBER_OF_DIGIT_OF_ROMAN
            = new HashMap<String, DigitOfNumber>() {{
        put("M", DigitOfNumber.DIGIT_FOUR);
        put("CM", DigitOfNumber.DIGIT_THREE);
        put("D", DigitOfNumber.DIGIT_THREE);
        put("CD", DigitOfNumber.DIGIT_THREE);
        put("C", DigitOfNumber.DIGIT_THREE);
        put("XC", DigitOfNumber.DIGIT_TWO);
        put("L", DigitOfNumber.DIGIT_TWO);
        put("XL", DigitOfNumber.DIGIT_TWO);
        put("X", DigitOfNumber.DIGIT_TWO);
        put("IX", DigitOfNumber.DIGIT_ONE);
        put("V", DigitOfNumber.DIGIT_ONE);
        put("IV", DigitOfNumber.DIGIT_ONE);
        put("I", DigitOfNumber.DIGIT_ONE);
    }};
    private static final Integer MAX_VALUE = 3999;
    private static final Integer MAX_COUNT_OF_REPEATED_SYMBOLS = 3;

    private enum DigitOfNumber {
        DIGIT_FOUR,
        DIGIT_THREE,
        DIGIT_TWO,
        DIGIT_ONE
    }
}
