package com.example.workouttracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ExerciseListActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SET_ATTRIBUTES = 1;
    private static final String EXTRA_WORKOUT_NAME = "workoutName";

    private Workout currentWorkout;
    private ArrayList<Exercise> selectedExercises;
    private ArrayList<Exercise> allExercises;
    private int currentSelectedPosition = -1;
    private WorkoutRepository workoutRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        workoutRepository = new WorkoutRepository(this);
        String workoutName = getIntent().getStringExtra("workoutName");
        currentWorkout = new Workout(workoutName); // Default name, can be overridden
        selectedExercises = new ArrayList<>();
        allExercises = new ArrayList<>();

        allExercises.add(new Exercise("Bench Press", 0, 0, 0, 0));
        allExercises.add(new Exercise("Overhead Press", 0, 0, 0, 0));
        allExercises.add(new Exercise("Squat", 0, 0, 0, 0));
        allExercises.add(new Exercise("Lat Pulldown", 0, 0, 0, 0));

        ListView exerciseListView = findViewById(R.id.exercise_list_view);
        ArrayAdapter<Exercise> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, allExercises);
        exerciseListView.setAdapter(adapter);
        exerciseListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        exerciseListView.setOnItemClickListener((parent, view, position, id) -> {
            currentSelectedPosition = position;
            Exercise exercise = allExercises.get(position);
            if (selectedExercises.contains(exercise)) {
                selectedExercises.remove(exercise);
            } else {
                selectedExercises.add(exercise);
                Intent intent = new Intent(ExerciseListActivity.this, SetExerciseAttributesActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SET_ATTRIBUTES);
            }
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            for (Exercise exercise : selectedExercises) {
                currentWorkout.addExercise(exercise);
            }
            new Thread(() -> {
                workoutRepository.addWorkout(currentWorkout);
                runOnUiThread(() -> {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(EXTRA_WORKOUT_NAME, currentWorkout.getName());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                });
            }).start();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SET_ATTRIBUTES) {
            if (resultCode == RESULT_OK && data != null) {
                int sets = data.getIntExtra("sets", 0);
                int reps = data.getIntExtra("reps", 0);
                double weight = data.getDoubleExtra("weight", 0);
                double increment = data.getDoubleExtra("increment", 0);
                Exercise exercise = allExercises.get(currentSelectedPosition);
                exercise.setSets(sets);
                exercise.setReps(reps);
                exercise.setWeight(weight);
                exercise.setIncrement(increment);
            } else if (resultCode == RESULT_CANCELED) {
                Exercise exercise = allExercises.get(currentSelectedPosition);
                selectedExercises.remove(exercise);
                ListView exerciseListView = findViewById(R.id.exercise_list_view);
                exerciseListView.setItemChecked(currentSelectedPosition, false);
            }
        }
    }
}
