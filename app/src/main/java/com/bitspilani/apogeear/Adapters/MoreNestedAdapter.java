package com.bitspilani.apogeear.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.apogeear.Models.MoreModel;
import com.bitspilani.apogeear.Models.MoreNestedModel;
import com.bitspilani.apogeear.R;

import java.util.List;

public class MoreNestedAdapter extends RecyclerView.Adapter<MoreNestedAdapter.MoreNestedViewHolder> {

    private Context context;
    private List<MoreNestedModel> moreNestedModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public MoreNestedAdapter(Context context, List<MoreNestedModel> moreNestedModelList) {
        this.context = context;
        this.moreNestedModelList = moreNestedModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public MoreNestedAdapter.MoreNestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.more_nested,parent,false);
        return new MoreNestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreNestedAdapter.MoreNestedViewHolder holder, int position) {
        MoreNestedModel moreNestedModel = moreNestedModelList.get(position);

        holder.name.setText(moreNestedModel.getName());
        holder.recyclerView.setRecycledViewPool(recycledViewPool);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        MoreAdapter moreAdapter = new MoreAdapter(context,moreNestedModel.getMoreModelList());
        holder.recyclerView.setAdapter(moreAdapter);
    }

    @Override
    public int getItemCount() {
        return moreNestedModelList.size();
    }

    public class MoreNestedViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RecyclerView recyclerView;

        MoreNestedViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.more_item_heading);
            recyclerView = itemView.findViewById(R.id.recycler_more1);
        }
    }
}
