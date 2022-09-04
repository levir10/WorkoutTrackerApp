package com.example.workouttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
Button myWorkoutsButton;
Button pastWorkoutsButton;
Button addWorkooutButton;
BottomNavigationView bottomNavigationView;
NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWorkoutsButton =findViewById(R.id.myWorkoutsButton);
        pastWorkoutsButton=findViewById(R.id.past_workouts_button);
        addWorkooutButton=findViewById(R.id.add_workout_button_main);
//        bottomNavigationView=findViewById(R.id.bottomNavigationView);


        myWorkoutsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToMyWorkouts = new Intent(MainActivity.this,MyWorkouts.class);
                startActivity(moveToMyWorkouts);

            }
        });
        pastWorkoutsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PastWorkoutsClass pastWorkouts;
                Intent intent = new Intent(MainActivity.this,PastWorkouts.class);
                startActivity(intent);

           }
        });
        addWorkooutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,AddIntervalTimer.class);
                startActivity(intent);
            }
        });


    }

}