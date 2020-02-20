package com.bitspilani.apogeear.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.apogeear.R;
import com.bitspilani.apogeear.Models.Rank;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {

    ArrayList<Rank> list;
    Context context;
    View view;

    public LeaderBoardAdapter (ArrayList<Rank> list, Context context){
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboarditem,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        switch (position){
            case 0:holder.name.setText(list.get(position).getUsername());
                holder.name.setTextColor(Color.parseColor("#D4AF37"));
                holder.score.setText(String.valueOf(list.get(position).getCoins()));
                holder.rank.setText(""+(position+1));
                holder.img.setImageDrawable(context.getDrawable(R.drawable.top1));
                break;
            case 1:holder.name.setText(list.get(position).getUsername());
                holder.name.setTextColor(Color.parseColor("#C0C0C0"));
                holder.score.setText(String.valueOf(list.get(position).getCoins()));
                holder.img.setImageDrawable(context.getDrawable(R.drawable.top2));
                holder.rank.setText(""+(position+1));
                break;
            case 2:holder.name.setText(list.get(position).getUsername());
                holder.name.setTextColor(Color.parseColor("#A57164"));
                holder.score.setText(String.valueOf(list.get(position).getCoins()));
                holder.img.setImageDrawable(context.getDrawable(R.drawable.top3));
                holder.rank.setText(""+(position+1));
                break;
            default:  holder.name.setText(list.get(position).getUsername());
                holder.score.setText(String.valueOf(list.get(position).getCoins()));
                holder.img.setImageDrawable(context.getDrawable(R.drawable.top1));
                holder.rank.setText(""+(position+1));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,rank,score;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.player_name);
            rank=itemView.findViewById(R.id.player_rank);
            score=itemView.findViewById(R.id.player_score);
            img = itemView.findViewById(R.id.badge);
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
