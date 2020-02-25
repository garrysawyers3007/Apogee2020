package com.bitspilani.apogeear;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class EventDialog extends Dialog {

    private Context context;
    private TextView e1,e2;
    private ArrayList<String> types;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private String userId;

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


        e1=findViewById(R.id.team11);
        e2=findViewById(R.id.team);
        if(types.contains(e1.getText().toString().toLowerCase()))
            e1.setTextColor(Color.parseColor("#D0D3D4"));
        if(types.contains(e2.getText().toString().toLowerCase()))
            e2.setTextColor(Color.parseColor("#D0D3D4"));

        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set(e1,e1.getText().toString());
            }
        });

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set(e2,e2.getText().toString());
            }
        });
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
