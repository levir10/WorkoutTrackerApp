
package com.example.workouttracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PrimitiveIterator;


public class WorkoutInterfacesAdapter extends RecyclerView.Adapter<WorkoutInterfacesAdapter.WorkoutInterfaceViewHolder> {

    Context mContext;
    private LinearLayout linearLayout;
    ArrayList<Integer> weights = new ArrayList<>();
    ArrayList<Integer> weightsForEachSet = new ArrayList<>();
    ArrayList<Integer> reps = new ArrayList<>();
    ArrayList<Integer> repsForEachSet = new ArrayList<>();
    ArrayList<Integer> isPressedColorReminder = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> pressedMatrix = new ArrayList<ArrayList<Integer>>();
    WorkoutClass workout;
    WorkoutInterfaceAdapter_Child workoutInterfaceAdapter_child;
    ArrayList<ArrayList<Integer>> repsMatrix = new ArrayList<ArrayList<Integer>>();
    public WorkoutInterfacesAdapter(Context mContext, WorkoutClass workout) {

        this.workout = workout;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public WorkoutInterfaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_interface_item, parent, false);
        return new WorkoutInterfacesAdapter.WorkoutInterfaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutInterfacesAdapter.WorkoutInterfaceViewHolder holder, int position) {
        repsMatrix.clear();
        weights.clear();//clears the arraylist (right now its void)
        weights = stringToArraylist(workout.weights);//this method provides "weights" wit each set's weight. for example [100,110,120]
        reps.clear();//clears the arraylist (right now its void)
        reps = stringToArraylist(workout.numberOfReps);// this method provides "reps" with each set numbers. for example: reps = [5,4,3,2]
        setRepsMatrix(reps);
        //each "position" variable, is reps's index.
//        repsForEachSet.clear();
        try {
            holder.numberOfSets.setText("Set No#" + (position + 1) + ": " + weights.get(position));

//            repsForEachSet = setCurrentRepsArray(reps.get(position));//this method makes arraylist for each set. for eaxmple: for set 1, with 5 reps, the arraylist
            //would be[1,2,3,4,5]. for set two with 4 reps, the arraylist would be[1,2,3,4]

        } catch (Exception e) {
            holder.numberOfSets.setText("Set No#" + (position + 1));
        }

        //here we set the new horizontal  recyclerview, that would get the repsForEachSet with the current set's repetitions.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        holder.ChildRV.setLayoutManager(layoutManager);
        holder.ChildRV.setHasFixedSize(true);
        WorkoutInterfaceAdapter_Child workoutInterfaceAdapter_child = new WorkoutInterfaceAdapter_Child(mContext, repsMatrix.get(position), workout, position);
        holder.ChildRV.setAdapter(workoutInterfaceAdapter_child);
        workoutInterfaceAdapter_child.notifyDataSetChanged();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext,"Set number: " + sets.get(position).numberOfSets + " ,Rep Number: "+sets.get(position).numberOfReps,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, WorkoutInterface.class);
                intent.putExtra("workout_sets", workout.numberOfSets);
//                intent.putExtra("workout_reps",sets.get(position).numberOfReps);
                mContext.startActivity(intent);

            }
        });
        holder.setWeight.setText(String.valueOf(weights.get(position)));
        //sets new weights to memory if the user chooses to update them
        holder.setWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                weights.set(position, Integer.parseInt(holder.setWeight.getText().toString()));
                DataBase dataBase = new DataBase(mContext);
                dataBase.updateuserdata(workout.workoutName, weights.size(), stringConverter(reps),
                        stringConverter(weights), workout.timeBetweenSets, workout.weightOrReps, workout.pressedButtons, "last workout was: " + stringConverter(weights));
                dataBase.close();

            }

        });

        holder.callTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WorkoutInterfaceTimer.class);
                intent.putExtra("workout_timeBetweenSets", workout.timeBetweenSets);
                System.out.println("jjjjjjjjjjjjjkhughjgGFHJGJFDXCGHJKFGHVBJ    " +  workout.timeBetweenSets + "   kjhgfdghjkljhgfhjkhgfc");

                mContext.startActivity(intent);

            }
        });

        holder.plusSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    reps.set(position, reps.get(position) + 1);
