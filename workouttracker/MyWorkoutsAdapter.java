package com.example.workouttracker;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyWorkoutsAdapter extends RecyclerView.Adapter<MyWorkoutsAdapter.WorkoutsMainViewHolder>{

    Context mContext;
    private ArrayList<WorkoutClass>  allWorkouts;
    ArrayList<String> optionAlertDialog;

    public MyWorkoutsAdapter(Context mContext , ArrayList<WorkoutClass> allWorkouts) {

        this.allWorkouts = allWorkouts;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public WorkoutsMainViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_workouts_list_item,parent,false);
        return new MyWorkoutsAdapter.WorkoutsMainViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  MyWorkoutsAdapter.WorkoutsMainViewHolder holder, int position) {
        holder.bind(allWorkouts.get(position));
        //new code:
//takes all the data from "MyWorkout" clicked-on workout, and sends those variable values to "WorkoutInterface.java"
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"clicked on " + allWorkouts.get(position).workoutName,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext,WorkoutInterface.class);

                intent.putExtra("chosen_workout",position);
                mContext.startActivity(intent);

            }
        });
        ArrayList<Integer> selectedItems = new ArrayList<>();


        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("What would you like to do?")
                        .setItems(R.array.option_array, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==1){//EDIT EXERCISE
                                        Intent intent = new Intent(mContext,EditWorkoutClass.class);
                                        intent.putExtra("chosen_workout",position);
                                        mContext.startActivity(intent);

                                }else if(i==0){//DELETE EXERCISE
                                    DataBase dataBase=new DataBase(mContext);
                                    AlertDialog.Builder deleteWorkoutBuilder = new AlertDialog.Builder(mContext);
                                    deleteWorkoutBuilder.setTitle("Are you sure you want to delete this exurcise?")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dataBase.deleteWorkout(allWorkouts.get(position));
                                                    allWorkouts.remove(position);
                                                    MyWorkoutsAdapter.this.notifyItemRemoved(position);
                                                    MyWorkoutsAdapter.this.notifyItemRangeChanged(position,allWorkouts.size());
                                                    MyWorkoutsAdapter.this.notifyDataSetChanged();
                                                }
                                            }).setNegativeButton("Cancel",null);
                                    AlertDialog alertDelete = deleteWorkoutBuilder.create();
                                    alertDelete.show();
                                    Toast.makeText(mContext,"Exurcise Deleted.",Toast.LENGTH_SHORT);
                                    //notify the adapter to refresh



                                }else if(i==2){
                                    System.out.println("chose graph!!!");
//                                    Intent intent=new Intent(mContext,ProgressGraph.class);
//                                    mContext.startActivity(intent);

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
        return allWorkouts.size();
    }
    static class WorkoutsMainViewHolder extends RecyclerView.ViewHolder{

        TextView workoutName;
        TextView description;
        ImageView editButton;



        public WorkoutsMainViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.workout_list_title);
            description = itemView.findViewById(R.id.workout_list_subtitle);
            editButton = itemView.findViewById(R.id.my_workouts_edit_button);

        }

        public void bind(WorkoutClass allWorkouts) {
            // יש למלא פה אתחולים מתוך projectviewholder ;ילקחו מתוך קובץ הלייאאוט
            workoutName.setText(allWorkouts.workoutName);
            description.setText(allWorkouts.description);
        }
    }


}
