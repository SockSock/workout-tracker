package com.example.workouttracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttracker.databinding.ActivityWorkoutCreatorBinding;

import java.util.ArrayList;

public class WorkoutCreator extends AppCompatActivity {

    private ActivityWorkoutCreatorBinding binding;
    private Workout workout;
    private ArrayList<Exercise> exerciseList;
    private RecyclerView recyclerView;
    private WorkoutCreatorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWorkoutCreatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String workoutName = getIntent().getStringExtra("workoutName");

        workout = new Workout(workoutName);
        exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise("Bench Press", 0, 0, 0, 2.5));
        exerciseList.add(new Exercise("Overhead Press", 0, 0, 0, 2.5));
        exerciseList.add(new Exercise("Barbell Squat", 0, 0, 0, 5));
        exerciseList.add(new Exercise("Lat Pulldown", 0, 0, 0, 2.5));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WorkoutCreatorAdapter(this, exerciseList);
        recyclerView.setAdapter(adapter);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Exercise> selectedExercises = adapter.getSelectedItems();
                for (Exercise exercise : selectedExercises) {
                    workout.addExercise(exercise);
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", workout);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}