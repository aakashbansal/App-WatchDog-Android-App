package com.android.blocker.model;

import android.graphics.drawable.Drawable;

public class AppViewUsageStats {

    //class members
    String packageName;
    String appName;
    Drawable icon;
    long launchCountThisHour;
    long launchCountThisDay;
    String lastUsed;
    long totalTimeUsedThisHour;
    long totalTimeUsedThisDay;

    //class constructors
    public AppViewUsageStats(String packageName, String appName, Drawable icon) {
        this.packageName = packageName;
        this.appName = appName;
        this.icon = icon;
    }

    public AppViewUsageStats() {

    }


    //class getters
    public String getPackageName() {
        return packageName;
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public long getLaunchCountThisHour() {
        return launchCountThisHour;
    }

    public long getLaunchCountThisDay() {
        return launchCountThisDay;
    }

    public String getLastUsed() {
        return lastUsed;
    }

    public long getTotalTimeUsedThisHour() {
        return totalTimeUsedThisHour;
    }

    public long getTotalTimeUsedThisDay() {
        return totalTimeUsedThisDay;
    }


    //class setters
    public void setPackageName(String packageName) {

        this.packageName = packageName;
    }


    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setLaunchCountThisHour(long launchCountThisHour) {
        this.launchCountThisHour = launchCountThisHour;
    }

    public void setLaunchCountThisDay(long launchCountThisDay) {
        this.launchCountThisDay = launchCountThisDay;
    }

    public void setLastUsed(String lastUsed) {
        this.lastUsed = lastUsed;
    }

    public void setTotalTimeUsedThisHour(long totalTimeUsedThisHour) {
        this.totalTimeUsedThisHour = totalTimeUsedThisHour;
    }

    public void setTotalTimeUsedThisDay(long totalTimeUsedThisDay) {
        this.totalTimeUsedThisDay = totalTimeUsedThisDay;
    }

    public void addFields(long launchCountThisHour, long launchCountThisDay, String lastUsed, long totalTimeUsedThisHour, long totalTimeUsedThisDay) {
        this.launchCountThisHour = launchCountThisHour;
        this.launchCountThisDay = launchCountThisDay;
        this.lastUsed = lastUsed;
        this.totalTimeUsedThisHour = totalTimeUsedThisHour;
        this.totalTimeUsedThisDay = totalTimeUsedThisDay;
    }

}
