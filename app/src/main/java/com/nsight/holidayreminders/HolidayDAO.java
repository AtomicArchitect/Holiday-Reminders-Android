package com.nsight.holidayreminders;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HolidayDAO {

    private final SQLiteDatabase db;

    public HolidayDAO(Context baseContext) {
        this.db = baseContext.openOrCreateDatabase("hr_database", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS holidays (holiday TEXT, date TEXT)");
    }

    public String isHoliday(String date) {
        Cursor query = db.rawQuery("SELECT * FROM holidays WHERE date = \"" + date + "\";", null);
        String result = "Пусто \uD83D\uDE14";
        if (query.moveToNext()) result = query.getString(0);
        query.close();
        return result;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        db.close();
    }
}
