package com.example.workouttracker;

import android.content.Context;
import androidx.room.Room;

import java.util.List;

public class WorkoutRepository {

    private AppDatabase database;
    private WorkoutDao workoutDao;

    public WorkoutRepository(Context context) {
        database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "workout-database").build();
        workoutDao = database.workoutDao();
    }

    public void addWorkout(Workout workout) {
        new Thread(() -> workoutDao.insert(workout)).start();
    }

    public Workout getWorkout(int id) {
        return workoutDao.getWorkoutById(id);
    }

    public void deleteWorkout(Workout workout) {
        new Thread(() -> workoutDao.delete(workout)).start();
    }

    public List<Workout> getAllWorkouts() {
        return workoutDao.getAllWorkouts();
    }
}

