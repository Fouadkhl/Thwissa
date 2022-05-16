package com.example.thwissa.fragment.homefragment.overview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;
import com.google.android.material.imageview.ShapeableImageView;

public class Adapter extends RecyclerView.Adapter<Adapter.InnerViewHolder>{

    private Context context;
    public Adapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_layout, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        holder.shapeableImageView.setImageResource(R.drawable.finish_4);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    protected static class InnerViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView shapeableImageView;
        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            shapeableImageView = itemView.findViewById(R.id.root);
        }
    }
}
