package com.example.timetableapp.model;

import android.util.Log;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Time implements Serializable {
    private final int hour, minute;

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

    private static final int NORMAL_ALARM = 0, SAME_WEEK_ALARM = 1, NEXT_WEEK_ALARM = 2;

    //Hours until the day the alarm is set comes in order to fire the PendingIntent and create an alarm on the Alarm Clock
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

        if(difference <= 0)
            return NEXT_WEEK_ALARM;
        if(difference > 1440)
            return SAME_WEEK_ALARM;
        return NORMAL_ALARM;
    }

    public static int getTimeInMinutes(Activity a){
        return a.getRepeatingDay()*1440 + a.getStartTime().getHour() * 60 + a.getStartTime().getMinute();
    }

    private static int getTimeInMinutes(Time time){
        return time.getHour()*60 + time.getMinute();
    }

    //If you want the alert to be set and run some minutes before the activity starts
    public static Time calculateTimeBeforeAlert(Time setTime, int minutesBefore){
        int timeInMinutes = getTimeInMinutes(setTime) - minutesBefore;
        if(timeInMinutes >= 0)
            return new Time(timeInMinutes/60, timeInMinutes - (timeInMinutes/60*60));
        return setTime;
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
