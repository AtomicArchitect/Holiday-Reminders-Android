package com.nsight.holidayreminders.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HolidayDB {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM");
    private static SQLiteDatabase db;
    private static Context baseContext;

    public static void initialize(Context context) {
        baseContext = context;
        db = baseContext.openOrCreateDatabase("hr_database", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS holidays (holiday TEXT, date TEXT);");
    }

    public static String isHoliday(String date) {
        String result = "Пусто \uD83D\uDE14";
        if (db == null) return result;
        Cursor query = db.rawQuery("SELECT * FROM holidays WHERE date = \"" + date + "\";", null);
        if (query.moveToNext()) result = query.getString(0);
        query.close();
        return result;
    }

    public static void save(String name, String date) {
        if (!date.isEmpty()) {
            if (allOnDate(date).contains(name.toLowerCase())) {
                Toast.makeText(baseContext, "Запись существует", Toast.LENGTH_SHORT).show();
            } else {
                db.execSQL("INSERT INTO holidays VALUES (\"" + name + "\", \"" + date + "\");");
                Toast.makeText(baseContext, "Запись добавлена", Toast.LENGTH_SHORT).show();
            }
        } else Toast.makeText(baseContext, "Дата не установлена", Toast.LENGTH_SHORT).show();
    }

    public static void remove(String name) {
        db.execSQL("DELETE FROM holidays WHERE holiday = \"" + name + "\";");
    }

    public static List<String> allOnDate(String date) {
        List<String> parsed = new ArrayList<>();
        Cursor query = db.rawQuery("SELECT * FROM holidays WHERE date = \"" + date + "\";", null);
        while (query.moveToNext()) {
            parsed.add(query.getString(0).toLowerCase());
        }
        query.close();
        return parsed;
    }

    public static List<String> all() {
        List<String> parsed = new ArrayList<>();
        Cursor query = db.rawQuery("SELECT * FROM holidays;", null);
        while (query.moveToNext()) {
            parsed.add(query.getString(0).toLowerCase());
        }
        query.close();
        return parsed;
    }

    public static void clean() {
        db.close();
    }
}
