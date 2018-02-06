package ru.unn.agile.StatisticalValues.infrastructure;

import java.util.Locale;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public final class CurrentTime {

    public static String getCurrentTime(final String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    private CurrentTime() {
    }
}
