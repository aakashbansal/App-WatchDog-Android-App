package com.android.blocker.helper;

import android.content.Context;
import android.util.Pair;

import com.android.blocker.ToastController.ToastDisplay;
import com.android.blocker.ToastController.ToastMessages;
import com.android.blocker.model.AppAddUsageLimit;
import com.android.blocker.model.AppViewUsageStats;
import com.android.blocker.sharedPreferences.MySharedPreferences;
import com.android.blocker.sharedPreferences.SPNames;

public class AppAccessAllowed {


    public static final String DEFAULT_APP = "DefaultApp";


    public static boolean canAppUsageRestrictionBeRemovedNow(Context context, AppViewUsageStats appViewUsageStats, AppAddUsageLimit restrictionInfo) {

        Pair<Boolean, String> appAccessAllowedNowStatus = AppAccessAllowed.getStatusAppAccessAllowedNow(context, appViewUsageStats, restrictionInfo);
        boolean isAppAccessAllowedNow = appAccessAllowedNowStatus.first;

        // If it is currently allowed to access an app, it is safe to delete the usage restriction on app.
        // On the contrary, if it is currently prohibited to use an app because the usage limit has exceeded on the given
        // app, it is not safe to remove usage restriction on app because removing that restriction will provide access to the
        // app to user and defeat the purpose of the app!!
        return isAppAccessAllowedNow;
    }



    public static Pair<Boolean, String> getStatusAppAccessAllowedNow(Context context, AppViewUsageStats appViewUsageStats,
                                                                      AppAddUsageLimit restrictionInfo) {

        String appName = MySharedPreferences.getStringValue(context, SPNames.SP_USER_INSTALLED_APPS,
                appViewUsageStats.getPackageName(), DEFAULT_APP);

        String toastMsg;

        // if launch restriction set
        if (restrictionInfo.getIsLaunchRestrictionSet()) {

            if (appViewUsageStats.getLaunchCountThisHour() >= restrictionInfo.getLaunchAllowedPerHour()) {
                toastMsg = appName + ToastMessages.HOURLY_LAUNCH_LIMIT;
                return new Pair<>(false, toastMsg);
            }

            if (appViewUsageStats.getLaunchCountThisDay() >= restrictionInfo.getLaunchAllowedPerDay()) {
                toastMsg = appName + ToastMessages.DAILY_LAUNCH_LIMIT;
                return new Pair<>(false, toastMsg);
            }
        }

        //if usage time restriction set
        if (restrictionInfo.getIsUsageTimeRestrictionSet()) {

            if (appViewUsageStats.getTotalTimeUsedThisHour() >= restrictionInfo.getTimeAllowedPerHour()) {
                toastMsg = appName + ToastMessages.HOURLY_TIME_LIMIT;
                return new Pair<>(false, toastMsg);
            }

            if (appViewUsageStats.getTotalTimeUsedThisDay() >= restrictionInfo.getTimeAllowedPerDay()) {
                toastMsg = appName + ToastMessages.DAILY_TIME_LIMIT;
                ToastDisplay.makeLongDurationToast(context, toastMsg);
                return new Pair<>(false, toastMsg);
            }
        }

        // if specific time restriction set
        if (restrictionInfo.getIsSpecifiTimeRestrictionSet()) {

            String Time = restrictionInfo.getSpecificTimeBegin();

            // because specific times are stored as a dash('-') separated string
            String[] beginTime = Time.split("-");

            Time = restrictionInfo.getSpecificTimeEnd();
            String[] endTime = Time.split("-");

            // iterate through all specific time slots to check if any
            // of the time slot covers the current time
            for (int i = 0; i < beginTime.length; i++) {

                // start time info
                String[] hourMinBegin = beginTime[i].split(":");
                android.icu.util.Calendar initial = android.icu.util.Calendar.getInstance();
                int initialHour = Integer.parseInt(hourMinBegin[0]);
                int initialMinute = Integer.parseInt(hourMinBegin[1]);
                initial.set(android.icu.util.Calendar.HOUR_OF_DAY, initialHour);
                initial.set(android.icu.util.Calendar.MINUTE, initialMinute);

                // end time info
                String[] hourMinEnd = endTime[i].split(":");
                android.icu.util.Calendar end = android.icu.util.Calendar.getInstance();
                int endHour = Integer.parseInt(hourMinEnd[0]);
                int endMinute = Integer.parseInt(hourMinEnd[1]);
                end.set(android.icu.util.Calendar.HOUR_OF_DAY, endHour);
                end.set(android.icu.util.Calendar.MINUTE, endMinute);

                android.icu.util.Calendar now = android.icu.util.Calendar.getInstance();

                long beginTimeMillis = initial.getTimeInMillis();
                long currentTimeMillis = now.getTimeInMillis();
                long endTimeMillis = end.getTimeInMillis();

                toastMsg = appName + ToastMessages.SPECIFIC_TIME_LIMIT;

                if (initialHour == endHour && initialMinute == endMinute) {
                    return new Pair<>(false, toastMsg);
                }

                if ((initialHour > endHour) || ((initialHour == endHour) && (initialMinute > endMinute))) {
                    if (!(endTimeMillis <= currentTimeMillis && currentTimeMillis <= beginTimeMillis)) {
                        return new Pair<>(false, toastMsg);
                    }
                }

                if (beginTimeMillis <= currentTimeMillis && currentTimeMillis <= endTimeMillis) {
                    return new Pair<>(false, toastMsg);
                }
            }

        }
        return new Pair<>(true, "");
    }

}
