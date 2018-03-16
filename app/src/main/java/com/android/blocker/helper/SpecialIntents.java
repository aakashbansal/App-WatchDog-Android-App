package com.android.blocker.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class SpecialIntents {

    public static void openUsageAccessSettingsPage(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static boolean showHomeScreen(Context context) {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startMain);
        return true;
    }

    public static void showAutoStartPermissionScreen(Context context) {

        String packageName = "com.miui.securitycenter";
        String className = "com.miui.permcenter.autostart.AutoStartManagementActivity";

        //this will open the screen where user can enable "AutoStart" permission for the app
        Intent autostartPermissionPage = new Intent();
        autostartPermissionPage.setComponent(new ComponentName(packageName, className));
        context.startActivity(autostartPermissionPage);

    }
}
