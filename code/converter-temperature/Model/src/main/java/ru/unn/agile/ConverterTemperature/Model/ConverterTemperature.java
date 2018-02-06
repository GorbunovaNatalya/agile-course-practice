package ru.unn.agile.ConverterTemperature.Model;

public final class ConverterTemperature {
    private ConverterTemperature() { }

    public static double convert(final double value, final Conversion conversion) {
        if (lessThanAbsoluteZero(value, conversion)) {
            throw new IllegalArgumentException("Value less than absolute zero");
        }
        return value * conversion.getScale() + conversion.getShift();
    }

    private static boolean lessThanAbsoluteZero(final double value, final Conversion conversion) {
        return value < AbsoluteZero.getAbsoluteZero(conversion);
    }
}
