package com.nsight.holidayreminders.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context.getApplicationContext());
    }

    public DBAdapter open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private Cursor getAllEntries() {
        return database.query(DBHelper.TABLE, DBHelper.getColumns(), null, null, null, null, null);
    }

    @SuppressLint("Range")
    public List<Holiday> getHolidays() {
        ArrayList<Holiday> holidays = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME));
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE));
            holidays.add(new Holiday(id, name, date));
        }
        cursor.close();
        return holidays;
    }

    public long getCount() {
        return DatabaseUtils.queryNumEntries(database, DBHelper.TABLE);
    }

    @SuppressLint("Range")
    public String getNameHolidayOnDate(String _date) {
        String result = "Праздников нет";
        String query = String.format("SELECT * FROM %s WHERE %s=?", DBHelper.TABLE, DBHelper.COLUMN_DATE);
        Cursor cursor = database.rawQuery(query, new String[]{_date});
        if (cursor.moveToNext()) {
            result = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME));
        }
        cursor.close();
        return result;
    }

    @SuppressLint("Range")
    public List<Holiday> getAllHolidaysOnDate(String _date) {
        ArrayList<Holiday> holidays = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE %s=?", DBHelper.TABLE, DBHelper.COLUMN_DATE);
        Cursor cursor = database.rawQuery(query, new String[]{_date});
        while (cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME));
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE));
            holidays.add(new Holiday(id, name, date));
        }
        cursor.close();
        return holidays;
    }

    public long insert(Holiday holiday) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, holiday.getName());
        cv.put(DBHelper.COLUMN_DATE, holiday.getDate());
        return database.insert(DBHelper.TABLE, null, cv);
    }

    public long delete(long holidayId) {
        String whereClause = DBHelper.COLUMN_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(holidayId)};
        return database.delete(DBHelper.TABLE, whereClause, whereArgs);
    }

    public long delete(String name) {
        String whereClause = DBHelper.COLUMN_NAME + " = ?";
        String[] whereArgs = new String[]{name};
        return database.delete(DBHelper.TABLE, whereClause, whereArgs);
    }

    public long update(Holiday holiday) {
        String whereClause = DBHelper.COLUMN_ID + "=" + holiday.getId();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, holiday.getName());
        cv.put(DBHelper.COLUMN_DATE, holiday.getDate());
        return database.update(DBHelper.TABLE, cv, whereClause, null);
    }
}
