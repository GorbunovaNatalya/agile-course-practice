package ru.unn.agile.NumberSystemConverter.infrastructure;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

import java.util.ArrayList;
import java.util.List;

public class TextLogger extends AbstractLogger {
    public TextLogger(final String fileName) {
        super();

        this.fileName = fileName;

        BufferedWriter textLoggerWriter = null;
        try {
            textLoggerWriter = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        writer = textLoggerWriter;
    }

    @Override
    public void log(final String message, final String tag) {
        try {
            writer.write(this.prepare(message, tag));
            writer.flush();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> logs = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                logs.add(line);
                line = reader.readLine();
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }

        return logs;
    }

    private final BufferedWriter writer;
    private final String fileName;
}
