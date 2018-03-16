package com.android.blocker.database.dbViewUsageStats;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBViewUsageStatsSchema extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Apps_Usage_Stats.db";
    public static final String TABLE_USAGE = "Usage_Stats";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PACKAGENAME = "PackageName";
    public static final String COLUMN_LAUNCH_DONE_PER_HOUR = "LaunchdDonePerHour";
    public static final String COLUMN_LAUNCH_DONE_PER_DAY = "LaunchdDonePerDay";
    public static final String COLUMN_TIME_COMPLETED_PER_HOUR = "TimeCompletedPerHour";
    public static final String COLUMN_TIME_COMPLETED_PER_DAY = "TimeCompletedPerDay";
    public static final String COLUMN_LAST_USED_TIME = "LastUsedTime";


    public DBViewUsageStatsSchema(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_USAGE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PACKAGENAME + " TEXT, " +
                COLUMN_LAUNCH_DONE_PER_HOUR + " INTEGER, " +
                COLUMN_LAUNCH_DONE_PER_DAY + " INTEGER, " +
                COLUMN_TIME_COMPLETED_PER_HOUR + " INTEGER, " +
                COLUMN_TIME_COMPLETED_PER_DAY + " INTEGER, " +
                COLUMN_LAST_USED_TIME + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USAGE);
        onCreate(db);
    }

}
