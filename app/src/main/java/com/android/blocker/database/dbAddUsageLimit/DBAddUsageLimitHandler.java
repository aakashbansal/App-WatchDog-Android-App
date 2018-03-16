package com.android.blocker.database.dbAddUsageLimit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

import com.android.blocker.helper.AndroidSystemWrappers.AndroidPackageManagerWrappers;
import com.android.blocker.helper.DBHelpers;
import com.android.blocker.helper.DataTypeManip;
import com.android.blocker.model.AppAddUsageLimit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DBAddUsageLimitHandler {

    //members declaration

    private static SQLiteDatabase Database;
    private static DBAddUsageLimitSchema mDBAddUsageLimitSchema;
    private static Context mContext;

    private static Map<String, Drawable> appIconsMap;
    private static AppAddUsageLimit appAddUsageLimit;

    //constructor
    public DBAddUsageLimitHandler(Context context) {
        mContext = context;
        mDBAddUsageLimitSchema = new DBAddUsageLimitSchema(context);
        Database = mDBAddUsageLimitSchema.getWritableDatabase();
    }

    public static AppAddUsageLimit getAppData() {
        return appAddUsageLimit;
    }

    public static AppAddUsageLimit getAppUsageRestrictionData(String packageName) {
        isRestrictionSet(packageName);
        return appAddUsageLimit;
    }

    // close database connection
    public void close() {
        Database.close();
    }


    public static void addNewAppLimit(AppAddUsageLimit appAddUsageLimit) {
        ContentValues values = DBHelpers.convertAppAddUsageLimitToContentValues(appAddUsageLimit);
        Database.insert(mDBAddUsageLimitSchema.TABLE_APPS, null, values);
    }


    public static void deleteAppLimit(String packageName) {
        String where = mDBAddUsageLimitSchema.COLUMN_PACKAGENAME + " = ?";
        String[] whereArgs = {String.valueOf(packageName)};
        Database.delete(mDBAddUsageLimitSchema.TABLE_APPS, where, whereArgs);
    }


    // check if restriction is set on app
    public static boolean isRestrictionSet(String packageName) {
        String currentPackage;
        Cursor mCursor = getAllAppsWithAnyTypeOfRestriction();

        while (!mCursor.isAfterLast()) {
            currentPackage = mCursor.getString(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_PACKAGENAME));
            if (currentPackage.equals(packageName)) {
                appAddUsageLimit = DBHelpers.extractAppAddUsageLimitDataFromDBCursor(mCursor);
                return true;
            }
            mCursor.moveToNext();
        }
        // app not found
        return false;
    }


    //get list of all apps with launch restriction set
    public static List<AppAddUsageLimit> getAppsWithLaunchLimit() {

        List<AppAddUsageLimit> appsList = new ArrayList<>();
        String appName;
        int launchAllowedPerHour, launchAllowedPerDay;

        Cursor mCursor = getAllAppsWithAnyTypeOfRestriction();
        while (!mCursor.isAfterLast()) {

            // if launch restriction is set on given app
            if (DataTypeManip.toBool(mCursor.getInt(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_LAUNCH_RESTRICTION_SET)))) {

                appName = mCursor.getString(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_APPNAME));
                launchAllowedPerDay = mCursor.getInt(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_LAUNCH_ALLOWED_PER_DAY));
                launchAllowedPerHour = mCursor.getInt(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_LAUNCH_ALLOWED_PER_HOUR));

                AppAddUsageLimit appAddUsageLimit = new AppAddUsageLimit();
                appAddUsageLimit.setLaunchRestrictionParams(appName, launchAllowedPerHour,
                        launchAllowedPerDay, appIconsMap.get(appName));

                appsList.add(appAddUsageLimit);
            }

            mCursor.moveToNext();
        }
        return appsList;
    }


    //get list of all apps with usage time restriction set
    public static List<AppAddUsageLimit> getAppsWithTimeLimit() {

        List<AppAddUsageLimit> appsList = new ArrayList<>();

        String appName;
        int timeAllowedPerHour, timeAllowedPerDay;

        Cursor mCursor = getAllAppsWithAnyTypeOfRestriction();

        while (!mCursor.isAfterLast()) {

            //if usage time restriction set
            if (DataTypeManip.toBool(mCursor.getInt(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_TIME_RESTRICTION_SET)))) {

                appName = mCursor.getString(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_APPNAME));
                timeAllowedPerDay = mCursor.getInt(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_TIME_ALLOWED_PER_DAY));
                timeAllowedPerHour = mCursor.getInt(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_TIME_ALLOWED_PER_HOUR));

                AppAddUsageLimit appAddUsageLimit = new AppAddUsageLimit();
                appAddUsageLimit.setUsageTimeRestrictionParams(appName, timeAllowedPerHour,
                        timeAllowedPerDay, appIconsMap.get(appName));

                appsList.add(appAddUsageLimit);
            }

            mCursor.moveToNext();
        }
        return appsList;
    }


    // get list of all apps with specific time restriction set
    public static List<AppAddUsageLimit> getAppsWithSpecificTimeLimit() {

        List<AppAddUsageLimit> appsList = new ArrayList<>();

        String appName;
        String beginTime, endTime;

        Cursor mCursor = getAllAppsWithAnyTypeOfRestriction();

        while (!mCursor.isAfterLast()) {

            if (DataTypeManip.toBool(mCursor.getInt(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_SPECIFIC_TIME_RESTRICTION_SET)))) {

                appName = mCursor.getString(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_APPNAME));
                beginTime = mCursor.getString(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_SPECIFIC_TIME_BEGIN));
                endTime = mCursor.getString(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_SPECIFIC_TIME_END));

                AppAddUsageLimit appAddUsageLimit = new AppAddUsageLimit();
                appAddUsageLimit.setAppName(appName);
                appAddUsageLimit.setSpecificTimeBegin(beginTime);
                appAddUsageLimit.setSpecificTimeEnd(endTime);
                appAddUsageLimit.setIcon(appIconsMap.get(appName));
                appsList.add(appAddUsageLimit);
            }
            mCursor.moveToNext();
        }
        return appsList;
    }

    // get list of all apps with any type of restriction set
    public static List<AppAddUsageLimit> getAppsWithAnyLimit() {

        List<AppAddUsageLimit> appsList = new ArrayList<>();

        String appName, packageName;
        boolean isLaunchRestrictionSet, isUsageTimeRestrictionSet, isSpecificTimeRestrictionSet;

        Cursor mCursor = getAllAppsWithAnyTypeOfRestriction();

        appIconsMap = AndroidPackageManagerWrappers.getAppIcons(mContext);

        while (!mCursor.isAfterLast()) {

            isLaunchRestrictionSet = DataTypeManip.toBool(mCursor.getInt(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_LAUNCH_RESTRICTION_SET)));
            isUsageTimeRestrictionSet = DataTypeManip.toBool(mCursor.getInt(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_TIME_RESTRICTION_SET)));
            isSpecificTimeRestrictionSet = DataTypeManip.toBool(mCursor.getInt(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_SPECIFIC_TIME_RESTRICTION_SET)));

            if (isLaunchRestrictionSet || isUsageTimeRestrictionSet || isSpecificTimeRestrictionSet) {
                appName = mCursor.getString(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_APPNAME));
                packageName = mCursor.getString(mCursor.getColumnIndex(mDBAddUsageLimitSchema.COLUMN_PACKAGENAME));
                AppAddUsageLimit appAddUsageLimit = new AppAddUsageLimit();

                appAddUsageLimit.setAppName(appName);
                appAddUsageLimit.setPackageName(packageName);
                appAddUsageLimit.setIsLaunchRestrictionSet(isLaunchRestrictionSet);
                appAddUsageLimit.setIsUsageTimeRestrictionSet(isUsageTimeRestrictionSet);
                appAddUsageLimit.setIsSpecifiTimeRestrictionSet(isSpecificTimeRestrictionSet);
                appAddUsageLimit.setIcon(appIconsMap.get(appName));

                appsList.add(appAddUsageLimit);
            }

            mCursor.moveToNext();
        }
        return appsList;
    }

    private static Cursor getAllAppsWithAnyTypeOfRestriction() {

        String query = "SELECT * FROM " + DBAddUsageLimitSchema.TABLE_APPS + " WHERE 1";
        Cursor mCursor = Database.rawQuery(query, null);
        mCursor.moveToFirst();
        return mCursor;
    }
}
