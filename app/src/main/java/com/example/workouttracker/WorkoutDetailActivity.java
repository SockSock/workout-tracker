package com.example.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutDetailActivity extends AppCompatActivity {
    private Workout workout;
    private WorkoutRepository workoutRepository;
    private ExerciseAdapter exerciseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        workoutRepository = new WorkoutRepository(this);

        Intent intent = getIntent();
        int workoutId = intent.getIntExtra("workoutId", -1);

        if (workoutId != -1) {
            new Thread(() -> {
                workout = workoutRepository.getWorkout(workoutId);
                runOnUiThread(this::populateWorkoutDetails);
            }).start();
        }

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            if (workout != null) {
                new Thread(() -> {
                    workoutRepository.deleteWorkout(workout);
                    runOnUiThread(() -> {
                        setResult(RESULT_OK);
                        finish();
                    });
                }).start();
            }
        });
    }

    private void populateWorkoutDetails() {
        TextView workoutName = findViewById(R.id.workout_name);
        workoutName.setText(workout.getName());

        RecyclerView exercisesRecyclerView = findViewById(R.id.exercises_recycler_view);
        exercisesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        exerciseAdapter = new ExerciseAdapter(workout.getExercises());
        exercisesRecyclerView.setAdapter(exerciseAdapter);
    }
}
