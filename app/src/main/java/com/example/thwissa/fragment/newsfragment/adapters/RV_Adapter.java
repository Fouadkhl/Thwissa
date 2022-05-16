package com.example.thwissa.fragment.newsfragment.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.classes.Trip;

import java.util.ArrayList;

public class RV_Adapter extends RecyclerView.Adapter<RV_Adapter.RVViewHolder>{

    private ArrayList<Trip> data = new ArrayList<>();

    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_trips_recycle_view_layout, parent, false);
        return new RVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
        Trip trip = data.get(position);
        holder.imageView.setImageResource(trip.imageId);
        holder.textView.setText(
                trip.description.substring(0, Math.min(20, trip.description.length()))+"..."
        );
        holder.textView1.setText(trip.rating+"");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<Trip> data) {
        this.data = data;
    }

    public static class RVViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView, textView1;

        public RVViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.tripImageView);
            textView = itemView.findViewById(R.id.tripTextView);
            textView1 = itemView.findViewById(R.id.tripRating);
        }
    }
}
