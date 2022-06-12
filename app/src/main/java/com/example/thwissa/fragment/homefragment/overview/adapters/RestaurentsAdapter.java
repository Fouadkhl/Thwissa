package com.example.thwissa.fragment.homefragment.overview.adapters;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;
import com.example.thwissa.fragment.homefragment.willaya.Place;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RestaurentsAdapter extends RecyclerView.Adapter<RestaurentsAdapter.InnerViewHolder> {
    private ArrayList<Place> restaurants;
    private Context context;
    private int layoutResource;
    public RestaurentsAdapter(Context context, ArrayList<Place> restaurants, int layoutResource){
        this.restaurants = restaurants;
        this.context = context;
        this.layoutResource = layoutResource;
    }
    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        Place currentRestaurant = restaurants.get(position);
        holder.restaurantName.setText(currentRestaurant.placeName);
        holder.restaurantCategory.setText(currentRestaurant.placeCategory);
        holder.restaurantAddress.setText(currentRestaurant.placeAddress);
        holder.restaurantRate.setText(String.valueOf(currentRestaurant.placeRate));
        holder.restaurantRatingBar.setRating((float)currentRestaurant.placeRate);
        try {
            Picasso.with(context).load(currentRestaurant.placeImagesUrls.get(0)).into(holder.restaurantImage);
        }catch (Exception e){
            holder.restaurantImage.setImageResource(R.drawable.finish_4);
        }
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder{
        TextView restaurantName;
        TextView restaurantCategory;
        TextView restaurantRate;
        RatingBar restaurantRatingBar;
        TextView restaurantAddress;
        ImageView restaurantImage;
        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            restaurantCategory = itemView.findViewById(R.id.restaurant_category);
            restaurantRate = itemView.findViewById(R.id.restaurant_rate_text);
            restaurantRatingBar = itemView.findViewById(R.id.restaurant_rating_bar);
            restaurantAddress = itemView.findViewById(R.id.restaurant_address);
            restaurantImage = itemView.findViewById(R.id.restaurant_picture);
        }
    }
}
