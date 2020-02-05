package com.bitspilani.apogeear.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bitspilani.apogeear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private CountDownTimer countDownTimer;
    private long totalTimeCountInMilliseconds;
    private long startTime,endTime;
    TextView showtime;
    ProgressBar timer;
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink;
    Calendar c;


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        showtime = view.findViewById(R.id.tvTimeCount);
        timer = view.findViewById(R.id.progressbar);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        c=Calendar.getInstance();
        c.getTimeInMillis();


        db.collection("Coins").document("Universal Coins").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        totalTimeCountInMilliseconds=documentSnapshot.getTimestamp("Expire Time").getSeconds()-c.getTimeInMillis()/1000;
                        startTime=documentSnapshot.getTimestamp("Start Time").getSeconds()*1000;
                        endTime=documentSnapshot.getTimestamp("Expire Time").getSeconds()*1000;
                        setTimer();
                    }
                });
        return view;
    }

    private void setTimer() {
        int time = 0;
        //Toast.makeText(getContext(), "Please Enter Minutes...",
          //      Toast.LENGTH_LONG).show();

        totalTimeCountInMilliseconds=totalTimeCountInMilliseconds*1000;

        //totalTimeCountInMilliseconds = 60 * time * 1000;

        timeBlinkInMilliseconds = 30 * 1000;

        startTimer();

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1000) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onFinish() {

            }

            @Override
            public void onTick(long leftTimeInMilliseconds) {

                //Log.d("Time left",""+leftTimeInMilliseconds);
                int minutes = (int) ((leftTimeInMilliseconds / (1000 * 60)) % 60);
                int seconds = (int) (leftTimeInMilliseconds / 1000) % 60;
                int hours = (int) ((leftTimeInMilliseconds/ (1000 * 60 * 60)) % 24);
                //i++;
                //Setting the Progress Bar to decrease wih the timer
                if(endTime!=startTime)
                timer.setProgress((int) (100*(1-(totalTimeCountInMilliseconds / (endTime-startTime)))));
                else
                    timer.setProgress(0);


                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                    // change the style of the textview .. giving a red
                    // alert style

                    if (blink) {
                        showtime.setVisibility(View.VISIBLE);
                        // if blink is true, textview will be visible
                    } else {
                        showtime.setVisibility(View.INVISIBLE);
                    }

                    blink = !blink; // toggle the value of blink
                }

                showtime.setText(hours+" hrs "+minutes+" min "+seconds+" sec");
                // format the textview to show the easily readable format

            }

        }.start();
    }
}
