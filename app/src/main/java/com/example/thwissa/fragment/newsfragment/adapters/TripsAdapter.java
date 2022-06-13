package com.example.thwissa.fragment.newsfragment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.NewsService;
import com.example.thwissa.fragment.newsfragment.classes.Trip;
import com.example.thwissa.fragment.newsfragment.interfaces.OnItemClickedListener;

import java.util.ArrayList;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.RVViewHolder>{

    private ArrayList<Trip> data = new ArrayList<>();
    private Context context;

    public TripsAdapter(Context context){
        this.context = context;
    }

    private OnItemClickedListener onItemClickedListener = new OnItemClickedListener() {
        @Override
        public void ItemClicked(String postID, int pos) {
            Toast.makeText(context, "methode not implemented", Toast.LENGTH_SHORT).show();
        }
    };

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
        // holder.imageView.setImageBitmap(NewsUtil.urlToBitmap(trip.picture));
        if(trip.picture!=null && !trip.picture.equals("")){
            String url = NewsService.BASE_URL + "/" + trip.picture.split("/")[1];
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(holder.imageView);
        }
        holder.textView.setText(
                trip.description.substring(0, Math.min(20, trip.description.length())).concat("...")
        );
        holder.textView1.setText(String.valueOf(trip.rating));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickedListener.ItemClicked(trip._id, holder.getAdapterPosition());
            }
        });
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
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
