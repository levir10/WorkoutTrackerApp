package com.example.workouttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBasePastWorkouts extends SQLiteOpenHelper {
    //Second table for PastWorkouts activity-->
    public static final String PAST_WORKOUTS_TABLE="PAST_WORKOUTS_TABLE";
    public static final String COLUMN_PAST_WORKOUT_NAME="COLUMN_PAST_WORKOUT_NAME";
    public static final String COLUMN_PAST_WORKOUT_DATE="COLUMN_PAST_WORKOUT_DATE";
    public static final String COLUMN_PAST_WORKOUT_WEIGHT="COLUMN_PAST_WORKOUT_WEIGHT";
    public static final String COLUMN_PAST_WORKOUT_ACTUAL_REPS="COLUMN_PAST_WORKOUT_ACTUAL_REPS";
    public static final String COLUMN_PAST_WORKOUT_ORM="COLUMN_PAST_WORKOUT_ORM";
    public static final String COLUMN_PAST_WORKOUT_RPE="COLUMN_PAST_WORKOUT_RPE";
    public static final String COLUMN_PAST_WORKOUT_NOTES="COLUMN_PAST_WORKOUT_NOTES";
    public static final String COLUMN_PAST_WORKOUT_ID="COLUMN_PAST_WORKOUT_ID";

    public DataBasePastWorkouts( Context context) {
        super(context, "PastWorkoutsDataBase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase Past_db) {
        String createTableStatement = "CREATE TABLE " + PAST_WORKOUTS_TABLE + "(" + COLUMN_PAST_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PAST_WORKOUT_NAME + " TEXT, " + COLUMN_PAST_WORKOUT_DATE + " TEXT, " + COLUMN_PAST_WORKOUT_WEIGHT + " INT, " + COLUMN_PAST_WORKOUT_ACTUAL_REPS + " INT, " + COLUMN_PAST_WORKOUT_ORM + " INT, " + COLUMN_PAST_WORKOUT_RPE + " INT, " + COLUMN_PAST_WORKOUT_NOTES + " TEXT )";// here we create the workouts table
        Past_db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase Past_db, int oldVersion, int newVersion) {

    }
    public boolean addPastWorkout(PastWorkoutsClass pastWorkoutClass){
        SQLiteDatabase Past_db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PAST_WORKOUT_ID,pastWorkoutClass.getPastWorkoutId());// new add 22.1.22
        cv.put(COLUMN_PAST_WORKOUT_NAME,pastWorkoutClass.getWorkoutName());
        cv.put(COLUMN_PAST_WORKOUT_DATE,pastWorkoutClass.getWorkoutDate());
        cv.put(COLUMN_PAST_WORKOUT_WEIGHT,pastWorkoutClass.getWorkoutWeight());
        cv.put(COLUMN_PAST_WORKOUT_ACTUAL_REPS,pastWorkoutClass.getActualNumberOfReps());
        cv.put(COLUMN_PAST_WORKOUT_ORM,pastWorkoutClass.getORM());
        cv.put(COLUMN_PAST_WORKOUT_RPE,pastWorkoutClass.getRPE());
        cv.put(COLUMN_PAST_WORKOUT_NOTES,pastWorkoutClass.getNotes());

        long insert = Past_db.insert(PAST_WORKOUTS_TABLE, null, cv);
        if(insert ==-1){
            return false;
        }else{
            return true;
        }


    }
    public List<PastWorkoutsClass> getAllPastWorkouts(){
        List<PastWorkoutsClass> returnList =new ArrayList<>();
        //get data from the database:
        String queryString = "SELECT * FROM " + PAST_WORKOUTS_TABLE;
        SQLiteDatabase Past_db = this.getReadableDatabase();//to SELECT item from database, we chose this method.
        Cursor cursor = Past_db.rawQuery(queryString,null);//local variable cursor is a result set. --> a complex array of items
        //check if succeed:
        if(cursor.moveToFirst()){
            //loop through the cursor(result set) and create new past - workout object. put them into the return list.
            do{// do the following code, as long as there is new lines:
                int pastWorkoutID = cursor.getInt(0);
                String pastWorkoutName = cursor.getString(1);
                String pastWorkoutDate = cursor.getString(2);
                int pastWorkoutWeight = cursor.getInt(3);
                int pastWorkoutActualReps = cursor.getInt(4);
                int pastWorkoutORM =cursor.getInt(5); // if it is 1 return true. else return false.
                int pastWorkoutRPE = cursor.getInt(6);
                String pastWorkoutNotes = cursor.getString(7);
                PastWorkoutsClass newPastWorkout = new PastWorkoutsClass(pastWorkoutID,pastWorkoutName,pastWorkoutDate,pastWorkoutWeight,pastWorkoutActualReps,pastWorkoutORM,pastWorkoutRPE,pastWorkoutNotes);
                returnList.add(newPastWorkout);

            } while(cursor.moveToNext() );//
        }
        else{
            //failure.do not add anything to the list.
        }
        //close the cursor and the db when done.
        cursor.close();
        Past_db.close();

        return returnList;
    }
    public boolean deleteWorkout(PastWorkoutsClass pastWorkouts){
        //find workout in the database. if it's found ,delete it and return true.
        //if its not found, return false
        SQLiteDatabase db =this.getWritableDatabase();
        String queryString = "DELETE FROM " + PAST_WORKOUTS_TABLE + " WHERE " + COLUMN_PAST_WORKOUT_ID + " = " + "'" +pastWorkouts.pastWorkoutId + "'";
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }
    //new code 02/10:
    public Boolean updateuserdata(String workoutName, String workoutDate,int workoutActualReps,int ORM,int RPE,int workoutWeights, String workoutNotes,int ID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PAST_WORKOUT_NAME, workoutName);
        contentValues.put(COLUMN_PAST_WORKOUT_DATE, workoutDate);
        contentValues.put(COLUMN_PAST_WORKOUT_WEIGHT, workoutWeights);
        contentValues.put(COLUMN_PAST_WORKOUT_ACTUAL_REPS, workoutActualReps);
        contentValues.put(COLUMN_PAST_WORKOUT_ORM, ORM);
        contentValues.put(COLUMN_PAST_WORKOUT_RPE,RPE);
        contentValues.put(COLUMN_PAST_WORKOUT_NOTES,workoutNotes);
        contentValues.put(COLUMN_PAST_WORKOUT_ID, ID);

        //test if workouTID exists. if so, it will update it
        Cursor cursor = DB.rawQuery("Select * from PAST_WORKOUTS_TABLE where COLUMN_PAST_WORKOUT_ID = ?", new String[]{String.valueOf(ID)});
        if (cursor.getCount() > 0) {
            long result = DB.update("PAST_WORKOUTS_TABLE", contentValues, "COLUMN_PAST_WORKOUT_ID=?", new String[]{String.valueOf(ID)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }}
    // end new code 02/10
}
