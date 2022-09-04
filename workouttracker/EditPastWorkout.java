package com.example.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class EditPastWorkout extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Integer> sumOfReps = new ArrayList<Integer>();
    TextView workoutName_tv;
    TextView lastMaxWeight_tv;
    TextView lastWorkoutDate_tv;
    TextView workoutDate_tv;
    EditText workoutWeight_et;
    EditText workoutActualReps_et;
    EditText workoutNotes_et;
    EditText workoutRPE_et;
    Button submit;
    Button testORM;
    EditText et_testWeight;
    EditText et_testReps;
    TextView tvORM;
    int ORM;
    int actualReps=0;
    boolean weightOrReps;
    int workoutSets;
    String workoutReps;
    String weights;
    String workoutName;
    int timeBetweenSets;
    String pressedButtons = "";
    String TAG = "Test";
    PastWorkoutsClass thisWorkout;
    List<PastWorkoutsClass> allPastWorkouts;
    FloatingActionButton addSet;
    FloatingActionButton calculateORM;
    private LinearLayout linearLayout;
    int id;
    int position;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_interface);
        RecyclerView listOfSets = findViewById(R.id.recyclerview_workout_interface);
        findViews();
        id=getIncomimgPositionIntent(savedInstanceState);//getting the position of the chosen workout from all the workouts.
        DataBasePastWorkouts dataBasePastWorkouts = new DataBasePastWorkouts(EditPastWorkout.this);
        allPastWorkouts=dataBasePastWorkouts.getAllPastWorkouts();//fill the all-past-workouts array from database.
        getPosition(id);
        thisWorkout=allPastWorkouts.get(position);// put index of chosen workout (that the user chose from PastWorkouts) to find the specific workout.
        if(thisWorkout.workoutDate!=null && thisWorkout.workoutDate!=""){
            workoutDate_tv.setText(thisWorkout.workoutDate);}
        if(thisWorkout.workoutName!=""){
            workoutName_tv.setText(thisWorkout.workoutName);}

        if (thisWorkout.workoutWeight!=0) {
            et_testWeight.setText(""+thisWorkout.workoutWeight);
            workoutWeight_et.setText(""+thisWorkout.workoutWeight);
        }

        if(thisWorkout.notes!=""){
            workoutNotes_et.setText(thisWorkout.notes);}
        if(thisWorkout.RPE!=0){
            workoutRPE_et.setText(""+thisWorkout.RPE);}
        addSet.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);


        testORM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_testReps.setVisibility(View.VISIBLE);
                et_testWeight.setVisibility(View.VISIBLE);
                tvORM.setVisibility(View.VISIBLE);
                calculateORM.setVisibility(View.VISIBLE);
            }
        });
        calculateORM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double orm;
                if(et_testReps!=null && et_testWeight!=null) {
                    double testReps = Double.parseDouble(et_testReps.getText().toString());
                    double testWeight = Double.parseDouble(et_testWeight.getText().toString());
                    orm = testWeight*(36/(37 - testReps));
                    double roundedORM =Math.round(orm);
                    tvORM.setText(String.valueOf(roundedORM));
                    ORM = (int) roundedORM;
                }
            }
        });
        et_testWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
        workoutNotes_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditPastWorkout.this,PastWorkouts.class);
//                try {
                    System.out.println("haaaaaaaaaaaaaaaaaa" + workoutName_tv.getText().toString());
                    System.out.println("haaaaaaaaaaaaaaaaaa" + workoutDate_tv.getText().toString());
                    System.out.println("haaaaaaaaaaaaaaaaaa" + thisWorkout.actualNumberOfReps);
                    System.out.println("haaaaaaaaaaaaaaaaaa" + ORM);
                    System.out.println("haaaaaaaaaaaaaaaaaa" + workoutRPE_et.getText().toString());
                    System.out.println("haaaaaaaaaaaaaaaaaa" +workoutWeight_et.getText().toString());
                    System.out.println("haaaaaaaaaaaaaaaaaa" +workoutWeight_et.getText().toString());
                    dataBasePastWorkouts.updateuserdata(workoutName_tv.getText().toString(), workoutDate_tv.getText().toString(), thisWorkout.actualNumberOfReps, ORM,workoutRPE_et.getText().toString().equals("")?thisWorkout.RPE:Integer.parseInt(workoutRPE_et.getText().toString()),workoutWeight_et.getText().toString()!=""?(int) Double.parseDouble(workoutWeight_et.getText().toString()):thisWorkout.workoutWeight
                            , workoutNotes_et.getText().toString()!=""?workoutNotes_et.getText().toString():thisWorkout.notes, thisWorkout.pastWorkoutId);
//                }catch (NumberFormatException ex){
//                    dataBasePastWorkouts.updateuserdata(thisWorkout.workoutName, thisWorkout.workoutDate, thisWorkout.actualNumberOfReps, thisWorkout.ORM,thisWorkout.RPE,thisWorkout.workoutWeight, thisWorkout.notes, thisWorkout.pastWorkoutId);
//                }
                //here we start the MainActivity
                dataBasePastWorkouts.close();
                startActivity(intent);


            }
        });






    }




    @Override
    public void onClick(View v) {
        Log.d(TAG, " Name " + ((CheckBox)v).getText() +" Id is "+v.getId());

    }

    public void findViews(){
        workoutName_tv=findViewById(R.id.tv_workout_name);
        workoutDate_tv = findViewById(R.id.tv_top_title_date);
        workoutWeight_et=findViewById(R.id.table_et_work_weight);
//        workoutActualReps_et=findViewById(R.id.table_et_reps);
        workoutNotes_et=findViewById(R.id.et_comments);
        workoutRPE_et=findViewById(R.id.et_RPE);
        submit=findViewById(R.id.btn_submit);
        lastMaxWeight_tv=findViewById(R.id.table_tv_last_PR);
        lastWorkoutDate_tv=findViewById(R.id.table_tv_date);
        addSet=findViewById(R.id.add_sets_and_reps_floating_button);
        testORM=findViewById(R.id.test_1rm);
        et_testWeight=findViewById(R.id.table_et_test_weight);
        et_testReps=findViewById(R.id.table_et_test_reps);
        tvORM=findViewById(R.id.table_tv_orm_result);
        calculateORM=findViewById(R.id.floatingActionButton_calculator);
        recyclerView=findViewById(R.id.recyclerview_workout_interface);
    }


    private int addSet(Bundle savedInstanceState){
//getting incoming intent
        int workoutIndex;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                workoutIndex=0;

            } else {
                workoutIndex = extras.getInt("chosen_workout");
            }
        } else {
            workoutIndex = (int) savedInstanceState.getSerializable("chosen_workout");
        }
//makes arraylist named setsAndReps, that has number of memory cells according to the workouts set number.
        //each cell contain all the variables for the workout if we would like to add features in the future and add them to each recyclerview row.
        return workoutIndex;

    }

    private int getIncomimgPositionIntent(Bundle savedInstanceState){
//getting incoming intent
        int workoutIndex;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                workoutIndex=0;

            } else {
                workoutIndex = extras.getInt("chosen_past_workout");
            }
        } else {
            workoutIndex = (int) savedInstanceState.getSerializable("chosen_past_workout");
        }
//makes arraylist named setsAndReps, that has number of memory cells according to the workouts set number.
        //each cell contain all the variables for the workout if we would like to add features in the future and add them to each recyclerview row.
        return workoutIndex;

    }

    private int getPosition(int id){
        for(int i=0 ; i<allPastWorkouts.size();i++){

            if(allPastWorkouts.get(i).pastWorkoutId==id){
                position=i;
            }
        }

        return position;
    }

}






