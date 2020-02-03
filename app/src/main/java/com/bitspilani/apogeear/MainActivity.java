package com.bitspilani.apogeear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bitspilani.apogeear.Fragments.Home;
import com.bitspilani.apogeear.Fragments.Map;
import com.bitspilani.apogeear.Fragments.More;
import com.bitspilani.apogeear.Fragments.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.blox.graphview.Graph;
import de.blox.graphview.ViewHolder;

public class MainActivity extends AppCompatActivity {

    private int nodeCount = 1;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Graph graph = new Graph();
        //GraphView graphView = findViewById(R.id.graph);
        final ArrayList<String> ev=new ArrayList<String>();

        loadFragment(new Home());

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        //Button filter=findViewById(R.id.filter);
//        filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                try
//                {
//                    AlertDialog.Builder dialog1=new AlertDialog.Builder(MainActivity.this);
//                    final String[] Types={"coding and fintech","quizzing and strategy"};
//                    final boolean[] _selections = {false,false};
//                    dialog1.setTitle("Categories");
//                    //dialog1.setMessage("Pick your category");
//                    dialog1.setMultiChoiceItems(Types, _selections, new DialogInterface.OnMultiChoiceClickListener()
//                    {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which, boolean isChecked)
//                        {
//                            if(isChecked)
//                            {
//                                ev.add(Types [which]);
//                                Toast.makeText(getApplicationContext(),"You have selected " + Types [which],Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });
//
//                    dialog1.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int id) {
//                            Toast.makeText(getApplicationContext(),"SAVED",Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    dialog1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int id) {
//                            Toast.makeText(getApplicationContext(),"CANCELED",Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    dialog1.show();
//                }
//                catch(Exception ex)
//                {
//                    Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment=new Home();
                switch (menuItem.getItemId()){
                    case R.id.home :
                        fragment=new Home();
                        break;

                    case R.id.map:
                        fragment=new Map();
                        break;
                    case R.id.profile:
                        fragment=new Profile();
                        break;

                    case R.id.more:
                        fragment=new More();
                        break;
                }

                return loadFragment(fragment);
            }
        });



//            final BaseGraphAdapter<ViewHolder> adapter=new BaseGraphAdapter<ViewHolder>(graph) {
//
//                    @NonNull
//                    @Override
//                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node, parent, false);
//                        return new SimpleViewHolder(view);
//                    }
//
//                    @Override
//                    public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
//                        ((SimpleViewHolder)viewHolder).textView.setText(data.toString());
//                    }
//            };

//
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
//        graphView.setAdapter(adapter);
//        adapter.setAlgorithm(new FruchtermanReingoldAlgorithm(100));
//        graphView.setZoomEnabled(true);



    }


    private String getNodeText() {
        return "Node " + nodeCount++;
    }
    private String toDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        return currentTime.trim();

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

class SimpleViewHolder extends ViewHolder {
    TextView textView;

    SimpleViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
    }



}


