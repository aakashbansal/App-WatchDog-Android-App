package com.android.blocker.model;

import android.graphics.drawable.Drawable;

public class AppAddUsageLimit {

    //class members
    private String appName;
    private String packageName;
    private Drawable icon;
    private int launchAllowedPerHour, launchAllowedPerDay;
    private int launchCompleted;
    private int timeAllowedPerDay, timeAllowedPerHour;
    private int timeCompleted;
    private String specificTimeBegin;
    private String specificTimeEnd;
    private boolean isLaunchRestrictionSet;
    private boolean isUsageTimeRestrictionSet;
    private boolean isSpecifiTimeRestrictionSet;

    //class constructors
    public AppAddUsageLimit(String appName, String packageName, Drawable icon) {
        this.appName = appName;
        this.packageName = packageName;
        this.icon = icon;
    }


    public void setLaunchRestrictionParams(String appName, int launchAllowedPerHour, int launchAllowedPerDay, Drawable icon) {
        this.appName = appName;
        this.launchAllowedPerHour = launchAllowedPerHour;
        this.launchAllowedPerDay = launchAllowedPerDay;
        this.icon = icon;
    }

    public void setUsageTimeRestrictionParams(String appName, int timeAllowedPerHour, int timeAllowedPerDay, Drawable icon) {
        this.appName = appName;
        this.timeAllowedPerHour = timeAllowedPerHour;
        this.timeAllowedPerDay = timeAllowedPerDay;
        this.icon = icon;
    }

    public AppAddUsageLimit() {
    }

    // class getters
    public String getAppName() {
        return appName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public int getLaunchAllowedPerHour() {
        return launchAllowedPerHour;
    }

    public int getLaunchAllowedPerDay() {
        return launchAllowedPerDay;
    }

    public int getTimeAllowedPerDay() {
        return timeAllowedPerDay;
    }

    public int getTimeAllowedPerHour() {
        return timeAllowedPerHour;
    }

    public int getLaunchCompleted() {
        return launchCompleted;
    }

    public int getTimeCompleted() {
        return timeCompleted;
    }

    public String getSpecificTimeBegin() {
        return specificTimeBegin;
    }

    public String getSpecificTimeEnd() {
        return specificTimeEnd;
    }

    public boolean getIsLaunchRestrictionSet() {
        return isLaunchRestrictionSet;
    }

    public boolean getIsUsageTimeRestrictionSet() {
        return isUsageTimeRestrictionSet;
    }

    public boolean getIsSpecifiTimeRestrictionSet() {
        return isSpecifiTimeRestrictionSet;
    }


    // class setters
    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setSpecificTimeEnd(String specificTimeEnd) {
        this.specificTimeEnd = specificTimeEnd;
    }

    public void setIsLaunchRestrictionSet(boolean isLaunchRestrictionSet) {
        this.isLaunchRestrictionSet = isLaunchRestrictionSet;
    }

    public void setIsUsageTimeRestrictionSet(boolean isUsageTimeRestrictionSet) {
        this.isUsageTimeRestrictionSet = isUsageTimeRestrictionSet;
    }

    public void setIsSpecifiTimeRestrictionSet(boolean isSpecifiTimeRestrictionSet) {
        this.isSpecifiTimeRestrictionSet = isSpecifiTimeRestrictionSet;
    }

    public void setLaunchAllowedPerHour(int launchAllowedPerHour) {
        this.launchAllowedPerHour = launchAllowedPerHour;
    }

    public void setLaunchAllowedPerDay(int launchAllowedPerDay) {
        this.launchAllowedPerDay = launchAllowedPerDay;
    }

    public void setLaunchCompleted(int launchCompleted) {
        this.launchCompleted = launchCompleted;
    }

    public void setTimeAllowedPerDay(int timeAllowedPerDay) {
        this.timeAllowedPerDay = timeAllowedPerDay;
    }

    public void setTimeAllowedPerHour(int timeAllowedPerHour) {
        this.timeAllowedPerHour = timeAllowedPerHour;
    }

    public void setTimeCompleted(int timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public void setSpecificTimeBegin(String specificTimeBegin) {
        this.specificTimeBegin = specificTimeBegin;
    }

    public void setspecificTimeEnd(String specificTimeEnd) {
        this.specificTimeEnd = specificTimeEnd;
    }


    // custom function for checking the equality of given class object
    @Override
    public boolean equals(Object o) {
        AppAddUsageLimit appAddUsageLimit = (AppAddUsageLimit) o;
        if (this.getPackageName().equals(appAddUsageLimit.getPackageName())) {
            return true;
        }
        return false;
    }


}
