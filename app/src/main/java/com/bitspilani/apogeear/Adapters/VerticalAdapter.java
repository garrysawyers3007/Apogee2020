package com.bitspilani.apogeear.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.apogeear.Models.Event_Details;
import com.bitspilani.apogeear.R;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.Calendar;

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
        ViewHolder viewHolder=new ViewHolder(view,viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalAdapter.ViewHolder holder, int position) {

        Calendar c1=Calendar.getInstance(),c2=Calendar.getInstance();
        c1.setTimeInMillis(lists.get(position).get(0).getTime().getSeconds()*1000);
        c2.setTimeInMillis(lists.get(position).get(lists.get(position).size()-1).getTime().getSeconds()*1000);

        if(lists.get(position).size()==1 || lists.get(position).get(0).getTime().compareTo(lists.get(position).get(lists.get(position).size()-1).getTime())==0)
        holder.text.setText(c1.get(Calendar.DATE)+" "+"Mar"+" "+c1.get(Calendar.HOUR)+":"+(c1.get(Calendar.MINUTE)==0?"00":c1.get(Calendar.MINUTE))+" "+(c1.get(Calendar.AM_PM)==0?"AM":"PM"));

        else
            holder.text.setText(c1.get(Calendar.DATE)+" "+"Mar"+" "+c1.get(Calendar.HOUR)+":"+(c1.get(Calendar.MINUTE)==0?"00":c1.get(Calendar.MINUTE))+" "+(c1.get(Calendar.AM_PM)==0?"AM":"PM")+"-"+c2.get(Calendar.HOUR)+":"+(c2.get(Calendar.MINUTE)==0?"00":c2.get(Calendar.MINUTE))+" "+(c2.get(Calendar.AM_PM)==0?"AM":"PM"));

        //holder.text.setText(""+position);

        holder.horizontalrv.setRecycledViewPool(recycledViewPool);
        holder.horizontalrv.setHasFixedSize(true);
        holder.horizontalrv.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(lists.get(position),context);
        holder.horizontalrv.setAdapter(horizontalAdapter);

        if(position==1)
            holder.timelineView.setMarker(holder.fail);

        if(position==5 || position==6)
            holder.timelineView.setMarker(holder.incomplete);

        if(position<4) {
            holder.timelineView.setEndLineColor(Color.parseColor("#262626"), holder.getItemViewType());
            holder.timelineView.setStartLineColor(Color.parseColor("#262626"), holder.getItemViewType());
        }
        if(position==4){
            holder.timelineView.setStartLineColor(Color.parseColor("#262626"), holder.getItemViewType());
        }
        //Log.d("Hey","Yo"+lists.get(position).size());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        RecyclerView horizontalrv;
        public TimelineView timelineView;
        Drawable fail,incomplete;

        public ViewHolder(@NonNull View itemView,int viewType) {
            super(itemView);

            text=itemView.findViewById(R.id.course_item_name_tv);
            horizontalrv=itemView.findViewById(R.id.horizontal_list);
            timelineView=itemView.findViewById(R.id.timeline);
            timelineView.initLine(viewType);

            fail=itemView.getResources().getDrawable(R.drawable.fail);
            incomplete=itemView.getResources().getDrawable(R.drawable.incomplete);

        }


    }
    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }
}
