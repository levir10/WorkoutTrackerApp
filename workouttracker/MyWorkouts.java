package com.example.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MyWorkouts extends AppCompatActivity {
    FloatingActionButton addButton;//Button to move to "add workout" activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_my_workouts_recyclerview);
        getSupportActionBar().hide();
        try{
            getNewWorkoutData();
            DataBase dataBase = new DataBase(MyWorkouts.this);
            List<WorkoutClass> allWorkouts = dataBase.getAllWorkouts();
//            Toast.makeText(MyWorkouts.this, allWorkouts.toString(), Toast.LENGTH_SHORT).show();
            RecyclerView listOfWorkouts = findViewById(R.id.recyclerview_my_workouts);
            MyWorkoutsAdapter myWorkoutsAdapter = new MyWorkoutsAdapter(this, (ArrayList<WorkoutClass>) allWorkouts);
            listOfWorkouts.setAdapter(myWorkoutsAdapter);

        }catch (Exception e){
            Toast.makeText(MyWorkouts.this,"שגיאה בהכנסת נתוני אימון. בבקשה נסו שנית",Toast.LENGTH_LONG).show();
        }
    }

    private void getNewWorkoutData() {

        addButton = findViewById(R.id.add_workout_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToAddWorkout = new Intent(MyWorkouts.this,AddWorkoutClass.class);
                startActivity(moveToAddWorkout);

            }
        });

    }
}
