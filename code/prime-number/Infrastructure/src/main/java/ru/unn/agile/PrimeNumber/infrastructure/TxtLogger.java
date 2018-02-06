package ru.unn.agile.PrimeNumber.infrastructure;

import ru.unn.agile.PrimeNumber.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TxtLogger implements ILogger {
    public TxtLogger(final String nameOfFile) {
        if (nameOfFile == null || nameOfFile.isEmpty()) {
            throw new IllegalArgumentException("Name of file can't be null or empty!");
        }
        this.logfileName = nameOfFile;

        BufferedWriter writer = null;
        try {
            FileWriter fileWriter = new FileWriter(nameOfFile);
            writer = new BufferedWriter(fileWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.logWriter = writer;
    }

    @Override
    public void log(final String message) {
        try {
            logWriter.write(getCurrentDateAndTime() + " >>> " + message + "\r\n");
            logWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> result = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(logfileName);
            reader = new BufferedReader(fileReader);
            String readLine = reader.readLine();
            while (readLine != null) {
                result.add(readLine);
                readLine = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getCurrentDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Date currentDate = calendar.getTime();
        return simpleDateFormat.format(currentDate);
    }

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter logWriter;
    private final String logfileName;
}
