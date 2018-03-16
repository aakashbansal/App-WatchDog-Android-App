package com.android.blocker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.blocker.ToastController.ToastDisplay;
import com.android.blocker.service.AppLaunchDetectService;
import com.android.blocker.service.UsageStatsUpdateService;
import com.android.blocker.sharedPreferences.MySharedPreferences;
import com.android.blocker.sharedPreferences.SPNames;

import java.util.Calendar;

public class DeviceTurnedOnReceiver extends BroadcastReceiver {

    private final long ONE_HOUR = 60 * 60 * 1000;

    public DeviceTurnedOnReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //start the app services as soon as the device turns on
        Intent appLaunchDetectServiceIntent = new Intent(context, AppLaunchDetectService.class);
        context.startService(appLaunchDetectServiceIntent);

        Intent usageStatsUpdateServiceIntent = new Intent(context, UsageStatsUpdateService.class);
        context.startService(usageStatsUpdateServiceIntent);

        //get the locally stored time at which device was turned off
        long deviceTurnOffTime = MySharedPreferences.getLongIntValue(context, SPNames.SP_DEVICE_TURNED_OFF,
                SPNames.SP_KEY_DEVICE_TURNED_OFF_TIME, 0);

        //current time - time at which device is turned on
        Calendar onTime = Calendar.getInstance();

        // time at which device was turned off
        Calendar offTime = Calendar.getInstance();
        offTime.setTimeInMillis(deviceTurnOffTime);

        int offHour = offTime.get(Calendar.HOUR_OF_DAY);
        int onHour = onTime.get(Calendar.HOUR_OF_DAY);

        long timeDifference = System.currentTimeMillis() - deviceTurnOffTime;

        // update the usage stats for current hour only
        // if the device was off at the time at which hourly
        // update was needed to be performed
        if (onHour != offHour || timeDifference > ONE_HOUR) {
            UsageStatsUpdateService.hourlyDBReset();
        }

        int onDay = onTime.get(Calendar.DAY_OF_YEAR);
        int offDay = offTime.get(Calendar.DAY_OF_YEAR);

        // update the usage stats for current day only
        // if the device was off at the time at which daily
        // update was needed to be performed
        if (onDay > offDay) {
            UsageStatsUpdateService.dailyDBReset();
        }


    }
}
