package com.example.timetableapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timetableapp.model.Activity;
import com.example.timetableapp.model.Link;

import java.util.Calendar;


public class EditActivity extends AppCompatActivity {
    private EditText nameET, descriptionET, linkET,
            linkDisplayET;
    private TimePicker startTP, endTP;
    private Button saveBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity_dialog);
        initFields();
    }

    private void initFields(){
       // Bundle bundle = getIntent().getExtras();
        final Activity oldActivity = (Activity)getIntent().getSerializableExtra("oldActivity");

        nameET = findViewById(R.id.editTextName);
        nameET.setText(oldActivity.getName());

        descriptionET = findViewById(R.id.editTextDescription);
        descriptionET.setText(oldActivity.getDescription());

        linkDisplayET = findViewById(R.id.editTextLinkDisplay);
        linkDisplayET.setText(oldActivity.getActivityLink().getDisplayText());

        linkET = findViewById(R.id.editTextLink);
        linkET.setText(oldActivity.getActivityLink().getLink());

        startTP = findViewById(R.id.timePickerStart);
        startTP.setHour(oldActivity.getStartTime().getHour());
        startTP.setMinute(oldActivity.getStartTime().getMinute());

        endTP = findViewById(R.id.timePickerEnd);
        endTP.setHour(oldActivity.getEndTime().getHour());
        endTP.setMinute(oldActivity.getEndTime().getMinute());

        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(e-> {
            MainActivity.getInstance().update(oldActivity, getNewActivity(oldActivity.getRepeatingDay()));
            finish();
        });


        cancelBtn = findViewById(R.id.cancleBtn);
        cancelBtn.setOnClickListener(e->{
            finish();
        });
    }

    private Activity getNewActivity(int repeatingDay){
        Activity activity = new Activity();
        activity.setName(nameET.getText().toString());
        activity.setDescription(descriptionET.getText().toString());
        activity.setActivityLink(new Link(linkDisplayET.getText().toString(),
                linkET.getText().toString()));
        activity.setStartTime(startTP.getHour(), startTP.getMinute());
        activity.setEndTime(endTP.getHour(), endTP.getMinute());
        activity.setRepeatingDay(repeatingDay);
        return activity;
    }

    private Activity getOldActivity(Bundle bundle){
        Activity activity = new Activity();
        activity.setName(bundle.getString("name"));
        activity.setDescription(bundle.getString("description"));
        activity.setActivityLink(new Link(bundle.getString("displayLink"), bundle.getString("link")));
        activity.setStartTime(bundle.getInt("startHour"), bundle.getInt("startMinute"));
        activity.setEndTime(bundle.getInt("endHour"), bundle.getInt("endMinute"));
        activity.setRepeatingDay(bundle.getInt("day"));
        return activity;
    }
}
