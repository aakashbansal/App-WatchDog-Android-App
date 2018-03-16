package com.android.blocker.database.dbAddUsageLimit;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DBAddUsageLimitSchema extends SQLiteOpenHelper {

    // static members of the class
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Apps_Usage_Limit.db";
    public static final String TABLE_APPS = "Usage_Limit";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_APPNAME = "AppName";
    public static final String COLUMN_PACKAGENAME = "PackageName";
    public static final String COLUMN_LAUNCH_ALLOWED_PER_HOUR = "LaunchAllowedPerHour";
    public static final String COLUMN_LAUNCH_ALLOWED_PER_DAY = "LaunchAllowedPerDay";
    public static final String COLUMN_LAUNCH_COMPLETED = "LaunchCompleted";
    public static final String COLUMN_TIME_ALLOWED_PER_HOUR = "TimeAllowedPerHour";
    public static final String COLUMN_TIME_ALLOWED_PER_DAY = "TimeAllowedPerDay";
    public static final String COLUMN_TIME_COMPLETED = "TimeCompleted";
    public static final String COLUMN_SPECIFIC_TIME_BEGIN = "SpecificTimeBegin";
    public static final String COLUMN_SPECIFIC_TIME_END = "SpecificTimeEnd";
    public static final String COLUMN_LAUNCH_RESTRICTION_SET = "LaunchRestrictionSet";
    public static final String COLUMN_TIME_RESTRICTION_SET = "TimeRestrictionSet";
    public static final String COLUMN_SPECIFIC_TIME_RESTRICTION_SET = "SpecificTimeRestrictionSet";


    // class constructor
    public DBAddUsageLimitSchema(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // create database
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_APPS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_APPNAME + " TEXT, " +
                COLUMN_PACKAGENAME + " TEXT, " +
                COLUMN_LAUNCH_ALLOWED_PER_HOUR + " INTEGER, " +
                COLUMN_LAUNCH_ALLOWED_PER_DAY + " INTEGER, " +
                COLUMN_LAUNCH_COMPLETED + " INTEGER, " +
                COLUMN_TIME_ALLOWED_PER_HOUR + " INTEGER, " +
                COLUMN_TIME_ALLOWED_PER_DAY + " INTEGER, " +
                COLUMN_TIME_COMPLETED + " INTEGER, " +
                COLUMN_SPECIFIC_TIME_BEGIN + " TEXT, " +
                COLUMN_SPECIFIC_TIME_END + " TEXT, " +
                COLUMN_LAUNCH_RESTRICTION_SET + " INTEGER, " +
                COLUMN_TIME_RESTRICTION_SET + " INTEGER, " +
                COLUMN_SPECIFIC_TIME_RESTRICTION_SET + " INTEGER " +
                ");";
        db.execSQL(query);
    }


    // on upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPS);
        onCreate(db);
    }

}
