package ru.unn.agile.PrimeNumber.viewmodel;

public enum LogMessages {
    CALCULATE_WAS_PRESSED("Calculate was pressed with "),
    OPERATION_CHANGED("Operation was changed to "),
    RANGE_CHANGED("Range changed to "),
    NUM_PRIMES_CHANGED("Maximum counts of primes changed to ");

    LogMessages(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    private final String name;
}
