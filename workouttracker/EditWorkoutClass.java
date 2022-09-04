package com.example.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditWorkoutClass extends AppCompatActivity {

    TextView workoutName;
    int numberOfSets;
    EditText timeBetweenSets;
    EditText numberOfReps;
    Switch weightOrReps;// weight is true
    EditText description;
    Button editWorkouts;
    FloatingActionButton addSet;
    ArrayList<String> weights;
    ArrayList<String> reps;
    EditText et_weight;
    EditText et_reps;
    String EditedWeights="";
    String EditedReps="";
    int EditedWorkoutSize=0;
    WorkoutClass thisWorkout;
    String editedWorkoutName;
    String editedDescription;
    String editedTime;
    Switch editedWeightOrReps;
    String finelName;


    String str_reps;
    String str_weights;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_workout_main);
        getSupportActionBar().hide();
        findViews();
//        setViewsContent();
        List<WorkoutClass> workout;
        DataBase dataBase=new DataBase(EditWorkoutClass.this);
        workout = dataBase.getAllWorkouts();// take the workout data from the database, to insert it to the edit workout.
        setViews(workout.get(getIncomingIntent(savedInstanceState)));// set the views with the original workout data, for the user to decide what he weant to change
        thisWorkout = workout.get(getIncomingIntent(savedInstanceState));
//        System.out.println("workout is: " +workout.get(getIncomingIntent(savedInstanceState)).workoutName + " reps are - " + workout.get(getIncomingIntent(savedInstanceState)).numberOfReps);
        ArrayList<SetsDataClass> setsArray = new ArrayList<SetsDataClass>();
        //creating arraylist from strings. fixing the square brackets [,], by using the method: .replaceAll("[\\p{Ps}\\p{Pe}]", "")
        // and fixing the spaces that created by using the method .trim()
        ArrayList<String> reps = new ArrayList<String>(Arrays.asList(workout.get(getIncomingIntent(savedInstanceState)).numberOfReps.replaceAll("[\\p{Ps}\\p{Pe}]", "").trim().split(",")));//creating arraylist from string variable
        ArrayList<String> weights = new ArrayList<String>(Arrays.asList(workout.get(getIncomingIntent(savedInstanceState)).weights.replaceAll("[\\p{Ps}\\p{Pe}]", "").trim().split(",")));//creating arraylist from string variable
//        System.out.println("THIS IS THE ARRAYS TO CHECKK: " +  reps.toString() + " weights " + weights.toString() );

        RecyclerView addSetsRecyclerview=findViewById(R.id.add_reps_sets_recyclerview_edit_workout);
        EditWorkoutSetsAdapter editWorkoutSetsAdapter = new EditWorkoutSetsAdapter(EditWorkoutClass.this,reps,weights,  thisWorkout);
        addSetsRecyclerview.setAdapter(editWorkoutSetsAdapter);
        addSetsRecyclerview.setLayoutManager(new LinearLayoutManager(EditWorkoutClass.this));
        addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reps.add(et_reps.getText().toString());
                weights.add(et_weight.getText().toString());
                numberOfSets=reps.size();
                dataBase.updateuserdata(thisWorkout.workoutName,numberOfSets>thisWorkout.numberOfSets?numberOfSets: thisWorkout.numberOfSets, reps.toString(),weights.toString(), thisWorkout.timeBetweenSets, thisWorkout.weightOrReps,thisWorkout.pressedButtons,thisWorkout.description);
                dataBase.close();
                addSetsRecyclerview.setAdapter(editWorkoutSetsAdapter);
                et_weight.setText(null);
                et_reps.setText(null);
            }
        });



        editWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase dataBase1 =new DataBase(EditWorkoutClass.this);
                dataBase1.updateuserdata(thisWorkout.workoutName,EditedWorkoutSize>thisWorkout.numberOfSets?EditedWorkoutSize:thisWorkout.numberOfSets,
                        stringConverter(reps),stringConverter(weights),timeBetweenSets.getText().toString()==""?thisWorkout.timeBetweenSets:Integer.parseInt(timeBetweenSets.getText().toString()),
                        weightOrReps.isChecked(),thisWorkout.pressedButtons,description.getText().toString()==""?thisWorkout.description:description.getText().toString());
                dataBase1.close();

                Intent intent = new Intent(EditWorkoutClass.this,MyWorkouts.class);
                startActivity(intent);
            }
        });


    }

    private void setViews(WorkoutClass workout) {
        workoutName.setText(workout.workoutName + " - Edit");
        timeBetweenSets.setText(String.valueOf(workout.timeBetweenSets));
        weightOrReps.setChecked(workout.weightOrReps);
        description.setText(workout.description);

    }



    //TAKE THE INPUT FROME THE USER
    private void findViews() {
        workoutName = findViewById(R.id.edit_text_workout_name_edit_workout);
//        numberOfSets=findViewById(R.id.edit_text_number_of_sets);
        timeBetweenSets=findViewById(R.id.edit_text_time_edit_workout);
//        numberOfReps=findViewById(R.id.edit_text_number_of_reps);
        weightOrReps=findViewById(R.id.switch2_edit_workout);
        description=findViewById(R.id.edit_text_workout_description_edit_workout);
        editWorkouts=findViewById(R.id.button_add_workout_edit_workout);
        addSet=findViewById(R.id.floatingActionButton_add_set_edit_workout);
        et_reps=findViewById(R.id.et_insert_number_of_reps_edit_workout);
        et_weight=findViewById(R.id.et_insert_set_weight_edit_workout);


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
    //method to get intent from "MyWorkoutsAdapter" getting the workout's index chosen by the user
private int getIncomingIntent(Bundle savedInstanceState){
        int workoutIndex;
    if (savedInstanceState == null) {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            workoutIndex=0;
        }else{
            workoutIndex=extras.getInt("chosen_workout");
        }
    }else{
        workoutIndex=(int) savedInstanceState.getSerializable("chosen_workout");
    }
    return workoutIndex;
}

    //A method to get intent from EditWorkoutSetAdapter -->taking the edited weights and reps
private void getIncomingEditedIntent(Bundle savedInstanceState){
    if (savedInstanceState == null) {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            EditedWeights="";
            EditedReps="";
            EditedWorkoutSize=0;
        }else{
            EditedWeights=extras.getString("edited_weights");
            EditedReps=extras.getString("edited_reps");
            EditedWorkoutSize=extras.getInt("edited_workout_sets");
            weights=extras.getStringArrayList("edited_workout_weight_array");
            reps=extras.getStringArrayList("edited_workout_sets_array");

        }
    }else{
        EditedWeights=(String) savedInstanceState.getSerializable("edited_weights");
        EditedReps=(String) savedInstanceState.getSerializable("edited_reps");
        EditedWorkoutSize=(int) savedInstanceState.getSerializable("edited_workout_sets");
        weights=(ArrayList<String>) savedInstanceState.getSerializable("edited_workout_weight_array");
        reps=(ArrayList<String>) savedInstanceState.getSerializable("edited_workout_sets_array");

    }
}
void setFocusListeners(){
workoutName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View view, boolean b) {
        editedWorkoutName=workoutName.getText().toString();
    }
});
    timeBetweenSets.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            editedTime=timeBetweenSets.getText().toString();

        }
    });
    description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            editedDescription=description.getText().toString();
        }
    });

}


}
