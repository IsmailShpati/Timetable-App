package com.example.timetableapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetableapp.model.Activity;
import com.example.timetableapp.model.Link;
import com.example.timetableapp.model.Time;
import com.example.timetableapp.model.Timetable;

import org.w3c.dom.Text;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView title;
    private Button addBtn;
    private static MainActivity mainActivity;
    private DataManager dataManager;
    private Timetable timetable;
    private ActivityAdapter activityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;


       // newAlarm.putExtra("scheduledAlarms", activities);

        initFields();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DayOfWeek day = LocalDate.now().getDayOfWeek();
            title.setText(day.name());
        }

        timetable = new Timetable(new DataManager(this));
        activityAdapter = new ActivityAdapter(timetable.getDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
                .getActivities());
        recyclerView.setAdapter(activityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(addIntent);
            }
        });
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        ArrayList<Activity> activities = timetable.getDay(2).getActivities();
//        Log.e("Main", "Size: " + activities.size());
//        for(int  i = 0 ; i < 10; i++){
//            Bundle bundle = new Bundle();
//            final int id = (int)System.currentTimeMillis();
//            bundle.putInt("hour", i + 1);
//            bundle.putInt("minute", i+1);
////            bundle.putString("name", activities.get(i).getName());
////            bundle.putInt("day", activities.get(i).getRepeatingDay());
//            Log.e("Main", "Activity " + i);
//            Intent newAlarm = new Intent(this, AlarmBroadcastReceiver.class);
//            newAlarm.putExtras(bundle);
//            PendingIntent pendingAlarm = PendingIntent.getBroadcast(this, id, newAlarm, PendingIntent.FLAG_ONE_SHOT);
//            alarmManager.set(AlarmManager.ELAPSED_REALTIME,
//                    SystemClock.elapsedRealtime()+10000 , pendingAlarm);
//        }
//        Toast.makeText(this, "Wait 2 seconds", Toast.LENGTH_SHORT).show();
        }

        public DataManager getDataManager(){
            return dataManager;
        }

    private void initFields(){
        recyclerView = findViewById(R.id.recyclerView);
        addBtn = findViewById(R.id.addButton);
        title = findViewById(R.id.textView);
    }

    public void addActivity(Activity activity){
        Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
        timetable.addActivity(activity);
        activityAdapter.update(timetable.getCurrentDay().getActivities());
    }

    public static MainActivity getInstance(){
        return mainActivity;
    }

    public void delete(Activity activity) {
        timetable.removeActivity(activity);
    }

    public void update(Activity oldActivity, Activity newActivity){
        timetable.updateActivity(oldActivity, newActivity);
        activityAdapter.update(newActivity, oldActivity);
        Toast.makeText(this, "Edited Successfully", Toast.LENGTH_SHORT).show();
    }
}