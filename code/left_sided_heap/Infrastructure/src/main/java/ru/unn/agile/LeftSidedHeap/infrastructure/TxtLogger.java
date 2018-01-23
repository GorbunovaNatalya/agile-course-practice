package ru.unn.agile.LeftSidedHeap.infrastructure;

import ru.unn.agile.LeftSidedHeap.viewmodel.legacy.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    public TxtLogger(final String logFileName) {
        this.logFileName = logFileName;

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(logFileName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.logWriter = writer;
    }

    @Override
    public void addInfo(final String s) {
        try {
            logWriter.write(now() + " > " + s);
            logWriter.newLine();
            logWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return dateFormatter.format(calendar.getTime());
    }

    @Override
    public List<String> getFullLog() {
        BufferedReader logReader;
        ArrayList<String> log = new ArrayList<>();
        try {
            logReader = new BufferedReader(new FileReader(logFileName));
            String line = logReader.readLine();

            while (line != null) {
                log.add(line);
                line = logReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter logWriter;
    private final String logFileName;
}
