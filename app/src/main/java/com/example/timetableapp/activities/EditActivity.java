package com.example.timetableapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timetableapp.R;
import com.example.timetableapp.model.Activity;
import com.example.timetableapp.model.Link;


public class EditActivity extends AppCompatActivity {
    private EditText nameET, descriptionET, linkET,
            linkDisplayET, minutesBeforeET;
    private TimePicker startTP, endTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity_dialog);
        initFields();
    }

    private void initFields(){
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

        minutesBeforeET = findViewById(R.id.editTextMinutesBefore);
        String minutesBeforeText = "" + oldActivity.getMinutesBeforeAlarm();
        minutesBeforeET.setText(minutesBeforeText);

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(e-> {
            MainActivity.getInstance().update(oldActivity, getNewActivity(oldActivity.getRepeatingDay()));
            finish();
        });

        Button cancelBtn = findViewById(R.id.cancleBtn);
        cancelBtn.setOnClickListener(e-> finish());
    }

    private Activity getNewActivity(int repeatingDay){
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
        activity.setRepeatingDay(repeatingDay);
        try {
            activity.setMinutesBeforeAlarm(Integer.parseInt(minutesBeforeET.getText().toString()));
        }catch(NumberFormatException e){
            Log.e("EditActivity", "Minutes before empty" );
        }
        return activity;
    }
}
