package ru.unn.agile.BookSale.view.legacy;

import ru.unn.agile.BookSale.Model.legacy.Book;
import ru.unn.agile.BookSale.ViewModel.legacy.ViewModel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class CostCalculator {

    public static void main(final String[] args) {
        JFrame frame = new JFrame("CostCalculator");

        frame.setContentPane(new CostCalculator(new ViewModel()).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    private CostCalculator() {
    }

    private CostCalculator(final ViewModel viewModel) {
        this.viewModel = viewModel;

        backBind();
        loadListOfAvailableBooks();

        listAvailableBooks.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(final ListSelectionEvent e) {
                bind();
                backBind();
            }
        });

        listOrderedBooks.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(final ListSelectionEvent e) {
                listOrderedBooks.clearSelection();
            }
        });

        btnAddBookToOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                viewModel.addSelectedBookToOrder();
                backBind();
            }
        });

        btnDeleteBookFromOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                viewModel.deleteSelectedBookFromOrder();
                backBind();
            }
        });
    }

    private void bind() {
        viewModel.onSelectBookFromAvailableList(listAvailableBooks.getSelectedValue());
    }

    private void backBind() {
        btnAddBookToOrder.setEnabled(viewModel.isAddBookToOrderButtonEnabled());
        btnDeleteBookFromOrder.setEnabled(viewModel.isDeleteBookFromOrderButtonEnabled());
        listOrderedBooks.setListData(viewModel.getOrderedBooks());
        txtFinalCost.setText(String.format("%.2f", viewModel.getCost()));
    }

    private void loadListOfAvailableBooks() {
        listAvailableBooks.setListData(viewModel.getAvailableBooks());
    }

    private ViewModel viewModel;

    private JPanel mainPanel;
    private JButton btnDeleteBookFromOrder;
    private JButton btnAddBookToOrder;
    private JTextArea txtFinalCost;
    private JList<Book> listAvailableBooks;
    private JList<ViewModel.OrderedBook> listOrderedBooks;
}
