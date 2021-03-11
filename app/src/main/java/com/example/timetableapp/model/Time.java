package com.example.timetableapp.model;

import android.util.Log;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Time implements Serializable {
    private int hour, minute;

    public Time(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }
    public void setHour(int hour){
        this.hour = hour;
    }
    public void setMinute(int minute){
        this.minute = minute;
    }

    private static final int NORMAL_ALARM = 0, SAME_WEEK_ALARM = 1, NEXT_WEEK_ALARM = 2;

    public static int getHoursUntil(Activity b, int day) {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int type = getAlarmType(b, day);
        if(type == NEXT_WEEK_ALARM)
              return (6 - day + b.getRepeatingDay())*24 +(24-hour);
          if(type == SAME_WEEK_ALARM)
              return (b.getRepeatingDay() - day)*24 - hour;
          return -1;
    }

    private static int getAlarmType(Activity a, int day) {
        Calendar now = Calendar.getInstance();
        int difference = getTimeInMinutes(a) -
                ((day * 1440 +
                  now.get(Calendar.HOUR_OF_DAY)*60 +
                  now.get(Calendar.MINUTE) ));
        Log.e("AlarmAt", "Next Activity: " + a.getRepeatingDay() + " Time: " + a.getStartTime().toString());
        Log.e("AlarmAt", "Starting Activity: " + day + " Time " + now.get(Calendar.HOUR_OF_DAY) + ":"+now.get(Calendar.MINUTE));
        if(difference <= 0)
            return NEXT_WEEK_ALARM;
        if(difference > 1440)
            return SAME_WEEK_ALARM;
        return NORMAL_ALARM;
    }

    private static int getTimeInMinutes(Activity a){
        return a.getRepeatingDay()*1440 + a.getStartTime().getHour() * 60 + a.getStartTime().getMinute();
    }

    @Override
    public String toString(){
        return (zeroPadding(hour) + ":" + zeroPadding(minute));
    }

    private String zeroPadding(int num){
        if(num < 10)
            return "0"+num;
        return num+"";
    }
}
