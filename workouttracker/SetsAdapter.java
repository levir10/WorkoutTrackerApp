package com.example.workouttracker;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

 class SetsAdaper extends ArrayAdapter<SetsDataClass> {

    private static final String TAG ="SetsAdapter";
    private Context mContext;
    int mResource;

    public SetsAdaper(Context context, int resource, ArrayList<SetsDataClass> object) {
        super(context,resource,object);
        mContext=context;
        mResource=resource;


    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
    //get data from user
        String weight = getItem(position).setWeight;
        String reps = getItem(position).setReps;
        //create the workout set object with all the data -
        SetsDataClass set =new SetsDataClass(weight,reps);

        LayoutInflater inflater =LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);


        TextView tv_weight=(android.widget.TextView) convertView.findViewById(R.id.tv_insert_set_weight);
        TextView tv_reps=(android.widget.TextView) convertView.findViewById(R.id.tv_insert_number_of_reps);
        tv_weight.setText(weight + " [Kg]");
        tv_reps.setText(reps + " [reps]");

        return convertView;
    }
    public void  removeItem(int position){


    }
}
