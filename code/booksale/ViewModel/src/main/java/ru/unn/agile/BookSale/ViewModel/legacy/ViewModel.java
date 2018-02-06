package ru.unn.agile.BookSale.ViewModel.legacy;

import ru.unn.agile.BookSale.Model.legacy.Book;
import ru.unn.agile.BookSale.Model.legacy.BooksTable;
import ru.unn.agile.BookSale.Model.legacy.Order;

import java.util.ArrayList;
import java.util.UUID;

public class ViewModel {
    public ViewModel() {
        booksTable = new BooksTable();
        booksTable.defaultInitialization();
        order = new Order(booksTable);
        updateOrderedBooksList();
        updateButtonActivityState();
        calculateCost();
    }

    public void onSelectBookFromAvailableList(final Book book) {
        selectedBook = book;
        updateButtonActivityState();
    }

    public void addSelectedBookToOrder() {
        order.addBook(selectedBook.getId());
        updateOrderedBooksList();
        updateButtonActivityState();
        calculateCost();
    }

    public void deleteSelectedBookFromOrder() {
        order.deleteBook(selectedBook.getId());
        updateOrderedBooksList();
        updateButtonActivityState();
        calculateCost();
    }

    public boolean isAddBookToOrderButtonEnabled() {
        return isAddBookToOrderButtonEnabled;
    }

    public boolean isDeleteBookFromOrderButtonEnabled() {
        return isDeleteBookFromOrderButtonEnabled;
    }

    public Book[] getAvailableBooks() {
        return booksTable.getBooks();
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public OrderedBook[] getOrderedBooks() {
        return orderedBooks;
    }

    public double getCost() {
        return cost;
    }

    private void updateOrderedBooksList() {
        ArrayList<OrderedBook> tmpList = new ArrayList<>();
        order.getBooksList().forEach((UUID key, Integer value) ->
                tmpList.add(new OrderedBook(booksTable.getBookById(key), value))
        );
        orderedBooks = tmpList.toArray(new OrderedBook[tmpList.size()]);
    }

    private void updateButtonActivityState() {
        if (selectedBook != null) {
            this.isAddBookToOrderButtonEnabled = true;
            if (order.getCountBookById(selectedBook.getId()) != null) {
                isDeleteBookFromOrderButtonEnabled = true;
            } else {
                isDeleteBookFromOrderButtonEnabled = false;
            }
        }
    }

    private void calculateCost() {
        cost = order.getCost();
    }

    private BooksTable booksTable;
    private Order order;
    private boolean isAddBookToOrderButtonEnabled = false;
    private boolean isDeleteBookFromOrderButtonEnabled = false;
    private Book selectedBook;
    private OrderedBook[] orderedBooks = new OrderedBook[0];
    private double cost = 0.0;

    public static class OrderedBook {
        public OrderedBook(final Book book, final Integer count) {
            this.book = book;
            this.count = count;
        }

        @Override
        public String toString() {
            return book.toString() + " - " + count.toString() + " PCs.";
        }

        public Book getBook() {
            return book;
        }

        public Integer getCount() {
            return count;
        }

        private Book book;
        private Integer count;
    }
}
