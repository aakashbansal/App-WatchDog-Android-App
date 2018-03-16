package com.android.blocker.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.android.blocker.receiver.NotificationAlarmReceiver;

import static android.content.Context.ALARM_SERVICE;

public class SetNotificationsAlarm {

    // Setup for displaying notifications everyday at 10:00 P.M.
    public static void displayNotificationsEveryday(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, NotificationAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        android.icu.util.Calendar alarmStartTime = android.icu.util.Calendar.getInstance();
        android.icu.util.Calendar now = android.icu.util.Calendar.getInstance();
        alarmStartTime.set(android.icu.util.Calendar.HOUR_OF_DAY, 22);
        alarmStartTime.set(android.icu.util.Calendar.MINUTE, 30);
        alarmStartTime.set(android.icu.util.Calendar.SECOND, 0);

        if (now.after(alarmStartTime)) {
            alarmStartTime.add(android.icu.util.Calendar.DATE, 1);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}
