package com.example.alarms;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channel1_id = "Alarm1ID";
    public static final String channel1_name = "Alarm1";

    public static final String channel2_id = "Alarm2ID";
    public static final String channel2_name = "Alarm2";

    private NotificationManager notificationManager = null;


    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels() {
        NotificationChannel channel1 = new NotificationChannel(channel1_id, channel1_name, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationChannel channel2 = new NotificationChannel(channel2_id, channel2_name, NotificationManager.IMPORTANCE_DEFAULT);
        channel2.enableLights(true);
        channel2.enableVibration(true);
        channel2.setLightColor(R.color.colorPrimary);
        channel2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getNotificationManager().createNotificationChannel(channel1);
        getNotificationManager().createNotificationChannel(channel2);
    }

    public NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotifications(int id, String title, String message) {
        NotificationCompat.Builder nb = null;
        switch (id) {
            case 1: {
                nb = new NotificationCompat.Builder(getApplicationContext(), channel1_id).setContentTitle(title).setContentText(message).setSmallIcon(R.drawable.notify);
                break;
            }

            case 2: {
                nb = new NotificationCompat.Builder(getApplicationContext(), channel2_id).setContentTitle(title).setContentText(message).setSmallIcon(R.drawable.notify);
                break;
            }
        }

        return nb;
    }


}
