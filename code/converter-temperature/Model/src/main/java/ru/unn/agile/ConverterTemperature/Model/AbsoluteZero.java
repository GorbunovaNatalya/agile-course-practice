package ru.unn.agile.ConverterTemperature.Model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

final class AbsoluteZero {
    private static final double CELSIUS = -273.15;
    private static final double FAHRENHEIT = -459.67;
    private static final double KELVIN = 0.0;
    private static final double NEWTON = -90.14;

    private static final Map<Conversion, Double> ABSOLUTE_ZEROS = Collections.unmodifiableMap(
        new HashMap<Conversion, Double>() {{
            put(Conversion.CELSIUS_TO_FAHRENHEIT, CELSIUS);
            put(Conversion.CELSIUS_TO_KELVIN,     CELSIUS);
            put(Conversion.CELSIUS_TO_NEWTON,     CELSIUS);

            put(Conversion.FAHRENHEIT_TO_CELSIUS, FAHRENHEIT);
            put(Conversion.FAHRENHEIT_TO_KELVIN,  FAHRENHEIT);
            put(Conversion.FAHRENHEIT_TO_NEWTON,  FAHRENHEIT);

            put(Conversion.KELVIN_TO_CELSIUS,    KELVIN);
            put(Conversion.KELVIN_TO_FAHRENHEIT, KELVIN);
            put(Conversion.KELVIN_TO_NEWTON,     KELVIN);

            put(Conversion.NEWTON_TO_CELSIUS,    NEWTON);
            put(Conversion.NEWTON_TO_FAHRENHEIT, NEWTON);
            put(Conversion.NEWTON_TO_KELVIN,     NEWTON);
        }}
    );

    private AbsoluteZero() { }

    static double getAbsoluteZero(final Conversion conversion) {
        if (!ABSOLUTE_ZEROS.containsKey(conversion)) {
            throw new IllegalArgumentException("Unknown conversion type");
        }

        return ABSOLUTE_ZEROS.get(conversion);
    }
}
