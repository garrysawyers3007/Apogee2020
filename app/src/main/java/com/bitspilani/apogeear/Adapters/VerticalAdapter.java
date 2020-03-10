package com.bitspilani.apogeear.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.apogeear.Models.Event_Details;
import com.bitspilani.apogeear.R;
import com.github.vipulasri.timelineview.TimelineView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder> {

    private ArrayList<ArrayList<Event_Details>> lists;
    private ArrayList<String> attended;
    private Context context;
    private String attend;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private RecyclerView.RecycledViewPool recycledViewPool;


    public VerticalAdapter(ArrayList<ArrayList<Event_Details>> lists, Context context, ArrayList<String> attended) {
        this.lists=lists;
        this.context=context;
        recycledViewPool=new RecyclerView.RecycledViewPool();
        this.attended=attended;
    }

    public VerticalAdapter(){

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

        Calendar c1=Calendar.getInstance(),c2=Calendar.getInstance(),c3=Calendar.getInstance();
        c1.setTimeInMillis(lists.get(position).get(0).getTime().getSeconds()*1000);
        c2.setTimeInMillis(lists.get(position).get(lists.get(position).size()-1).getTime().getSeconds()*1000);
        c3.setTimeInMillis(lists.get(position>0?position-1:position).get(0).getTime().getSeconds()*1000);

        if(position==0 ||(position>0 && c1.get(Calendar.DATE)>c3.get(Calendar.DATE))) {
            holder.date.setText(c1.get(Calendar.DATE)+" March");
            holder.date.setVisibility(View.VISIBLE);
            holder.datecard.setVisibility(View.VISIBLE);
            if(position!=0) {
                holder.timext.setVisibility(View.VISIBLE);
                holder.timext.setBackgroundColor(Color.parseColor("#262626"));
            }
            if(position>lists.size()-3)
                holder.timext.setBackgroundColor(Color.parseColor("#d0d3d4"));
        }

        if(lists.get(position).size()==1 || lists.get(position).get(0).getTime().compareTo(lists.get(position).get(lists.get(position).size()-1).getTime())==0)
        holder.text.setText(c1.get(Calendar.HOUR)+":"+(c1.get(Calendar.MINUTE)==0?"00":c1.get(Calendar.MINUTE))+" "+(c1.get(Calendar.AM_PM)==0?"AM":"PM"));

        else
            holder.text.setText(c1.get(Calendar.HOUR)+":"+(c1.get(Calendar.MINUTE)==0?"00":c1.get(Calendar.MINUTE))+" "+(c1.get(Calendar.AM_PM)==0?"AM":"PM")+"-"+c2.get(Calendar.HOUR)+":"+(c2.get(Calendar.MINUTE)==0?"00":c2.get(Calendar.MINUTE))+" "+(c2.get(Calendar.AM_PM)==0?"AM":"PM"));

        SharedPreferences sharedPref=context.getSharedPreferences("userinfo",MODE_PRIVATE);
        String user=sharedPref.getString("username","");

                attend="";
                for(int i=0;i<lists.get(position).size();i++)
                if(attended.contains(lists.get(position).get(i).getName()))
                    attend=lists.get(position).get(i).getName();

                holder.horizontalrv.setRecycledViewPool(recycledViewPool);
                holder.horizontalrv.setHasFixedSize(true);
                holder.horizontalrv.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
                HorizontalAdapter horizontalAdapter = new HorizontalAdapter(lists.get(position),context,attend,!attend.equals(""));
                holder.horizontalrv.setAdapter(horizontalAdapter);
                horizontalAdapter.setOnItemClickListener(new HorizontalAdapter.ClickListener() {
                    @Override
                    public void onItemClicked(int position, View v) {
                        Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();
                    }
                });


                if(attend.equals(""))
                    holder.timelineView.setMarker(holder.fail);
                if(position==lists.size()-1 || position==lists.size()-2)
                    holder.timelineView.setMarker(holder.incomplete);
                if(position<lists.size()-3) {
                    holder.timelineView.setEndLineColor(Color.parseColor("#262626"), holder.getItemViewType());
                    holder.timelineView.setStartLineColor(Color.parseColor("#262626"), holder.getItemViewType());
                }
                if(position==lists.size()-3){
                    holder.timelineView.setStartLineColor(Color.parseColor("#262626"), holder.getItemViewType());
                }




        //Log.d("Hey","Yo"+lists.get(position).size());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text,date;
        CardView datecard;
        View timext;
        RecyclerView horizontalrv;
        public TimelineView timelineView;
        Drawable fail,incomplete;

        public ViewHolder(@NonNull View itemView,int viewType) {
            super(itemView);

            text=itemView.findViewById(R.id.course_item_name_tv);
            horizontalrv=itemView.findViewById(R.id.horizontal_list);
            timelineView=itemView.findViewById(R.id.timeline);
            date=itemView.findViewById(R.id.date);
            timelineView.initLine(viewType);
            timext=itemView.findViewById(R.id.timext);
            datecard=itemView.findViewById(R.id.datecard);

            fail=itemView.getResources().getDrawable(R.drawable.fail);
            incomplete=itemView.getResources().getDrawable(R.drawable.incomplete);

        }


    }
    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }
}
