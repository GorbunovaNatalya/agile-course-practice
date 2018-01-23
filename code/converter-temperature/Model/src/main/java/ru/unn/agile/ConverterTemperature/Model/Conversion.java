package ru.unn.agile.ConverterTemperature.Model;

public enum Conversion {
    CELSIUS_TO_FAHRENHEIT(1.8, 32.0),
    FAHRENHEIT_TO_CELSIUS(1.0 / CELSIUS_TO_FAHRENHEIT.getScale(),
        -CELSIUS_TO_FAHRENHEIT.getShift() / CELSIUS_TO_FAHRENHEIT.getScale()),

    CELSIUS_TO_KELVIN(1.0, 273.15),
    KELVIN_TO_CELSIUS(1.0 / CELSIUS_TO_KELVIN.getScale(),
        -CELSIUS_TO_KELVIN.getShift() / CELSIUS_TO_KELVIN.getScale()),

    CELSIUS_TO_NEWTON(0.33, 0.0),
    NEWTON_TO_CELSIUS(1.0 / CELSIUS_TO_NEWTON.getScale(),
        -CELSIUS_TO_NEWTON.getShift() / CELSIUS_TO_NEWTON.getScale()),

    FAHRENHEIT_TO_KELVIN(FAHRENHEIT_TO_CELSIUS.getScale() / KELVIN_TO_CELSIUS.getScale(),
        (FAHRENHEIT_TO_CELSIUS.getShift() - KELVIN_TO_CELSIUS.getShift())
            / KELVIN_TO_CELSIUS.getScale()),
    KELVIN_TO_FAHRENHEIT(1.0 / FAHRENHEIT_TO_KELVIN.getScale(),
        -FAHRENHEIT_TO_KELVIN.getShift() / FAHRENHEIT_TO_KELVIN.getScale()),

    FAHRENHEIT_TO_NEWTON(FAHRENHEIT_TO_CELSIUS.getScale() / NEWTON_TO_CELSIUS.getScale(),
        (FAHRENHEIT_TO_CELSIUS.getShift() - NEWTON_TO_CELSIUS.getShift())
            / NEWTON_TO_CELSIUS.getScale()),
    NEWTON_TO_FAHRENHEIT(1.0 / FAHRENHEIT_TO_NEWTON.getScale(),
        -FAHRENHEIT_TO_NEWTON.getShift() / FAHRENHEIT_TO_NEWTON.getScale()),

    KELVIN_TO_NEWTON(KELVIN_TO_CELSIUS.getScale() / NEWTON_TO_CELSIUS.getScale(),
        (KELVIN_TO_CELSIUS.getShift() - NEWTON_TO_CELSIUS.getShift())
            / NEWTON_TO_CELSIUS.getScale()),
    NEWTON_TO_KELVIN(1.0 / KELVIN_TO_NEWTON.getScale(),
        -KELVIN_TO_NEWTON.getShift() / KELVIN_TO_NEWTON.getScale());

    Conversion(final double scale, final double shift) {
        this.scale = scale;
        this.shift = shift;
    }

    public double getScale() {
        return scale;
    }

    public double getShift() {
        return shift;
    }

    private final double scale;
    private final double shift;
}
