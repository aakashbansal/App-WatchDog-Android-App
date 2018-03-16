package com.android.blocker.DialogController;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Handler;

import com.android.blocker.ToastController.ToastDisplay;
import com.android.blocker.ToastController.ToastMessages;
import com.android.blocker.helper.DateAndTimeManip;
import com.android.blocker.helper.NavDrawerItemsIntentResolver;
import com.android.blocker.helper.SpecialIntents;
import com.android.blocker.model.AppAddUsageLimit;
import com.android.blocker.sharedPreferences.MySharedPreferences;
import com.android.blocker.sharedPreferences.SPNames;


public class PromptAlertUserDialogs {


    private final static long TEN_MINUTES = 10 * 60 * 1000;


    // prompt user for usage access required
    public static void showDialogUsageAccessRequired(final Context context) {

        AlertDialog usageAccessRequiredDialog;

        String title = "USAGE ACCESS REQUIRED !!";
        String text = "The App requires USAGE ACCESS to work. Please provide permission to continue using this app.";
        String OKButton = "GIVE USAGE ACCESS";
        String CancelButton = "NO THANKS";

        usageAccessRequiredDialog = new AlertDialog.Builder(context).create();
        usageAccessRequiredDialog.setTitle(title);
        usageAccessRequiredDialog.setMessage(text);
        usageAccessRequiredDialog.setCancelable(false);
        usageAccessRequiredDialog.setCanceledOnTouchOutside(false);

        usageAccessRequiredDialog.setButton(AlertDialog.BUTTON_POSITIVE, OKButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SpecialIntents.openUsageAccessSettingsPage(context);
                    }
                });

        usageAccessRequiredDialog.setButton(AlertDialog.BUTTON_NEGATIVE, CancelButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SpecialIntents.showHomeScreen(context);
                    }
                });


        usageAccessRequiredDialog.show();

    }


    // prompt user for showing help page
    public static void showDialogOpenHelpPage(final Context context) {

        AlertDialog openHelpPageDialog;

        String title = "Please Read Help Section !";
        String text = "You are requested to read the Help Section at least once " +
                "to get an idea about the working of App Watchdog as the App may be a bit complex to understand for " +
                "the First-Time User.";
        String OKButton = "SHOW HELP";
        String CancelButton = "NO THANKS";

        openHelpPageDialog = new AlertDialog.Builder(context).create();
        openHelpPageDialog.setTitle(title);
        openHelpPageDialog.setMessage(text);
        openHelpPageDialog.setCancelable(false);
        openHelpPageDialog.setCanceledOnTouchOutside(false);

        openHelpPageDialog.setButton(AlertDialog.BUTTON_POSITIVE, OKButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NavDrawerItemsIntentResolver.onHelpSelected(context);
                    }
                });

        openHelpPageDialog.setButton(AlertDialog.BUTTON_NEGATIVE, CancelButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });


        openHelpPageDialog.show();
    }


    // prompt user when app limit is not removed
    public static void showDialogAppLimitNotRemoved(final Context context, AppAddUsageLimit appAddUsageLimit) {

        AlertDialog appLimitNotRemovedDialog = new AlertDialog.Builder(context).create();

        String title = "Usage Restriction Not Removed";
        String text = "Usage Restriction on " + appAddUsageLimit.getAppName() +
                " can not be removed right now as you have reached your daily/hourly/time-specific limit" +
                " to access it. Removing restriction now will provide you access to " +
                appAddUsageLimit.getAppName() + ". Try again later !";
        String OKButton = "OK";

        appLimitNotRemovedDialog.setTitle(title);
        appLimitNotRemovedDialog.setMessage(text);
        appLimitNotRemovedDialog.setIcon(appAddUsageLimit.getIcon());
        appLimitNotRemovedDialog.setCancelable(false);
        appLimitNotRemovedDialog.setCanceledOnTouchOutside(false);

        appLimitNotRemovedDialog.setButton(AlertDialog.BUTTON_POSITIVE, OKButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        appLimitNotRemovedDialog.show();
    }


    public static void showDisableAppDialog(final Context context) {

        String title = " Disable App WatchDog ? ";
        String text = "Are you sure you want to disable the app ? You can only do it once per day.";

        AlertDialog confirmAppDisableDialog = new AlertDialog.Builder(context).create();
        confirmAppDisableDialog.setTitle(title);
        confirmAppDisableDialog.setMessage(text);
        confirmAppDisableDialog.setCanceledOnTouchOutside(false);


        // YES button
        confirmAppDisableDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // set app-disabled-once-a-day option to true
                        MySharedPreferences.storeBooleanValue(context,
                                SPNames.SP_APP_DISABLED, SPNames.SP_KEY_WAS_APP_DISABLED_ONCE_TODAY, true);

                        // set app-enabled-currently option to false
                        MySharedPreferences.storeBooleanValue(context,
                                SPNames.SP_APP_DISABLED, SPNames.SP_KEY_IS_APP_CURRENTLY_ENABLED, false);

                        // set app-enabled-time
                        MySharedPreferences.storeLongIntValue(context,
                                SPNames.SP_APP_DISABLED, SPNames.SP_KEY_APP_ENABLE_TIME, DateAndTimeManip.getTimeAfterTenMin());

                        ToastDisplay.makeLongDurationToast(context, ToastMessages.APP_DISABLED_SUCCESS);
                    }
                });

        // NO button
        confirmAppDisableDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        confirmAppDisableDialog.show();
    }

}
