package com.example.timetableapp.model;

import android.util.Log;
import com.example.timetableapp.database.DataManager;

import java.util.ArrayList;
import java.util.Calendar;

public class Timetable {
    private ArrayList<Day> days;
    private final DataManager dataManager;

    public Timetable(DataManager dataManager){
        this.dataManager = dataManager;
        this.days = dataManager.selectAll();
    }

    public Day getCurrentDay(){
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        Log.i("Info", "Day: " + Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        return days.get(day-1);
    }

    public Day getDay(int day){
        return days.get(day);
    }

    //Handle updates on timetable
    public void addActivity(Activity activity){
        int day = activity.getRepeatingDay();
        days.get(day).addActivity(activity);
        dataManager.insert(activity);
    }

    public void removeActivity(Activity activity) {
        int day = activity.getRepeatingDay();
        days.get(day).getActivities().remove(activity);
        dataManager.delete(activity);
    }

    public void updateActivity(Activity oldActivity, Activity newActivity){
        int day = oldActivity.getRepeatingDay();
        days.set(day, dataManager.selectDay(day));
        dataManager.update(oldActivity, newActivity);
    }
}
