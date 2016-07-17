package com.malenik.example.smshandler;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.SmsMessage;

/**
 * Created by NIK on 04.05.2016.
 */
public class SMSDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smstable.db";
    private static final int DATABASE_VERSION = 1;

    public SMSDatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SMSTable.onCreate(this.getWritableDatabase());
        //SQLiteDatabase database = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        SMSTable.onCreate(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        SMSTable.onUpgrade(database, oldVersion, newVersion);
    }

    public void clear(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + SMSTable.TABLE_SMS);
    }

    public boolean exists(String number, String body) {
        body = body.replace("'", "");
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + SMSTable.TABLE_SMS + " WHERE " + SMSTable.COLUMN_NUMBER + "='" + number + "' AND " + SMSTable.COLUMN_BODY + "='" + body + "'", null);
        return cursor.moveToNext();
    }

    public boolean insertData(String number, String body) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SMSTable.COLUMN_NUMBER, number);
        values.put(SMSTable.COLUMN_BODY, body);

        long result = database.insert(SMSTable.TABLE_SMS, null, values);
        if (result == -1)
            return false;
        else
            return true;
        }

    public Cursor getAllData(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor res = database.rawQuery("select * from "+SMSTable.TABLE_SMS, null);
        return res;
    }

}
