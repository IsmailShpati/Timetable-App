package com.example.timetableapp.model;

import java.util.ArrayList;

//Represents a day of the week holding different activites
public class Day {
    private ArrayList<Activity> activities;

    public Day(){
    }

    public ArrayList<Activity> getActivities(){
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities){
        this.activities = activities;
    }
    public void deleteActivity(Activity activity){
        activities.remove(activity);
    }

    public void addActivity(Activity activity){
        activities.add(activity);
    }
}
