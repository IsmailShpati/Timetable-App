package com.example.timetableapp.model;

public class Activity {
    private String name, description;
    private Time startTime, endTime;
    private int repeatingDay;

    //To put a meeting link
    private String activityLink;
    public Activity(){
    }

    public Activity(String name, Time startTime, Time endTime){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public void setActivityLink(String activityLink){
        this.activityLink = activityLink;
    }

    public String getActivityLink(){
        return activityLink;
    }
}
