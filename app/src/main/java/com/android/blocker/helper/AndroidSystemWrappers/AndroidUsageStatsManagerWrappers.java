package com.android.blocker.helper.AndroidSystemWrappers;


import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.ArraySet;

import com.android.blocker.R;
import com.android.blocker.helper.CheckUsageStatsPermission;
import com.android.blocker.helper.DateAndTimeManip;
import com.android.blocker.helper.CheckServiceRunning;
import com.android.blocker.model.AppViewUsageStats;
import com.android.blocker.sharedPreferences.MySharedPreferences;
import com.android.blocker.sharedPreferences.SPNames;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AndroidUsageStatsManagerWrappers {

    public static final String NO_APP_FOUND = "NoAppFound";


    public static String getForegroundApp(final Context context) {

        // check to see if the app has Usage Stats Permission
        if (!CheckUsageStatsPermission.hasUsageStatsPermission(context))
            return null;

        String foregroundApp = null;

        //get UsageStatsManager instance
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Service.USAGE_STATS_SERVICE);

        long time = System.currentTimeMillis();
        long oneHour = 3600 * 1000;

        // get list of apps accessed within the last hour
        UsageEvents usageEvents = mUsageStatsManager.queryEvents(time - oneHour, time);
        UsageEvents.Event event = new UsageEvents.Event();

        // check to see which is the current app in the foreground
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event);
            if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                foregroundApp = event.getPackageName();
            }
        }

        return foregroundApp;
    }




    public static List<AppViewUsageStats> getRecentlyUsedApps(Context context) {

        List<AppViewUsageStats> allAppsUsageInfo = new ArrayList<AppViewUsageStats>();

        // check to see if the app has Usage Stats Permission
        if (!CheckUsageStatsPermission.hasUsageStatsPermission(context)) {
            return allAppsUsageInfo;
        }


        //get Usage Stats Manager Instance
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context
                .getSystemService(Context.USAGE_STATS_SERVICE);

        long twoDaysBeforeTime = DateAndTimeManip.getTimeBeforeTwoDays();

        // get list of apps used within last two days
        List<UsageStats> queryUsageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,
                twoDaysBeforeTime, System.currentTimeMillis());

        UsageStats mUsageStats;

        Set<String> uniqueApps = new ArraySet<String>();

        // list of recently used apps returned by the usage stats manager
        // has duplicate app names.
        // So, the below code snippet is used to get the list of unique apps
        for (int i = 0; i < queryUsageStats.size(); i++) {

            mUsageStats = queryUsageStats.get(i);


            // get app name from the package name of a given app
            // retrieved through locally stored app_name-package_name mapping
            String packageName = mUsageStats.getPackageName();
            String appName = MySharedPreferences.getStringValue(context, SPNames.SP_USER_INSTALLED_APPS,
                    packageName, NO_APP_FOUND);

            // if the condition is evaluated to true, that means a unique
            // app has just been found. In this case add it to the list of
            // unique apps
            if (appName != NO_APP_FOUND && !uniqueApps.contains(appName)) {
                uniqueApps.add(appName);
                Drawable appIcon;

                //assign Icon to the app
                try {
                    appIcon = context.getPackageManager().getApplicationIcon(packageName);
                } catch (PackageManager.NameNotFoundException e) {
                    appIcon = context.getDrawable(R.mipmap.ic_launcher);
                }

                //finally combine app name, package name and app icon into single entity
                allAppsUsageInfo.add(new AppViewUsageStats(packageName, appName, appIcon));
            }
        }

        return allAppsUsageInfo;
    }


}


