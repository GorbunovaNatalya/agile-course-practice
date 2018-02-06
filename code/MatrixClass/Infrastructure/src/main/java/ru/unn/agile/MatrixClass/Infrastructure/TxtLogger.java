package ru.unn.agile.MatrixClass.Infrastructure;

import ru.unn.agile.MatrixClass.viewmodel.ILogg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogg {
    public TxtLogger(final String fileName) {
        this.fileName = fileName;
        BufferedWriter loggerWriter = null;
        try {
            loggerWriter = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = loggerWriter;
    }

    @Override
    public void log(final String str) {
        try {
            writer.write(now() + " > " + str);
            writer.newLine();
            writer.flush();
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        ArrayList<String> logger = new ArrayList<String>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String txtLine = reader.readLine();
            while (txtLine != null) {
                logger.add(txtLine);
                txtLine = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return logger;
    }

    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;
    private final String fileName;

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }
}
