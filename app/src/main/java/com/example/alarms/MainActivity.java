package com.example.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button Alarm1;
    Button Alarm2;
    Button cancelAlarm1;
    Button cancelAlarm2;
    TextView chosenTime1;
    TextView chosenTime2;
    AlarmManager alarmMgr;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        chosenTime1 = findViewById(R.id.chosen_time_1);
        chosenTime2 = findViewById(R.id.chosen_time_2);
        Alarm1 = findViewById(R.id.alarm1_time);
        Alarm1.setOnClickListener(this);
        cancelAlarm1 = findViewById(R.id.cancel_alarm1);
        cancelAlarm1.setOnClickListener(this);
        Alarm2 = findViewById(R.id.alarm2_time);
        Alarm2.setOnClickListener(this);
        cancelAlarm2 = findViewById(R.id.cancel_alarm2);
        cancelAlarm2.setOnClickListener(this);
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Bundle params = new Bundle();
        params.putString("screen_name", "Main screen");
        mFirebaseAnalytics.logEvent("screenview", params);


    }

    void addAlarm(final long id) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String alarmText = "Alarm set for " + Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        if (id == R.id.alarm1_time) {
                            chosenTime1.setText(alarmText);
                            Log.e("Not", "schedule alarm 1: ");
                            scheduleAlarm(c, 1);


                        } else {
                            chosenTime2.setText(alarmText);
                            Log.e("Not", "schedule alarm 2: ");
                            scheduleAlarm(c, 2);
                        }

                    }
                }, hour, min, false);

        timePickerDialog.show();
        timePickerDialog.getButton(TimePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        timePickerDialog.getButton(TimePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);

    }

    public void scheduleAlarm(Calendar calendar, int request) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("AlarmID", request);
        intent.putExtra("Hours", calendar.get(Calendar.HOUR));
        intent.putExtra("Minute", calendar.get(Calendar.MINUTE));
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, request, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Log.e("Not", "pending intent set ");
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);

    }

    public void cancelAlarm(int request) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, request, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(alarmIntent);
        Log.e("Not", "intent cancelled");
        alarmIntent.cancel();
    }


    @Override
    public void onClick(View v) {

        String canceltxt = "Alarm cancelled";


        switch (v.getId()) {
            case R.id.alarm1_time: {
                addAlarm(R.id.alarm1_time);
                break;
            }

            case R.id.alarm2_time: {
                addAlarm(R.id.alarm2_time);
                break;
            }

            case R.id.cancel_alarm1: {
                cancelAlarm(1);
                Toast toast = Toast.makeText(this, "Alarm 1 has been cancelled.", Toast.LENGTH_SHORT);
                toast.show();
                chosenTime1.setText(canceltxt);
                break;
            }

            case R.id.cancel_alarm2: {
                cancelAlarm(2);
                Toast toast = Toast.makeText(this, "Alarm 2 has been cancelled.", Toast.LENGTH_SHORT);
                toast.show();
                chosenTime2.setText(canceltxt);
                break;
            }
        }




    }
}

