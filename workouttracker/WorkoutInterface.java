package com.example.workouttracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class WorkoutInterface extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Integer> sumOfReps = new ArrayList<Integer>();
    TextView workoutName_tv;
    TextView lastMaxWeight_tv;
    TextView lastWorkoutDate_tv;
    TextView workoutDate_tv;
    EditText workoutWeight_et;
    TextView workWeight_tv;
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
    WorkoutClass thisWorkout;
    List<WorkoutClass> allWorkouts;
    FloatingActionButton addSet, fabPlus,fabMinus;
    Animation fabOpen,fabClose,rotateForward,rotateBack;
    boolean isOpen = false;//by default false
    FloatingActionButton calculateORM;
    private LinearLayout linearLayout;
    int maxWeight;
    int pastWorkoutIndex;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.workout_interface);
        RecyclerView listOfSets = findViewById(R.id.recyclerview_workout_interface);
        findViews();
        Calendar calendar=Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        workoutDate_tv.setText(currentDate);
        DataBase dataBase=new DataBase(this);
        allWorkouts=dataBase.getAllWorkouts();
        thisWorkout=allWorkouts.get(addSet(savedInstanceState));// put index of chosen workout (that the user chose from MyWorkouts) to find the specific workout.
        workoutName_tv.setText(workoutName);
        dataBase.updateuserdata(thisWorkout.workoutName, thisWorkout.numberOfSets, thisWorkout.numberOfReps,thisWorkout.weights, thisWorkout.timeBetweenSets,thisWorkout.weightOrReps,"",thisWorkout.description);
        WorkoutInterfacesAdapter workoutInterfacesAdapter = new WorkoutInterfacesAdapter(this, thisWorkout);//gives workoutInterfaceAdapter the arraylist that contains all the needed variables
        listOfSets.setAdapter(workoutInterfacesAdapter);//sets the adapter
        listOfSets.setLayoutManager(new LinearLayoutManager(WorkoutInterface.this));
        DataBasePastWorkouts dataBasePastWorkouts=new DataBasePastWorkouts(WorkoutInterface.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(dataBasePastWorkouts.getAllPastWorkouts().size()>0){
            setLastWorkoutViews(dataBasePastWorkouts);}
        }
        ORM=0;
        if(!thisWorkout.weightOrReps){//max REPS ==> true. max WEIGHT ==>false
            workoutWeight_et.setVisibility(View.VISIBLE);
            workWeight_tv.setVisibility(View.VISIBLE);
        }else {
            workoutWeight_et.setVisibility(View.INVISIBLE);
            workWeight_tv.setVisibility(View.INVISIBLE);
        }
//        getIncomingIntent(savedInstanceState,sumOfReps);
//        actualReps=sumOfReps(sumOfReps);
//        System.out.println("AAA TT EE NN  TT  II OO  NN AAATTENTIOO!!! " + actualReps);

