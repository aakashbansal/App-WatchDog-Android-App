package com.android.blocker.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.NotificationCompat;

import com.android.blocker.database.dbViewUsageStats.DBViewUsageStatsHandler;
import com.android.blocker.R;
import com.android.blocker.sharedPreferences.MySharedPreferences;
import com.android.blocker.sharedPreferences.SPNames;

import java.util.List;

public class NotificationAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // checks to see if the user has turned on notifications
        boolean shouldNotify = MySharedPreferences.getBooleanValue(context, SPNames.SP_NOTIFICATION,
                SPNames.SP_KEY_NOTIFICATION_SHOULD_NOTIFY, false);

        //display the notification if turned on by the user
        if (shouldNotify) {
            displayNotification(context);
        }
    }


    private void displayNotification(Context context) {

        //get most used apps data
        List<String> data = DBViewUsageStatsHandler.getMostUsedApps();

        NotificationCompat.Builder mBuilder;

        //building notification text
        if (data.get(0).equals(data.get(2))) {
            mBuilder = new NotificationCompat.Builder(context).setContentTitle("Today's Most Used Apps !")
                    .setContentText(data.get(0)).setSmallIcon(R.drawable.notification_icon);
        } else {
            mBuilder = new NotificationCompat.Builder(context).setContentTitle("Today's Most Used Apps !")
                    .setContentText(data.get(0) + "  and " + data.get(2)).setSmallIcon(R.drawable.notification_icon);
        }

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // building notification text for when its expanded
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Today's Most Used Apps Details !");
        inboxStyle.addLine(data.get(0) + " ( Most Launched ) : " + data.get(1) + " Launches ");
        inboxStyle.addLine(data.get(2) + " ( Most Used ) : " + data.get(3));

        mBuilder.setStyle(inboxStyle);

        mNotificationManager.notify(1, mBuilder.build());
    }
}
