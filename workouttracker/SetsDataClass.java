package com.example.workouttracker;

public class SetsDataClass {
    String setWeight;
    String setReps;
    public SetsDataClass(String setWeight, String setReps) {
        this.setWeight = setWeight;
        this.setReps = setReps;
    }

    @Override
    public String toString() {
        return "SetsDataClass{" +
                "setWeight='" + setWeight + '\'' +
                ", setReps='" + setReps + '\'' +
                '}';
    }

    public String getSetWeight() {
        return setWeight;
    }

    public void setSetWeight(String setWeight) {
        this.setWeight = setWeight;
    }

    public String getSetReps() {
        return setReps;
    }

    public void setSetReps(String setReps) {
        this.setReps = setReps;
    }
}
