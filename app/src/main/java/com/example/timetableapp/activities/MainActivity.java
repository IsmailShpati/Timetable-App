package com.example.timetableapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.timetableapp.ActivityAdapter;
import com.example.timetableapp.R;
import com.example.timetableapp.database.DataManager;
import com.example.timetableapp.model.Activity;
import com.example.timetableapp.model.Timetable;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Spinner titleSpinner;
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
        initFields();
    }

    public static MainActivity getInstance(){
        return mainActivity;
    }
    public DataManager getDataManager(){
        return dataManager;
    }

    private void initFields(){
        recyclerView = findViewById(R.id.recyclerView);
        addBtn = findViewById(R.id.addButton);
        addBtn.setOnClickListener(onClick->{
            Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
            addIntent.putExtra("day", titleSpinner.getSelectedItemPosition());
            startActivity(addIntent);
        });

        initSpinner();
        timetable = new Timetable(new DataManager(this));
        activityAdapter = new ActivityAdapter(timetable.getDay(titleSpinner.getSelectedItemPosition())
                .getActivities());
        recyclerView.setAdapter(activityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initSpinner(){
        titleSpinner = findViewById(R.id.spinnerTitle);

        ArrayAdapter<CharSequence> spinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.days,
                        android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSpinner.setAdapter(spinnerAdapter);
        titleSpinner.setSelection(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1);
        titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activityAdapter.update(timetable.getDay(i).getActivities(), i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void addActivity(Activity activity){
        timetable.addActivity(activity);
        activityAdapter.update(
                timetable.getDay(titleSpinner.getSelectedItemPosition()).getActivities(),
                titleSpinner.getSelectedItemPosition());
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