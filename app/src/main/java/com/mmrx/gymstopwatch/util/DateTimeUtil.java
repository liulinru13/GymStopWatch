package com.mmrx.gymstopwatch.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    /**
     * Gets the current date of the system (1970-01-01 24:00:00)
     */
    public static String getSysTime() {
        return getDate(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static String getYmdTime() {
        return getDate(new Date(System.currentTimeMillis()), "yyyy-MM-dd");
    }

    public static String getHmsTime() {
        return getDate(new Date(System.currentTimeMillis()), "HH:mm:ss");
    }

    /**
     * date to get the string
     * 
     * @param date
     * @param format
     *            ("hh:mm:ss yyyy-MM-dd")
     * @return
     */
    public static String getDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
