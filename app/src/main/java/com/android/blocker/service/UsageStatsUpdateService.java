package com.android.blocker.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;

import com.android.blocker.database.dbViewUsageStats.DBViewUsageStatsHandler;
import com.android.blocker.helper.AndroidSystemWrappers.AndroidUsageStatsManagerWrappers;
import com.android.blocker.helper.DateAndTimeManip;
import com.android.blocker.sharedPreferences.MySharedPreferences;
import com.android.blocker.sharedPreferences.SPNames;


public class UsageStatsUpdateService extends Service {


    private HandlerThread updateUsageStatsThread;
    private Looper serviceLooper;
    private static Handler serviceHandler;
    private Context context;
    private static DBViewUsageStatsHandler mDBViewUsageStatsHandler;
    private String prevApp;
    private long currentTime;
    private long nearestHour, nearestDay;
    private Runnable taskExecuteEveryTwoSeconds;

    private static final String UPDATE_USAGE_STATS_THREAD_NAME = "UpdateUsageStatsThread";
    private static final long TWO_SECONDS = 2000;
    private static final String NO_APP_SELECTED = "NoAppSelected";
    private static final long MAX_SAFE_TIME_ALLOWED = 60 * 1000; // one minute

    public UsageStatsUpdateService() {
    }


    @Override
    public void onCreate() {
        updateUsageStatsThread = new HandlerThread(UPDATE_USAGE_STATS_THREAD_NAME,
                Process.THREAD_PRIORITY_BACKGROUND);
        updateUsageStatsThread.start();

        context = getApplicationContext();

        serviceLooper = updateUsageStatsThread.getLooper();
        serviceHandler = new Handler(serviceLooper);
        mDBViewUsageStatsHandler = new DBViewUsageStatsHandler(context);

        prevApp = NO_APP_SELECTED;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {

        taskExecuteEveryTwoSeconds = new Runnable() {
            @Override
            public void run() {

                // add this task to the list of executable tasks after two seconds.
                // this happens each time this task is executed
                serviceHandler.postDelayed(this, TWO_SECONDS);

                //get nearest complete hour time
                nearestHour = MySharedPreferences.getLongIntValue(context, SPNames.SP_NEAREST_TIME,
                        SPNames.SP_KEY_NEAREST_HOUR, 0);

                //get nearest complete day time
                nearestDay = MySharedPreferences.getLongIntValue(context, SPNames.SP_NEAREST_TIME,
                        SPNames.SP_KEY_NEAREST_DAY, 0);

                // if hourly update required
                if (nearestHour < Calendar.getInstance().getTimeInMillis()) {
                    hourlyDBReset();
                    updateNearestHourTime();
                }

                // if daily update required
                if (nearestDay < Calendar.getInstance().getTimeInMillis()) {
                    dailyDBReset();
                    updateNearestDayTime();
                    setAppDisabledOnceTodayOptionToFalse();
                }

                String currentAppInForeground = AndroidUsageStatsManagerWrappers.getForegroundApp(getApplicationContext());

                if (currentAppInForeground != null) {
                    updateCurrentAppUsageStats(currentAppInForeground);
                }

            }
        };

        serviceHandler.post(taskExecuteEveryTwoSeconds);
        return START_STICKY;
    }


    private void updateCurrentAppUsageStats(String currentAppInForeground) {

        long currentAppTimeInForeground = System.currentTimeMillis() - currentTime;

        // don't update the usage info of an app while the current app is still
        // in foreground.
        // Only update the usage info if the app has left foreground
        if (!prevApp.equals(currentAppInForeground)) {

            //handles the case when initially there was no app
            if (!prevApp.equals(NO_APP_SELECTED)) {
                mDBViewUsageStatsHandler.updateUsageStats(prevApp, currentTime,
                        DateAndTimeManip.getCurrentTimeString());
            }

            prevApp = currentAppInForeground;
            currentTime = System.currentTimeMillis();

            // handles the case, where the usage time allowed of an app exceeds while the app
            // is still in foreground. Usage stats of the app will get updated
            // after a specified "Max Safe" time so that the app may be closed mid way
            // if usage limit is exceeded
        } else if (currentAppTimeInForeground > MAX_SAFE_TIME_ALLOWED) {

            mDBViewUsageStatsHandler.updateOnlyUsageTime(prevApp, currentAppTimeInForeground);
            currentTime = System.currentTimeMillis();
        }
    }


    private void setAppDisabledOnceTodayOptionToFalse() {
        MySharedPreferences.storeBooleanValue(context, SPNames.SP_APP_DISABLED,
                SPNames.SP_KEY_WAS_APP_DISABLED_ONCE_TODAY, false);
    }


    private void updateNearestHourTime() {
        nearestHour = DateAndTimeManip.getUpcomingHourTime();
        MySharedPreferences.storeLongIntValue(context, SPNames.SP_NEAREST_TIME,
                SPNames.SP_KEY_NEAREST_HOUR, nearestHour);
    }


    private void updateNearestDayTime() {
        nearestDay = DateAndTimeManip.getUpcomingDayTime();
        MySharedPreferences.storeLongIntValue(context, SPNames.SP_NEAREST_TIME,
                SPNames.SP_KEY_NEAREST_DAY, nearestDay);
    }


    public static void hourlyDBReset() {
        Runnable hourlyUpdate = new Runnable() {
            @Override
            public void run() {
                mDBViewUsageStatsHandler.perHourDBFieldsReset();
            }
        };
        serviceHandler.postDelayed(hourlyUpdate, TWO_SECONDS);
    }


    public static void dailyDBReset() {
        Runnable dailyUpdate = new Runnable() {
            @Override
            public void run() {
                mDBViewUsageStatsHandler.perDayDBFieldsReset();
            }
        };
        serviceHandler.postDelayed(dailyUpdate, TWO_SECONDS);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        // code cleanup
        serviceHandler.removeCallbacks(taskExecuteEveryTwoSeconds);
        updateUsageStatsThread.quit();
        mDBViewUsageStatsHandler.close();

        // send intent to restart this service
        Intent broadcastIntent = new Intent("RelaunchUsageService");
        sendBroadcast(broadcastIntent);
    }


    public String serviceName() {
        return this.getClass().getName();
    }

}
