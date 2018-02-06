package ru.unn.agile.ConvertNumeral.infrastructure;

import ru.unn.agile.ConvertNumeral.ViewModel.legacy.ILogger;

import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FileLogger implements ILogger {
    public FileLogger(final String fileLogName) {
        this.fileLogName = fileLogName;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(fileLogName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = logWriter;
    }

    @Override
    public List<String> getLog() {
        ArrayList<String> log = new ArrayList<String>();
        try {
            BufferedReader bufferedReader = getReader();
            String row = bufferedReader.readLine();

            while (row != null) {
                log.add(row);
                row = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return log;
    }

    @Override
    public void log(final String logMessage) {
        try {
            writer.write(now() + " > " + logMessage);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String now() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return formatter.format(Calendar.getInstance().getTime());
    }

    private BufferedReader getReader() throws FileNotFoundException {
        return new BufferedReader(new FileReader(fileLogName));
    }

    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final String fileLogName;
    private final BufferedWriter writer;
}
