package ru.unn.agile.Polynomial.infrastructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ru.unn.agile.Polynomial.viewmodel.ILogger;

public class TxtLogger implements ILogger {
    public TxtLogger(final String filename) {
        this.filename = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        bufWriter = logWriter;
    }

    @Override
    public void addInfo(final String message) {
        try {
            bufWriter.write(getTime() + " > " + message);
            bufWriter.newLine();
            bufWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> returnLog = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                returnLog.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return returnLog;
    }

    private static String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter bufWriter;
    private final String filename;
}
