package com.example.workouttracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutsFragment extends Fragment implements WorkoutNameDialogFragment.WorkoutNameDialogListener {
    private static final int REQUEST_CODE_NEW_WORKOUT = 1;
    private static final int REQUEST_CODE_VIEW_WORKOUT = 2;

    private WorkoutViewModel workoutViewModel;
    private WorkoutAdapter workoutAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.workouts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        workoutAdapter = new WorkoutAdapter(new ArrayList<>(), this::onWorkoutClicked);
        recyclerView.setAdapter(workoutAdapter);

        WorkoutRepository workoutRepository = new WorkoutRepository(getContext());
        WorkoutViewModelFactory factory = new WorkoutViewModelFactory(workoutRepository);
        workoutViewModel = new ViewModelProvider(this, factory).get(WorkoutViewModel.class);

        workoutViewModel.getWorkouts().observe(getViewLifecycleOwner(), workouts -> {
            workoutAdapter.setWorkouts(workouts);
            workoutAdapter.notifyDataSetChanged();
        });

        Button newWorkout = view.findViewById(R.id.new_workout);
        newWorkout.setOnClickListener(v -> showWorkoutNameDialog());

        return view;
    }

    private void showWorkoutNameDialog() {
        WorkoutNameDialogFragment dialogFragment = new WorkoutNameDialogFragment();
        dialogFragment.setListener(this);
        dialogFragment.show(getParentFragmentManager(), "WorkoutNameDialogFragment");
    }

    @Override
    public void onWorkoutNameEntered(String workoutName) {
        Intent intent = new Intent(getActivity(), ExerciseListActivity.class);
        intent.putExtra("workoutName", workoutName);
        startActivityForResult(intent, REQUEST_CODE_NEW_WORKOUT);
    }

    private void onWorkoutClicked(Workout workout) {
        Intent intent = new Intent(getActivity(), WorkoutDetailActivity.class);
        intent.putExtra("workoutId", workout.getId());
        startActivityForResult(intent, REQUEST_CODE_VIEW_WORKOUT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            workoutViewModel.loadWorkouts();
        }
    }
}
