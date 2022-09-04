package com.example.workouttracker;

import java.util.ArrayList;

public class WorkoutClass {
    String workoutName;
    int numberOfSets;
    double timeBetweenSets;
    String numberOfReps;
    String weights;
    boolean weightOrReps;// weight is true
    String description;
    String pressedButtons;



    //Constructor:
    public WorkoutClass(String workoutName, int numberOfSets, double timeBetweenSets, String numberOfReps,String weights, boolean weightOrReps,String pressedButtons ,String description) {
        this.workoutName = workoutName;
        this.numberOfSets = numberOfSets;
        this.timeBetweenSets = timeBetweenSets;
        this.numberOfReps = numberOfReps;
        this.weights=weights;
        this.weightOrReps = weightOrReps;
        this.description = description;
        this.pressedButtons=pressedButtons;


    }



    //  toString() method

    @Override
    public String toString() {
        return "WorkoutClass{" +
                "workoutName='" + workoutName + '\'' +
                ", numberOfSets=" + numberOfSets +
                ", timeBetweenSets=" + timeBetweenSets +
                ", numberOfReps='" + numberOfReps + '\'' +
                ", weights='" + weights + '\'' +
                ", weightOrReps=" + weightOrReps +
                ", description='" + description + '\'' +
                ", pressedButtons='" + pressedButtons + '\'' +
                '}';
    }

    //Getters & Setters:

    public String getPressedButtons() {
        return pressedButtons;
    }

    public void setPressedButtons(String pressedButtons) {
        this.pressedButtons = pressedButtons;
    }

    public boolean isWeightOrReps() {
        return weightOrReps;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public int getNumberOfSets() {
        return numberOfSets;
    }

    public void setNumberOfSets(int numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    public double getTimeBetweenSets() {
        return timeBetweenSets;
    }

    public void setTimeBetweenSets(double timeBetweenSets) {
        this.timeBetweenSets = timeBetweenSets;
    }

    public String getNumberOfReps() {
        return numberOfReps;
    }

    public void setNumberOfReps(String numberOfReps) {
        this.numberOfReps = numberOfReps;
    }
    public String getWeights() {
        return weights;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }

    public boolean getWeightOrReps() {
        return weightOrReps;
    }

    public void setWeightOrReps(boolean weightOrReps) {
        this.weightOrReps = weightOrReps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
