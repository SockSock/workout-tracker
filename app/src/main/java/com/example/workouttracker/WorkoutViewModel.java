package com.example.workouttracker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class WorkoutViewModel extends ViewModel {
    private MutableLiveData<List<Workout>> workouts;
    private WorkoutRepository workoutRepository;

    public WorkoutViewModel(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
        workouts = new MutableLiveData<>();
        loadWorkouts();
    }

    public LiveData<List<Workout>> getWorkouts() {
        return workouts;
    }

    public void loadWorkouts() {
        new Thread(() -> {
            List<Workout> workoutList = workoutRepository.getAllWorkouts();
            workouts.postValue(workoutList);
        }).start();
    }

    public void deleteWorkout(Workout workout) {
        new Thread(() -> {
            workoutRepository.deleteWorkout(workout);
            loadWorkouts();
        }).start();
    }
}
