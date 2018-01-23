package ru.unn.agile.BookSale.Model.legacy;

import java.util.UUID;

public class Book {
    public Book(final String name, final double price, final UUID id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " (" + Double.toString(price) + "$)";
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    private UUID id;
    private String name;
    private double price;
}
