package com.example.timetableapp.database;

import android.database.Cursor;
import android.util.Log;

import com.example.timetableapp.model.Activity;
import com.example.timetableapp.model.Day;
import com.example.timetableapp.model.Link;
import com.example.timetableapp.model.Time;

import java.util.ArrayList;

public class DataParser {



    public static ArrayList<Day> getDaysData(Cursor savedData){
        ArrayList<Day> savedDays = new ArrayList<>();
        for(int i = 0; i < 7; i++)
            savedDays.add( new Day());
        final int nameIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_NAME);
        final int descriptionIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_DESCRIPTION);
        final int linkDisplayIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_LINK_DISPLAY);
        final int linkIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_LINK);
        final int dayIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_DAY);
        final int startHourIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_START_HOUR);
        final int startMinuteIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_START_MINUTE);
        final int endHourIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_END_HOUR);
        final int endMinuteIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_END_MINUTE);
        final int minutesBeforeIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_MINUTES_BEFORE);

        while (savedData.moveToNext()) {
            int day = savedData.getInt(dayIndex);
            Log.e("DataController", "Day " + day);
            savedDays.get(day)
                    .addActivity(
                    new Activity(
                            savedData.getString(nameIndex),
                            savedData.getString(descriptionIndex),
                            new Time(savedData.getInt(startHourIndex), savedData.getInt(startMinuteIndex)),
                            new Time(savedData.getInt(endHourIndex), savedData.getInt(endMinuteIndex)),
                            new Link(savedData.getString(linkDisplayIndex), savedData.getString(linkIndex)),
                            day,
                            savedData.getInt(minutesBeforeIndex)));
        }

        savedData.close();
        return savedDays;
    }

    public static Day getDayData(Cursor savedData){
        final int nameIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_NAME);
        final int descriptionIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_DESCRIPTION);
        final int linkDisplayIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_LINK_DISPLAY);
        final int linkIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_LINK);
        final int dayIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_DAY);
        final int startHourIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_START_HOUR);
        final int startMinuteIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_START_MINUTE);
        final int endHourIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_END_HOUR);
        final int endMinuteIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_END_MINUTE);
        final int minutesBeforeIndex = savedData.getColumnIndex(DataManager.TABLE_ROW_MINUTES_BEFORE);

        Day day = new Day();
        while (savedData.moveToNext()) {
                    day.addActivity(
                            new Activity(
                                    savedData.getString(nameIndex),
                                    savedData.getString(descriptionIndex),
                                    new Time(savedData.getInt(startHourIndex), savedData.getInt(startMinuteIndex)),
                                    new Time(savedData.getInt(endHourIndex), savedData.getInt(endMinuteIndex)),
                                    new Link(savedData.getString(linkDisplayIndex), savedData.getString(linkIndex)),
                                    savedData.getInt(dayIndex),
                                    savedData.getInt(minutesBeforeIndex)
                            ));
        }

        savedData.close();
        return day;
    }

}
