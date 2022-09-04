package com.example.workouttracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PastWorkoutsAdapter extends RecyclerView.Adapter<PastWorkoutsAdapter.PastWorkoutsViewHolder> {
    Context mContext;
    private ArrayList<PastWorkoutsClass> allPastWorkouts;

    public PastWorkoutsAdapter(Context mContext , ArrayList<PastWorkoutsClass> allPastWorkouts) {

        this.allPastWorkouts = allPastWorkouts;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public PastWorkoutsAdapter.PastWorkoutsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_workouts_item,parent,false);
        return new PastWorkoutsAdapter.PastWorkoutsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  PastWorkoutsAdapter.PastWorkoutsViewHolder holder, int position) {
        holder.bind(allPastWorkouts.get(position));
        //new code:
//takes all the data from "MyWorkout" clicked-on workout, and sends those variable values to "WorkoutInterface.java"
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"clicked on " + allPastWorkouts.get(position).workoutName,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext,PastWorkouts.class);
//                intent.putExtra("workout_name",allWorkouts.get(position).workoutName);
//                intent.putExtra("workout_sets",allWorkouts.get(position).numberOfSets);
//                intent.putExtra("workout_reps",allWorkouts.get(position).numberOfReps);
//                intent.putExtra("workout_timeBetweenSets",allWorkouts.get(position).timeBetweenSets);
//                intent.putExtra("workout_weightOrReps",allWorkouts.get(position).weightOrReps);
                mContext.startActivity(intent);

            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("What would you like to do?")
                        .setItems(R.array.option_array, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==1){//EDIT EXERCISE
                                    Intent intent = new Intent(mContext, EditPastWorkout.class);
                                    intent.putExtra("chosen_past_workout",allPastWorkouts.get(position).getPastWorkoutId());//we send allPastWorkouts.size()-position-1 because the list starts
                                    //from bottom to top! so we should transfer allPastWorkouts.size()-position, and deduct 1 so we get index and not size.
                                    mContext.startActivity(intent);

                                }else if(i==0){//DELETE EXERCISE
                                    DataBasePastWorkouts dataBasePastWorkouts=new DataBasePastWorkouts(mContext);
                                    AlertDialog.Builder deleteWorkoutBuilder = new AlertDialog.Builder(mContext);
                                    deleteWorkoutBuilder.setTitle("Are you sure you want to delete this exurcise?")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dataBasePastWorkouts.deleteWorkout(allPastWorkouts.get(position));
                                                    allPastWorkouts.remove(position);
                                                    PastWorkoutsAdapter.this.notifyItemRemoved(position);
                                                    PastWorkoutsAdapter.this.notifyItemRangeChanged(position,allPastWorkouts.size());
                                                    PastWorkoutsAdapter.this.notifyDataSetChanged();
                                                }
                                            }).setNegativeButton("Cancel",null);
                                    AlertDialog alertDelete = deleteWorkoutBuilder.create();
                                    alertDelete.show();
                                    Toast.makeText(mContext,"Exurcise Deleted.",Toast.LENGTH_SHORT);
                                    //notify the adapter to refresh



                                }else if(i==2){
                                    System.out.println("chose 3!!!!!!!!!!!!!!!!!!");

                                }

                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return allPastWorkouts.size();
    }
    static class PastWorkoutsViewHolder extends RecyclerView.ViewHolder{

        TextView workoutName;
        TextView date;
        TextView weight;
        TextView reps;
        TextView orm;
        TextView rpe;
        TextView notes;
        ImageView editButton;



        public PastWorkoutsViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.tv_exercise);
            date = itemView.findViewById(R.id.tv_date);
            weight = itemView.findViewById(R.id.tv_weight);
            reps = itemView.findViewById(R.id.tv_reps);
            orm = itemView.findViewById(R.id.tv_ORM);
            rpe = itemView.findViewById(R.id.tv_rpe);
            notes= itemView.findViewById(R.id.tv_notes);
            editButton=itemView.findViewById(R.id.tv_header_blank);

        }

        public void bind(PastWorkoutsClass allPastWorkouts) {
            // יש למלא פה אתחולים מתוך projectviewholder ;ילקחו מתוך קובץ הלייאאוט
            System.out.println(allPastWorkouts.workoutName + " " + allPastWorkouts.workoutDate+" " + allPastWorkouts.notes);
            workoutName.setText(allPastWorkouts.workoutName);
            date.setText(allPastWorkouts.workoutDate);
            weight.setText("" + allPastWorkouts.workoutWeight);
            reps.setText("" +allPastWorkouts.actualNumberOfReps);
            orm.setText("" + allPastWorkouts.ORM);
            rpe.setText("" + allPastWorkouts.RPE);
            notes.setText(allPastWorkouts.notes);
        }
    }


}

