package com.example.timetableapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetableapp.model.Activity;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityHolder> {

    private ArrayList<Activity> activities;

    public ActivityAdapter(ArrayList<Activity> activities){
        this.activities = activities;
    }

    public void update(ArrayList<Activity> newActivities){
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
        holder.setUri(selectedActivity.getActivityLink().getLink());
    }

    public class ActivityHolder extends RecyclerView.ViewHolder{

        TextView nameView, descriptionView, timeView, linkView;
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
            linkView = itemView.findViewById(R.id.linkView);
            linkView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            editView = itemView.findViewById(R.id.editImage);
            editView.setOnClickListener(e -> {
                Toast.makeText(itemView.getContext(), "Editing", Toast.LENGTH_SHORT).show();
                Intent editIntent = new Intent(itemView.getContext(), EditActivity.class);
                editIntent.putExtra("oldActivity", activities.get(getAdapterPosition()));
                itemView.getContext().startActivity(editIntent);
            });
            deleteView = itemView.findViewById(R.id.deleteImage);
            deleteView.setOnClickListener(e->{
                Toast.makeText(itemView.getContext(), "Delete", Toast.LENGTH_SHORT).show();
                MainActivity.getInstance().delete(activities.get(getAdapterPosition()));
                notifyDataSetChanged();
            });
            setAlarmBtn = itemView.findViewById(R.id.setAlarmBtn);
            setAlarmBtn.setOnClickListener(e->{
                Activity a = activities.get(getAdapterPosition());
                Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, a.getStartTime().getHour());
                alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, a.getStartTime().getMinute());
                alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, a.getName());
                alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                alarmIntent.putExtra(AlarmClock.EXTRA_DAYS, a.getRepeatingDay());
                itemView.getContext().startActivity(alarmIntent);
            });
            initLinkListener();
        }
        public void setUri(String link){
            this.link = link;
        }

        private void initLinkListener(){
            linkView.setOnClickListener(view -> {
                    try{
                        Intent openWebsite = new Intent(Intent.ACTION_VIEW);
                        openWebsite.setData(Uri.parse(link));
                        view.getContext().startActivity(openWebsite);
                    }catch(Exception e){
                        Toast.makeText(view.getContext(), "Invalid link",Toast.LENGTH_SHORT).show();
                    }

            });
        }
    }
}
