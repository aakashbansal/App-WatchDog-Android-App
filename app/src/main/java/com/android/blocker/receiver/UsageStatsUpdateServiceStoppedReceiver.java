package com.android.blocker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.blocker.helper.CheckServiceRunning;
import com.android.blocker.service.UsageStatsUpdateService;

public class UsageStatsUpdateServiceStoppedReceiver extends BroadcastReceiver {


    public UsageStatsUpdateServiceStoppedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // restart the given service if stopped
        if (!isUsageServiceRunning(context)) {
            Intent myService = new Intent(context, UsageStatsUpdateService.class);
            context.startService(myService);
        }
    }

    // checks to see if the given service is running or not
    public boolean isUsageServiceRunning(Context context) {

        UsageStatsUpdateService mUsageStatsUpdateService = new UsageStatsUpdateService();
        String serviceName = mUsageStatsUpdateService.serviceName();

        //check if running
        boolean result = CheckServiceRunning.isGivenServiceRunning(context, serviceName);
        return result;
    }
}
