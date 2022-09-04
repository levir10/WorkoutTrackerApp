
package com.example.workouttracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class WorkoutInterfaceAdapter_Child extends RecyclerView.Adapter<WorkoutInterfaceAdapter_Child.childWorkoutInterfaceViewHolder>{

    Context mContext;
    WorkoutClass workout;
    boolean isPressed= false;
    LinearLayout linearLayout;
    ArrayList<Integer> reps;//reps has the values of repsForEachSet that given in WorkoutInterfaceAdapter. for example repsForEachSet=[1,2,3,4,5,6]
    public  ArrayList<ArrayList<Integer>> pressedMatrix=new ArrayList<ArrayList<Integer>>();
    int verticalIndex;
    ArrayList<String> isPressedArray=new ArrayList<>();
    int actualReps;



    public WorkoutInterfaceAdapter_Child(Context mContext, ArrayList<Integer> reps, WorkoutClass workout, int position) {

        this.workout=workout;
        this.mContext = mContext;
        this.reps=reps;
        this.verticalIndex=position;

    }

    @NonNull
    @Override
    public WorkoutInterfaceAdapter_Child.childWorkoutInterfaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_workout_interface_item,parent,false);
        return new WorkoutInterfaceAdapter_Child.childWorkoutInterfaceViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  WorkoutInterfaceAdapter_Child.childWorkoutInterfaceViewHolder holder, int position) {
//        System.out.println( " position is: " + position + " numberofrets is "+ reps);

        holder.numberOfReps.setText(""+ reps.get(position));
//        System.out.println("THIS IS reps arraylist , and its vlaue is:" +reps.get(position) +" size of reps is : " + reps.size() );


        //new code:
        final boolean[] isPressed = {false};


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext,WorkoutInterface.class);
            @Override
            public void onClick(View v) {
                if(isPressed[0] ==false){//check if the button is already pressed
                    isPressed[0] =true;
                    actualReps+=1;// add rep count to the actual reps variable
                    if(workout.pressedButtons==null){//save the number of presses in list
                        workout.pressedButtons="";
                    }else {
                        workout.pressedButtons+=","+String.valueOf(verticalIndex) + String.valueOf(position);
                        buttonColorCheck(holder, position, verticalIndex);//if button is pressed in the past, keep it green
                    }
                }else{
                    holder.linearLayout.setBackgroundColor(Color.argb(255,166, 172, 175) );
                    isPressed[0] =false;
                    actualReps-=1;
                    changeButtonStatus(String.valueOf(verticalIndex)+String.valueOf(position));

                }
            }
        });




//        System.out.println(" The current spot being worked is: ["+verticalIndex +","+position+"]");
        if(workout.pressedButtons!=null) {
//            System.out.println("the  v index + position: "+String.valueOf(verticalIndex) + String.valueOf(position) );
//            System.out.println("reps.get(position): "+reps.get(position));
            buttonColorCheck(holder,holder.getLayoutPosition(), verticalIndex);
//            buttonColorGrey(holder,holder.getLayoutPosition(),verticalIndex);
        }
//        System.out.println("the sssssssssiiiiiiiizzzzzzzeeeeee issssssssssss: "+reps.size());
    }
    @Override
    public int getItemCount() {

        return reps.size();
    }



    static class childWorkoutInterfaceViewHolder extends RecyclerView.ViewHolder{
        TextView numberOfReps;
        //        int numberOfReps;
        LinearLayout linearLayout;
        public childWorkoutInterfaceViewHolder(@NonNull View itemView) {
            super(itemView);
            numberOfReps =itemView.findViewById(R.id.tv_reps);
            linearLayout=itemView.findViewById(R.id.ll_child);




        }

        public void bind(WorkoutInterfaceClass sets) {
// מילאתי דברים בתוך onBindViewHolder במקום פה. לא ממש משנה

        }


    }

public  void changeButtonStatus(String checkedString){
        //initiating the arraylist of presses with the presses vector content:
        String tmp="";
//    System.out.println("checkstring value is "+checkedString);
        isPressedArray = new ArrayList<String>(Arrays.asList(workout.pressedButtons.split(",")));
        //check if the pressed button exists in the array -->thus is already pressed.
        for(int i=0;i<isPressedArray.size();i++){
//            System.out.println("isPressedArray.get(i) is "+isPressedArray.get(i));
            if(checkedString.equals(isPressedArray.get(i))){//this button object is already pressed. un-press it by not adding it to tmp variable  and continue
                //do not add to tmp variable
//                System.out.println("take button "+ isPressedArray.get(i) + "out");
            }else {// if this button object is not nullify, add it to the tmp variable

                if(i>0 && isPressedArray.get(i-1)!=","){//an if statement that makes sure we dont insert comma for no reason:
                    tmp+=","+isPressedArray.get(i);
                }else{
                    tmp+=isPressedArray.get(i);
                }
            }
        }

        workout.pressedButtons=tmp;

}
  // a method that checks inside the current vector of presses, whether or not it is pressed. if a button is pressed - dont recycle it back to grey
    public void buttonColorCheck(WorkoutInterfaceAdapter_Child.childWorkoutInterfaceViewHolder holder,int position,int verticalIndex){
        ArrayList<String> pressStatusArray= new ArrayList<String>(Arrays.asList(workout.pressedButtons.split(",")));
        boolean isInList=false;
        for(int i=0;i<pressStatusArray.size();i++){
            if(pressStatusArray.get(i).equals(String.valueOf(verticalIndex) + String.valueOf(position))) {

                   holder.linearLayout.setBackgroundColor(Color.argb(255, 26, 188, 156));
                   isInList=true;
            }
            if(isInList==false){
                holder.linearLayout.setBackgroundColor(Color.argb(255,166, 172, 175) );
            }

        }

    }



}







