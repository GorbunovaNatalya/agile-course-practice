package ru.unn.agile.Polynomial.view;

import ru.unn.agile.Polynomial.viewmodel.Operation;
import ru.unn.agile.Polynomial.viewmodel.Status;
import ru.unn.agile.Polynomial.viewmodel.ViewModel;
import ru.unn.agile.Polynomial.infrastructure.TxtLogger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public final class Calculator {
    private Calculator(final ViewModel viewModel) {
        this.viewModel = viewModel;
        backBind();
        loadListOfOperations();

        cbOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                backBind();
            }
        });

        btnCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                viewModel.calculate();
                backBind();
            }
        });

        final DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent documentEvent) {
                bind();
                viewModel.processTextChanged();
                backBind();
            }

            @Override
            public void removeUpdate(final DocumentEvent documentEvent) {
                bind();
                viewModel.processTextChanged();
                backBind();
            }

            @Override
            public void changedUpdate(final DocumentEvent documentEvent) { }
        };

        txtFirstPolynomial.getDocument().addDocumentListener(documentListener);
        txtSecondPolynomial.getDocument().addDocumentListener(documentListener);
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Calculator");

        TxtLogger logger = new TxtLogger("./ViewPolynomial.log");
        frame.setContentPane(new Calculator(new ViewModel(logger)).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void loadListOfOperations() {
        Operation[] operations = Operation.values();
        cbOperation.setModel(new JComboBox<>(operations).getModel());
    }

    private void bind() {
        viewModel.setFirstPolynomial(txtFirstPolynomial.getText());
        viewModel.setSecondPolynomial(txtSecondPolynomial.getText());

        viewModel.setOperation((Operation) cbOperation.getSelectedItem());
    }

    private void backBind() {
        btnCalc.setEnabled(viewModel.isButtonCalculateEnabled());
        txtResult.setText(viewModel.getResult());
        Status status = viewModel.getStatus();
        lbStatus.setText(status.toString());
        List<String> logText = viewModel.getLog();
        String[] items = logText.toArray(new String[logText.size()]);
        listLog.setListData(items);

    }

    private ViewModel viewModel;
    private JPanel mainPanel;
    private JLabel lbStatus;
    private JTextField txtResult;
    private JTextField txtFirstPolynomial;
    private JTextField txtSecondPolynomial;
    private JButton btnCalc;
    private JComboBox<Operation> cbOperation;
    private JList<String> listLog;
}
