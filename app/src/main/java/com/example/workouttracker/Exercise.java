package com.example.workouttracker;

import java.io.Serializable;

public class Exercise implements Serializable {
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

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getIncrement() {
        return increment;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }

    public void increaseWeight() {
        this.weight += this.increment;
    }

    public void decreaseWeight() {
        if (this.weight - this.increment >= 0) {
            this.weight -= this.increment;
        } else {
            this.weight = 0;
        }
    }
}
