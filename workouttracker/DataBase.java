package com.example.workouttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
//First table for MyWorkouts activity -->
    public static final String WORKOUTS_TABLE = "WORKOUTS_TABLE";
    public static final String COLUMN_WORKOUT_NAME = "COLUMN_WORKOUT_NAME";
    public static final String COLUMN_WORKOUT_SETS = "COLUMN_WORKOUT_SETS";
    public static final String COLUMN_WORKOUT_REPS = "COLUMN_WORKOUT_REPS";
    public static final String COLUMN_WORKOUT_WEIGHTS = "COLUMN_WORKOUT_WEIGHTS";
    public static final String COLUMN_WORKOUT_TIME = "COLUMN_WORKOUT_TIME";
    public static final String COLUMN_WHEIGHTORREPS = "COLUMN_WHEIGHTORREPS";
    public static final String COLUMN_DESCRIPTION = "COLUMN_DESCRIPTION";
    public static final String COLUMN_PRESSED_BUTTONS = "COLUMN_PRESSED_BUTTONS";
    public static final String COLUMN_ID = "COLUMN_ID";




    public DataBase(@Nullable Context context) {
        super(context, "workoutDataBase", null, 1);//name: I chose,  factory: null , version is 1..
    }

    //this is called the first time a database is accessed. There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + WORKOUTS_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_WORKOUT_NAME + " TEXT," + COLUMN_WORKOUT_SETS + " INTEGER," + COLUMN_WORKOUT_REPS + " TEXT," + COLUMN_WORKOUT_WEIGHTS + " TEXT," + COLUMN_WORKOUT_TIME + " REAL," + COLUMN_WHEIGHTORREPS + " NUMERIC," + COLUMN_PRESSED_BUTTONS + " TEXT," + COLUMN_DESCRIPTION + " TEXT )";// here we create the workouts table
        db.execSQL(createTableStatement);

    }
//this is called if the database version number changes.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addWorkout(WorkoutClass workoutClass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WORKOUT_NAME,workoutClass.getWorkoutName());
        cv.put(COLUMN_WORKOUT_SETS,workoutClass.getNumberOfSets());
        cv.put(COLUMN_WORKOUT_REPS,workoutClass.getNumberOfReps());
        cv.put(COLUMN_WORKOUT_WEIGHTS,workoutClass.getWeights());
        cv.put(COLUMN_WORKOUT_TIME,workoutClass.getTimeBetweenSets());
        cv.put(COLUMN_WHEIGHTORREPS,workoutClass.getWeightOrReps());
        cv.put(COLUMN_PRESSED_BUTTONS,workoutClass.getPressedButtons());
        cv.put(COLUMN_DESCRIPTION,workoutClass.getDescription());

        long insert = db.insert(WORKOUTS_TABLE, null, cv);
        if(insert ==-1){
            return false;
        }else{
            return true;
        }


    }
    //new code 02/10:
    public Boolean updateuserdata(String workoutName, int workoutSets, String workoutReps,String workoutWeights, double timeBetweenSets,boolean weightOrReps,String pressedButtons, String description) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WORKOUT_NAME, workoutName);
        contentValues.put(COLUMN_WORKOUT_SETS, workoutSets);
        contentValues.put(COLUMN_WORKOUT_REPS, workoutReps);
        contentValues.put(COLUMN_WORKOUT_WEIGHTS, workoutWeights);
        contentValues.put(COLUMN_WORKOUT_TIME, timeBetweenSets);
        contentValues.put(COLUMN_WHEIGHTORREPS, weightOrReps);
        contentValues.put(COLUMN_PRESSED_BUTTONS,pressedButtons);
        contentValues.put(COLUMN_DESCRIPTION, description);

        //test if workoutName exists. if so, it will update it
        Cursor cursor = DB.rawQuery("Select * from WORKOUTS_TABLE where COLUMN_WORKOUT_NAME = ?", new String[]{workoutName});
        if (cursor.getCount() > 0) {
            long result = DB.update("WORKOUTS_TABLE", contentValues, "COLUMN_WORKOUT_NAME=?", new String[]{workoutName});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }}
    // end new code 02/10



    public List<WorkoutClass> getAllWorkouts(){
        List<WorkoutClass> returnList =new ArrayList<>();
        //get data from the database:
        String queryString = "SELECT * FROM " + WORKOUTS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();//to SELECT item from database, we chose this method.
        Cursor cursor = db.rawQuery(queryString,null);//local variable cursor is a result set. --> a complex array of items
        //check if succeed:
        if(cursor.moveToFirst()){
             //loop through the cursor(result set) and create new workout object. put them into the return list.
              do{// do the following code, as long as there is new lines:
                  int workoutID = cursor.getInt(0);
                  String workoutName = cursor.getString(1);
                  int workoutSets = cursor.getInt(2);
                  String workoutReps = cursor.getString(3);
                  String workoutWeights = cursor.getString(4);
                  double workoutTime = cursor.getInt(5);
                  boolean workoutWeightOrReps =cursor.getInt(6)==1 ? true:false ; // if it is 1 return true. else return false.
                  String pressedButtons = cursor.getString(7);
                  String workoutDescription = cursor.getString(8);
                  WorkoutClass newWorkout = new WorkoutClass(workoutName,workoutSets,workoutTime,workoutReps,workoutWeights,workoutWeightOrReps,pressedButtons,workoutDescription);
                  returnList.add(newWorkout);

              } while(cursor.moveToNext() );//
        }
        else{
            //failure.do not add anything to the list.
        }
        //close the cursor and the db when done.
        cursor.close();
        db.close();

        return returnList;
    }

    public boolean deleteWorkout(WorkoutClass workout){
        //find workout in the database. if it's found ,delete it and return true.
        //if its not found, return false
        SQLiteDatabase db =this.getWritableDatabase();
        String queryString = "DELETE FROM " + WORKOUTS_TABLE + " WHERE " + COLUMN_WORKOUT_NAME + " = " + "'" +workout.getWorkoutName().trim() + "'";
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }
}
