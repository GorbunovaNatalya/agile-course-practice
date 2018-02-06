package ru.unn.agile.StatisticalValues.viewmodel;

class VectorStatistic<T> implements Printable {
    @Override
    public String print() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            result.append(values[i]);
            if (i < values.length - 1) {
                result.append(", ");
            }
        }

        return result.toString();
    }

    VectorStatistic(final T[] values) {
        this.values = values;
    }

    private final T[] values;
}
