package com.example.workouttracker;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class WorkoutViewModelFactory implements ViewModelProvider.Factory {
    private final WorkoutRepository workoutRepository;

    public WorkoutViewModelFactory(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WorkoutViewModel.class)) {
            return (T) new WorkoutViewModel(workoutRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

