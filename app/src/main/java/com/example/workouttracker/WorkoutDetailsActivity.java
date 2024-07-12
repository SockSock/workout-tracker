package com.example.workouttracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class WorkoutDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewExercises;
    private ExerciseAdapter exerciseAdapter;
    private ArrayList<Exercise> exerciseList;
    private Workout workout;
    private WorkoutDao workoutDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        // Retrieve workout from intent
        workout = (Workout) getIntent().getSerializableExtra("workout");
        exerciseList = workout.getExercises();

        // Initialize Room database
        WorkoutDatabase db = WorkoutDatabase.getInstance(getApplicationContext());
        workoutDao = db.workoutDao();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(workout.getName());

        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerViewExercises = findViewById(R.id.recyclerViewExercises);
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(this));
        exerciseAdapter = new ExerciseAdapter(exerciseList);
        recyclerViewExercises.setAdapter(exerciseAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.workout_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_workout) {
            showDeleteConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Workout")
                .setMessage("Are you sure you want to delete this workout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteWorkout();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteWorkout() {
        new Thread(() -> {
            workoutDao.delete(workout);
            finish();
        }).start();
    }
}
