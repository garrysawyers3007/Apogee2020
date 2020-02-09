package com.bitspilani.apogeear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bitspilani.apogeear.Adapters.ViewPagerAdapter;
import com.bitspilani.apogeear.Fragments.Home;
import com.bitspilani.apogeear.Fragments.Leaderboard;
import com.bitspilani.apogeear.Fragments.Map;
import com.bitspilani.apogeear.Fragments.More;
import com.bitspilani.apogeear.Fragments.Profile;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ramotion.foldingcell.FoldingCell;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.blox.graphview.Graph;
import de.blox.graphview.ViewHolder;

public class MainActivity extends AppCompatActivity {

    private int nodeCount = 1;
    //BottomNavigationView bottomNavigationView;
    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    ArrayList<Fragment> fragments;
    int selected=1;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottom_nav);

        final ArrayList<String> ev=new ArrayList<String>();

        fragment = new Home();
        bottomNavigationView.setSelectedItemId(R.id.home);

        loadFragment(fragment);
        fragments=new ArrayList<>();

        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
       // viewPager=findViewById(R.id.viewpager);
        //viewPager.setAdapter(viewPagerAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home :
                        fragment=new Home();
                        loadFragment(fragment);
                        return true;
                    case R.id.map:
                        fragment=new Map();
                        loadFragment(fragment);
                        return true;
                    case R.id.profile:
                        fragment=new Profile();
                        loadFragment(fragment);
                        return true;
                    case R.id.more:
                        fragment=new More();
                        loadFragment(fragment);
                        return true;
                    case R.id.leaderboard:
                        fragment=new Leaderboard();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }});

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        ArrayList<Event_Details> list=new ArrayList<>();
//        ArrayList<String> ev=new ArrayList<>();
//        ev.add("coding and fintech");
//        ev.add("quizzing and strategy");
//        db.collection("Events").whereIn("Type", ev)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.w("TAG", "Listen failed.", e);
//                            return;
//                        }
//
//                        if (queryDocumentSnapshots != null ) {
//
//                            Log.d("sjcbscj","Yeah!!");
//
//                            //final Graph graph = new Graph();
//                            //GraphView graphView = findViewById(R.id.graph);
//
//                            Log.d("sjcbscj","Yeah!!");
//
//                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                                Log.d("TAG", document.getId() + " => " + document.get("Name"));
//                                if(document.get("Name")!=null && document.get("Type")!=null && document.getTimestamp("Time")!=null)
//                                    list.add(new Event_Details(document.get("Name").toString(),document.get("Type").toString(),document.getTimestamp("Time")));
//                            }
//
//                            Comparator<Event_Details> comparebyTime=(Event_Details e1,Event_Details e2)->{
//                                try {
//                                    int i = e1.getTime().compareTo(e2.getTime());
//                                    return i;
//                                } catch (Exception ex) {
//                                    ex.printStackTrace();
//                                }
//                                return 0;
//                            };
//
//                            Collections.sort(list,comparebyTime);
//
//                            Node node1=new Node("Start");
//                            int prev=0;
//                            long seconds=86400;
//                            ArrayList<Event_Details> temp=new ArrayList<>();
//                            temp.add(list.get(0));
//                            for(int i=1;i<list.size();i++){
//
//                                if(toDate(list.get(i).getTime().toDate()).equals(toDate(list.get(i-1).getTime().toDate())))
//                                seconds=list.get(i).getTime().getSeconds()-list.get(i-1).getTime().getSeconds();
//
//                                Log.d("Time",""+list.get(i).getTime().getSeconds());
//
//                                node1=new Node(list.get(prev).getName());
//                                Node node2 = new Node(list.get(i).getName());
//                                graph.addEdge(node1,node2);
//                                node1=node2;
//
//
//
//                                if(i==list.size()-1){
//                                    Node node2 = new Node("Finish");
//
//                                    for(int j=0;j<temp.size();j++){
//                                        Node node3=new Node(temp.get(j).getName());
//                                        graph.addEdge(node1,node3);
//                                        graph.addEdge(node3,node2);
//                                    }
//                                }
//
//                                if(seconds<=3600){
//                                    temp.add(list.get(i));
//                                }
//                                else {
//                                    Node node2 = new Node("Diversion"+i);
//
//                                    for(int j=0;j<temp.size();j++){
//                                        Node node3=new Node(temp.get(j).getName());
//                                        graph.addEdge(node1,node3);
//                                        graph.addEdge(node3,node2);
//                                    }
//                                    prev=i;
//                                    temp.clear();
//                                    temp.add(list.get(i));
//                                    seconds=86400;
//                                    node1 = node2;
//                                }
//                            }
//
//                        } else {
//                            Log.d("TAG", "Current data: null");
//                        }
//                    }
//
//                });

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}




