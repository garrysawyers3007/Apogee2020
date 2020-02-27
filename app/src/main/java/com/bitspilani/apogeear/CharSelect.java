package com.bitspilani.apogeear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.widget.Adapter;

import com.bitspilani.apogeear.Adapters.CharAdapter;
import com.bitspilani.apogeear.Models.CharacterModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharSelect extends AppCompatActivity {

    ViewPager viewPager;
    CharAdapter adapter;
    List<CharacterModel> list;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_select);

        list = new ArrayList<>();
        list.add(new CharacterModel("The HackerMan",R.drawable.hackerman_charpage));
        list.add(new CharacterModel("Maestro",R.drawable.maestro));
        list.add(new CharacterModel("Paint Slinger",R.drawable.coinimage));
        list.add(new CharacterModel("Grease Monkey",R.drawable.coinimage));

        adapter = new CharAdapter(list,this);

        viewPager = findViewById(R.id.char_view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(100,400,100,0);

        Integer[] colors_temp = {getResources().getColor(R.color.gold),getResources().getColor(R.color.BACKGROUND),getResources().getColor(R.color.PRIMARY_VARIANT),getResources().getColor(R.color.PRIMARY_VARIANT)};
        colors = colors_temp;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position<(adapter.getCount()-1)&&position<(colors.length-1)){
                    viewPager.setBackgroundColor((Integer)argbEvaluator.evaluate(positionOffset,colors[position],colors[position+1]));
                }else{
                    viewPager.setBackgroundColor(colors[colors.length-1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
