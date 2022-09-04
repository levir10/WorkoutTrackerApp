package com.example.workouttracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditWorkoutSetsAdapter extends RecyclerView.Adapter<EditWorkoutSetsAdapter.MainViewHolder> {

    Context mContext;
    private ArrayList<String> reps;
    private ArrayList<String> weights;
    WorkoutClass workout;

    int index;


    public EditWorkoutSetsAdapter(Context mContext, ArrayList<String> reps, ArrayList<String> weights, WorkoutClass workout) {

        this.reps=reps;
        this.weights=weights;        this.mContext = mContext;
        this.workout= workout;

    }


    @NonNull
    @Override
    public EditWorkoutSetsAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_workout_item,parent,false);
        return new EditWorkoutSetsAdapter.MainViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  EditWorkoutSetsAdapter.MainViewHolder holder, int position) {
//takes all the data from "MyWorkout" clicked-on workout, and sends those variable values to "WorkoutInterface.java"

//        System.out.println("reps array is " + reps.toString());
//        System.out.println("weights array is " + weights.toString());
//        System.out.println("number of sets is " + reps.size() +", position's value is "+ position + " index value is " + index);


        holder.et_reps.setText(reps.get(position));
        holder.et_weights.setText(weights.get(position));

        holder.et_reps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                reps.set(position,holder.et_reps.getText().toString());
                DataBase dataBase=new DataBase(mContext);
                dataBase.updateuserdata(workout.workoutName,workout.numberOfSets,stringConverter(reps),stringConverter(weights),workout.timeBetweenSets,workout.weightOrReps,workout.pressedButtons,workout.description);
                dataBase.close();

            }

        });
holder.et_weights.setOnFocusChangeListener(new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View view, boolean b) {
        weights.set(position,holder.et_weights.getText().toString());
        DataBase dataBase=new DataBase(mContext);
        dataBase.updateuserdata(workout.workoutName,workout.numberOfSets,stringConverter(reps),stringConverter(weights),workout.timeBetweenSets,workout.weightOrReps,workout.pressedButtons,workout.description);
        dataBase.close();

    }
});

holder.deleteSet.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        reps.remove(position);
        weights.remove(position);
        workout.numberOfSets-=1;
        DataBase dataBase=new DataBase(mContext);
        dataBase.updateuserdata(workout.workoutName,workout.numberOfSets,stringConverter(reps),stringConverter(weights),workout.timeBetweenSets,workout.weightOrReps,workout.pressedButtons,workout.description);
        dataBase.close();
        EditWorkoutSetsAdapter.this.notifyItemRemoved(position);
        EditWorkoutSetsAdapter.this.notifyItemRangeChanged(position,reps.size());
        EditWorkoutSetsAdapter.this.notifyDataSetChanged();

    }
});



    }

    @Override
    public int getItemCount() {
        return reps.size();
    }
    static class MainViewHolder extends RecyclerView.ViewHolder{

        EditText et_reps;
        EditText et_weights;
        ImageView deleteSet;



        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
           et_reps = itemView.findViewById(R.id.et_insert_number_of_reps_edit_item);
           et_weights = itemView.findViewById(R.id.et_insert_set_weight_edit_item);
           deleteSet = itemView.findViewById(R.id.delete_set);

        }

        public void bind(SetsDataClass repsAndWeights) {
            // יש למלא פה אתחולים מתוך projectviewholder ;ילקחו מתוך קובץ הלייאאוט

        }
    }

    // ניסיתי לעשות שיטה שמעדכנת את מערך החזרות כדי לשמור על ערך של האדיט טקסט.. לא ממש עבד 
    public void repsUpdate(EditWorkoutSetsAdapter.MainViewHolder holder, int position,EditText et_reps){
        ArrayList<String> newReps= new ArrayList<String>();

        for(int i=0;i<reps.size();i++){
            if(holder.et_reps.getText().toString()!=reps.get(i)){
              reps.set(i,holder.et_reps.getText().toString());
            }
            Intent intent=new Intent(mContext,EditWorkoutClass.class);
            intent.putExtra("new_reps_array",reps);
        }
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
}
