package com.example.timetableapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;

public class AlarmBroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.e("Receiver", "Received " + bundle.getInt("hour"));
            Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
            alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, bundle.getInt("hour"));
            alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, bundle.getInt("minute"));
         //   alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, bundle.getString("name"));
        //    alarmIntent.putExtra(AlarmClock.EXTRA_DAYS, bundle.getInt("day"));
            alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
            alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(alarmIntent);
        }


}

