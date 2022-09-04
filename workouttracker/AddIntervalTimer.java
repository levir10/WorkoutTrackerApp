package com.example.workouttracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.workouttracker.AddWorkoutClass;
import com.example.workouttracker.IntervalTimer;
import com.example.workouttracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddIntervalTimer extends AppCompatActivity {
    private int workTime;
    private int restTime;
    private int cycles;
    int total;
    EditText et_worktime;
    EditText et_restTime;
    EditText et_cycles;
    EditText totalTime;
    TextView tv_total;
    FloatingActionButton plusWorkTime;
    FloatingActionButton plusRestTime;
    FloatingActionButton plusCycle;
    FloatingActionButton MinusWorkTime;
    FloatingActionButton MinusRestTime;
    FloatingActionButton MinusCycle;
    Button timerButton;
    private Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;
    EditText increaseMe_tv;
    EditText decreaseMe_tv;
    int increaseMe;
    int decreaseMe;
    LottieAnimationView arrow1;
    LottieAnimationView arrow2;
    LottieAnimationView arrow3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interval_main);
        findViews();
// IF USER WANT TO INCREASE WORK TIME:

        plusWorkTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_worktime.getText().toString().equals("")) {// the edit text has content
                    workTime = 1;
                } else {// the edit text is empty
                    workTime = Integer.parseInt(et_worktime.getText().toString()) + 1;
                }
                et_worktime.setText("" + workTime);
                arrow1.setVisibility(View.INVISIBLE);
                arrow2.setVisibility(View.VISIBLE);
            }

        });
        plusWorkTime.setOnLongClickListener(// if user presses long-->add numbers quickly
                new View.OnLongClickListener() {
                    public boolean onLongClick(View arg0) {
                        mAutoIncrement = true;
                        repeatUpdateHandler.post(new RptUpdater());
                        increaseMe=workTime;
                        increaseMe_tv=findViewById(R.id.et_work_time);
                        arrow1.setVisibility(View.INVISIBLE);
                        arrow2.setVisibility(View.VISIBLE);
                        return false;
                    }
                }
        );

        plusWorkTime.setOnTouchListener(new View.OnTouchListener() {// if user presses long-->add numbers quickly
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                        && mAutoIncrement) {
                    mAutoIncrement = false;
                    arrow1.setVisibility(View.INVISIBLE);
                    arrow2.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        // IF USER WANT TO DECREASE WORK TIME:

        MinusWorkTime.setOnClickListener(new View.OnClickListener() {// if user presses long-->add numbers quickly
            @Override
            public void onClick(View v) {
                if (et_worktime.getText().toString().equals("") || et_worktime.getText().toString().equals("0")) {// the edit text has content
                    workTime = 0;
                } else {// the edit text is empty
                    workTime = Integer.parseInt(et_worktime.getText().toString()) - 1;
                }
                et_worktime.setText("" + workTime);
            }

        });
        MinusWorkTime.setOnLongClickListener(// if user presses long-->decrease numbers quickly
                new View.OnLongClickListener() {
                    public boolean onLongClick(View arg0) {
                        mAutoDecrement = true;
                        repeatUpdateHandler.post(new RptUpdater());
                        decreaseMe=workTime;
                        decreaseMe_tv=findViewById(R.id.et_work_time);
                        return false;
                    }
                }
        );

        MinusWorkTime.setOnTouchListener(new View.OnTouchListener() {// if user presses long-->add numbers quickly
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                        && mAutoDecrement) {
                    mAutoDecrement = false;
                }
                return false;
            }
        });

        // IF USER WANT TO INCREASE REST TIME:

        plusRestTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_restTime.getText().toString().equals("")) {// the edit text has content
                    restTime = 1;
                } else {// the edit text is empty
                    restTime = Integer.parseInt(et_restTime.getText().toString()) + 1;
                }
                et_restTime.setText("" + restTime);
                arrow2.setVisibility(View.INVISIBLE);
                arrow3.setVisibility(View.VISIBLE);
            }
        });

        plusRestTime.setOnLongClickListener(// if user presses long-->add numbers quickly
                new View.OnLongClickListener() {
                    public boolean onLongClick(View arg0) {
                        mAutoIncrement = true;
                        repeatUpdateHandler.post(new RptUpdater());
                        increaseMe=restTime;
                        increaseMe_tv=findViewById(R.id.et_rest_time);
                        return false;
                    }
                }
        );

        plusRestTime.setOnTouchListener(new View.OnTouchListener() {// if user presses long-->add numbers quickly
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                        && mAutoIncrement) {
                    mAutoIncrement = false;
                    arrow2.setVisibility(View.INVISIBLE);
                    arrow3.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        // IF USER WANT TO DECREASE REST TIME:
        MinusRestTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_restTime.getText().toString().equals("") || et_restTime.getText().toString().equals("0")) {// the edit text has content
                    restTime = 0;
                } else {// the edit text is empty
                    restTime = Integer.parseInt(et_restTime.getText().toString()) - 1;
                }
                et_restTime.setText("" + restTime);
            }
        });

        MinusRestTime.setOnLongClickListener(// if user presses long-->decrease numbers quickly
                new View.OnLongClickListener() {
                    public boolean onLongClick(View arg0) {
                        mAutoDecrement = true;
                        repeatUpdateHandler.post(new RptUpdater());
                        decreaseMe=restTime;
                        decreaseMe_tv=findViewById(R.id.et_rest_time);
                        return false;
                    }
                }
        );

        MinusRestTime.setOnTouchListener(new View.OnTouchListener() {// if user presses long-->add numbers quickly
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                        && mAutoDecrement) {
                    mAutoDecrement = false;
                }
                return false;
            }
        });
