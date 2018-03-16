package com.android.blocker.helper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.android.blocker.activity.AboutActivity;
import com.android.blocker.activity.ViewAppUsageStatsActivity;
import com.android.blocker.activity.HelpActivity;
import com.android.blocker.activity.AddAppUsageLimitActivity;
import com.android.blocker.activity.SettingsActivity;

public class NavDrawerItemsIntentResolver {

    public static void onlimitAppsUsageSelected(Context context) {
        Intent intent = new Intent(context, AddAppUsageLimitActivity.class);
        context.startActivity(intent);
    }

    public static void onappsUsageStatsselected(Context context) {
        Intent intent = new Intent(context, ViewAppUsageStatsActivity.class);
        context.startActivity(intent);
    }

    public static void onShareSelected(Context context) {
        boolean isShareitInstalled = false;
        try {
            context.getPackageManager().getApplicationInfo("com.lenovo.anyshare.gps", 0);
            isShareitInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        if (isShareitInstalled) {
            Intent i = context.getPackageManager().getLaunchIntentForPackage("com.lenovo.anyshare.gps");
            context.startActivity(i);
        } else {
            Toast.makeText(context, " Shareit Not Installed ! ", Toast.LENGTH_LONG).show();
        }
    }

    public static void onSettingsselected(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    public static void onAboutSelected(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    public static void onHelpSelected(Context context) {
        Intent intent = new Intent(context, HelpActivity.class);
        context.startActivity(intent);
    }
}
