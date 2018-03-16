package com.android.blocker.sharedPreferences;

public class SPNames {


    // store info about device turned off time
    public static final String SP_DEVICE_TURNED_OFF = "DeviceOff";
    public static final String SP_KEY_DEVICE_TURNED_OFF_TIME = "DeviceOffTime";


    //store info about whether to display notification or not
    public static final String SP_NOTIFICATION = "Notification";
    public static final String SP_KEY_NOTIFICATION_SHOULD_NOTIFY = "ShouldNotify";

    // store info about every unique user installed apps
    //( app name - package name mapping )
    public static final String SP_USER_INSTALLED_APPS = "UserInstalledApps";


    // store info whether the app is disabled by the user or not
    // ( the 10 min disable feature implemented by the app)
    public static final String SP_APP_DISABLED = "AppDisabled";
    public static final String SP_KEY_APP_ENABLE_TIME = "AppEnableTime";
    public static final String SP_KEY_IS_APP_CURRENTLY_ENABLED = "IsAppCurrentlyEnabled";
    public static final String SP_KEY_WAS_APP_DISABLED_ONCE_TODAY = "WasAppDisabledOnceToday";

    // store info about nearest hour and nearest day time in integer format
    // used for deciding the time at which to update the database hourly or daily
    public static final String SP_NEAREST_TIME = "NearestTime";
    public static final String SP_KEY_NEAREST_HOUR = "NearestHour";
    public static final String SP_KEY_NEAREST_DAY = "NearestDay";


    // store info about whether the app is opened by the user for
    // for the first time or not - to display help dialog box
    public static final String SP_APP_FIRST_TIME_USED = "AppUsedFirstTime";
    public static final String SP_KEY_APP_FIRST_LAUNCH = "AppLaunchedFirstTime";


}
