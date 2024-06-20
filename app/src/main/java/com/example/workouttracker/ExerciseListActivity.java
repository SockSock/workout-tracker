package com.example.workouttracker;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ExerciseListActivity extends AppCompatActivity {

    private Workout currentWorkout;
    private ArrayList<Exercise> selectedExercises;
    private ArrayList<Exercise> allExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        currentWorkout = new Workout("My Workout");
        selectedExercises = new ArrayList<>();
        allExercises = new ArrayList<>();

        allExercises.add(new Exercise("a", 0, 0, 0, 0));
        allExercises.add(new Exercise("b", 0, 0, 0, 0));
        allExercises.add(new Exercise("c", 0, 0, 0, 0));
        allExercises.add(new Exercise("d", 0, 0, 0, 0));

        ListView exerciseListView = findViewById(R.id.exercise_list_view);
        ArrayAdapter<Exercise> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, allExercises);
        exerciseListView.setAdapter(adapter);
        exerciseListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise exercise = allExercises.get(position);
                if (selectedExercises.contains(exercise)) {
                    selectedExercises.remove(exercise);
                } else {
                    selectedExercises.add(exercise);
                }
            }
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Exercise exercise : selectedExercises) {
                    currentWorkout.addExercise(exercise);
                }
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
