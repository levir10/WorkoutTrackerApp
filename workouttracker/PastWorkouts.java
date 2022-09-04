package com.example.workouttracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PastWorkouts extends AppCompatActivity {
    private ImageView editPastWorkouts;
    Dialog myDialog;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_interface);
        setContentView(R.layout.past_workouts);
        editPastWorkouts=findViewById(R.id.edit_past_work_outs);
        //getting all of the past workouts to run inside the adapter and show them as a list.
        DataBasePastWorkouts dataBasePastWorkouts = new DataBasePastWorkouts(PastWorkouts.this);
        List<PastWorkoutsClass> allPastWorkouts = dataBasePastWorkouts.getAllPastWorkouts();
        Collections.reverse(allPastWorkouts);// make the past workout list from start to finish
        RecyclerView listOfPastWorkouts = findViewById(R.id.recyclerview_past_workouts);
        PastWorkoutsAdapter pastWorkoutsAdapter = new PastWorkoutsAdapter(this, (ArrayList<PastWorkoutsClass>) allPastWorkouts);
        listOfPastWorkouts.setAdapter(pastWorkoutsAdapter);
        listOfPastWorkouts.setLayoutManager(new LinearLayoutManager(PastWorkouts.this));

        //taking care of the sort feature:
        DataBase dataBase = new DataBase(PastWorkouts.this);
        List<WorkoutClass> allWorkouts = dataBase.getAllWorkouts();
        ArrayList<String> workoutNames = new ArrayList<>();
        for(int i=0;i<allWorkouts.size();i++){
            workoutNames.add(allWorkouts.get(i).workoutName);
        }
        workoutNames.add("Show all Past workouts");
        final CharSequence[] finalNames = workoutNames.toArray(new CharSequence[workoutNames.size()]);
        editPastWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(PastWorkouts.this);
                myBuilder.setTitle("To show specific exercise only, please choose from the following:").setItems(finalNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
//                        System.out.println("CIIIIIIIIIIIICKKKKEEDDD ONNNNNNN - " + finalNames[position].toString());
                        List<PastWorkoutsClass> sortedList = new ArrayList<PastWorkoutsClass>();
                        for(int i=0;i<allPastWorkouts.size();i++){
                                if(allPastWorkouts.get(i).workoutName.equals(finalNames[position].toString())){
                                    sortedList.add(allPastWorkouts.get(i));
//                                    System.out.println(allPastWorkouts.get(i));
                                }else if(finalNames[position].toString()=="Show all Past workouts"){
                                    sortedList=allPastWorkouts;
                                }

                        }

                        PastWorkoutsAdapter pastWorkoutsAdapter = new PastWorkoutsAdapter(PastWorkouts.this, (ArrayList<PastWorkoutsClass>) sortedList);
                        listOfPastWorkouts.setAdapter(pastWorkoutsAdapter);
                        listOfPastWorkouts.setLayoutManager(new LinearLayoutManager(PastWorkouts.this));
                        pastWorkoutsAdapter.notifyDataSetChanged();
                    }
                });
                myDialog = myBuilder.create();
                myDialog.show();



            }
        });

    }

    @Override
    public void onBackPressed() {// when the user presses the "back" button bellow
        //super.onBackPressed();
        Intent intent = new Intent(PastWorkouts.this,MainActivity.class);
        startActivity(intent);
    }



}
