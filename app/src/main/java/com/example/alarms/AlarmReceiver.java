package com.example.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        int alarm = intent.getIntExtra("AlarmID", 0);
        String title = "";
        String details = "";

        if(alarm == 1) {
            title = "Alarm type 1";
            details = "Alarm set at " + intent.getStringExtra("Hours") + ":" + intent.getStringExtra("Minute");

        } else {
            title = "Alarm type 2";
            details = "Alarm set at " + intent.getStringExtra("Hours") + ":" + intent.getStringExtra("Minute");
        }

        Log.e("Not", "onReceive: " );

        NotificationCompat.Builder nb = notificationHelper.getChannelNotifications(alarm, title, details);
        notificationHelper.getNotificationManager().notify(1, nb.build());
    }
}
