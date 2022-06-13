package com.example.thwissa.fragment.newsfragment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.NewsService;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;


public class PostPictursViewPagerAdapter extends RecyclerView.Adapter<PostPictursViewPagerAdapter.InnerViewHolder>{

    private ArrayList<String> names = new ArrayList<>();
    private final Context context;

    public PostPictursViewPagerAdapter(Context context){
        this.context = context;
    };

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
        String url;
        for(String name : names){
            url = NewsService.BASE_URL + "/" + name.split("/")[0];
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(holder.shapeableImageView);
        }
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public void setImgIds(List<String> names) {
        this.names.addAll(names);
    }

    protected static class InnerViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView shapeableImageView;
        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            shapeableImageView = itemView.findViewById(R.id.postPic);
        }
    }
}
