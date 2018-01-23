package ru.unn.agile.StatisticalValues.viewmodel;

public enum Statistic {
    CENTRAL_MOMENT("Central moment"),
    MEAN("Mean"),
    MEDIAN("Median"),
    MODE("Mode"),
    MOMENT("Moment"),
    STD("Std"),
    VARIANCE("Variance");

    Statistic(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    private final String name;
}
