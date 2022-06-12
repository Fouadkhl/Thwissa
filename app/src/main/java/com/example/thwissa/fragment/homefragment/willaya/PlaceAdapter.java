package com.example.thwissa.fragment.homefragment.willaya;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;
import com.example.thwissa.fragment.homefragment.overview.interfaces.OnPlaceClickedListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyViewHolder>{
    private ArrayList<Place> mPlacesList;
    private Context context;

    private OnPlaceClickedListener onPlaceClickedListener = new OnPlaceClickedListener() {
        @Override
        public void placeClicked(Place place) {}
    };
    /** VIEW HOLDER INNER CLASS */
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // OUR RECYLER VIEW ITEM ATTRIBUTES
        public ImageView placeImage;           // place image
        public TextView placeName;            // place name
        public TextView placeSummary;            // place summary
        public RatingBar placeRatingBar;     // place rating bar
        public TextView placeRateText;            // place rate text

        // CONSTRUCTOR
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = (ImageView) itemView.findViewById(R.id.place_image);
            placeName = (TextView) itemView.findViewById(R.id.place_name);
            placeSummary = (TextView) itemView.findViewById(R.id.place_summary);
            placeRatingBar = (RatingBar) itemView.findViewById(R.id.place_rating_bar);
            placeRateText = (TextView) itemView.findViewById(R.id.place_rate_text);
        }
    }

    /** CONSTRUCTOR */
    public PlaceAdapter(Context context, ArrayList<Place> placesList){
        this.context = context;
        mPlacesList = placesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_recycler_view_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Place currentPlace = mPlacesList.get(position);
        try {
            Picasso.with(context).load(currentPlace.placeImagesUrls.get(0)).into(holder.placeImage);
        }catch (Exception e){
            holder.placeImage.setImageResource(R.drawable.finish_4);
        }
        holder.placeName.setText(currentPlace.placeName);
        String placeSummaryText = "Category: " + currentPlace.placeCategory + "\nAddress: " + currentPlace.placeAddress +
                "\nPopularity: " + currentPlace.placePopularity +"/10\nDistance From Local State: " +
                currentPlace.placeDistance + "m\nDescription: " + currentPlace.placeDescription;
        holder.placeSummary.setText(placeSummaryText);
        holder.placeRateText.setText(String.valueOf(currentPlace.placeRate));
        holder.placeRatingBar.setRating((float)currentPlace.placeRate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceClickedListener.placeClicked(currentPlace);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlacesList.size();
    }



    public void setOnPlaceClickedListener(OnPlaceClickedListener onPlaceClickedListener){
        this.onPlaceClickedListener = onPlaceClickedListener;
    }
}