// IF USER WANT TO INCREASE CYCLES:
        plusCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_cycles.getText().toString().equals("")) {// the edit text has content
                    cycles = 1;
                } else {// the edit text is empty
                    cycles = Integer.parseInt(et_cycles.getText().toString()) + 1;
                }
                et_cycles.setText("" + cycles);

            }
        });


        plusCycle.setOnLongClickListener(// if user presses long-->add numbers quickly
                new View.OnLongClickListener() {
                    public boolean onLongClick(View arg0) {
                        mAutoIncrement = true;
                        repeatUpdateHandler.post(new RptUpdater());
                        increaseMe=cycles;
                        increaseMe_tv=findViewById(R.id.et_cycles);
                        return false;
                    }
                }
        );

        plusCycle.setOnTouchListener(new View.OnTouchListener() {// if user presses long-->add numbers quickly
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                        && mAutoIncrement) {
                    mAutoIncrement = false;
                    arrow3.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        // IF USER WANT TO DECREASE CYCLES:
        MinusCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_cycles.getText().toString().equals("") || et_cycles.getText().toString().equals("0")) {// the edit text has content
                    cycles = 0;
                } else {// the edit text is empty
                    cycles = Integer.parseInt(et_cycles.getText().toString()) - 1;
                }
                et_cycles.setText("" + cycles);
            }
        });

        MinusCycle.setOnLongClickListener(// if user presses long-->decrease numbers quickly
                new View.OnLongClickListener() {
                    public boolean onLongClick(View arg0) {
                        mAutoDecrement = true;
                        repeatUpdateHandler.post(new RptUpdater());
                        decreaseMe=cycles;
                        decreaseMe_tv=findViewById(R.id.et_cycles);
                        return false;
                    }
                }
        );

        MinusCycle.setOnTouchListener(new View.OnTouchListener() {// if user presses long-->add numbers quickly
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                        && mAutoDecrement) {
                    mAutoDecrement = false;
                }
                return false;
            }
        });

        // show total time and data
        if (cycles != 0 && workTime != 0 && restTime != 0) {
            tv_total.setVisibility(View.VISIBLE);
            totalTime.setVisibility(View.VISIBLE);
            total = (workTime + restTime) * cycles - restTime;
            totalTime.setText("" + total);
        }


        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_cycles.getText().toString()!="" && et_worktime.getText().toString()!="" && et_restTime.getText().toString()!="") {
                    setViews();
                    Intent intent = new Intent(AddIntervalTimer.this, IntervalTimer.class);
                    intent.putExtra("work_time",et_worktime.getText().toString()!="" &&Integer.parseInt(et_worktime.getText().toString()) >workTime?Integer.parseInt(et_worktime.getText().toString()):workTime);
                    intent.putExtra("rest_time",et_restTime.getText().toString()!="" &&Integer.parseInt(et_restTime.getText().toString()) >restTime?Integer.parseInt(et_restTime.getText().toString()):restTime);
                    intent.putExtra("cycles_number",et_cycles.getText().toString()!="" &&Integer.parseInt(et_cycles.getText().toString()) >cycles?Integer.parseInt(et_cycles.getText().toString()):cycles);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddIntervalTimer.this);
                    builder.setTitle("Insert input")
                            .setMessage("Please insert Work time, rest time, and number of cycles to continue.")
                            .setPositiveButton("Ok.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });


    }

    private void findViews() {
        et_worktime = findViewById(R.id.et_work_time);
        et_restTime = findViewById(R.id.et_rest_time);
        et_cycles = findViewById(R.id.et_cycles);
        totalTime = findViewById(R.id.et_total_time);
        plusWorkTime = findViewById(R.id.fb_plus_work_interval);
        plusRestTime = findViewById(R.id.fb_plus_rest_interval);
        plusCycle = findViewById(R.id.fb_plus_cycles_interval);
        MinusWorkTime = findViewById(R.id.fb_minus_work_interval);
        MinusRestTime = findViewById(R.id.fb_minus_break_interval);
        MinusCycle = findViewById(R.id.fb_minus_cycle_interval);
        timerButton = findViewById(R.id.button_start_intervals);
        arrow1 = findViewById(R.id.arrow_green);
        arrow2 = findViewById(R.id.arrow_green2);
        arrow3 = findViewById(R.id.arrow_green3);

    }

    private void setViews() {
        cycles = Integer.parseInt(et_cycles.getText().toString());
        workTime = Integer.parseInt(et_worktime.getText().toString());
        restTime = Integer.parseInt(et_restTime.getText().toString());

    }

    class RptUpdater implements Runnable {// new thread for long presses

        public void run() {
            if (mAutoIncrement) {
                increment();
                repeatUpdateHandler.postDelayed(new RptUpdater(), 50);
            } else if (mAutoDecrement) {
                decrement();
                repeatUpdateHandler.postDelayed(new RptUpdater(), 50);
            }
        }
    }

    public void decrement() {
        if(decreaseMe>0){
            decreaseMe--;
            decreaseMe_tv.setText("" + decreaseMe);
        }

    }

    public void increment() {
        increaseMe++;
        increaseMe_tv.setText("" + increaseMe);
    }
}
