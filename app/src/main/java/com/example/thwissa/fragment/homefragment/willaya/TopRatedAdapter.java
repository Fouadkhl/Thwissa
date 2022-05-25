package com.example.thwissa.fragment.homefragment.willaya;

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

import java.util.ArrayList;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.MyViewHolder>{
    private ArrayList<Place> mTopRatedList;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // OUR RECYLER VIEW ITEM ATTRIBUTES
        public ImageView mImageView;    // top rated image
        public TextView mTextView1;    // top rated text
        public TextView mTextView2;    // top rated rate
        public CardView cardviewparent;

        // CONSTRUCTOR
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.topRated_image);
            mTextView1 = (TextView) itemView.findViewById(R.id.topRated_name);
            mTextView2 = (TextView) itemView.findViewById(R.id.topRated_rate);
            cardviewparent = (CardView)  itemView.findViewById(R.id.cv_top_rated_item);
        }
    }

    /** CONSTRUCTOR */
    public TopRatedAdapter(ArrayList<Place> topRatedList) {
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
        holder.mImageView.setImageResource(mTopRatedList.get(position).getmPlaceImageResource());
        holder.mTextView1.setText(mTopRatedList.get(position).getmPlaceName());
        holder.mTextView2.setText(String.valueOf(mTopRatedList.get(position).getmPlaceRate()));

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
