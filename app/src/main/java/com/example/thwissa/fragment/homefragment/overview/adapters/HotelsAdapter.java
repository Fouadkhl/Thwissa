package com.example.thwissa.fragment.homefragment.overview.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;


public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.InnerViewHolder>{
    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder{

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
