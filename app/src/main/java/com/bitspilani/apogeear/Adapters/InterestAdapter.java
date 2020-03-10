package com.bitspilani.apogeear.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.apogeear.R;

import java.util.ArrayList;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {

    private ArrayList<String> types;

    public InterestAdapter(ArrayList<String> types) {
        this.types=types;
    }

    @NonNull
    @Override
    public InterestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_filter_item,parent,false);
        InterestAdapter.ViewHolder viewHolder=new InterestAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InterestAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
