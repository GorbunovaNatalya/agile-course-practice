package ru.unn.agile.MetricsDistance.Infrastructure;

import java.util.Locale;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public final class CurrentDate {

    public static String getCurrentDate(final String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    private CurrentDate() {
    }
}
