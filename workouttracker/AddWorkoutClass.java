package com.example.workouttracker;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AddWorkoutClass extends AppCompatActivity {
    EditText workoutName;
    int numberOfSets;
    EditText timeBetweenSets;
    EditText numberOfReps;
    Switch weightOrReps;// weight is true
    EditText description;
    Button addToWorkouts;
    ListView addSetslistview;
    FloatingActionButton addSet;
    EditText et_weight;
    EditText et_reps;
    ArrayList<String> weights;
    ArrayList<String> reps;
    String str_reps;
    String str_weights;
    Boolean check=false;
    ImageView deleteSetAlert;
    LottieAnimationView arrowAnimation;
    private View background;
    NumberPicker numberPickerMin;
    NumberPicker numberPickerSec;
    TextView totalTime_tv;
    int timeMin;
    double timeSec;
    double totalTime;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //APPLY CIRCULAR EFFECT-----------------------------------------------------------------------------------------------------------------------------------------
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);// Take animation from resource file we named "do_not_move"
        setContentView(R.layout.main_add_workout);//set content view from xml file
        getSupportActionBar().hide();//hide the top colored line

        background = findViewById(R.id.background);//the parent layout with the color for this activity (light blue)

        if (savedInstanceState == null) {// the savedInstanceState will always be null the first time an Activity is started
            background.setVisibility(View.INVISIBLE);

            final ViewTreeObserver viewTreeObserver = background.getViewTreeObserver();//variable to allow making live changes in the background layout

            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        background.getViewTreeObserver().removeOnGlobalLayoutListener(this);//when the activity is done, we have to call removeONGlobalLayoutListener.
                    }

                });
            }

        }


        //here is the code for the actual "add workout" activity-----------------------------------------------------------------------------------------------------------------------------------------

//        setContentView(R.layout.main_add_workout);
        findViews();
        numberPickerMin.setMaxValue(10);
        numberPickerMin.setMinValue(0);
        numberPickerMin.setValue(0);
        numberPickerSec.setMaxValue(60);
        numberPickerSec.setMinValue(0);
        numberPickerSec.setValue(0);

        numberPickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue ) {
                timeMin=newValue*60;
                totalTime_tv.setText(""+timeMin);
                totalTime=Double.parseDouble(totalTime_tv.getText().toString());

            }
        });
        numberPickerSec.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValueSec, int newValueSec) {
//                timeSec=(double)newValueSec/(double)60;
                timeSec=newValueSec;
                System.out.println("dfgsdrfgergreg reger ergf eargqrew     " + timeSec);
                totalTime_tv.setText(""+new DecimalFormat("####.####").format(Double.parseDouble(String.valueOf(timeMin+timeSec))));
                totalTime=Double.parseDouble(totalTime_tv.getText().toString());
                System.out.println("dfgsdrfgergreg reger ergf eargqrew     " + totalTime);

            }
        });




        ArrayList<SetsDataClass> setsArray = new ArrayList<SetsDataClass>();
        ArrayList<String> weights = new ArrayList<String>();
        ArrayList<String> reps = new ArrayList<String>();
        SetsAdaper setsAdaper = new SetsAdaper(this,R.layout.add_workout_item,setsArray);
        addSetslistview.setAdapter(setsAdaper);
        weightOrReps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check=true;
                } else {
                    check=false;
                }
            } });


        addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_weight.getText().toString().isEmpty()|| et_reps.getText().toString().isEmpty() ){
                    AlertDialog.Builder builder=new AlertDialog.Builder(AddWorkoutClass.this);
                    builder.setTitle("Insert input")
                            .setMessage("Please insert weight [kg] and desired number of reps BEFORE pressing the ''Add'' button.")
                            .setPositiveButton("Ok.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                }else{
                setsArray.add(setsArray.size(),new SetsDataClass(et_weight.getText().toString(),et_reps.getText().toString()));
                weights.add(weights.size(),et_weight.getText().toString());
                reps.add(reps.size(),et_reps.getText().toString());
                numberOfSets=reps.size();
                setsAdaper.notifyDataSetChanged();
//                et_weight.setText(null);
//                et_reps.setText(null);
                    deleteSetAlert.setVisibility(View.VISIBLE);
                arrowAnimation.setVisibility(View.INVISIBLE);
                Toast.makeText(AddWorkoutClass.this,"To add more sets, press the pluss button again and fill treps, and weights.",Toast.LENGTH_SHORT).show();
            }}
        });
        addSetslistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                setsArray.remove(position);
                reps.remove(position);// new edit
                weights.remove(position);// new edit
                numberOfSets=reps.size();// new edit
                setsAdaper.notifyDataSetChanged();
                Toast.makeText(AddWorkoutClass.this,"Set Deleted.",Toast.LENGTH_SHORT);

                return false;
            }
        });

        deleteSetAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddWorkoutClass.this);
                builder.setMessage("To delete a specific set, please press on it for longer time.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });

        //BUTTON FOR ADDING WORKOUT TO THE myworkouts ACTIVITY
        addToWorkouts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(reps.size()<1 || weights.size()<1){

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddWorkoutClass.this);
                    builder.setTitle("Please insert valid input!")
                            .setMessage("You should insert at least one set, with work weight of at least: 0." +
                                    " Put reps number, weight, and press the green plus button to save the set. ")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    System.out.println("error: did not put valid input - problem in AddWorkoutClass.java");
                                }
                            });
                    AlertDialog alertError = builder.create();
                    builder.show();
                }else {

                    WorkoutClass workout;
                    Intent intent = new Intent(AddWorkoutClass.this, MyWorkouts.class);
                    str_reps = stringConverter(reps);
                    str_weights = stringConverter(weights);
                    try {
                        //HERE we put the values that the user inserted, and put them inside thw workout variable from type workoutClass.
                        workout = new WorkoutClass(workoutName.getText().toString(), numberOfSets,totalTime, str_reps, str_weights, check, "", description.getText().toString());
                        //here we start the MainActivity
                        startActivity(intent);
                        //here we insert (from this addWorkoutActivity!) values to the dataBase, and check it we succeeded.
                        DataBase dataBase = new DataBase(AddWorkoutClass.this);
                        boolean success = dataBase.addWorkout(workout);
                        Toast.makeText(AddWorkoutClass.this, "Success:" + success, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        System.out.println("error: did not put valid input");
//                     workout = new WorkoutClass("error",0,0,str_reps,str_weights,false,"","none");
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddWorkoutClass.this);
                        builder.setTitle("Please insert: workout name,sets data and minimal time between sets.")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        System.out.println("error: did not put valid input - problem in AddWorkoutClass.java");
                                    }
                                });
                        AlertDialog alertError = builder.create();
                        builder.show();

                    }

                }
                //circular animation back to "my workouts"
                circularBackReveal();
            }
        });

    }
