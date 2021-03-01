package com.example.timetableapp.model;

public class Time {
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