//        workoutActualReps_et.setText(String.valueOf(setsAndReps.get(0).actualReps));
// fab that opens two more fabs -->plus set and minus set
        addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
            }
        });
        //add more set with 5 reps
        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
                                //פה יכנס הקוד הזה , רק אם אלחץ על כפתור OK
                                Bundle extras = getIntent().getExtras();
                                thisWorkout.numberOfSets+=1;

                                if(savedInstanceState==null) {
                                    if (extras == null) {
                                        thisWorkout.numberOfReps += ",5";
                                        thisWorkout.weights+=",0";
                                    }else {
                                        thisWorkout.numberOfReps += ",5";
                                        thisWorkout.weights+=",0";
                                    }
                                }else{
                                    thisWorkout.numberOfReps += ",5";
                                    thisWorkout.weights+=",0";
                                }

                                dataBase.updateuserdata(thisWorkout.workoutName, thisWorkout.numberOfSets,thisWorkout.numberOfReps,thisWorkout.weights, thisWorkout.timeBetweenSets,thisWorkout.weightOrReps,thisWorkout.pressedButtons,thisWorkout.description);
                                workoutInterfacesAdapter.notifyDataSetChanged();

                            }

        });

        fabMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
                //פה יכנס הקוד הזה , רק אם אלחץ על כפתור OK
                Bundle extras = getIntent().getExtras();
                if(thisWorkout.numberOfSets>1){
                    thisWorkout.numberOfSets-=1;
                    if(savedInstanceState==null) {
                        if (extras == null) {
                            thisWorkout.numberOfReps=stringToArraylistAndBack(thisWorkout.numberOfReps);
                            thisWorkout.weights=stringToArraylistAndBack(thisWorkout.weights);
                        }else {
                            thisWorkout.numberOfReps=stringToArraylistAndBack(thisWorkout.numberOfReps);
                            thisWorkout.weights=stringToArraylistAndBack(thisWorkout.weights);
                        }
                    }else{
                        thisWorkout.numberOfReps=stringToArraylistAndBack(thisWorkout.numberOfReps);
                        thisWorkout.weights=stringToArraylistAndBack(thisWorkout.weights);
                    }

                    dataBase.updateuserdata(thisWorkout.workoutName, thisWorkout.numberOfSets,thisWorkout.numberOfReps,thisWorkout.weights, thisWorkout.timeBetweenSets,thisWorkout.weightOrReps,thisWorkout.pressedButtons,thisWorkout.description);
                    workoutInterfacesAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(WorkoutInterface.this,"No more sets to decrease",Toast.LENGTH_LONG);
                }


            }

        });


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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random rand=new Random();// generate random number for ID
                PastWorkoutsClass pastWorkouts;
                List<PastWorkoutsClass> allPastWorkouts;
                allPastWorkouts=dataBasePastWorkouts.getAllPastWorkouts();
                ArrayList<String> actualRepsArray = new ArrayList<String>(Arrays.asList(thisWorkout.pressedButtons.split(",")));

                int actualReps = actualRepsArray.size()-1;

                Intent intent = new Intent(WorkoutInterface.this,PastWorkouts.class);
                try {

//                    if(!thisWorkout.weightOrReps) {// if the user did not put weight
//                        workoutWeight_et.setText("0");
//                        pastWorkouts = new PastWorkoutsClass(rand.nextInt(1000),thisWorkout.workoutName, workoutDate_tv.getText().toString(), Integer.parseInt(workoutWeight_et.getText().toString()),actualReps, ORM, Integer.parseInt(workoutRPE_et.getText().toString()),    "Max reps done: " + actualReps);
//
//                    }else{
                    if(allPastWorkouts.size()>0){
                         pastWorkoutIndex = allPastWorkouts.size()-1;
                    }else if(allPastWorkouts.size()==0){
                        pastWorkoutIndex = 0;
                    }else{
                        pastWorkoutIndex=rand.nextInt(1000);
                    }
                    if(!thisWorkout.weightOrReps){// if the user wanted weight

                        if(!workoutWeight_et.getText().toString().equals("")) {
                            maxWeight = Integer.parseInt(workoutWeight_et.getText().toString());
                            pastWorkouts = new PastWorkoutsClass(allPastWorkouts.size()+1, thisWorkout.workoutName, workoutDate_tv.getText().toString(), maxWeight, actualReps, ORM, workoutRPE_et.getText().toString()!=""?0:Integer.parseInt(workoutRPE_et.getText().toString()), workoutNotes_et.getText().toString() + "\n ; weights lifted:" + thisWorkout.weights);
                        }else{
                            maxWeight = 0;
                            pastWorkouts = new PastWorkoutsClass(allPastWorkouts.size()+1, thisWorkout.workoutName, workoutDate_tv.getText().toString(), maxWeight, actualReps, ORM, workoutRPE_et.getText().toString()!=""?0:Integer.parseInt(workoutRPE_et.getText().toString()), workoutNotes_et.getText().toString() + "\n ; No Max Weight inserted. sets weigt is: " + thisWorkout.weights);

                        }
                    }else{// if the user wanted reps
                        if(!workoutWeight_et.getText().toString().equals("")) {
                            maxWeight = Integer.parseInt(workoutWeight_et.getText().toString());
                            pastWorkouts = new PastWorkoutsClass(allPastWorkouts.size()+1, thisWorkout.workoutName, workoutDate_tv.getText().toString(), maxWeight, actualReps, ORM, workoutRPE_et.getText().toString()!=""?0:Integer.parseInt(workoutRPE_et.getText().toString()), workoutNotes_et.getText().toString() + "\n ; Reps done:" + thisWorkout.numberOfReps);
                        }else{
                            maxWeight = 0;
                            pastWorkouts = new PastWorkoutsClass(allPastWorkouts.size()+1, thisWorkout.workoutName, workoutDate_tv.getText().toString(), maxWeight, actualReps, ORM, workoutRPE_et.getText().toString()!=""?0:Integer.parseInt(workoutRPE_et.getText().toString()), workoutNotes_et.getText().toString() + "\n ; Reps done: " + thisWorkout.numberOfReps);

                        }
                    }
//                      if(!workoutWeight_et.getText().toString().equals("")) {
//                          maxWeight = Integer.parseInt(workoutWeight_et.getText().toString());
//                          pastWorkouts = new PastWorkoutsClass(pastWorkoutIndex, thisWorkout.workoutName, workoutDate_tv.getText().toString(), maxWeight, actualReps, ORM, workoutRPE_et.getText().toString()!=""?0:Integer.parseInt(workoutRPE_et.getText().toString()), workoutNotes_et.getText().toString() + "\n ; weights lifted:" + thisWorkout.weights);
//                      }else{
//                          maxWeight = 0;
//                          pastWorkouts = new PastWorkoutsClass(pastWorkoutIndex, thisWorkout.workoutName, workoutDate_tv.getText().toString(), maxWeight, actualReps, ORM, workoutRPE_et.getText().toString()!=""?0:Integer.parseInt(workoutRPE_et.getText().toString()), workoutNotes_et.getText().toString() + "\n ; No Max Weight inserted. sets weigt is: " + thisWorkout.weights);
//
//                      }
//                    }
                }catch (Exception e){
//                    System.out.println(pastWorkoutIndex);
//                    System.out.println(thisWorkout.workoutName);
//                    System.out.println(workoutDate_tv.getText().toString());
//                    System.out.println(maxWeight);
//                    System.out.println(ORM);
//                    System.out.println(actualReps);
//                    System.out.println(workoutRPE_et.getText().toString()!=""?0:Integer.parseInt(workoutRPE_et.getText().toString()));
//                    System.out.println(workoutNotes_et.getText().toString() + "\n ; No Max Weight inserted. sets weigt is: " + thisWorkout.weights);
                    Toast.makeText(WorkoutInterface.this,"You did not fill valid input!  Insert weight, tic reps and so on.",Toast.LENGTH_LONG).show();
//                    System.out.println("error: did not put valid input - problem in WorkoutInterface.java");
//                    System.out.println("workoutname: " + thisWorkout.workoutName + "date: " + workoutDate_tv.getText().toString() + "weight: " + Integer.parseInt(workoutWeight_et.getText().toString()) + " pressed buttons: " + actualReps + " orm: " + ORM+ "RPE:  " + Integer.parseInt(workoutRPE_et.getText().toString()) + " NOTES: " + workoutNotes_et.getText().toString() );
                    pastWorkouts = new PastWorkoutsClass(rand.nextInt(1000),thisWorkout.workoutName,workoutDate_tv.getText().toString() , 0, 0, 0, 0, "No notes given"+ "\n ; weights lifted:" + thisWorkout.weights);
                }
                //here we start the MainActivity
                startActivity(intent);
                //here we insert (from this addWorkoutActivity!) values to the dataBase, and check it we succeeded.
