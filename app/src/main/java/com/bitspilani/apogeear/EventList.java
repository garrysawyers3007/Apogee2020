package com.bitspilani.apogeear;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.bitspilani.apogeear.Adapters.VerticalAdapter;
import com.bitspilani.apogeear.Models.Event_Details;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class EventList extends AppCompatActivity {

    VerticalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventlist);

        ArrayList<ArrayList<Event_Details>> lists=new ArrayList<>();
        ArrayList<String> ev=new ArrayList<>();
        ev.add("coding and fintech");
        ev.add("quizzing and strategy");
        ArrayList<Event_Details> list=new ArrayList<>();


        RecyclerView vertical=findViewById(R.id.vertical1);
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        vertical.setHasFixedSize(true);

        LinearLayoutManager llm=new LinearLayoutManager(this);
        vertical.setLayoutManager(llm);


        db.collection("Events").whereIn("Type",ev)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Log.d("TAG", document.getId() + " => " + document.get("Name"));
                            if(document.get("Name")!=null && document.get("Type")!=null && document.getTimestamp("Time")!=null)
                                list.add(new Event_Details(document.get("Name").toString(),document.get("Type").toString(),document.getTimestamp("Time")));
                        }

                        Comparator<Event_Details> comparebyTime=(Event_Details e1, Event_Details e2)->{
                            try {
                                int i = e1.getTime().compareTo(e2.getTime());
                                return i;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            return 0;
                        };

                        Collections.sort(list,comparebyTime);
                        long seconds=86400;
                        int prev=0;
                        ArrayList<Event_Details> temp=new ArrayList<>();
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

                        for(int i=1;i<list.size();i++){
                            if(toDate(list.get(i).getTime().toDate()).equals(toDate(list.get(prev).getTime().toDate())))
                                seconds=list.get(i).getTime().getSeconds()-list.get(prev).getTime().getSeconds();

                            if(seconds<=3600)
                                temp.add(list.get(i));
                            else {

                                Log.d("Size",""+temp.size());
                                ArrayList<Event_Details> temp1=new ArrayList<>();
                                    temp1.addAll(temp);
                                lists.add(temp1);
                                temp.clear();
                               Log.d("Size",""+lists.get(0).size());
                                prev=i;
                                temp.add(list.get(i));
                                seconds=86400;
                            }

                            if(i==list.size()-1 ){

                                lists.add(temp);
                                break;
                            }
                            Log.d("Date",list.get(i).getTime().toDate().toString());
                            Log.d("Time",""+list.get(i).getTime().getSeconds());
                        }
                        //adapter=new VerticalAdapter(lists, EventList.this, attended);
                       // vertical.setAdapter(adapter);
                    }
                });

    }

    private String toDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        return currentTime.trim();

    }
}
