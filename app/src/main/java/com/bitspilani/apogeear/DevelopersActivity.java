package com.bitspilani.apogeear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bitspilani.apogeear.Adapters.DevelopersAdapter;
import com.bitspilani.apogeear.Models.Developers;

import java.util.ArrayList;
import java.util.List;

public class DevelopersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Developers> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        recyclerView = findViewById(R.id.developers_rv);

        list = new ArrayList<>();
        list.add(new Developers("Anubhav","Developer",R.drawable.topimg));
        list.add(new Developers("Gauransh","Developer",R.drawable.topimg));
        list.add(new Developers("Naman","Developer",R.drawable.topimg));
        list.add(new Developers("Anubhav","Developer",R.drawable.topimg));
        list.add(new Developers("Gauransh","Developer",R.drawable.topimg));
        list.add(new Developers("Naman","Developer",R.drawable.topimg));
        list.add(new Developers("Anubhav","Developer",R.drawable.topimg));
        list.add(new Developers("Gauransh","Developer",R.drawable.topimg));
        list.add(new Developers("Naman","Developer",R.drawable.topimg));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DevelopersAdapter developersAdapter = new DevelopersAdapter(this,list);
        recyclerView.setAdapter(developersAdapter);


    }
}