//                DataBasePastWorkouts dataBasePastWorkouts=new DataBasePastWorkouts(WorkoutInterface.this);
                boolean success = dataBasePastWorkouts.addPastWorkout(pastWorkouts);



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
        workWeight_tv=findViewById(R.id.table_tv_working_weight_title);

        fabPlus=findViewById(R.id.fab_more_sets);
        fabMinus=findViewById(R.id.fab_less_sets);
        fabOpen= AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);

        rotateForward=AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotateBack=AnimationUtils.loadAnimation(this,R.anim.rotate_backward);


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setLastWorkoutViews(DataBasePastWorkouts dataBasePastWorkouts){
        String pastWorkoutDate;
        String lastWorkoutDate = null;
        String pastWorkoutName;
        String lastWorkoutName;
        String lastWorkoutWeight;


//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        List<PastWorkoutsClass> pastWorkoutsList;
        pastWorkoutsList = dataBasePastWorkouts.getAllPastWorkouts();
        pastWorkoutDate=pastWorkoutsList.get(0).workoutDate.toString();
        pastWorkoutName=pastWorkoutsList.get(0).workoutName.toString();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd, yyyy" );
        try {
            Date pastDate = simpleDateFormat.parse(pastWorkoutDate);
       

        for(int i=0; i<pastWorkoutsList.size(); i++) {
            if (pastWorkoutsList.get(i).workoutName != null) {
                lastWorkoutName = pastWorkoutsList.get(i).workoutName.toString();
                lastWorkoutDate = pastWorkoutsList.get(i).workoutDate.toString();


                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MMM dd, yyyy");
                try {
                    Date lastDate = simpleDateFormat2.parse(lastWorkoutDate);
                    if (lastDate.after(pastDate) && lastWorkoutName == pastWorkoutName) {
                        pastDate = lastDate;
                        lastWorkoutDate = pastWorkoutsList.get(i + 1).workoutDate.toString();
                        System.out.println("last workout name " + lastWorkoutName + ",last date: " + lastWorkoutDate + " ,past workout name" + pastWorkoutName + ",past date: " + pastWorkoutDate);
                    }
                    if (i + 1 == pastWorkoutsList.size()) {
//                        System.out.println("#@$%@#$#@$ Result String of the date: " + lastWorkoutDate + '\''
//                                + "#@$%@#$#@$ Result String of the name: " + lastWorkoutName);
                        lastWorkoutDate_tv.setText(lastWorkoutDate);
                        lastMaxWeight_tv.setText(String.valueOf(pastWorkoutsList.get(i).workoutWeight));

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println("Error in first catch expression");

                }

            }
        }
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Error in second catch expression");

        }
 

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
//decrease set number
    public String stringToArraylistAndBack(String str) {
        String resultStr = "";
        //convert string to array:
        ArrayList<String> stringArrayList = new ArrayList<String>(Arrays.asList(str.split(",")));
        ArrayList<Integer> integerArrayList = new ArrayList<Integer>();
        for (int i = 0; i < stringArrayList.size(); i++) {

            integerArrayList.add(Integer.parseInt(stringArrayList.get(i).replaceAll("[\\p{Ps}\\p{Pe}]", "").trim()));
        }
        //delete last object:
        integerArrayList.remove(integerArrayList.size()-1);
        //turn the array back to string:
        resultStr=stringConverter(integerArrayList);

        return resultStr;
    }
    //array to string with no brackets
    private String stringConverter(ArrayList<Integer> stringArrayList) {
        String listOfObjects = "";
        for (int i = 0; i < stringArrayList.size(); i++) {
            if (i == 0) {
                listOfObjects += "" + String.valueOf(stringArrayList.get(i));
            } else {
                listOfObjects += "," + String.valueOf(stringArrayList.get(i));
            }
        }
        return listOfObjects;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        IsFinish("Are you sure you want to leave without submitting?");
    }

    public void IsFinish(String alertmessage) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        android.os.Process.killProcess(android.os.Process.myPid());
                        // This above line close correctly
                        //finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(WorkoutInterface.this);
        builder.setMessage(alertmessage)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
    private void animateFab(){

        if(isOpen){
            addSet.startAnimation(rotateForward);
            fabPlus.startAnimation(fabClose);
            fabMinus.startAnimation(fabClose);
            fabPlus.setClickable(false);
            fabPlus.setClickable(false);
            isOpen=false;
        }else{
            addSet.startAnimation(rotateBack);
            fabPlus.startAnimation(fabOpen);
            fabMinus.startAnimation(fabOpen);
            fabPlus.setClickable(true);
            fabPlus.setClickable(true);
            isOpen=true;

        }

    }

    }


