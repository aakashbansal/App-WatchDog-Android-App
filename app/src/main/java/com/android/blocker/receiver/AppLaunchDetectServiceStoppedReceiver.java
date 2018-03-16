package com.android.blocker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.blocker.helper.CheckServiceRunning;
import com.android.blocker.service.AppLaunchDetectService;

public class AppLaunchDetectServiceStoppedReceiver extends BroadcastReceiver {

    public AppLaunchDetectServiceStoppedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // restart the given service if stopped
        if (!isAppLaunchDetectServiceRunning(context)) {
            Intent myService = new Intent(context, AppLaunchDetectService.class);
            context.startService(myService);
        }
    }

    // checks to see if the given service is running or not
    public boolean isAppLaunchDetectServiceRunning(Context context) {

        //get service name
        AppLaunchDetectService mAppLaunchDetectServiceService = new AppLaunchDetectService();
        String serviceName = mAppLaunchDetectServiceService.serviceName();

        //check if running
        boolean result = CheckServiceRunning.isGivenServiceRunning(context, serviceName);
        return result;
    }
}
