package ru.unn.agile.NumberSystemConverter.model;

public enum NumberSystemBase {
    BIN(2),
    OCT(8),
    DEC(10),
    HEX(16);

    private final int baseSystem;

    NumberSystemBase(final int baseSystem) {
        this.baseSystem = baseSystem;
    }

    public int getBaseSystem() {
        return baseSystem;
    }
}
