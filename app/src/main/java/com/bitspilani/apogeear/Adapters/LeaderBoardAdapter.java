package com.bitspilani.apogeear.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        if (viewType == 0){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_top_1,parent,false);
        }else if (viewType == 1){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_top_2,parent,false);
        }else if (viewType == 2){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_top_3,parent,false);
        }else{
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboarditem,parent,false);
        }
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getUsername());
        holder.score.setText(String.valueOf(list.get(position).getCoins()));
        holder.rank.setText(""+(position+1));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,rank,score;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.player_name);
            rank=itemView.findViewById(R.id.player_rank);
            score=itemView.findViewById(R.id.player_score);
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
