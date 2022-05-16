package com.example.thwissa.fragment.newsfragment.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChoosedImagesAdapter extends RecyclerView.Adapter<ChoosedImagesAdapter.InnerViewHolder>{


    private ArrayList<Bitmap> ImagesIds = new ArrayList<>();

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.compose_images_layout, parent, false
        );
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        holder.imageView.setImageBitmap(
                ImagesIds.get(position)
        );
        holder.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagesIds.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return ImagesIds.size();
    }

    public void setImagesIds(ArrayList<Bitmap> imagesIds) {
        ImagesIds = imagesIds;
    }
    public ArrayList<Bitmap> getImagesIds(){ return ImagesIds; }

    protected static class InnerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private FloatingActionButton floatingActionButton;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.Image);
            floatingActionButton = itemView.findViewById(R.id.cancelButton);
        }
    }
}
