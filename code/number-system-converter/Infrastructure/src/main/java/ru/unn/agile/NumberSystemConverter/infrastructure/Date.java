package ru.unn.agile.NumberSystemConverter.infrastructure;

import java.util.Locale;
import java.util.Calendar;

import java.text.SimpleDateFormat;

public final class Date {
    public static String getDate(final String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    private Date() {
    }
}
