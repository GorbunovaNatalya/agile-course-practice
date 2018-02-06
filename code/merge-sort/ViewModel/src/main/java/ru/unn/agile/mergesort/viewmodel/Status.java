package ru.unn.agile.mergesort.viewmodel;

enum Status {
    WAITING("Ожидаю ввода"),
    READY("Ввод корректен"),
    BAD_FORMAT("Ввод некорректен"),
    SUCCESS("Готово!");

    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }

    private final String name;
}
