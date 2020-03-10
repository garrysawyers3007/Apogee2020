package com.bitspilani.apogeear.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.apogeear.Fragments.Leaderboard;
import com.bitspilani.apogeear.R;
import com.bitspilani.apogeear.Models.Rank;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {

    private ArrayList<Rank> list;
    private Context context;
    private Activity activity;
    private View view;
    private StorageReference charRef;

    public LeaderBoardAdapter (ArrayList<Rank> list, Context context, Activity activity){
        this.list=list;
        this.context=context;
        this.activity=activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_new,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.name.setSingleLine(true);
        holder.name.setSelected(true);
        holder.name.setMarqueeRepeatLimit(-1);

        switch (position){

            case 0:holder.name.setText(list.get(position).getUsername());
                holder.name.setTextColor(Color.parseColor("#000000"));
                holder.charName.setText(list.get(position).getCharName());
                holder.charName.setTextColor(Color.parseColor("#000000"));
                holder.score.setText(String.valueOf(list.get(position).getCoins()));
                holder.score.setTextColor(Color.parseColor("#000000"));
                holder.rank.setText(""+(position+1));
                getTagFromDatabase(holder.img);
                holder.relativeLayout.setBackground(context.getDrawable(R.drawable.leaderboard_rank1_bg));
                getImageFromDatabase(holder.img1,list.get(position).getCharName());
                break;

            case 1:holder.name.setText(list.get(position).getUsername());
                holder.charName.setText(list.get(position).getCharName());
                holder.name.setTextColor(Color.parseColor("#000000"));
                holder.charName.setTextColor(Color.parseColor("#000000"));
                holder.score.setText(String.valueOf(list.get(position).getCoins()));
                holder.score.setTextColor(Color.parseColor("#000000"));
                holder.rank.setText(""+(position+1));
                getTagFromDatabase(holder.img);
                holder.relativeLayout.setBackground(context.getDrawable(R.drawable.leaderboard_rank2_bg));
                holder.rank.setText(""+(position+1));
                getImageFromDatabase(holder.img1,list.get(position).getCharName());
                break;

            case 2:holder.name.setText(list.get(position).getUsername());
                holder.charName.setText(list.get(position).getCharName());
                holder.name.setTextColor(Color.parseColor("#000000"));
                holder.charName.setTextColor(Color.parseColor("#000000"));
                holder.score.setTextColor(Color.parseColor("#000000"));
                holder.score.setText(String.valueOf(list.get(position).getCoins()));
                holder.rank.setText(""+(position+1));
                getTagFromDatabase(holder.img);
                holder.relativeLayout.setBackground(context.getDrawable(R.drawable.leaderboard_rank3_bg));
                getImageFromDatabase(holder.img1,list.get(position).getCharName());
                break;

            default:  holder.name.setText(list.get(position).getUsername());
                holder.charName.setText(list.get(position).getCharName());
                holder.score.setText(String.valueOf(list.get(position).getCoins()));
                getTagFromDatabase(holder.img);
                holder.rank.setText(""+(position+1));
                getImageFromDatabase(holder.img1,list.get(position).getCharName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,rank,score,charName;
        ImageView img,img1;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.player_name);
            rank=itemView.findViewById(R.id.player_rank);
            score=itemView.findViewById(R.id.player_score);
            img = itemView.findViewById(R.id.badge);
            img1 = itemView.findViewById(R.id.circleImageView);
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

    private void getImageFromDatabase(ImageView v,String charName){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        switch (charName){
            case "The HackerMan": charRef = storageRef.child("Characters/Hackerman.png");
                break;
            case "Maestro": charRef = storageRef.child("Characters/Maestro.png");
                break;
            default: charRef = storageRef.child("Characters/Maestro.png");
                break;
        }


            charRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if(context!=null && activity!=null)
                    Glide.with(activity).load(uri.toString()).into(v);
                }
            });

    }

    private void getTagFromDatabase(ImageView v){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference charRef = storageRef.child("Badges/topimg.png");

            charRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
//                    if(context!=null && activity!=null)
//                    Glide.with(context).load(uri.toString()).into(v);

                    if(context!=null && activity!=null)
                        Picasso.get().load(uri.toString()).into(v);
                }
            });
    }

}
