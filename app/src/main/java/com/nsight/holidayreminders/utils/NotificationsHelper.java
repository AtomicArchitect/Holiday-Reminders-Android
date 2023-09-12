package com.nsight.holidayreminders.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.nsight.holidayreminders.R;

public class NotificationsHelper {
    private static final int NOTIFY_ID = 12;
    private static String CHANNEL_ID = "Holiday Notifications Channel";

    public static void send(String title, String description, Context context, Class cls, NotificationManager nm) {
        Intent notificationIntent = new Intent(context, cls);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_app_round)
                .setContentTitle(title)
                .setContentText(description)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(contentIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(notificationChannel);
        }
        nm.notify(NOTIFY_ID, builder.build());
    }
}
