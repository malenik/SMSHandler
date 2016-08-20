package com.malenik.example.smshandler;

/**
 * Created by NIK on 04.05.2016.
 */
import android.database.sqlite.SQLiteDatabase;

public class SMSTable {

    public static final String TABLE_SMS = "sms_test";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_DATE = "date";

    public static final String DATABASE_CREATE = "create table "
            + TABLE_SMS
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NUMBER + " text not null, "
            + COLUMN_BODY + " text not null, "
            + COLUMN_DATE + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);
        onCreate(database);

    }
}