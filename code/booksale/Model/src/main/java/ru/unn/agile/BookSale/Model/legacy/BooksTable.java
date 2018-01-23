package ru.unn.agile.BookSale.Model.legacy;

import java.util.HashSet;
import java.util.UUID;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class BooksTable {

    public BooksTable() {
        this.books = new HashMap<UUID, Book>();
    }

    public BooksTable(final Set<Book> books) {
        this.books = new HashMap<UUID, Book>();
        for (Book book : books) {
            this.books.put(book.getId(), book);
        }
    }

    public Book[] getBooks() {
        return this.books.values().toArray(new Book[0]);
    }

    public Book getBookById(final UUID id) {
        return this.books.get(id);
    }

    public void defaultInitialization() {
        this.books = new HashMap<UUID, Book>();
        for (Book book : DEFAULT_BOOKS) {
            this.books.put(book.getId(), book);
        }
    }

    private Map<UUID, Book> books;
    private static final double PRICE = 8.0;
    private static final Set<Book> DEFAULT_BOOKS = new HashSet<Book>() {{
        add(new Book("Harry Potter and the Philosopher's Stone", PRICE, UUID.randomUUID()));
        add(new Book("Harry Potter And the Chamber of secrets", PRICE, UUID.randomUUID()));
        add(new Book("Harry Potter and the prisoner of Azkaban", PRICE, UUID.randomUUID()));
        add(new Book("Harry Potter and the Goblet of Fire", PRICE, UUID.randomUUID()));
        add(new Book("Harry Potter and the Order of the Phoenix", PRICE, UUID.randomUUID()));
    }};
}
