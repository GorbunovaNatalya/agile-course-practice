package ru.unn.agile.StatisticalValues.viewmodel;

interface Computable {
    Printable compute();

    boolean isOrderUsing();
    boolean isBiasUsing();
}
