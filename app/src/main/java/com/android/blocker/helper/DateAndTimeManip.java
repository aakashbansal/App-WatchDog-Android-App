package com.android.blocker.helper;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

public class DateAndTimeManip {

    private final static long TEN_MINUTES = 10 * 60 * 1000;

    public static long getCurrentTime(){
        return Calendar.getInstance().getTimeInMillis();
    }
    public static long getTimeAfterTenMin() {
        long currentTime = getCurrentTime();
        currentTime += TEN_MINUTES;
        return currentTime;
    }

    public static long getUpcomingHourTime() {

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        hour++;

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis();
    }


    public static long getUpcomingDayTime() {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTimeInMillis();
    }


    public static String getCurrentTimeString() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm aaa");
        String lastUsed = df.format(c.getTime());
        return lastUsed;
    }


    //return time 2 days before current day
    public static long getTimeBeforeTwoDays() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        calendar.set(java.util.Calendar.HOUR, 23);
        calendar.set(java.util.Calendar.MINUTE, 59);
        calendar.set(java.util.Calendar.SECOND, 59);
        calendar.add(java.util.Calendar.DAY_OF_WEEK, -2);

        return calendar.getTimeInMillis();
    }


    public static String getTimeStringFromIntegerTime(long time) {

        long tmp = time / 1000;
        if (tmp == 0)
            return " NIL ";
        int seconds = (int) (time / 1000) % 60;
        int minutes = (int) ((time / (1000 * 60)) % 60);
        int hours = (int) (time / (1000 * 60 * 60));

        String Time = "";
        if (hours > 0) {
            Time = Integer.toString(hours) + "h ";
        }
        if (minutes > 0) {
            Time += Integer.toString(minutes) + "min ";
        }
        if (seconds > 0) {
            Time += Integer.toString(seconds) + "sec";
        }

        return Time;
    }


    public static String getTimeInHoursAndMin(int time) {
        time /= 60000;
        String timeString = "";
        int hour = time / 60;
        int min = time % 60;
        if (hour > 0) {
            timeString = Integer.toString(hour) + " Hours ";
        }
        if (min > 0) {
            timeString += Integer.toString(min) + " Minutes ";
        }
        return timeString;
    }


}
