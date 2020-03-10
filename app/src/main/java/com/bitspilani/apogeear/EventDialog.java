package com.bitspilani.apogeear;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.apogeear.Adapters.InterestAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class EventDialog extends Dialog {

    private Context context;
    RecyclerView gridView;
    private ArrayList<String> types;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private String userId;
    InterestAdapter interestAdapter;

    public EventDialog(@NonNull Context context, ArrayList<String> types,String userId) {
        super(context);
        this.context=context;
        this.types=types;
        this.userId=userId;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_filter);

        gridView=findViewById(R.id.mygridview);

        GridLayoutManager llm = new GridLayoutManager(context,2);
        gridView.setLayoutManager(llm);
        interestAdapter=new InterestAdapter(types);

        gridView.setAdapter(interestAdapter);
    }

    private void set(TextView textView,String ele){
        if(types.contains(ele.toLowerCase())){

            types.remove(ele.toLowerCase());
            db.collection("Users").document(userId)
                    .update("Types",types)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            textView.setTextColor(Color.parseColor("#fdfdfd"));
                        }
                    });
        }
        else {
            types.add(ele.toLowerCase());
            db.collection("Users").document(userId)
                    .update("Types",types)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            textView.setTextColor(Color.parseColor("#d0d3d4"));
                        }
                    });

        }
    }
}
