package ru.unn.agile.StatisticalValues.viewmodel;

class ScalarStatistic<T> implements Printable {
    @Override
    public String print() {
        return String.valueOf(value);
    }

    ScalarStatistic(final T value) {
        this.value = value;
    }

    private final T value;
}
