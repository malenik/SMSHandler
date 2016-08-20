package com.malenik.example.smshandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public void drop(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DROP TABLE " + SMSTable.TABLE_SMS);
    }

    public void create(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(SMSTable.DATABASE_CREATE);
    }

    public boolean exists(String number, String body, String date) {
        body = body.replace("'", "");
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + SMSTable.TABLE_SMS + " WHERE " + SMSTable.COLUMN_NUMBER + "='" + number + "' AND " + SMSTable.COLUMN_BODY + "='" + body + "' AND " + SMSTable.COLUMN_DATE + "='" + date + "'", null);
        return cursor.moveToNext();
    }

    public boolean insertData(String number, String body, String date) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SMSTable.COLUMN_NUMBER, number);
        values.put(SMSTable.COLUMN_BODY, body);
        values.put(SMSTable.COLUMN_DATE, date);

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
