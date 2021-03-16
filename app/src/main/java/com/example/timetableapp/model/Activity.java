package com.example.timetableapp.model;

import android.util.Log;

import java.io.Serializable;

public class Activity implements Serializable, Comparable<Activity> {
    private String name, description;
    private Time startTime, endTime;
    private int repeatingDay, minutesBeforeAlarm;

    //To put a meeting link
    private Link activityLink;
    public Activity(){
        minutesBeforeAlarm = 10;
    }

    private Activity(String name, String description){
        this.name = name;
        this.description = description;
    }
    public Activity(String name, String description, Time startTime, Time endTime,
                    Link activityLink, int repeatingDay, int minutesBeforeAlarm){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.activityLink = activityLink;
        this.repeatingDay = repeatingDay;
        this.minutesBeforeAlarm = minutesBeforeAlarm;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Time getStartTime(){
        return startTime;
    }

    public void setStartTime(Time startTime){
        this.startTime = startTime;
    }

    public void setStartTime(int hour, int minute){
        this.startTime = new Time(hour, minute);
    }

    public Time getEndTime(){
        return endTime;
    }

    public void setEndTime(Time endTime){
        this.endTime = endTime;
    }

    public void setEndTime(int hour, int minute){
        this.endTime = new Time(hour, minute);
    }

    public int getRepeatingDay() {
        return repeatingDay;
    }

    public void setRepeatingDay(int repeatingDay) {
        this.repeatingDay = repeatingDay;
    }

    public void setActivityLink(Link activityLink){
        this.activityLink = activityLink;
    }

    public int getMinutesBeforeAlarm() { return minutesBeforeAlarm; }

    public void setMinutesBeforeAlarm(int minutesBeforeAlarm) { this.minutesBeforeAlarm = minutesBeforeAlarm; }

    public Link getActivityLink(){
        return activityLink;
    }

    public String getTimeString(){
        Log.d("Activity Time String", startTime.toString() + " - " + endTime.toString());
        return (startTime.toString() + " - " + endTime.toString());
    }

    @Override
    public int compareTo(Activity activity) {
        return Time.getTimeInMinutes(this) - Time.getTimeInMinutes(activity);
    }
}
