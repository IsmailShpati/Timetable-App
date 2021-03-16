package com.example.timetableapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetableapp.activities.EditActivity;
import com.example.timetableapp.activities.MainActivity;
import com.example.timetableapp.model.Activity;
import com.example.timetableapp.model.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityHolder> {

    private ArrayList<Activity> activities;
    public ActivityAdapter(ArrayList<Activity> activities){
        this.activities = activities;
    }

    public void update(ArrayList<Activity> newActivities, int currentDay){
        Collections.sort(newActivities);
        this.activities = newActivities;
        notifyDataSetChanged();
    }

    public void update(Activity newActivity, Activity oldActivity){
        int index = getActivityIndex(oldActivity);
        activities.set(index, newActivity);
        notifyItemChanged(index);
    }

    private int getActivityIndex(Activity activity){
        for(int i = 0; i < activities.size(); i++)
            if(activities.get(i).getName().equals(activity.getName()))
                if(activities.get(i).getDescription().equals(activity.getDescription()))
                    if(activities.get(i).getStartTime().getHour() == activity.getStartTime().getHour())
                        return i;

        return -1;
    }
    @NonNull
    @Override
    public ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_layout, parent, false);
        return new ActivityHolder(view);
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityHolder holder, int position) {
        Activity selectedActivity = activities.get(position);
        holder.nameView.setText(selectedActivity.getName());
        holder.descriptionView.setText(selectedActivity.getDescription());
        holder.timeView.setText(selectedActivity.getTimeString());
        holder.linkView.setText(selectedActivity.getActivityLink().getDisplayText());
        String minutesBeforeText = "Minutes before the alarm " + selectedActivity.getMinutesBeforeAlarm();
        holder.minutesBeforeView.setText(minutesBeforeText);
        holder.setUri(selectedActivity.getActivityLink().getLink());
    }

    public class ActivityHolder extends RecyclerView.ViewHolder{

        TextView nameView, descriptionView, timeView, linkView, minutesBeforeView;
        ImageView editView, deleteView;
        Button setAlarmBtn;
        private String link;
        public ActivityHolder(@NonNull View itemView) {
            super(itemView);
            initFields();
        }

        private void initFields(){
            nameView = itemView.findViewById(R.id.nameView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
            timeView = itemView.findViewById(R.id.timeView);
            minutesBeforeView = itemView.findViewById(R.id.minutesBeforeView);

            linkView = itemView.findViewById(R.id.linkView);
            linkView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            linkView.setOnClickListener(getLinkClickListener());

            editView = itemView.findViewById(R.id.editImage);
            editView.setOnClickListener(getEditClickListener());

            deleteView = itemView.findViewById(R.id.deleteImage);
            deleteView.setOnClickListener(getDeleteClickListener());

            setAlarmBtn = itemView.findViewById(R.id.setAlarmBtn);
            setAlarmBtn.setOnClickListener(getAlarmClickListener());

        }
        public void setUri(String link){
            this.link = link;
        }

        private View.OnClickListener getEditClickListener(){
            return e->{
                Intent editIntent = new Intent(itemView.getContext(), EditActivity.class);
                editIntent.putExtra("oldActivity", activities.get(getAdapterPosition()));
                itemView.getContext().startActivity(editIntent);
            };
        }

        private View.OnClickListener getDeleteClickListener(){
            return e->{
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(itemView.getContext());
                alertBuilder.setPositiveButton("Yes", (dialogInterface, i) -> {
                    MainActivity.getInstance().delete(activities.get(getAdapterPosition()));
                    notifyDataSetChanged();
                    dialogInterface.dismiss();
                });
                alertBuilder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
                alertBuilder.setMessage("Are you sure you want to delete this activity?");
                AlertDialog alertDialog = alertBuilder.create();
                alertBuilder.show();
            };
        }

        private View.OnClickListener getAlarmClickListener(){
            return view -> {
                Activity activity = activities.get(getAdapterPosition());
                int hoursUntil = Time.getHoursUntil(activity, Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1);
                Log.e("AlarmAt", "Hours until: " + hoursUntil);
                Time alarmTime = Time.calculateTimeBeforeAlert(activity.getStartTime(), activity.getMinutesBeforeAlarm());
                if(hoursUntil < 0) {
                    Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);

                    alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, alarmTime.getHour());
                    alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, alarmTime.getMinute());
                    alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, activity.getName());
                    alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                    itemView.getContext().startActivity(alarmIntent);
                }
                else{
                    AlarmManager alarmManager =
                            (AlarmManager)itemView.getContext().getSystemService(Context.ALARM_SERVICE);
                    Intent alarmIntent = new Intent(itemView.getContext(), AlarmBroadcastReceiver.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", activity.getName());
                    bundle.putInt("hour", alarmTime.getHour());
                    bundle.putInt("minute", alarmTime.getMinute());
                    alarmIntent.putExtras(bundle);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(itemView.getContext(),
                            0, alarmIntent, 0);
                    alarmManager.set(AlarmManager.ELAPSED_REALTIME,
                            SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HOUR * hoursUntil,
                            pendingIntent);
                    Toast.makeText(itemView.getContext(), "The alarm will be set approx at " +
                            "0-1 Am on the specified day, after " + hoursUntil + " hours", Toast.LENGTH_SHORT).show();
                }
            };
        }

        private View.OnClickListener getLinkClickListener(){
            return view -> {
                    try{
                        Intent openWebsite = new Intent(Intent.ACTION_VIEW);
                        openWebsite.setData(Uri.parse(link));
                        view.getContext().startActivity(openWebsite);
                    }catch(Exception e){
                        Toast.makeText(view.getContext(), "Invalid link",Toast.LENGTH_SHORT).show();
                    }
            };
        }
    }
}
