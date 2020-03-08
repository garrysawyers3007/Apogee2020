package com.bitspilani.apogeear.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bitspilani.apogeear.EventDialog;
import com.bitspilani.apogeear.Models.Event_Details;
import com.bitspilani.apogeear.R;
import com.bitspilani.apogeear.Adapters.VerticalAdapter;
import com.github.vipulasri.timelineview.TimelineView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class Home extends Fragment {

    private VerticalAdapter adapter;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private ImageView filter;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        String userid=mAuth.getCurrentUser().getUid();

        filter=view.findViewById(R.id.filter);

        FirebaseFirestore db=FirebaseFirestore.getInstance();

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Users").document(userid)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                ArrayList<String> types=(ArrayList<String>) documentSnapshot.get("Types");
                                EventDialog eventDialog=new EventDialog(getContext(),types,userid);
                                eventDialog.show();
                            }
                        });
            }
        });

        db.collection("Users").document(userid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        ArrayList<String> ev=(ArrayList<String>) documentSnapshot.get("Types");
                        ArrayList<String> attended=(ArrayList<String>) documentSnapshot.get("Attended");

                        RecyclerView vertical=view.findViewById(R.id.vertical);
                        vertical.setHasFixedSize(true);

                        LinearLayoutManager llm=new LinearLayoutManager(getContext());
                        vertical.setLayoutManager(llm);

                        if(ev.size()!=0) {
                            db.collection("Events").whereIn("Type", ev)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                            ArrayList<ArrayList<Event_Details>> lists=new ArrayList<>();
                                            ArrayList<Event_Details> list=new ArrayList<>();

                                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                                Log.d("TAG", document.getId() + " => " + document.get("Name"));
                                                if (document.get("Name") != null && document.get("Type") != null && document.getTimestamp("Time") != null)
                                                    list.add(new Event_Details(document.get("Name").toString(), document.get("Type").toString(), document.getTimestamp("Time")));
                                            }

                                            Comparator<Event_Details> comparebyTime = (Event_Details e1, Event_Details e2) -> {
                                                try {
                                                    int i = e1.getTime().compareTo(e2.getTime());
                                                    return i;
                                                } catch (Exception ex) {
                                                    ex.printStackTrace();
                                                }
                                                return 0;
                                            };

                                            Collections.sort(list, comparebyTime);
                                            long seconds = 86400;
                                            int prev = 0;
                                            ArrayList<Event_Details> temp = new ArrayList<>();
                                            temp.add(list.get(0));

//                        for(int i=0;i<list.size();i++){
//                            temp.add(list.get(i));
//                            if(i%2==1 || i==list.size()-1) {
//                                Log.d("Size",""+temp.size());
//                                ArrayList<Event_Details> temp1=new ArrayList<>();
//                                    temp1.addAll(temp);
//                                lists.add(temp1);
//                                temp.clear();
//                                Log.d("Size",""+lists.get(0).size());
//                            }
//                        }

                                            for (int i = 1; i < list.size(); i++) {
                                                if (toDate(list.get(i).getTime().toDate()).equals(toDate(list.get(prev).getTime().toDate())))
                                                    seconds = list.get(i).getTime().getSeconds() - list.get(prev).getTime().getSeconds();

                                                if (seconds == 0)
                                                    temp.add(list.get(i));
                                                else {

                                                    Log.d("Size", "" + temp.size());
                                                    ArrayList<Event_Details> temp1 = new ArrayList<>();
                                                    temp1.addAll(temp);
                                                    lists.add(temp1);
                                                    temp.clear();
                                                    Log.d("Size", "" + lists.get(0).size());
                                                    prev = i;
                                                    temp.add(list.get(i));
                                                    seconds = 86400;
                                                }

                                                if (i == list.size() - 1) {

                                                    lists.add(temp);
                                                    break;
                                                }
                                                Log.d("Date", list.get(i).getTime().toDate().toString());
                                                Log.d("Time", "" + list.get(i).getTime().getSeconds());
                                            }
                                            adapter = new VerticalAdapter(lists, getContext(),attended);
                                            vertical.setAdapter(adapter);
                                        }
                                    });
                        }

                    }
                });

        return view;
    }

    private String toDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        return currentTime.trim();

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
