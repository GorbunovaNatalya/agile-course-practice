package ru.unn.agile.NumberSystemConverter.model;

import java.math.BigInteger;
import java.util.Locale;

public final class NumberSystemConverter {

    public static String convert(final String input, final NumberSystemBase baseNumberSystem,
                                 final NumberSystemBase targetNumberSystem)
            throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException("Input is Null");
        }
        if (input.isEmpty()) {
            return "";
        }
        BigInteger inputInteger = new BigInteger(input, baseNumberSystem.getBaseSystem());
        return inputInteger.toString(targetNumberSystem.getBaseSystem()).toUpperCase(Locale.US);
    }

    private NumberSystemConverter() {
    }
}
