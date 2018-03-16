package com.android.blocker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.blocker.sharedPreferences.MySharedPreferences;
import com.android.blocker.sharedPreferences.SPNames;

import java.util.Calendar;


public class DeviceShutDownReceiver extends BroadcastReceiver {

    public DeviceShutDownReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();

        // this receiver is used to store the time at which the device
        // is turned off so that when the device is turned on again
        // after some time, the usage stats info of a particular
        // day or hour may be appropriately updated
        MySharedPreferences.storeLongIntValue(context, SPNames.SP_DEVICE_TURNED_OFF,
                SPNames.SP_KEY_DEVICE_TURNED_OFF_TIME, currentTime);
    }
}
