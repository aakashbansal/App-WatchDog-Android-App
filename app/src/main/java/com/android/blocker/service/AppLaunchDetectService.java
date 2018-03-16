package com.android.blocker.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.util.Pair;

import com.android.blocker.ToastController.ToastDisplay;
import com.android.blocker.ToastController.ToastMessages;
import com.android.blocker.helper.AndroidSystemWrappers.AndroidUsageStatsManagerWrappers;
import com.android.blocker.helper.AppAccessAllowed;
import com.android.blocker.helper.DateAndTimeManip;
import com.android.blocker.helper.SpecialIntents;
import com.android.blocker.model.AppAddUsageLimit;
import com.android.blocker.model.AppViewUsageStats;
import com.android.blocker.database.dbAddUsageLimit.DBAddUsageLimitHandler;
import com.android.blocker.database.dbViewUsageStats.DBViewUsageStatsHandler;
import com.android.blocker.sharedPreferences.MySharedPreferences;
import com.android.blocker.sharedPreferences.SPNames;

public class AppLaunchDetectService extends Service {

    private HandlerThread appLaunchDetectThread;
    private Looper serviceLooper;
    private Handler serviceHandler;
    private DBAddUsageLimitHandler mDbAddUsageLimitHandler;
    private Runnable taskExecuteEveryTwoSeconds;

    private AppAddUsageLimit appRestrictionInfo;
    private AppViewUsageStats appViewUsageStats;

    private static final String APP_LAUNCH_DETECT_THREAD_NAME = "AppLaunchDetectThread";
    private static final long TWO_SECONDS = 2000;

    public AppLaunchDetectService() {
    }


    @Override
    public void onCreate() {

        appLaunchDetectThread = new HandlerThread(APP_LAUNCH_DETECT_THREAD_NAME,
                Process.THREAD_PRIORITY_BACKGROUND);
        appLaunchDetectThread.start();

        serviceLooper = appLaunchDetectThread.getLooper();
        serviceHandler = new Handler(serviceLooper);
        mDbAddUsageLimitHandler = new DBAddUsageLimitHandler(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        taskExecuteEveryTwoSeconds = new Runnable() {
            @Override
            public void run() {

                // add this task to the list of executable tasks after two seconds.
                // this happens each time this task is executed
                serviceHandler.postDelayed(this, TWO_SECONDS);

                String currentAppInForeground = AndroidUsageStatsManagerWrappers.getForegroundApp(getApplicationContext());

                boolean isAppWatchDogEnabled = MySharedPreferences.getBooleanValue(getApplicationContext(),
                        SPNames.SP_APP_DISABLED, SPNames.SP_KEY_IS_APP_CURRENTLY_ENABLED, true);

                if (isAppWatchDogEnabled) {

                    // check if restriction is set on given app
                    if (mDbAddUsageLimitHandler.isRestrictionSet(currentAppInForeground)) {

                        appRestrictionInfo = mDbAddUsageLimitHandler.getAppData();
                        appViewUsageStats = DBViewUsageStatsHandler.getAppUsageData(currentAppInForeground);

                        Pair<Boolean, String> appAccessAllowedNowStatus = AppAccessAllowed.getStatusAppAccessAllowedNow(getApplicationContext(),
                                appViewUsageStats, appRestrictionInfo);

                        boolean isAppAccessAllowedNow = appAccessAllowedNowStatus.first;
                        String toastMsgOnAppExit = appAccessAllowedNowStatus.second;

                        // check if given app should be closed
                        if (isAppAccessAllowedNow == false) {
                            SpecialIntents.showHomeScreen(getApplicationContext());
                            ToastDisplay.makeLongDurationToast(getApplicationContext(), toastMsgOnAppExit);
                        }
                    }
                }else{
                    long currentTime = DateAndTimeManip.getCurrentTime();
                    long appDisableTime=MySharedPreferences.getLongIntValue(getApplicationContext(),
                            SPNames.SP_APP_DISABLED, SPNames.SP_KEY_APP_ENABLE_TIME,0);

                    if(currentTime>=appDisableTime){
                        // set app-enabled-currently option to true
                        MySharedPreferences.storeBooleanValue(getApplicationContext(),
                                SPNames.SP_APP_DISABLED, SPNames.SP_KEY_IS_APP_CURRENTLY_ENABLED, true);

                        // set app-enabled-time
                        MySharedPreferences.storeLongIntValue(getApplicationContext(),
                                SPNames.SP_APP_DISABLED, SPNames.SP_KEY_APP_ENABLE_TIME, 0);
                    }
                }
            }
        };

        serviceHandler.post(taskExecuteEveryTwoSeconds);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        serviceHandler.removeCallbacks(taskExecuteEveryTwoSeconds);
        appLaunchDetectThread.quit();
        mDbAddUsageLimitHandler.close();

        // send intent to restart this service
        Intent broadcastIntent = new Intent("RelaunchService");
        sendBroadcast(broadcastIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public String serviceName() {
        return this.getClass().getName();
    }

}
