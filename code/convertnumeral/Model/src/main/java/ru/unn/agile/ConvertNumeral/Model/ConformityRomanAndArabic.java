package ru.unn.agile.ConvertNumeral.Model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class ConformityRomanAndArabic {
    public static String getRomanNumber(final Integer arabicNumber) {
        return ROMAN_NUMBERS.get(ARABIC_NUMBERS.indexOf(arabicNumber));
    }

    public static Iterator<String> getRomanIterator() {
        return ROMAN_NUMBERS.iterator();
    }

    public static Integer getArabicNumber(final String romanNumber) {
        return ARABIC_NUMBERS.get(ROMAN_NUMBERS.indexOf(romanNumber));
    }

    public static Iterator<Integer> getArabicIterator() {
        return ARABIC_NUMBERS.iterator();
    }

    private ConformityRomanAndArabic() {
    }

    private static final List<Integer> ARABIC_NUMBERS
            = Arrays.asList(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1);
    private static final List<String> ROMAN_NUMBERS
            = Arrays.asList("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I");
}
