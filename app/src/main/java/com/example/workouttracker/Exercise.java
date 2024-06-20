package com.example.workouttracker;

import androidx.annotation.NonNull;

public class Exercise {
    private String name;
    private int sets;
    private int reps;
    private double weight;
    private double increment;

    public Exercise(String name, int sets, int reps, double weight, double increment) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.increment = increment;
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    public double getIncrement() {
        return increment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }

    public void increaseWeight() {
        weight += increment;
    }

    public void decreaseWeight() {
        weight -= increment;
        if (weight < 0) {
            weight = 0;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
