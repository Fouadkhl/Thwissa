package com.example.thwissa.fragment.homefragment.overview.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thwissa.R;
import com.example.thwissa.dataclasses.AgencyReviews;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;


public class TipsAgencyAdapter extends RecyclerView.Adapter<TipsAgencyAdapter.InnerViewHolder> {
    private ArrayList<AgencyReviews> tips = new ArrayList<>();

    public TipsAgencyAdapter(ArrayList<AgencyReviews> tips) {
        this.tips = tips;
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder {
        public TextView tipDate;
        public TextView tipText;
        public TextView username;
        public ShapeableImageView userimage;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            tipDate = itemView.findViewById(R.id.tip_date);
            tipText = itemView.findViewById(R.id.tip_text);
            username = itemView.findViewById(R.id.user_name);
            userimage = itemView.findViewById(R.id.iv_user_reviewing);
        }
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tip, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        holder.tipDate.setText(tips.get(position).getDate());
        holder.tipText.setText(tips.get(position).getRatingText());
        holder.username.setText(tips.get(position).getName());
        Glide.with(holder.itemView)
                .load(tips.get(position).getUserphoto())
                .into(holder.userimage) ;
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }
}
