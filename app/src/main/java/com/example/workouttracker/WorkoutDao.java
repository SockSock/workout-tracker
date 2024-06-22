package com.example.workouttracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface WorkoutDao {
    @Insert
    void insert(Workout workout);

    @Query("SELECT * FROM workouts WHERE id = :id")
    Workout getWorkoutById(int id);

    @Query("SELECT * FROM workouts")
    List<Workout> getAllWorkouts();

    @Delete
    void delete(Workout workout);
}
