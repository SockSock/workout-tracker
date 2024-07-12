package com.example.workouttracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class WorkoutsFragment extends Fragment {
    private RecyclerView recyclerView;
    private WorkoutAdapter workoutAdapter;
    private WorkoutDao workoutDao;
    private static final int REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);

        WorkoutDatabase db = WorkoutDatabase.getInstance(requireContext().getApplicationContext());
        workoutDao = db.workoutDao();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        workoutAdapter = new WorkoutAdapter(new ArrayList<>());
        recyclerView.setAdapter(workoutAdapter);

        FloatingActionButton newWorkoutButton = view.findViewById(R.id.NewWorkoutButton);
        newWorkoutButton.setOnClickListener(v -> showNewWorkoutDialog());

        loadWorkoutsFromDatabase();

        return view;
    }

    private void loadWorkoutsFromDatabase() {
        new Thread(() -> {
            List<Workout> workouts = workoutDao.getAllWorkouts();
            requireActivity().runOnUiThread(() -> {
                workoutAdapter.setWorkouts(workouts);
                workoutAdapter.notifyDataSetChanged();
            });
        }).start();
    }

    private void showNewWorkoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Create New Workout");

        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_new_workout, null);
        builder.setView(dialogView);

        EditText editTextWorkoutName = dialogView.findViewById(R.id.editTextWorkoutName);

        builder.setPositiveButton("Create", (dialog, which) -> {
            String workoutName = editTextWorkoutName.getText().toString().trim();
            if (workoutName.isEmpty()) {
                workoutName = "Untitled Workout";
            }
            startWorkoutCreator(workoutName);
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

    private void startWorkoutCreator(String workoutName) {
        Intent intent = new Intent(requireActivity(), WorkoutCreator.class);
        intent.putExtra("workoutName", workoutName);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Workout returnedData = (Workout) data.getSerializableExtra("result");
            if (returnedData != null) {
                new Thread(() -> {
                    workoutDao.insert(returnedData); // Insert the returned workout into the database
                    requireActivity().runOnUiThread(() -> {
                        workoutAdapter.addWorkout(returnedData); // Update the adapter with the new workout
                        workoutAdapter.notifyDataSetChanged();
                    });
                }).start();
            }
        }
    }
}
