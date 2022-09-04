package com.example.workouttracker;

import java.util.Date;

public class WorkoutInterfaceClass {

    public int getActualReps() {
        return actualReps;
    }

    public void setActualReps(int actualReps) {
        this.actualReps = actualReps;
    }

    public WorkoutInterfaceClass(int numberOfSets, String numberOfReps,String weights, int timeBetweenSets, String workoutName, boolean weightOrReps, int actualReps,String pressedButtons) {
        this.numberOfSets = numberOfSets;
        this.numberOfReps = numberOfReps;
        this.weights=weights;
        this.timeBetweenSets = timeBetweenSets;
        this.workoutName = workoutName;
        this.weightOrReps = weightOrReps;
        this.actualReps=actualReps;


    }

    int numberOfSets;
    String numberOfReps;
    int timeBetweenSets;
    String workoutName;
    boolean weightOrReps;
    int actualReps;
    String weights;
    String pressedButtons;

    @Override
    public String toString() {
        return "WorkoutInterfaceClass{" +
                "numberOfSets=" + numberOfSets +
                ", numberOfReps='" + numberOfReps + '\'' +
                ", timeBetweenSets=" + timeBetweenSets +
                ", workoutName='" + workoutName + '\'' +
                ", weightOrReps=" + weightOrReps +
                ", actualReps=" + actualReps +
                ", weights='" + weights + '\'' +
                ", pressedButtons='" + pressedButtons + '\'' +
                '}';
    }

    public String getPressedButtons() {
        return pressedButtons;
    }

    public void setPressedButtons(String pressedButtons) {
        this.pressedButtons = pressedButtons;
    }

    public int getNumberOfSets() {
        return numberOfSets;
    }

    public void setNumberOfSets(int numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    public String getNumberOfReps() {
        return numberOfReps;
    }

    public void setNumberOfReps(String numberOfReps) {
        this.numberOfReps = numberOfReps;
    }

    public int getTimeBetweenSets() {
        return timeBetweenSets;
    }

    public void setTimeBetweenSets(int timeBetweenSets) {
        this.timeBetweenSets = timeBetweenSets;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public boolean isWeightOrReps() {
        return weightOrReps;
    }

    public void setWeightOrReps(boolean weightOrReps) {
        this.weightOrReps = weightOrReps;
    }

    public String getWeights() {
        return weights;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }
}
