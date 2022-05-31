package com.example.thwissa.fragment.homefragment.willaya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.example.thwissa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.MyViewHolder>{
    private ArrayList<Place> mTopRatedList;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // OUR RECYLER VIEW ITEM ATTRIBUTES
        public ImageView topRatedImage;    // top rated image
        public TextView topRatedName;    // top rated text
        public TextView topRatedRate;    // top rated rate
        public CardView cardviewparent;

        // CONSTRUCTOR
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            topRatedImage = (ImageView) itemView.findViewById(R.id.topRated_image);
            topRatedName = (TextView) itemView.findViewById(R.id.topRated_name);
            topRatedRate = (TextView) itemView.findViewById(R.id.topRated_rate);
            cardviewparent = (CardView)  itemView.findViewById(R.id.cv_top_rated_item);
        }
    }

    /** CONSTRUCTOR */
    public TopRatedAdapter(Context context, ArrayList<Place> topRatedList) {
        this.context = context;
        this.mTopRatedList = topRatedList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.toprated_recycler_view_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Place currentTopRated = mTopRatedList.get(position);
        try {
            Picasso.with(context).load(currentTopRated.placeImagesUrls.get(0)).into(holder.topRatedImage);
        }catch (Exception e){
            holder.topRatedImage.setImageResource(R.drawable.finish_4);
        }
        holder.topRatedName.setText(currentTopRated.placeName);
        holder.topRatedRate.setText(String.valueOf(currentTopRated.placeRate));

        holder.cardviewparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_placesFragment_to_overview);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTopRatedList.size();
    }
}
