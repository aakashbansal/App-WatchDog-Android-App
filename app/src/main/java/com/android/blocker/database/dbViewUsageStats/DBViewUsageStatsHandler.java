package com.android.blocker.database.dbViewUsageStats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.blocker.helper.AndroidSystemWrappers.AndroidPackageManagerWrappers;
import com.android.blocker.helper.DateAndTimeManip;
import com.android.blocker.model.AppViewUsageStats;
import com.android.blocker.sharedPreferences.MySharedPreferences;
import com.android.blocker.sharedPreferences.SPNames;

import java.util.ArrayList;
import java.util.List;


public class DBViewUsageStatsHandler {

    private static SQLiteDatabase Database;
    private static DBViewUsageStatsSchema mDBViewUsageStatsSchema;
    private static Context mContext;

    public DBViewUsageStatsHandler(Context context) {
        mContext = context;
        mDBViewUsageStatsSchema = new DBViewUsageStatsSchema(context);
        Database = mDBViewUsageStatsSchema.getWritableDatabase();
    }


    public void close() {
        Database.close();
    }

    public void updateUsageStats(String prevApp, long currentTime, String lastUsed) {

        if (!Database.isOpen()) {
            return;
        }

        boolean found = false;
        Cursor mCursor = getAllAppsUsageStats();
        while (!mCursor.isAfterLast()) {
            String currentRowPackage = mCursor.getString(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_PACKAGENAME));
            if (currentRowPackage.equals(prevApp)) {
                found = true;
                break;
            }
            mCursor.moveToNext();
        }

        long timeCompletedThisLaunch = System.currentTimeMillis() - currentTime;

