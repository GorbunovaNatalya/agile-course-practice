package ru.unn.agile.StatisticalValues.infrastructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class StatisticalLogger extends AbstractLogger {

    public StatisticalLogger(final String filename) {
        super();
        this.fileName = filename;

        BufferedWriter logWriter = null;

        if (filename.isEmpty()) {
            throw new IllegalArgumentException("Filename can't be empty");
        }

        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writerBuffer = logWriter;
    }

    @Override
    public void addLogText(final String message, final String logTag) {
        try {
            String mes = this.prepareLogMessage(message, logTag);
            writerBuffer.write(mes);
            writerBuffer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLogText() {
        BufferedReader reader;
        ArrayList<String> logResult = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                logResult.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return logResult;
    }

    private final BufferedWriter writerBuffer;
    private final String fileName;
}
