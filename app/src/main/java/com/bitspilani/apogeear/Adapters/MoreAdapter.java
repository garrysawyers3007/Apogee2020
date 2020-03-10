package com.bitspilani.apogeear.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.apogeear.DevelopersActivity;
import com.bitspilani.apogeear.Models.MoreModel;
import com.bitspilani.apogeear.R;

import java.util.List;

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.MoreViewHolder> {

    private Context context;
    private List<MoreModel> moreList;

    public MoreAdapter(Context context, List<MoreModel> moreList) {
        this.context = context;
        this.moreList = moreList;
    }

    @NonNull
    @Override
    public MoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.more_item_view,null);
        return new MoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoreViewHolder holder, int position) {
        MoreModel moreModel = moreList.get(position);

        holder.textView.setText(moreModel.getName());
        holder.imageView.setImageDrawable(context.getResources().getDrawable(moreModel.getImage()));
        holder.textView2.setText(moreModel.getSubName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t = v.findViewById(R.id.more);
                String a = t.getText().toString();
                Toast.makeText(context, a, Toast.LENGTH_SHORT).show();

                switch (a){
                    case "Developers":
                        context.startActivity(new Intent(context,DevelopersActivity.class));
                        break;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return moreList.size();
    }

    class MoreViewHolder extends RecyclerView.ViewHolder{

        TextView textView,textView2;
        ImageView imageView;

        MoreViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.more);
            imageView = itemView.findViewById(R.id.mark);
            textView2 = itemView.findViewById(R.id.sub_more);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 0;
        }else if (position == 1){
            return 1;
        }else{
            return 2;
        }
    }
}


