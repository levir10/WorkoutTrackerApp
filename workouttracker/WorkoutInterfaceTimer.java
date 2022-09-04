package com.example.workouttracker;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class WorkoutInterfaceTimer extends AppCompatActivity {
    //defining a constant for starting time comfort. we could do regular edit text..
    private static long START_TIME_IN_MILLIS;// the time (600,000 milisec=10 minutes.) will change according to the user!
    private TextView mTextViewCountDown;
    private Button mButtonReset;
    private Button mButtonStartPause;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;// variable to tell us if the time is running or not
    private long mTimeLeftInMillis;
    private SoundPool soundPool;
    private Vibrator vibrator;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.workout_interface_timer);
        START_TIME_IN_MILLIS=getIncomingIntent();
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        mTextViewCountDown = findViewById(R.id.textView_timer);
        mButtonReset = findViewById(R.id.reset_button);
        mButtonStartPause=findViewById(R.id.pause_start_button);
        startTimer();

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimerRunning){
                    pauseTimer();
                }else{
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
    private void startTimer(){
        //variables passed to CountDownTimer: time left in milliseconds, and interval size--> we call countDownTimer every 1000 milliseconds
        mCountDownTimer =new CountDownTimer((long) mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning=false;
                mButtonStartPause.setText("Start");
                ringSoundOn();
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning=true;
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
        int minutes = (int) (mTimeLeftInMillis/1000)/60;
        int seconds = (int)(mTimeLeftInMillis/1000)%60;
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%2d",minutes,seconds);
        mTextViewCountDown.setText(timeLeftFormatted);

    }
    private long getIncomingIntent() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            System.out.println("jjjjjjjjjjjjjkhughjgGFHJGJFDXCGHJKFGHVBJ    " + 1000*bundle.getDouble("workout_timeBetweenSets") + "   kjhgfdghjkljhgfhjkhgfc");

            long timeBetweenSets = (long) (1000*bundle.getDouble("workout_timeBetweenSets"));
            System.out.println("jjjjjjjjjjjjjkhughjgGFHJGJFDXCGHJKFGHVBJ    " + timeBetweenSets + "   kjhgfdghjkljhgfhjkhgfc");

            return timeBetweenSets;
        } else {
            return Long.valueOf(3*60000);
        }
    }
//make ring sound
private void ringSoundOn(){
        //set vibrator to vibrate
    vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);
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
    soundPool.load(WorkoutInterfaceTimer.this, R.raw.boxing, 1);
    vibrator.vibrate(1000);

}




}