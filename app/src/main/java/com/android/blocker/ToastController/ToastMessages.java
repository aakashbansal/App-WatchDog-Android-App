package com.android.blocker.ToastController;

public class ToastMessages {


    // toast messages displayed when app usage limit is exceeded
    public static final String HOURLY_LAUNCH_LIMIT = "'s Hourly Launch Limit Reached !";
    public static final String DAILY_LAUNCH_LIMIT = "'s Daily Launch Limit Reached !";
    public static final String HOURLY_TIME_LIMIT = "'s Hourly Time Limit Reached !";
    public static final String DAILY_TIME_LIMIT = "'s Daily Time Limit Reached !";
    public static final String SPECIFIC_TIME_LIMIT = "'s Access Not Allowed During This Time !";


    // toast messages displayed when app disabled option is selected
    public static final String APP_WAS_ALREADY_DISABLED_ONCE = "App WatchDog had already been disabled once today. ";
    public static final String APP_DISABLED_SUCCESS = "App WatchDog is disabled for 10 minutes. Enjoy !";
    public static final String AUTOSTART_PERMISSION_NOT_REQUIRED = "The AUTOSTART permission is not required on this device.";


    // displayed when given time slot limit is not set due to leaving time
    // picker fragment in the middle
    public static final String TIME_LIMIT_NOT_SET = "Time Limit Not Set";


    // displayed when usage restriction is removed from the selected app
    public static final String USAGE_RESTRICTION_REMOVED = "Usage Restriction successfully removed from ";


    // toast messages displayed while setting restriction on a given app
    // and the user is to be prompted while validating fields
    public static final String SELECT_AN_APP = "Please select an app";
    public static final String SELECT_AT_LEAST_ONE = "Please make at least one selection";
    public static final String FILL_AT_LEAST_ONE_ENTRY = "Please fill at least one entry";
    public static final String LAUNCH_PER_DAY_MORE_THAN_LAUNCH_PER_HOUR = "Launches allowed per day must be more than launches allowed per hour!";
    public static final String HOUR_HAS_60_MIN = "An hour has only 60 minutes!"; // being sarcastic,
    public static final String DAY_HAS_24_HOURS = "A day has only 24 hours!";    // you know ;)
    public static final String TIME_PER_DAY_MORE_THAN_TIME_PER_HOUR = "Time allowed per day must be more than time allowed per hour";
    public static final String SELECT_AT_LEAST_TIME_SLOT = "Please set at least one Time Slot";
    public static final String LIMIT_SET = "Limit has been successfully set on ";
}
