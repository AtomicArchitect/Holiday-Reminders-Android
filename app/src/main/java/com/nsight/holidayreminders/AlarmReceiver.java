package com.nsight.holidayreminders;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;

import com.nsight.holidayreminders.db.DBAdapter;
import com.nsight.holidayreminders.db.Holiday;
import com.nsight.holidayreminders.utils.NotificationsHelper;

import java.util.Date;
import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    private static SimpleDateFormat format = new SimpleDateFormat("dd.MM");
    private DBAdapter dbAdapter;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (dbAdapter == null) dbAdapter = new DBAdapter(context);
        dbAdapter.open();
        List<Holiday> holidays = dbAdapter.getAllHolidaysOnDate(format.format(new Date()));
        if (!holidays.isEmpty()) {
            NotificationsHelper.send(
                    "Пора праздновать",
                    holidays.get(0).getName(),
                    context,
                    MainActivity.class,
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
        }
        dbAdapter.close();
    }
}
