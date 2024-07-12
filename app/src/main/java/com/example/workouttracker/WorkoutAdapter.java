package com.example.workouttracker;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private List<Workout> workoutList;

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView WorkoutName;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            WorkoutName = itemView.findViewById(R.id.WorkoutName);
        }
    }

    public WorkoutAdapter(List<Workout> workoutList) {
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
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), WorkoutDetailsActivity.class);
            intent.putExtra("workout", workout);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public void addWorkout(Workout workout) {
        workoutList.add(workout);
    }

    public void setWorkouts(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }
}
