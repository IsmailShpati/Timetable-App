package com.example.timetableapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timetableapp.R;
import com.example.timetableapp.model.Activity;
import com.example.timetableapp.model.Link;

public class AddActivity extends AppCompatActivity {
    private final MainActivity mainActivity;
    private EditText nameET, descriptionET, linkET,
            linkDisplayET, minutesBeforeET;
    private TimePicker startTP, endTP;
    private Button saveBtn, cancelBtn;
    private int day;

    public AddActivity(){
        mainActivity = MainActivity.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity_dialog);
        day = getIntent().getIntExtra("day", 0);
        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancleBtn);
        initViews();

    }

    private void initViews(){
        nameET = findViewById(R.id.editTextName);
        descriptionET = findViewById(R.id.editTextDescription);
        linkET = findViewById(R.id.editTextLink);
        linkDisplayET = findViewById(R.id.editTextLinkDisplay);
        startTP = findViewById(R.id.timePickerStart);
        endTP = findViewById(R.id.timePickerEnd);
        minutesBeforeET = findViewById(R.id.editTextMinutesBefore);
    }


    public void onCancel(View view) {
        finish();
    }

    public void onSave(View view) {
        mainActivity.addActivity(getActivity());
        finish();
    }

    private Activity getActivity(){
        Activity activity = new Activity();
        activity.setName(nameET.getText().toString());
        activity.setDescription(descriptionET.getText().toString());
        if(linkDisplayET.getText().length() < 1)
            activity.setActivityLink(new Link(linkET.getText().toString(),
                linkET.getText().toString()));
        else
            activity.setActivityLink(new Link(linkDisplayET.getText().toString(),
                    linkET.getText().toString()));
        activity.setStartTime(startTP.getHour(), startTP.getMinute());
        activity.setEndTime(endTP.getHour(), endTP.getMinute());
        activity.setRepeatingDay(day);
        try {
            activity.setMinutesBeforeAlarm(Integer.parseInt(minutesBeforeET.getText().toString()));
        }catch(NumberFormatException e){
            Log.e("[AddActivity]", "Minutes Empty");
        }
        return activity;
    }
}
