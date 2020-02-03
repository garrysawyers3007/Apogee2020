package com.bitspilani.apogeear;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder> {

    private ArrayList<ArrayList<Event_Details>> lists;
    private Context context;
    private RecyclerView.RecycledViewPool recycledViewPool;


    public VerticalAdapter(ArrayList<ArrayList<Event_Details>> lists, Context context) {
        this.lists=lists;
        this.context=context;
        recycledViewPool=new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public VerticalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.verticalitem,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalAdapter.ViewHolder holder, int position) {

        holder.text.setText(""+position);

        holder.horizontalrv.setRecycledViewPool(recycledViewPool);
        holder.horizontalrv.setHasFixedSize(true);
        holder.horizontalrv.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(lists.get(position),context);
        holder.horizontalrv.setAdapter(horizontalAdapter);
        Log.d("Hey","Yo"+lists.get(position).size());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        RecyclerView horizontalrv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text=itemView.findViewById(R.id.course_item_name_tv);
            horizontalrv=itemView.findViewById(R.id.horizontal_list);

        }
    }
}
