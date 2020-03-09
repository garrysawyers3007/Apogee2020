package com.bitspilani.apogeear.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitspilani.apogeear.LoginActivity;
import com.bitspilani.apogeear.Models.Rank;
import com.bitspilani.apogeear.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.Calendar;

public class Profile extends Fragment {

    private CountDownTimer countDownTimer;
    private long totalTimeCountInMilliseconds;
    private long startTime, endTime;
    private TextView showtime, coins, name, charName;
    private ImageView logout,bgProfile,usercharImage;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ProgressBar timer;
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink;
    private Calendar c;
    private GoogleSignInOptions gso;
    private GoogleSignInClient googleSignInClient;
    private String userid;
    View view;
    private Activity mActivity;

    public Profile() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //user=mAuth.getCurrentUser();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_green,container,false);

        initalize();

        bgProfile = view.findViewById(R.id.header_profile);
        usercharImage = view.findViewById(R.id.yyyy);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userid = mAuth.getCurrentUser().getUid();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        db.collection("Users").document(userid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        long coinval=Math.round(documentSnapshot.getDouble("score"));
                        coins.setText(coinval+"");
                        name.setText(documentSnapshot.get("name").toString());
                        charName.setText(documentSnapshot.get("char").toString());
                    }
                });

        db.collection("Users").document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String userCharacter = documentSnapshot.get("char").toString();

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference bgRef,charRef;

                switch (userCharacter){
                    case "The HackerMan":bgRef = storageRef.child("Backgrounds/backg_hackerman.png");
                        charRef = storageRef.child("Characters/Hackerman.png");
                        break;
                    case "Maestro":bgRef = storageRef.child("Backgrounds/backg_hackerman.png");
                        charRef = storageRef.child("Characters/Maestro.png");
                        break;
                    default:bgRef = storageRef.child("Backgrounds/backg_hackerman.png");
                        charRef = storageRef.child("Characters/Hackerman.png");
                        break;
                }
                bgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (getContext() != null) {
                            Glide.with(getContext()).load(uri.toString()).into(bgProfile);
                        }
                    }
                });
                charRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (getContext() != null) {
                            Glide.with(getContext()).load(uri.toString()).into(usercharImage);
                        }
                    }
                });
            }
        });

        db.collection("Coins").document("Universal Coins")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        c = Calendar.getInstance();
                        totalTimeCountInMilliseconds = documentSnapshot.getTimestamp("Expire Time").getSeconds() - c.getTimeInMillis() / 1000;
                        startTime = documentSnapshot.getTimestamp("Start Time").getSeconds() * 1000;
                        endTime = documentSnapshot.getTimestamp("Expire Time").getSeconds() * 1000;
                        if (startTime > c.getTimeInMillis())
                            totalTimeCountInMilliseconds = 0;
                        setTimer();
                    }
                });
        return view;
    }

    private void initalize(){
        showtime = view.findViewById(R.id.tvTimeCount);
        coins = view.findViewById(R.id.total);
        timer = view.findViewById(R.id.progressbar);
        logout = view.findViewById(R.id.logout);
        name = view.findViewById(R.id.zzzz);
        charName = view.findViewById(R.id.aaaa);

        setLogout();
    }

    private void setLogout(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInClient.signOut();
                mAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                Toast.makeText(getContext(), "Signed Out",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTimer() {
        int time = 0;
        //Toast.makeText(getContext(), "Please Enter Minutes...",
        //      Toast.LENGTH_LONG).show();
        totalTimeCountInMilliseconds = totalTimeCountInMilliseconds * 1000;
        //totalTimeCountInMilliseconds = 60 * time * 1000;
        timeBlinkInMilliseconds = 30 * 1000;
        startTimer();
    }

    private void startTimer() {

        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1000) {
            // 500 means, onTick function will be called at every 500
            // milliseconds
            @Override
            public void onFinish() {
                timer.setProgress(0);
                showtime.setVisibility(View.VISIBLE);
            }
            @Override
            public void onTick(long leftTimeInMilliseconds) {

                //Log.d("Time left",""+leftTimeInMilliseconds);
                int minutes = (int) ((leftTimeInMilliseconds / (1000 * 60)) % 60);
                int seconds = (int) (leftTimeInMilliseconds / 1000) % 60;
                int hours = (int) ((leftTimeInMilliseconds / (1000 * 60 * 60)) % 24);
                //i++;
                //Setting the Progress Bar to decrease wih the timer
                Log.d("timeleft", "" + totalTimeCountInMilliseconds);
                Log.d("total count", "" + (endTime - startTime));
                Log.d("lefttt", "" + leftTimeInMilliseconds);
                if (endTime != startTime) {
                    timer.setMax((int) (endTime - startTime));
                    timer.setProgress((int) (0.83*(endTime - startTime - leftTimeInMilliseconds)));
                } else
                    timer.setProgress(0);
//                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
//                    // change the style of the textview .. giving a red
//                    // alert style
//
//                    if (blink) {
//                        showtime.setVisibility(View.VISIBLE);
//                        // if blink is true, textview will be visible
//                    } else {
//                        showtime.setVisibility(View.INVISIBLE);
//                    }
//
//                    blink = !blink; // toggle the value of blink
//                }
                showtime.setText(hours + " hrs " + minutes + " min " + seconds + " sec");
                // format the textview to show the easily readable format

            }

        }.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

}
