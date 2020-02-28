package com.bitspilani.apogeear.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.apogeear.R;
import com.bitspilani.apogeear.Models.Rank;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {

    private ArrayList<Rank> list;
    private Context context;
    private View view;

    public LeaderBoardAdapter (ArrayList<Rank> list, Context context){
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_new,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        switch (position){

            case 0:holder.name.setText(list.get(position).getUsername());
                holder.name.setTextColor(Color.parseColor("#000000"));
                holder.charName.setText(list.get(position).getCharName());
                holder.charName.setTextColor(Color.parseColor("#000000"));
                holder.score.setText(String.valueOf(list.get(position).getCoins()));
                holder.score.setTextColor(Color.parseColor("#000000"));
                holder.rank.setText(""+(position+1));
                holder.img.setImageDrawable(context.getDrawable(R.drawable.topimg));
                holder.relativeLayout.setBackground(context.getDrawable(R.drawable.leaderboard_rank1_bg));
                break;
            case 1:holder.name.setText(list.get(position).getUsername());
                holder.charName.setText(list.get(position).getCharName());
                holder.name.setTextColor(Color.parseColor("#000000"));
                holder.charName.setTextColor(Color.parseColor("#000000"));
                holder.score.setText(String.valueOf(list.get(position).getCoins()));
                holder.score.setTextColor(Color.parseColor("#000000"));
                holder.rank.setText(""+(position+1));
                holder.img.setImageDrawable(context.getDrawable(R.drawable.topimg));
                holder.relativeLayout.setBackground(context.getDrawable(R.drawable.leaderboard_rank2_bg));
                holder.rank.setText(""+(position+1));
                break;
            case 2:holder.name.setText(list.get(position).getUsername());
                holder.charName.setText(list.get(position).getCharName());
                holder.name.setTextColor(Color.parseColor("#000000"));
                holder.charName.setTextColor(Color.parseColor("#000000"));
                holder.score.setTextColor(Color.parseColor("#000000"));
                holder.score.setText(String.valueOf(list.get(position).getCoins()));
                holder.img.setImageDrawable(context.getDrawable(R.drawable.topimg));
                holder.rank.setText(""+(position+1));
                holder.relativeLayout.setBackground(context.getDrawable(R.drawable.leaderboard_rank3_bg));
                break;
            default:  holder.name.setText(list.get(position).getUsername());
                holder.charName.setText(list.get(position).getCharName());
                holder.score.setText(String.valueOf(list.get(position).getCoins()));
                holder.img.setImageDrawable(context.getDrawable(R.drawable.topimg));
                holder.rank.setText(""+(position+1));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,rank,score,charName;
        ImageView img;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.player_name);
            rank=itemView.findViewById(R.id.player_rank);
            score=itemView.findViewById(R.id.player_score);
            img = itemView.findViewById(R.id.badge);
            charName = itemView.findViewById(R.id.player_char_name);
            relativeLayout = itemView.findViewById(R.id.card_name);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1){
            return 1;
        }else if (position == 2){
            return 2;
        }else{
            return 3;
        }
    }
}
