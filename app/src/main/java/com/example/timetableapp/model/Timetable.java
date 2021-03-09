package com.example.timetableapp.model;

import android.util.Log;
import com.example.timetableapp.DataManager;

import java.util.ArrayList;
import java.util.Calendar;

public class Timetable {
    private ArrayList<Day> days = new ArrayList<>();
    private final DataManager dataManager;

//    public Timetable(ArrayList<Day> days){
//        this.days = days;
//        for(Day d : days){
//            for(Activity a : d.getActivities()){
//                Log.e("Inside Timetable()", "Name=["+a.getName()+"] Day=["+a.getRepeatingDay());
//            }
//        }
//    }

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
        return days.get(day-1);
    }

    //Handle updates on timetable
    public void addActivity(Activity activity){
        int day = activity.getRepeatingDay();
        days.get(day-1).addActivity(activity);
        dataManager.insert(activity);
    }

    public void removeActivity(Activity activity) {
        int day = activity.getRepeatingDay();
        days.get(day-1).getActivities().remove(activity);
        dataManager.delete(activity);
    }

    public void updateActivity(Activity oldActivity, Activity newActivity){
        int day = oldActivity.getRepeatingDay();
        days.set(day-1, dataManager.selectDay(day));
        dataManager.update(oldActivity, newActivity);
    }
}
