package com.bitspilani.apogeear.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bitspilani.apogeear.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private CountDownTimer countDownTimer;
    private long totalTimeCountInMilliseconds;
    TextView showtime;
    ProgressBar timer;
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink;


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
        return view;
    }

    private void setTimer() {
        int time = 0;
        Toast.makeText(getContext(), "Please Enter Minutes...",
                Toast.LENGTH_LONG).show();

        totalTimeCountInMilliseconds = 60 * time * 1000;

        timeBlinkInMilliseconds = 30 * 1000;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onFinish() {

            }

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                //i++;
                //Setting the Progress Bar to decrease wih the timer
                timer.setProgress((int) (leftTimeInMilliseconds / 1000));


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

                showtime.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));
                // format the textview to show the easily readable format

            }

        };
    }
}
