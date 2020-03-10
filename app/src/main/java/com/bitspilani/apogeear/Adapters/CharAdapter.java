package com.bitspilani.apogeear.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bitspilani.apogeear.CharSelect;
import com.bitspilani.apogeear.MainActivity;
import com.bitspilani.apogeear.Models.CharacterModel;
import com.bitspilani.apogeear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class CharAdapter extends PagerAdapter {

    private List<CharacterModel> list;
    private Context context;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Intent fromLogin;

    public CharAdapter(List<CharacterModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.char_select,container,false);

        TextView textView = view.findViewById(R.id.user_name1);
        ImageView imageView = view.findViewById(R.id.user_icon1);

        textView.setText(list.get(position).getName());
        imageView.setImageResource(list.get(position).getImage());

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        fromLogin = ((Activity)context).getIntent();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("char",textView.getText().toString().trim());
                db.collection("Users").document(user.getUid()).update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        SharedPreferences sharedPref=context.getSharedPreferences("userinfo",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("char",textView.getText().toString().trim());
                        editor.apply();
                        Intent i = new Intent(context, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                        ((Activity) context).finish();
                    }
                });
            }
        });

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
