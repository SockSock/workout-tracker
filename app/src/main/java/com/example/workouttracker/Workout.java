package com.example.workouttracker;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "workouts")
public class Workout {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @TypeConverters(ExerciseTypeConverter.class)
    private List<Exercise> exercises;

    public Workout(String name) {
        this.name = name;
        this.exercises = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
    }
}
