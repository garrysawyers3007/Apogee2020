package com.bitspilani.apogeear;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {

    ArrayList<Event_Details> event_details;
    Context context;

    public HorizontalAdapter(ArrayList<Event_Details> event_details, Context context) {
        this.event_details=event_details;
        this.context=context;
    }

    @NonNull
    @Override
    public HorizontalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontalitem,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(event_details.get(position).getName());
        Log.d("Event",event_details.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return event_details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.horizontal_item_text);
        }
    }
}
