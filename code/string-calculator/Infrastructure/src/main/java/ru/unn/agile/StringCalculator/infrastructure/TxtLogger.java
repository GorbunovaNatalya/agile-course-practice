package ru.unn.agile.StringCalculator.infrastructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.List;

public class TxtLogger extends AbstractLogger {
    public TxtLogger(final String fileName) {
        super();
        this.fileName = fileName;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception excp) {
            excp.printStackTrace();
        }
        writer = logWriter;
    }

    @Override
    public void log(final String logTag, final String s) {
        try {
            String message = prepareLogMessage(logTag, s);
            writer.write(message);
            writer.flush();
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                log.add(line);
                line = reader.readLine();
            }
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
        }

        return log;
    }

    private final BufferedWriter writer;
    private final String fileName;
}
