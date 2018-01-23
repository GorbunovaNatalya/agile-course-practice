package ru.unn.agile.NumbersInWords.viewmodel;

enum Status {
    WAITING("Please enter a number"),
    READY("Press 'Translate'"),
    BAD_FORMAT("Error in input number"),
    SUCCESS("Success");

    Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    private final String name;
}
