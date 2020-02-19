package com.bitspilani.apogeear.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bitspilani.apogeear.Models.CharacterModel;
import com.bitspilani.apogeear.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CharAdapter extends PagerAdapter {

    private List<CharacterModel> list;
    private Context context;

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
        CircleImageView circleImageView = view.findViewById(R.id.user_icon1);

        textView.setText(list.get(position).getName());
        circleImageView.setImageResource(list.get(position).getImage());

        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
