package com.example.timetableapp;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            initClickListener();
        }
        public void setUri(String link){
            this.link = link;
        }

        private void initClickListener(){
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
