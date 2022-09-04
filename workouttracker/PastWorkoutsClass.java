package com.example.workouttracker;

public class PastWorkoutsClass {
    String workoutName;
    String workoutDate;
    int workoutWeight;
    int actualNumberOfReps;
    int ORM;
    int RPE;
    String notes;
    int pastWorkoutId;

    @Override
    public String toString() {
        return "PastWorkoutsClass{" +
                "workoutDate=" + workoutDate +
                ", workoutName='" + workoutName + '\'' +
                ", workoutWeight=" + workoutWeight +
                ", numberOdReps='" + actualNumberOfReps + '\'' +
                ", ORM=" + ORM +
                ", RPE=" + RPE +
                ", notes='" + notes + '\'' +
                '}';
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }

    public int getWorkoutWeight() {
        return workoutWeight;
    }

    public void setWorkoutWeight(int workoutWeight) {
        this.workoutWeight = workoutWeight;
    }

    public int getActualNumberOfReps() {
        return actualNumberOfReps;
    }

    public void setActualNumberOfReps(int actualNumberOfReps) {
        this.actualNumberOfReps = actualNumberOfReps;
    }

    public int getORM() {
        return ORM;
    }

    public void setORM(int ORM) {
        this.ORM = ORM;
    }

    public int getRPE() {
        return RPE;
    }

    public void setRPE(int RPE) {
        this.RPE = RPE;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getPastWorkoutId() {
        return pastWorkoutId;
    }

    public void setPastWorkoutId(int pastWorkoutId) {
        this.pastWorkoutId = pastWorkoutId;
    }

    public PastWorkoutsClass(int pastWorkoutId, String workoutName, String workoutDate, int workoutWeight, int actualNumberOfReps, int ORM, int RPE, String notes) {
        this.workoutDate = workoutDate;
        this.workoutName = workoutName;
        this.workoutWeight = workoutWeight;
        this.actualNumberOfReps = actualNumberOfReps;
        this.ORM = ORM;
        this.RPE = RPE;
        this.notes = notes;
        this.pastWorkoutId=pastWorkoutId;
    }
}
