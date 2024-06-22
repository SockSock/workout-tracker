package com.example.workouttracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private List<Workout> workouts;
    private OnItemClickListener listener;

    public WorkoutAdapter(List<Workout> workouts, OnItemClickListener listener) {
        this.workouts = workouts;
        this.listener = listener;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout workout = workouts.get(position);
        holder.bind(workout, listener);
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Workout workout);
    }

    static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewWorkoutName;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWorkoutName = itemView.findViewById(R.id.text_workout_name);
        }

        public void bind(Workout workout, OnItemClickListener listener) {
            textViewWorkoutName.setText(workout.getName());
            itemView.setOnClickListener(v -> listener.onItemClick(workout));
        }
    }
}