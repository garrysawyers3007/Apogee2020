package com.bitspilani.apogeear.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bitspilani.apogeear.LoginActivity;
import com.bitspilani.apogeear.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.Calendar;

public class Profile extends Fragment {

    private CountDownTimer countDownTimer;
    private long totalTimeCountInMilliseconds;
    private long startTime, endTime;
    private TextView showtime, coins, name, charName;
    private ImageView logout,bgProfile,usercharImage;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ProgressBar timer;
    ListenerRegistration listenerRegistration1,listenerRegistration2;
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink;
    private Calendar c;
    private GoogleSignInOptions gso;
    FirebaseFirestore db ;
    private GoogleSignInClient googleSignInClient;
    private String userid;
    View view;


    public Profile() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //user=mAuth.getCurrentUser();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_green,container,false);

        initalize();

        bgProfile = view.findViewById(R.id.header_profile);
        usercharImage = view.findViewById(R.id.yyyy);


        userid = mAuth.getCurrentUser().getUid();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        initalize();
        setLogout();

        db= FirebaseFirestore.getInstance();

        db= FirebaseFirestore.getInstance();
        listenerRegistration1=db.collection("Users").document(userid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        if(documentSnapshot!=null) {
                            long coinval = Math.round(documentSnapshot.getDouble("score"));
                            coins.setText(coinval + "");
                            name.setText(documentSnapshot.get("name").toString());
                            charName.setText(documentSnapshot.get("char").toString());
                        }
                    }
                });
        listenerRegistration2=db.collection("Coins").document("Universal Coins")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if(documentSnapshot!=null) {

                            c = Calendar.getInstance();
                            totalTimeCountInMilliseconds = documentSnapshot.getTimestamp("Expire Time").getSeconds() - c.getTimeInMillis() / 1000;
                            startTime = documentSnapshot.getTimestamp("Start Time").getSeconds() * 1000;
                            endTime = documentSnapshot.getTimestamp("Expire Time").getSeconds() * 1000;
//                            if (startTime > c.getTimeInMillis())
//                                totalTimeCountInMilliseconds = 0;
                            setTimer();
                        }
                    }
                });

        db.collection("Users").document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String userCharacter = documentSnapshot.get("char").toString();

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference bgRef, charRef;

                switch (userCharacter) {
                    case "The HackerMan":
                        bgRef = storageRef.child("Backgrounds/backg_hackerman.png");
                        charRef = storageRef.child("Characters/Hackerman.png");
                        break;
                    case "Maestro":
                        bgRef = storageRef.child("Backgrounds/backg_hackerman.png");
                        charRef = storageRef.child("Characters/Maestro.png");
                        break;
                    default:
                        bgRef = storageRef.child("Backgrounds/backg_hackerman.png");
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

                if (listenerRegistration1!= null) {
                    listenerRegistration1.remove();
                    listenerRegistration1 = null;
                }
                if (listenerRegistration2!= null) {
                    listenerRegistration2.remove();
                    listenerRegistration2 = null;
                }

                googleSignInClient.signOut();
                mAuth.signOut();
                Intent i=new Intent(getActivity(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
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

                showtime.setText(0 + " hrs " + 0 + " min " + 0 + " sec");
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
                if (endTime != startTime && endTime-startTime>leftTimeInMilliseconds) {
                    timer.setMax((int) (endTime - startTime));
                    timer.setProgress((int) (0.83*(endTime - startTime - leftTimeInMilliseconds)));
                    showtime.setText(hours + " hrs " + minutes + " min " + seconds + " sec");
                } else {
                    timer.setProgress(0);
                    showtime.setText(0 + " hrs " + 0 + " min " + 0 + " sec");
                }
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

                // format the textview to show the easily readable format

            }

        }.start();
    }


    @Override
    public void onPause() {
//
//        if (listenerRegistration1!= null) {
//            listenerRegistration1.remove();
//            listenerRegistration1 = null;
//        }
//        if (listenerRegistration2!= null) {
//            listenerRegistration2.remove();
//            listenerRegistration2 = null;
//        }
        Log.d("pause","profilepaused");
        super.onPause();
    }

}
