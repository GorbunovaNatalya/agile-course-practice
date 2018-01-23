package ru.unn.agile.LeftSidedHeap.view.legacy;

import ru.unn.agile.LeftSidedHeap.viewmodel.legacy.ViewModel;
import ru.unn.agile.LeftSidedHeap.infrastructure.TxtLogger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public final class ViewLHeap {
    public static void main(final String[] args) {
        JFrame frame = new JFrame("Left Heap");

        String loggerFileName = String.format("ViewLHeap_{%s}.log",
                java.util.UUID.randomUUID().toString());
        TxtLogger logger = new TxtLogger("./" + loggerFileName);
        frame.setContentPane(new ViewLHeap(new ViewModel(logger)).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    private ViewLHeap() {

    }

    private ViewLHeap(final ViewModel viewModel) {
        this.viewModel = viewModel;
        backBind();

        buttonAdd.addActionListener((actionEvent) -> {
                bind();
                this.viewModel.add();
                backBind();
            }
        );

        buttonRemove.addActionListener((actionEvent) -> {
                bind();
                this.viewModel.remove();
                backBind();
            }
        );

        fieldAdd.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                bind();
                ViewLHeap.this.viewModel.processKeyInAddField();
                backBind();
            }
        });

        fieldRemove.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                bind();
                ViewLHeap.this.viewModel.processKeyInRemoveField();
                backBind();
            }
        });
    }

    private void bind() {
        viewModel.setTextAdd(fieldAdd.getText());
        viewModel.setTextRemove(fieldRemove.getText());
    }

    private void backBind() {
        fieldAdd.setText(viewModel.getTextAdd());
        fieldRemove.setText(viewModel.getTextRemove());
        buttonAdd.setEnabled(viewModel.isButtonAddEnabled());
        buttonRemove.setEnabled(viewModel.isButtonRemoveEnabled());

        textSizeHeap.setText(viewModel.getTextSizeHeap());
        textMinInHeap.setText(viewModel.getTextMinInHeap());
        textRemoveFromHeap.setText(viewModel.getTextRemoveFromHeap());
        textStatus.setText("Status: " + viewModel.getStatus());
        List<String> fullLog = viewModel.getFullLog();
        String[] items = fullLog.toArray(new String[fullLog.size()]);
        lstFullLog.setListData(items);
    }

    private ViewModel viewModel;
    private JTextField fieldAdd;
    private JButton buttonAdd;
    private JTextField fieldRemove;
    private JButton buttonRemove;
    private JPanel mainPanel;
    private JTextField textStatus;
    private JTextField textSizeHeap;
    private JTextField textMinInHeap;
    private JTextField textRemoveFromHeap;
    private JList<String> lstFullLog;
}
