package com.android.blocker.DialogController;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.blocker.R;
import com.android.blocker.helper.DateAndTimeManip;
import com.android.blocker.model.AppViewUsageStats;

public class AppsUsageStatsInfoDialog {

    public static void showDialogAppUsageStats(Activity activity, AppViewUsageStats app) {

        TextView lastUsedTextView, lastUsedTimeTextView,
                thisHourUsageTextView, launchCountPerDayTextView,
                launchCountPerHourTextView, thisDayUsageTextView;

        AlertDialog.Builder appUsageStatsDialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fragment_view_usage_stats, null);
        appUsageStatsDialogBuilder.setView(dialogView);


        // referencing the views
        lastUsedTextView = (TextView) dialogView.findViewById(R.id.lastUsedTextView);
        lastUsedTimeTextView = (TextView) dialogView.findViewById(R.id.lastUsedTimeTextView);

        thisHourUsageTextView = (TextView) dialogView.findViewById(R.id.thisHourUsageTextView);
        thisDayUsageTextView = (TextView) dialogView.findViewById(R.id.thisDayUsageTextView);

        launchCountPerHourTextView = (TextView) dialogView.findViewById(R.id.launchCountPerHourTextView);
        launchCountPerDayTextView = (TextView) dialogView.findViewById(R.id.launchCountPerDayTextView);

        String Date = app.getLastUsed();

        if (!Date.equals("NIL")) {
            String[] DateParts = Date.split(" ");

            lastUsedTextView.setText(DateParts[0]);
            lastUsedTimeTextView.setText("At " + DateParts[1] + " " + DateParts[2]);

        } else {
            lastUsedTextView.setText("N/A");
        }

        // setting the views
        thisDayUsageTextView.setText(DateAndTimeManip.getTimeStringFromIntegerTime(app.getTotalTimeUsedThisDay()));
        thisHourUsageTextView.setText(DateAndTimeManip.getTimeStringFromIntegerTime(app.getTotalTimeUsedThisHour()));

        launchCountPerHourTextView.setText(getLaunchCountString(app.getLaunchCountThisHour()));
        launchCountPerDayTextView.setText(getLaunchCountString(app.getLaunchCountThisDay()));

        String title = app.getAppName() + " Usage Info ";

        appUsageStatsDialogBuilder.setTitle(title);
        appUsageStatsDialogBuilder.setIcon(app.getIcon());

        appUsageStatsDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        AlertDialog appUsageStatsDialog = appUsageStatsDialogBuilder.create();
        appUsageStatsDialog.show();

    }


    private static String getLaunchCountString(long launch) {
        if (launch == 0)
            return "NIL";
        if (launch == 1)
            return " 1 Time";
        return Long.toString(launch) + " Times ";
    }
}
