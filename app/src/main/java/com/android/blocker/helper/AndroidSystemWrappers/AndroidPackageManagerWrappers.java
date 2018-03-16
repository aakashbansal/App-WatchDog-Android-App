package com.android.blocker.helper.AndroidSystemWrappers;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.android.blocker.database.dbAddUsageLimit.DBAddUsageLimitHandler;
import com.android.blocker.helper.CustomComparators;
import com.android.blocker.model.AppAddUsageLimit;
import com.android.blocker.sharedPreferences.MySharedPreferences;
import com.android.blocker.sharedPreferences.SPNames;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AndroidPackageManagerWrappers {

    public static final String MY_APP_NAME = "App WatchDog";



    public static List<AppAddUsageLimit> getAllInstalledAppsExceptRestrictedAppsAndMyApp(Context context) {

        Collection<AppAddUsageLimit> installedAppsList = AndroidPackageManagerWrappers.getAllInstalledAppsExceptMyApp(context);
        Collection<AppAddUsageLimit> restrictedAppsList = DBAddUsageLimitHandler.getAppsWithAnyLimit();

        installedAppsList.removeAll(restrictedAppsList);

        List<AppAddUsageLimit> finalInstalledAppsList = new ArrayList<>(installedAppsList);
        Collections.sort(finalInstalledAppsList, new CustomComparators.alphabetOrderAppAddUsageLimit());

        return finalInstalledAppsList;
    }



    public static List<AppAddUsageLimit> getAllInstalledAppsExceptMyApp(Context context) {

        List<AppAddUsageLimit> userInstalledAppAddUsageLimits = new ArrayList<AppAddUsageLimit>();
        List<PackageInfo> pack = context.getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < pack.size(); i++) {
            PackageInfo currentPackage = pack.get(i);

            if ((AndroidPackageManagerWrappers.isSystemPackage(currentPackage) == false)) {

                String appName = currentPackage.applicationInfo.loadLabel(context.getPackageManager()).toString();
                String packageName = currentPackage.packageName;

                MySharedPreferences.storeStringValue(context, SPNames.SP_USER_INSTALLED_APPS,
                        packageName, appName);

                Drawable icon = currentPackage.applicationInfo.loadIcon(context.getPackageManager());

                if (!appName.equals(MY_APP_NAME)) {
                    userInstalledAppAddUsageLimits.add(new AppAddUsageLimit(appName, packageName, icon));
                }
            }
        }

        return userInstalledAppAddUsageLimits;
    }





    public static void generateAndStoreAppNamePackageNamePair(Context context) {

        List<PackageInfo> pack = context.getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < pack.size(); i++) {
            PackageInfo currentPackage = pack.get(i);

            if ((AndroidPackageManagerWrappers.isSystemPackage(currentPackage) == false)) {

                String appName = currentPackage.applicationInfo.loadLabel(context.getPackageManager()).toString();
                String packageName = currentPackage.packageName;

                MySharedPreferences.storeStringValue(context, SPNames.SP_USER_INSTALLED_APPS,
                        packageName, appName);
            }
        }
    }




    public static boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }

    public static boolean isSystemApp(Context context, String packageName) {

        PackageInfo targetPkgInfo;
        try {
            PackageManager mPackageManager = (PackageManager) context.getPackageManager();
            targetPkgInfo = mPackageManager.getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return ((targetPkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }


    public static Map<String, Drawable> getAppIcons(Context context) {

        Map<String, Drawable> appIconsMap = new HashMap<String, Drawable>();

        //package manager (contains app icons)
        List<PackageInfo> pack = context.getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < pack.size(); i++) {
            PackageInfo currentPackage = pack.get(i);

            //get app icon only if given package is not a system package
            if ((AndroidPackageManagerWrappers.isSystemPackage(currentPackage) == false)) {

                String appName = currentPackage.applicationInfo.loadLabel(context.getPackageManager()).toString();
                Drawable icon = currentPackage.applicationInfo.loadIcon(context.getPackageManager());
                appIconsMap.put(appName, icon);
            }
        }

        return appIconsMap;
    }

}
