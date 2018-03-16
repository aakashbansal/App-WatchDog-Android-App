package com.android.blocker.helper;

import android.content.ContentValues;
import android.database.Cursor;

import com.android.blocker.database.dbAddUsageLimit.DBAddUsageLimitSchema;
import com.android.blocker.model.AppAddUsageLimit;

public class DBHelpers {



    public static ContentValues convertAppAddUsageLimitToContentValues(AppAddUsageLimit appAddUsageLimit) {

        //constructing a query to store data
        ContentValues values = new ContentValues();

        //app name and package name
        values.put(DBAddUsageLimitSchema.COLUMN_APPNAME, appAddUsageLimit.getAppName());
        values.put(DBAddUsageLimitSchema.COLUMN_PACKAGENAME, appAddUsageLimit.getPackageName());

        // type of restriction on app
        values.put(DBAddUsageLimitSchema.COLUMN_LAUNCH_RESTRICTION_SET, DataTypeManip.toInt(appAddUsageLimit.getIsLaunchRestrictionSet()));
        values.put(DBAddUsageLimitSchema.COLUMN_TIME_RESTRICTION_SET, DataTypeManip.toInt(appAddUsageLimit.getIsUsageTimeRestrictionSet()));
        values.put(DBAddUsageLimitSchema.COLUMN_SPECIFIC_TIME_RESTRICTION_SET, DataTypeManip.toInt(appAddUsageLimit.getIsSpecifiTimeRestrictionSet()));

        // if launch restriction set
        if (appAddUsageLimit.getIsLaunchRestrictionSet()) {
            values.put(DBAddUsageLimitSchema.COLUMN_LAUNCH_ALLOWED_PER_HOUR, appAddUsageLimit.getLaunchAllowedPerHour());
            values.put(DBAddUsageLimitSchema.COLUMN_LAUNCH_ALLOWED_PER_DAY, appAddUsageLimit.getLaunchAllowedPerDay());
        }

        // if time restriction set
        if (appAddUsageLimit.getIsUsageTimeRestrictionSet()) {
            values.put(DBAddUsageLimitSchema.COLUMN_TIME_ALLOWED_PER_HOUR, appAddUsageLimit.getTimeAllowedPerHour());
            values.put(DBAddUsageLimitSchema.COLUMN_TIME_ALLOWED_PER_DAY, appAddUsageLimit.getTimeAllowedPerDay());
        }

        // if specific time restriction set
        if (appAddUsageLimit.getIsSpecifiTimeRestrictionSet()) {
            values.put(DBAddUsageLimitSchema.COLUMN_SPECIFIC_TIME_BEGIN, appAddUsageLimit.getSpecificTimeBegin());
            values.put(DBAddUsageLimitSchema.COLUMN_SPECIFIC_TIME_END, appAddUsageLimit.getSpecificTimeEnd());
        }

        return values;
    }




    public static AppAddUsageLimit extractAppAddUsageLimitDataFromDBCursor(Cursor mCursor) {

        AppAddUsageLimit appAddUsageLimit = new AppAddUsageLimit();

        // check launch restriction
        appAddUsageLimit.setIsLaunchRestrictionSet(
                DataTypeManip.toBool(mCursor.getInt(
                        mCursor.getColumnIndex(DBAddUsageLimitSchema.COLUMN_LAUNCH_RESTRICTION_SET)
                ))
        );

        if (appAddUsageLimit.getIsLaunchRestrictionSet()) {
            appAddUsageLimit.setLaunchAllowedPerDay(mCursor.getInt(
                    mCursor.getColumnIndex(DBAddUsageLimitSchema.COLUMN_LAUNCH_ALLOWED_PER_DAY)
            ));
            appAddUsageLimit.setLaunchAllowedPerHour(mCursor.getInt(
                    mCursor.getColumnIndex(DBAddUsageLimitSchema.COLUMN_LAUNCH_ALLOWED_PER_HOUR)
            ));
        }

        //check usage time restriction
        appAddUsageLimit.setIsUsageTimeRestrictionSet(
                DataTypeManip.toBool(mCursor.getInt(
                        mCursor.getColumnIndex(DBAddUsageLimitSchema.COLUMN_TIME_RESTRICTION_SET)
                ))
        );

        if (appAddUsageLimit.getIsUsageTimeRestrictionSet()) {
            appAddUsageLimit.setTimeAllowedPerHour(mCursor.getInt(
                    mCursor.getColumnIndex(DBAddUsageLimitSchema.COLUMN_TIME_ALLOWED_PER_HOUR)
            ));
            appAddUsageLimit.setTimeAllowedPerDay(mCursor.getInt(
                    mCursor.getColumnIndex(DBAddUsageLimitSchema.COLUMN_TIME_ALLOWED_PER_DAY)
            ));
        }


        // check specific time restriction
        appAddUsageLimit.setIsSpecifiTimeRestrictionSet(
                DataTypeManip.toBool(mCursor.getInt(
                        mCursor.getColumnIndex(DBAddUsageLimitSchema.COLUMN_SPECIFIC_TIME_RESTRICTION_SET)
                ))
        );


        if (appAddUsageLimit.getIsSpecifiTimeRestrictionSet()) {
            appAddUsageLimit.setSpecificTimeBegin(mCursor.getString(
                    mCursor.getColumnIndex(DBAddUsageLimitSchema.COLUMN_SPECIFIC_TIME_BEGIN)
            ));
            appAddUsageLimit.setSpecificTimeEnd(mCursor.getString(
                    mCursor.getColumnIndex(DBAddUsageLimitSchema.COLUMN_SPECIFIC_TIME_END)
            ));
        }

        return appAddUsageLimit;
    }


}
