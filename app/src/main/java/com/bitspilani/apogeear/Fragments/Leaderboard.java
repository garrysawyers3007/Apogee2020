package com.bitspilani.apogeear.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitspilani.apogeear.Adapters.LeaderBoardAdapter;
import com.bitspilani.apogeear.R;
import com.bitspilani.apogeear.Models.Rank;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Leaderboard extends Fragment {

    private RecyclerView leaderboard;
    private FirebaseFirestore db;
    private ArrayList<Rank> list;
    private LeaderBoardAdapter leaderBoardAdapter;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private TextView rank,name,coins,charName;
    private ImageView userImage;
    private StorageReference charRef;
    private String userChar;

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
        userImage = view.findViewById(R.id.user_image);
        db=FirebaseFirestore.getInstance();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String userid=mAuth.getCurrentUser().getUid();
        list=new ArrayList<>();

        db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                list.clear();
                int i=1;
                for(QueryDocumentSnapshot document:queryDocumentSnapshots){
                    Rank ob=new Rank(document.get("name").toString(),document.getDouble("score"),document.get("username").toString(),document.get("char").toString());
                    list.add(ob);
                    i++;
                }

                Comparator<Rank> comparebycoins=(Rank o1,Rank o2)-> (int)(o2.getCoins()-o1.getCoins());
                Collections.sort(list,comparebycoins);

                leaderBoardAdapter=new LeaderBoardAdapter(list,getContext(),getActivity());
                leaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
                leaderboard.setAdapter(leaderBoardAdapter);

                for(Rank rc: list){
                    if(rc.getUserId().equals(userid)){
                        rank.setText((list.indexOf(rc)+1)+"");
                        name.setText(rc.getUsername());
                        coins.setText(rc.getCoins()+"");
                        charName.setText(rc.getCharName());
                    }
                }
            }
        });

        db.collection("Users").document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userChar = documentSnapshot.get("char").toString();
                switch (userChar){
                    case "The HackerMan": charRef = storageRef.child("Characters/Hackerman.png");
                        break;
                    case "Maestro": charRef = storageRef.child("Characters/Maestro.png");
                        break;
                    default: charRef = storageRef.child("Characters/Hackerman.png");
                        break;
                }


                    charRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if(getContext()!=null)
                            Glide.with(getContext()).load(uri.toString()).into(userImage);
                        }
                    });

            }
        });
        return view;
    }

}