        if (!found) {

            // record for the given app does not exist
            // create first entry
            ContentValues values = new ContentValues();
            values.put(mDBViewUsageStatsSchema.COLUMN_PACKAGENAME, prevApp);
            values.put(mDBViewUsageStatsSchema.COLUMN_LAUNCH_DONE_PER_HOUR, 1);
            values.put(mDBViewUsageStatsSchema.COLUMN_LAUNCH_DONE_PER_DAY, 1);
            values.put(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_HOUR, timeCompletedThisLaunch);
            values.put(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_DAY, timeCompletedThisLaunch);
            values.put(mDBViewUsageStatsSchema.COLUMN_LAST_USED_TIME, lastUsed);

            Database.insert(mDBViewUsageStatsSchema.TABLE_USAGE, null, values);
        } else {

            // record for the given app already exists
            // just update the record
            ContentValues values = new ContentValues();

            int launchCountPerHour = mCursor.getInt(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_LAUNCH_DONE_PER_HOUR));
            ++launchCountPerHour;
            int launchCountPerDay = mCursor.getInt(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_LAUNCH_DONE_PER_DAY));
            ++launchCountPerDay;
            values.put(mDBViewUsageStatsSchema.COLUMN_LAUNCH_DONE_PER_HOUR, launchCountPerHour);
            values.put(mDBViewUsageStatsSchema.COLUMN_LAUNCH_DONE_PER_DAY, launchCountPerDay);

            int timeCompletedThisHour = mCursor.getInt(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_HOUR));
            timeCompletedThisHour += timeCompletedThisLaunch;
            int timeCompletedThisDay = mCursor.getInt(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_DAY));
            timeCompletedThisDay += timeCompletedThisLaunch;
            values.put(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_HOUR, timeCompletedThisHour);
            values.put(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_DAY, timeCompletedThisDay);

            values.put(mDBViewUsageStatsSchema.COLUMN_LAST_USED_TIME, lastUsed);

            String selection = mDBViewUsageStatsSchema.COLUMN_PACKAGENAME + " LIKE ?";
            String[] selectionArgs = {prevApp};
            int count = Database.update(
                    mDBViewUsageStatsSchema.TABLE_USAGE,
                    values,
                    selection,
                    selectionArgs);
        }
    }


    // update only usage time stats of given app
    public void updateOnlyUsageTime(String prevApp, long timeCompletedThisLaunch) {

        if (!Database.isOpen()) {
            return;
        }

        boolean found = false;
        Cursor mCursor = getAllAppsUsageStats();
        while (!mCursor.isAfterLast()) {
            String currentRowPackage = mCursor.getString(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_PACKAGENAME));
            if (currentRowPackage.equals(prevApp)) {
                found = true;
                break;
            }
            mCursor.moveToNext();
        }

        if (!found) {

            // record for the given app does not exist
            // create first entry
            ContentValues values = new ContentValues();
            values.put(mDBViewUsageStatsSchema.COLUMN_PACKAGENAME, prevApp);
            values.put(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_HOUR, timeCompletedThisLaunch);
            values.put(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_DAY, timeCompletedThisLaunch);

            Database.insert(mDBViewUsageStatsSchema.TABLE_USAGE, null, values);
        } else {

            // record for the given app already exists
            // just update the record
            ContentValues values = new ContentValues();

            int timeCompletedThisHour = mCursor.getInt(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_HOUR));
            timeCompletedThisHour += timeCompletedThisLaunch;
            int timeCompletedThisDay = mCursor.getInt(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_DAY));
            timeCompletedThisDay += timeCompletedThisLaunch;
            values.put(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_HOUR, timeCompletedThisHour);
            values.put(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_DAY, timeCompletedThisDay);

            String selection = mDBViewUsageStatsSchema.COLUMN_PACKAGENAME + " LIKE ?";
            String[] selectionArgs = {prevApp};
            int count = Database.update(
                    mDBViewUsageStatsSchema.TABLE_USAGE,
                    values,
                    selection,
                    selectionArgs);
        }
    }


    // get usage stats corresponding to given app
    public static AppViewUsageStats getAppUsageData(String appPackageName) {

        String currentPackage;
        boolean found = false;
        Cursor mCursor = getAllAppsUsageStats();
        while (!mCursor.isAfterLast()) {
            currentPackage = mCursor.getString(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_PACKAGENAME));
            if (currentPackage.equals(appPackageName)) {
                found = true;
                break;
            }
            mCursor.moveToNext();
        }

        AppViewUsageStats appViewUsageStats = new AppViewUsageStats();
        appViewUsageStats.setPackageName(appPackageName);

        if (found) {
            long launchCountThisHour = mCursor.getInt(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_LAUNCH_DONE_PER_HOUR));
            long launchCountThisDay = mCursor.getInt(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_LAUNCH_DONE_PER_DAY));
            String lastUsed = mCursor.getString(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_LAST_USED_TIME));
            long totalTimeUsedThisHour = mCursor.getInt(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_HOUR));
            long totalTimeUsedThisDay = mCursor.getInt(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_DAY));

            appViewUsageStats.addFields(launchCountThisHour, launchCountThisDay, lastUsed, totalTimeUsedThisHour, totalTimeUsedThisDay);
        } else {
            appViewUsageStats.addFields(0, 0, "NIL",
                    0, 0);
        }
        return appViewUsageStats;
    }


    // get most used apps data for displaying on notification
    public static List<String> getMostUsedApps() {

        String mostLaunchedApp = "", mostUsedApp = "", currentPackage;
        int curLaunchCount = 0, curUsageTime = 0;
        int maxLaunchCount = -1, maxUsageTime = -1;

        Cursor mCursor = getAllAppsUsageStats();
        while (!mCursor.isAfterLast()) {

            currentPackage = mCursor.getString(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_PACKAGENAME));
            curLaunchCount = mCursor.getInt(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_LAUNCH_DONE_PER_DAY));
            curUsageTime = mCursor.getInt(mCursor.getColumnIndex(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_DAY));

            if (!AndroidPackageManagerWrappers.isSystemApp(mContext, currentPackage)) {

                if (curLaunchCount > maxLaunchCount) {
                    maxLaunchCount = curLaunchCount;
                    mostLaunchedApp = currentPackage;
                }

                if (curUsageTime > maxUsageTime) {
                    maxUsageTime = curUsageTime;
                    mostUsedApp = currentPackage;
                }
            }
            mCursor.moveToNext();
        }

        List<String> data = new ArrayList<String>();

        //create notification data
        data.add(0, MySharedPreferences.getStringValue(mContext,
                SPNames.SP_USER_INSTALLED_APPS, mostLaunchedApp, mostUsedApp));
        data.add(1, Integer.toString(maxLaunchCount));
        data.add(2, MySharedPreferences.getStringValue(mContext,
                SPNames.SP_USER_INSTALLED_APPS, mostUsedApp, mostUsedApp));
        data.add(3, DateAndTimeManip.getTimeStringFromIntegerTime(maxUsageTime));

        return data;
    }


    // reset the db fields representing hourly usage stats
    public void perHourDBFieldsReset() {
        if (!Database.isOpen()) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(mDBViewUsageStatsSchema.COLUMN_LAUNCH_DONE_PER_HOUR, 0);
        values.put(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_HOUR, 0);
        Database.update(mDBViewUsageStatsSchema.TABLE_USAGE, values, null, null);
    }

    // reset the db fields representing daily usage stats
    public void perDayDBFieldsReset() {
        if (!Database.isOpen()) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(mDBViewUsageStatsSchema.COLUMN_LAUNCH_DONE_PER_DAY, 0);
        values.put(mDBViewUsageStatsSchema.COLUMN_TIME_COMPLETED_PER_DAY, 0);
        Database.update(mDBViewUsageStatsSchema.TABLE_USAGE, values, null, null);
    }

    private static Cursor getAllAppsUsageStats() {
        String query = "SELECT * FROM " + mDBViewUsageStatsSchema.TABLE_USAGE + " WHERE 1";
        Cursor mCursor = Database.rawQuery(query, null);
        mCursor.moveToFirst();
        return mCursor;
    }

}
