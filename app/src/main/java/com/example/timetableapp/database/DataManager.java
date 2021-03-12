package com.example.timetableapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.timetableapp.model.Activity;
import com.example.timetableapp.model.Day;

import java.util.ArrayList;

public class DataManager {

    private final SQLiteDatabase database;

    public final static String DB_NAME = "timetable_database";
    public final static int DB_VERSION = 1;
    public final static String TABLE_ACTIVITIES = "days_table";

    public final static String TABLE_ROW_ID = "_id";
    public final static String TABLE_ROW_NAME = "name";
    public final static String TABLE_ROW_DESCRIPTION = "description";
    public final static String TABLE_ROW_LINK_DISPLAY = "link_display";
    public final static String TABLE_ROW_LINK = "link";
    public final static String TABLE_ROW_START_HOUR = "start_hour";
    public final static String TABLE_ROW_START_MINUTE = "start_minute";
    public final static String TABLE_ROW_END_HOUR = "end_hour";
    public final static String TABLE_ROW_END_MINUTE = "end_minute";
    public final static String TABLE_ROW_DAY = "day";

    public DataManager(Context context){
        CustomSQLhelper sqlHelper =
                new CustomSQLhelper(context, DB_NAME, null, DB_VERSION);
        database = sqlHelper.getWritableDatabase();
    }

    public void insert(Activity activity){
        String insertQuery = "INSERT INTO " + TABLE_ACTIVITIES +
                " ("  + TABLE_ROW_NAME+
                ", " + TABLE_ROW_DESCRIPTION +
                ", " + TABLE_ROW_DAY +
                ", " + TABLE_ROW_LINK_DISPLAY +
                ", " + TABLE_ROW_LINK +
                ", " + TABLE_ROW_START_HOUR +
                ", " + TABLE_ROW_START_MINUTE +
                ", " + TABLE_ROW_END_HOUR +
                ", " + TABLE_ROW_END_MINUTE
                +") VALUES " +
                "('"   + activity.getName() +
                "', '" + activity.getDescription() +
                "', " + activity.getRepeatingDay() +
                ", '" + activity.getActivityLink().getDisplayText() +
                "', '" + activity.getActivityLink().getLink() +
                "', " + activity.getStartTime().getHour() +
                ", " + activity.getStartTime().getMinute() +
                ", " + activity.getEndTime().getHour() +
                ", " + activity.getEndTime().getMinute() +
                ");";
        Log.e("insert()", insertQuery);
        database.execSQL(insertQuery);
    }

    public ArrayList<Day> selectAll(){
        return DataParser.getDaysData(database.rawQuery("SELECT * FROM " + TABLE_ACTIVITIES, null));
    }

    public Day selectDay(int day){
        String selectQuery = "SELECT * FROM " + TABLE_ACTIVITIES + " WHERE " + TABLE_ROW_DAY + "=" + day;
        return DataParser.getDayData(database.rawQuery(selectQuery, null));
    }

    public void deleteAll(){
        database.execSQL("DELETE FROM " + TABLE_ACTIVITIES+";");
    }
    public void delete(Activity activity){
        String deleteQuery = "DELETE FROM " + TABLE_ACTIVITIES + " WHERE " +
                TABLE_ROW_DAY +" = "+activity.getRepeatingDay() + " AND " +
                TABLE_ROW_NAME + "='" + activity.getName() + "' AND " +
                TABLE_ROW_START_HOUR + "=" + activity.getStartTime().getHour()+
                ";";
        Log.e("delete()", deleteQuery);
        database.execSQL(deleteQuery);
    }

    public void update(Activity oldActivity, Activity newActivity) {
        String updateQuery = "UPDATE " + TABLE_ACTIVITIES +
                " SET " +
                TABLE_ROW_NAME + " = '" + newActivity.getName() + "', " +
                TABLE_ROW_DESCRIPTION + " = '" + newActivity.getDescription() + "', " +
                TABLE_ROW_LINK + " = '" + newActivity.getActivityLink().getLink() + "', " +
                TABLE_ROW_LINK_DISPLAY + " = '" + newActivity.getActivityLink().getDisplayText() + "', " +
                TABLE_ROW_START_HOUR + " = " + newActivity.getStartTime().getHour() + ", " +
                TABLE_ROW_START_MINUTE + " = " + newActivity.getStartTime().getMinute() + ", " +
                TABLE_ROW_END_HOUR + " = " + newActivity.getEndTime().getHour() + ", " +
                TABLE_ROW_END_MINUTE + " = " + newActivity.getEndTime().getMinute() +
                " WHERE " +
                TABLE_ROW_DAY + " = " + oldActivity.getRepeatingDay() + " AND " +
                TABLE_ROW_NAME +" ='" + oldActivity.getName() + "' AND " +
                TABLE_ROW_START_HOUR + " = " + oldActivity.getStartTime().getHour()+";";

        Log.e("update()", updateQuery);
        database.execSQL(updateQuery);
    }

    //  public void execQuery(String query){
//        database.execSQL(query);
//    }



    public class CustomSQLhelper extends SQLiteOpenHelper{

        public CustomSQLhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
                String newTableQuery = "CREATE TABLE " + TABLE_ACTIVITIES + " ("+
                        TABLE_ROW_ID + " integer not null primary key autoincrement, " +
                        TABLE_ROW_NAME + " text not null, " +
                        TABLE_ROW_DESCRIPTION + " text, " +
                        TABLE_ROW_DAY + " integer not null, " +
                        TABLE_ROW_LINK_DISPLAY + " text," +
                        TABLE_ROW_LINK + " text, " +
                        TABLE_ROW_START_HOUR + " integer, " +
                        TABLE_ROW_START_MINUTE + " integer, " +
                        TABLE_ROW_END_HOUR + " integer, " +
                        TABLE_ROW_END_MINUTE + " integer " +
                        ");";
               database.execSQL(newTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }
    }
}
