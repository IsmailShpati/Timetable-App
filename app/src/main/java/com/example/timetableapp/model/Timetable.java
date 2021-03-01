package com.example.timetableapp.model;

import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Timetable {
    private ArrayList<Day> days = new ArrayList<>();

    public Timetable(){

        Day monday = new Day();
        ArrayList<Activity> mondayActivities = new ArrayList<>();
        Activity mondayActivity = new Activity("First Activity", "desc",new Time(8, 30),
                new Time(9, 10),  new Link("link","https://stackoverflow.com/questions/32136973/how-to-get-a-context-in-a-recycler-view-adapter"));
        Activity mondayActivity2 = new Activity("Second Activity", "desc",new Time(12,
                30), new Time(13, 10),   new Link("link","https://stackoverflow.com/questions/32136973/how-to-get-a-context-in-a-recycler-view-adapter"));
        mondayActivities.add(mondayActivity);
        mondayActivities.add(mondayActivity2);
        monday.setActivities(mondayActivities);

        ArrayList<Activity> wednesdayActivities = new ArrayList<>();
        Activity wednesdayActivity = new Activity("First Activity", "desc",new Time(8, 30), new Time(9, 10),   new Link("link","https://stackoverflow.com/questions/32136973/how-to-get-a-context-in-a-recycler-view-adapter"));
        Activity wednesdayActivity2 = new Activity("Second Activity", "desc",new Time(12, 30), new Time(13, 10),   new Link("link","https://stackoverflow.com/questions/32136973/how-to-get-a-context-in-a-recycler-view-adapter"));
        Day wednesday = new Day();
        wednesdayActivities.add(wednesdayActivity);
        wednesdayActivities.add(wednesdayActivity2);
        wednesday.setActivities(wednesdayActivities);
        //It goes sunday, monday, tuesday
        days.add(monday);
        days.add(monday);
        days.add(wednesday);
        days.add(wednesday);
        days.add(new Day());
    }

    public Day getCurrentDay(){
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        Log.i("Info", "Day: " + Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        return days.get(day-1);
    }

    //Handle updates on timetable
}
