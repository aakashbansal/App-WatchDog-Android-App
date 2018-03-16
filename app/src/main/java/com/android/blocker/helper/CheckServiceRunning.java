package com.android.blocker.helper;

import android.app.ActivityManager;
import android.content.Context;

public class CheckServiceRunning {

    public static boolean isGivenServiceRunning(Context context, String serviceName) {
        // get list of all running services
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (service.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        //service not found in running services list
        return false;
    }


}
