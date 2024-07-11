package com.example.workouttracker;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class WorkoutsFragment extends Fragment {
    private RecyclerView recyclerView;
    private WorkoutAdapter workoutAdapter;
    private ArrayList<Workout> workoutList;
    private static final int REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        workoutList = new ArrayList<>();
        workoutAdapter = new WorkoutAdapter(workoutList);
        recyclerView.setAdapter(workoutAdapter);

        FloatingActionButton newWorkoutButton = view.findViewById(R.id.NewWorkoutButton);
        newWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewWorkoutDialog();
            }
        });

        return view;
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
        Intent intent = new Intent(getActivity(), WorkoutCreator.class);
        intent.putExtra("workoutName", workoutName);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Workout returnedData = (Workout) data.getSerializableExtra("result");
            workoutList.add(returnedData);
            workoutAdapter.notifyDataSetChanged();
        }
    }
}
