package com.wordnik.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static final long SECONDS_IN_MILLIS = 1000;
    public static final long MINUTES_IN_MILLIS = SECONDS_IN_MILLIS * 60;
    public static final long HOURS_IN_MILLIS = MINUTES_IN_MILLIS * 60;
    public static final long DAYS_IN_MILLIS = HOURS_IN_MILLIS * 24;

    public static Date getDateMinusMilliseconds(long intervalInMilliseconds) {
        return getDateMinusMilliseconds(new Date(), intervalInMilliseconds);
    }

    public static Date getDateMinusMilliseconds(Date date, long intervalInMilliseconds){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if(intervalInMilliseconds > Integer.MAX_VALUE){
            int days = (int)Math.floor((double)intervalInMilliseconds / 86400000.0);
            intervalInMilliseconds -= (days * 86400000);
            c.add(Calendar.DAY_OF_YEAR, -days);
            c.add(Calendar.MILLISECOND, (int)-intervalInMilliseconds);
        }
        else{
            c.add(Calendar.MILLISECOND, (int)-intervalInMilliseconds);
        }
        return c.getTime();
    }

    public static long millisToSec(long millis) {
        return (long)((double)millis / (double)SECONDS_IN_MILLIS);
    }

    public static long millisFromSeconds(double seconds) {
        return (long)((double)SECONDS_IN_MILLIS * seconds);
    }
	
    public static long millisFromHours(double hours) {
        return (long)((double)HOURS_IN_MILLIS * hours);
    }

    public static long millisFromDays(double days) {
        return (long)((double)DAYS_IN_MILLIS * days);
    }

    public static long millisFromMinutes(int minutes) {
        return (long)((double)MINUTES_IN_MILLIS * minutes);
    }
}