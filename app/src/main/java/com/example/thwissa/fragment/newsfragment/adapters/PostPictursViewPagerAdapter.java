package com.example.thwissa.fragment.newsfragment.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.ArrayList;


public class PostPictursViewPagerAdapter extends RecyclerView.Adapter<PostPictursViewPagerAdapter.InnerViewHolder>{

    private ArrayList<Bitmap> imgIds = new ArrayList<>();

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.post_images_view_pager_layout, parent, false
        );
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        holder.shapeableImageView.setImageBitmap(imgIds.get(position));
    }

    @Override
    public int getItemCount() {
        return imgIds.size();
    }

    public void setImgIds(ArrayList<Bitmap> imgIds) {
        this.imgIds = imgIds;
    }

    protected static class InnerViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView shapeableImageView;
        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            shapeableImageView = itemView.findViewById(R.id.postPic);
        }
    }
}
