package ru.unn.agile.ConverterTemperature.viewmodel;

public enum NameSystem {
    CELSIUS("CELSIUS"),
    FAHRENHEIT("FAHRENHEIT"),
    KELVIN("KELVIN"),
    NEWTON("NEWTON");

    NameSystem(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
    private final String name;
}
