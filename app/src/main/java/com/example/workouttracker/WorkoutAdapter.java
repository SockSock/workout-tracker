package com.example.workouttracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private ArrayList<Workout> workoutList;

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView WorkoutName;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            WorkoutName = itemView.findViewById(R.id.WorkoutName);
        }
    }

    public WorkoutAdapter(ArrayList<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout workout = workoutList.get(position);
        holder.WorkoutName.setText(workout.getName());
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }
}
