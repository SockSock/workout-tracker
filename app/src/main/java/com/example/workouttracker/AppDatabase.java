package com.example.workouttracker;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Workout.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WorkoutDao workoutDao();
}
