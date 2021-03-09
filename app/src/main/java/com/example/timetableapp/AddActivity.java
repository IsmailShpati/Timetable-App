package com.example.timetableapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timetableapp.model.Activity;
import com.example.timetableapp.model.Link;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private MainActivity mainActivity;
    private EditText nameET, descriptionET, linkET,
            linkDisplayET;
    private TimePicker startTP, endTP;
    private Button saveBtn, cancelBtn;

    public AddActivity(){
        mainActivity = MainActivity.getInstance();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity_dialog);
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
        activity.setActivityLink(new Link(linkDisplayET.getText().toString(),
                linkET.getText().toString()));
        activity.setStartTime(startTP.getHour(), startTP.getMinute());
        activity.setEndTime(endTP.getHour(), endTP.getMinute());
        activity.setRepeatingDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        return activity;
    }
}
