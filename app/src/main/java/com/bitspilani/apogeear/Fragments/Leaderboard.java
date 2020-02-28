package com.bitspilani.apogeear.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitspilani.apogeear.Adapters.LeaderBoardAdapter;
import com.bitspilani.apogeear.R;
import com.bitspilani.apogeear.Models.Rank;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class Leaderboard extends Fragment {

    private RecyclerView leaderboard;
    private FirebaseFirestore db;
    private ArrayList<Rank> list;
    private LeaderBoardAdapter leaderBoardAdapter;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private TextView rank,name,coins,charName;

    public Leaderboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_leaderboard, container, false);
        leaderboard=view.findViewById(R.id.leader_rv);
        rank=view.findViewById(R.id.user_rank);
        name=view.findViewById(R.id.user_name);
        coins=view.findViewById(R.id.user_score);
        charName = view.findViewById(R.id.user_char_name);
        db=FirebaseFirestore.getInstance();

        String userid=mAuth.getCurrentUser().getUid();
        list=new ArrayList<>();

        db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int i=1;
                for(QueryDocumentSnapshot document:queryDocumentSnapshots){
                    Rank ob=new Rank(document.get("name").toString(),document.getDouble("score"),document.get("username").toString(),document.get("char").toString(),i);
                    list.add(ob);
                    i++;
                }

                Comparator<Rank> comparebycoins=(Rank o1,Rank o2)-> (int)(o2.getCoins()-o1.getCoins());
                Collections.sort(list,comparebycoins);

                leaderBoardAdapter=new LeaderBoardAdapter(list,getContext());
                leaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
                leaderboard.setAdapter(leaderBoardAdapter);

                for(Rank rc: list){
                    if(rc.getUserId().equals(userid)){
                        rank.setText(rc.getRank()+"");
                        name.setText(rc.getUsername());
                        coins.setText(rc.getCoins()+"");
                        charName.setText(rc.getCharName());
                    }
                }
            }
        });

        return view;
    }


}
