package ru.unn.agile.Triangle.Infrastructure;

import ru.unn.agile.Triangle.ViewModel.ILogger;

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

    public TxtLogger(final String filesname) {
        this.nameOfFile = filesname;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filesname));
        } catch (Exception e) {
            e.printStackTrace();
        }
        filewriter = logWriter;
    }

    @Override
    public void log(final String s) {
        try {
            filewriter.write(now() + " > " + s);
            filewriter.newLine();
            filewriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader filereader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            filereader = new BufferedReader(new FileReader(nameOfFile));
            String oneline = filereader.readLine();

            while (oneline != null) {
                log.add(oneline);
                oneline = filereader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter filewriter;
    private final String nameOfFile;

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }
}