//TAKE THE INPUT FROME THE xml file
    private void findViews() {
        workoutName = findViewById(R.id.edit_text_workout_name);
        workoutName.setGravity(Gravity.CENTER_HORIZONTAL);
//        numberOfSets=findViewById(R.id.edit_text_number_of_sets);
//        timeBetweenSets.setGravity(Gravity.CENTER_HORIZONTAL);
//        numberOfReps=findViewById(R.id.edit_text_number_of_reps);
        weightOrReps=findViewById(R.id.switch2);
        description=findViewById(R.id.edit_text_workout_description);
        addToWorkouts=findViewById(R.id.button_add_workout);
        addSetslistview=findViewById(R.id.add_reps_sets_listview);
        addSet=findViewById(R.id.floatingActionButton_add_set);
        et_weight=findViewById(R.id.et_insert_set_weight);
        et_weight.setGravity(Gravity.CENTER_HORIZONTAL);
        et_reps=findViewById(R.id.et_insert_number_of_reps);
        et_reps.setGravity(Gravity.CENTER_HORIZONTAL);
        arrowAnimation=findViewById(R.id.arrow_green);
        deleteSetAlert=findViewById(R.id.delete_alert);
        numberPickerMin=findViewById(R.id.edit_text_time_min);
        numberPickerSec=findViewById(R.id.edit_text_time_sec);
        totalTime_tv=findViewById(R.id.total_time);

    }
    private String stringConverter(ArrayList<String> stringArrayList){
        String listOfObjects="";
        for(int i=0;i<stringArrayList.size();i++){
            if(i==0){
                listOfObjects+=""+stringArrayList.get(i);
            }else{
                listOfObjects+=","+stringArrayList.get(i);
            }
        }
        return listOfObjects;
    }

//    @Override
//    public void onBackPressed() {// when the user presses the "back" button bellow
//        //super.onBackPressed();
//        Intent intent = new Intent(AddWorkoutClass.this,MainActivity.class);
//        startActivity(intent);
//    }
    //CODE FOR TE CIRCULAR EFFECT------------------------------------------------------------------------------------------------------------------------------------------------

    private void circularRevealActivity() {
        int cx = background.getRight() - getDips(44);//These methods return the coordinates of the right and bottom edges of the rectangle representing the view
        int cy = background.getBottom() - getDips(44);//for example:getRight()==getLeft() + getWidth()

        float finalRadius = Math.max(background.getWidth(), background.getHeight());// the final radius of the circle would be as big as the screen

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                background,
                cx,
                cy,
                0,
                finalRadius);

        circularReveal.setDuration(1500);//duration for the wffwct
        background.setVisibility(View.VISIBLE);
        circularReveal.start();

    }

    private int getDips(int dps) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = background.getWidth() - getDips(44);
            int cy = background.getBottom() - getDips(44);

            float finalRadius = Math.max(background.getWidth(), background.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, finalRadius, 0);

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    background.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            circularReveal.setDuration(1500);
            circularReveal.start();
        }
        else {
            super.onBackPressed();
        }
    }

    public void circularBackReveal(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = background.getWidth() - getDips(44);
            int cy = background.getBottom() - getDips(44);

            float finalRadius = Math.max(background.getWidth(), background.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, finalRadius, 0);

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    background.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            circularReveal.setDuration(1500);
            circularReveal.start();
        }
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------
}
