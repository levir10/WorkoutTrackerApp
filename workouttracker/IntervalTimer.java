package com.example.workouttracker;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.concurrent.CyclicBarrier;

public class IntervalTimer extends AppCompatActivity {

    //defining a constant for starting time comfort. we could do regular edit text..
    private static long START_TIME_IN_MILLIS;// the time (600,000 milisec=10 minutes.) will change according to the user!
    private TextView mTextViewCountDown;
    private Button mButtonReset;
    private Button mButtonStartPause;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;// variable to tell us if the time is running or not
    private long mTimeLeftInMillis;
    private long mWorkTime;
    private long mRestTime;
    private int workTime;
    private int restTime;
    private int cycles;
    private int cyclesCount;
    private long timeCounter;
    private long mTimeToShow;
    private SoundPool soundPool;
    private int boxingBell,countDownSound;
    private boolean isFirstCycle;
    TextView cyclesLeft_tv;
    TextView workOrRest_tv;
    RelativeLayout layout;
    private Vibrator vibrator;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //looping on each cycle, and calling rest and work functions:
        cycles = setCycles();
        cyclesCount = 1;
        setContentView(R.layout.workout_interface_timer);
        workTime=setWorkTime();
        restTime=setRestTime();
//        timeCounter=(workTime+1)*1000;
        timeCounter=6000;

        START_TIME_IN_MILLIS=((workTime+restTime)*(cycles+1)/2-restTime+5+cycles)*1000;//added 5 seconds for get ready.
        mTimeLeftInMillis=START_TIME_IN_MILLIS;
        mTextViewCountDown = findViewById(R.id.textView_timer);
        mButtonReset = findViewById(R.id.reset_button);
        mButtonStartPause = findViewById(R.id.pause_start_button);
        layout = findViewById(R.id.relative_layout_timer);
        cyclesLeft_tv=findViewById(R.id.cycles_left);
        workOrRest_tv=findViewById(R.id.rest_or_work);
        workOrRest_tv.setVisibility(View.VISIBLE);
        cyclesLeft_tv.setVisibility(View.VISIBLE);
        System.out.println("worktime: " + workTime + ", resttime: " + restTime + " cycles: " + cycles);
        isFirstCycle=true;
        vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);
        startTimer();

        //sounds for the UI
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        }
        else {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 1.0f, 1.0f, 0, 0, 1.0f);
            }
        });
//        soundPool.load(this, R.raw.boxing, 1);
        //END of sounds


        mButtonStartPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        updateCountDownText();


    }

    private void startTimer() {
        //variables passed to CountDownTimer: time left in milliseconds, and interval size--> we call countDownTimer every 1000 milliseconds
            mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    mTimerRunning = false;
                    mButtonStartPause.setText("Start");
                    mButtonStartPause.setVisibility(View.INVISIBLE);
                    mButtonReset.setVisibility(View.VISIBLE);
                }

            }.start();
            mTimerRunning = true;
            mButtonStartPause.setText("Pause");
            mButtonReset.setVisibility(View.INVISIBLE);

        }

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning=false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);

    }
    private  void resetTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {

        timeCounter-=1*1000;

        if(isFirstCycle) {// for first "Get ready" alert

            int minutes = (int) (timeCounter/1000)/60;
            int seconds = (int)(timeCounter/1000)%60;
            String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%2d",minutes,seconds);
            mTextViewCountDown.setText(timeLeftFormatted);
            if(timeCounter==0){
                timeCounter=(workTime+1)*1000;
            }
            workOrRest_tv.setText("Ready? Get Set..");
            if(minutes==0 && seconds==1){

                cyclesLeft_tv.setText("Cycles:"+(cyclesCount+1)/2+"/"+(cycles+1)/2);
                workOrRest_tv.setText("Go!");
            }else if(minutes==0 && seconds==5){
                // Sound effect on next cycle
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        soundPool.play(sampleId, 1.0f, 1.0f, 0, 0, 1.0f);

                    }
                });

                soundPool.load(IntervalTimer.this, R.raw.countdoun, 1);
                workOrRest_tv.setText("Read? Get set..");
                vibrator.vibrate(1000);
            }else if(minutes==0 && seconds==0){
                isFirstCycle=false;
            }

        }else if(cyclesCount%2!=0){//work sets
            workOrRest_tv.setText("Work!");
            layout.setBackgroundColor(Color.argb(255,255, 93, 54));
            int minutes = (int) (timeCounter/1000)/60;
            int seconds = (int)(timeCounter/1000)%60;
            String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%2d",minutes,seconds);
            mTextViewCountDown.setText(timeLeftFormatted);
            if(timeCounter==0){
              timeCounter=(restTime+1)*1000;
              cyclesCount+=1;

            }
            if(minutes==0 && seconds==1){
                // Sound effect on next cycle
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        soundPool.play(sampleId, 1.0f, 1.0f, 0, 0, 1.0f);

                    }
                });
                soundPool.load(IntervalTimer.this, R.raw.boxing, 1);
                vibrator.vibrate(1000);
                if (mTimeLeftInMillis==0){
                    mTextViewCountDown.setText("Done!");
                }
            }
        }else if(cyclesCount%2==0){
            workOrRest_tv.setText("Rest..");
            layout.setBackgroundColor(Color.argb(255,163, 228, 215));
            int minutes = (int) (timeCounter/1000)/60;
            int seconds = (int)(timeCounter/1000)%60;
            String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%2d",minutes,seconds);
            mTextViewCountDown.setText(timeLeftFormatted);
            if(timeCounter==0){
                timeCounter=(workTime+1)*1000;
                cyclesCount+=1;
                cyclesLeft_tv.setText("Cycles:"+(cyclesCount+1)/2+"/"+(cycles+1)/2);
            }
            if(minutes==0 && seconds==1){
                // Sound effect on next cycle
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        soundPool.play(sampleId, 1.0f, 1.0f, 0, 0, 1.0f);
                    }
                });
                soundPool.load(IntervalTimer.this, R.raw.boxing, 1);
                vibrator.vibrate(1000);
            }
            if (mTimeLeftInMillis==0){
                mTextViewCountDown.setText("Done!");
            }
        }
//        System.out.println("mTextViewCountDown.getText().toString()==\"00:00\" is: " + mTextViewCountDown.getText().toString());

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
    private Long getIncomingIntent() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            long timeBetweenSets = (long) bundle.getInt("workout_timeBetweenSets");
            return timeBetweenSets*60000;
        } else {
            return Long.valueOf(3*60000);
        }
    }





    private int setWorkTime() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            workTime = bundle.getInt("work_time");
            return workTime;
        } else {
            System.out.println("Error in IntervalTimer insidd setCycles method! ");
            return 5;
        }
    }
    private int setRestTime() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            restTime = bundle.getInt("rest_time");
            return restTime;
        } else {
            System.out.println("Error in IntervalTimer insidd setCycles method! ");
            return 5;
        }
    }

    private int setCycles() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            int getCycles = bundle.getInt("cycles_number") * 2 - 1;
            return getCycles;
        } else {
            System.out.println("Error in IntervalTimer insidd setCycles method! ");
            return 5;
        }
    }

}

//
