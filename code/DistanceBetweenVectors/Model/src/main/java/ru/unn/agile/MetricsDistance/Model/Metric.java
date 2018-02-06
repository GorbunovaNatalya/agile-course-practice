package ru.unn.agile.MetricsDistance.Model;

public enum Metric {
    Chebyshev("Chebyshev"),
    Minkowski("Minkowski");

    private final String name;
    Metric(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