//                        repsMatrix.get(position).set(repsMatrix.get(position).size(),repsMatrix.get(position).size()+1)
                    setRepsMatrix(reps);
                    WorkoutInterfaceAdapter_Child workoutInterfaceAdapter_child = new WorkoutInterfaceAdapter_Child(mContext, repsMatrix.get(position), workout, position);
                    holder.ChildRV.setAdapter(workoutInterfaceAdapter_child);                } catch (Exception e) {
                    while (position >= reps.size()) {
                        reps.add(5);
                    }
                    reps.set(position, reps.get(position) + 1);
                    setRepsMatrix(reps);
                    WorkoutInterfaceAdapter_Child workoutInterfaceAdapter_child = new WorkoutInterfaceAdapter_Child(mContext, repsMatrix.get(position), workout, position);
                    holder.ChildRV.setAdapter(workoutInterfaceAdapter_child);                }

            }
        });
        holder.minusSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DataBase dataBase=new DataBase(mContext);// call new Database variable
                try {
                    if (reps.get(position) > 1) {
                        reps.set(position, reps.get(position) - 1);
                        setRepsMatrix(reps);
                    }
                    WorkoutInterfaceAdapter_Child workoutInterfaceAdapter_child = new WorkoutInterfaceAdapter_Child(mContext, repsMatrix.get(position), workout, position);
                    holder.ChildRV.setAdapter(workoutInterfaceAdapter_child);
                } catch (Exception e) {
                    System.out.println("Error ocured in WorkoutInterfaceAdatpter - minus set");
                }


            }

        });


    }

    public class LinearLayoutManagerWrapper extends LinearLayoutManager {

        public LinearLayoutManagerWrapper(Context context) {
            super(context);
        }

        public LinearLayoutManagerWrapper(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public LinearLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return workout.numberOfSets;
    }

    static class WorkoutInterfaceViewHolder extends RecyclerView.ViewHolder {
        EditText setWeight;
        TextView numberOfSets;
        ImageView plusSet;
        ImageView minusSet;
        int numberOfReps;
        int weights;
        RecyclerView ChildRV;
        Button callTimer;


        public WorkoutInterfaceViewHolder(@NonNull View itemView) {
            super(itemView);
            numberOfSets = itemView.findViewById(R.id.edit_text_number_of_sets_item);
            ChildRV = itemView.findViewById(R.id.child_recyclerview);
            callTimer = itemView.findViewById(R.id.call_timer);
            setWeight = itemView.findViewById(R.id.edit_text_name_of_set);
            plusSet = itemView.findViewById(R.id.iv_plus_icon);
            minusSet = itemView.findViewById(R.id.iv_minus_icon);
        }

        public void bind(WorkoutInterfaceClass sets) {
            // יש למלא פה אתחולים מתוך projectviewholder ;ילקחו מתוך קובץ הלייאאוט
//            numberOfSets.setText(sets.numberOfSets + ") " +  + "[Kg]"); ;
//            numberOfReps=sets.numberOfReps;

        }


    }

    public ArrayList<Integer> stringToArraylist(String str) {
        ArrayList<String> stringArrayList = new ArrayList<String>(Arrays.asList(str.split(",")));
        ArrayList<Integer> integerArrayList = new ArrayList<Integer>();
        for (int i = 0; i < stringArrayList.size(); i++) {

            integerArrayList.add(Integer.parseInt(stringArrayList.get(i).replaceAll("[\\p{Ps}\\p{Pe}]", "").trim()));
        }
        return integerArrayList;
    }

    public ArrayList<Integer> setCurrentRepsArray(int currentRepsNumber) {

        repsForEachSet.clear();
        for (int i = 0; i < currentRepsNumber; i++) {
            repsForEachSet.add(i + 1);
        }
//            System.out.println(repsForEachSet.toString());
        return repsForEachSet;
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

    public void setRepsMatrix(ArrayList<Integer> reps) {
       ArrayList<Integer> repsArr = new ArrayList<>();
       repsMatrix.clear();
       for (int row=0;row<reps.size();row++){
//System.out.println("THE Reps ARR IS: " + reps + " AND REPS SIZE IS " + reps.size());
           repsMatrix.add(new ArrayList<Integer>());
           for (int col = 0; col < reps.get(row); col++) {

                   repsMatrix.get(row).add(col + 1);


               }

       }

//            System.out.println(repsForEachSet.toString());
//        System.out.println("THE REPS MATRIX IS: " + repsMatrix);
    }

}