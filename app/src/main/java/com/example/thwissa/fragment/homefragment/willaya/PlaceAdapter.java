package com.example.thwissa.fragment.homefragment.willaya;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyViewHolder>{
    private ArrayList<Place> mPlacesList;

    /** VIEW HOLDER INNER CLASS */
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // OUR RECYLER VIEW ITEM ATTRIBUTES
        public ImageView mImageView;           // place image
        public TextView mTextView1;            // place name
        public TextView mTextView2;            // place summary
        public LinearLayout mLinearLayout;     // place rate
        public TextView mTextView3;            // place rate text
        public CardView cardviewparent;            // place rate text

        // CONSTRUCTOR
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.place_image);
            mTextView1 = (TextView) itemView.findViewById(R.id.place_name);
            mTextView2 = (TextView) itemView.findViewById(R.id.place_summary);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.place_rate);
            cardviewparent = (CardView)  itemView.findViewById(R.id.cv_place_item);

            mTextView3 = (TextView) mLinearLayout.getChildAt(0);
        }
    }

    /** CONSTRUCTOR */
    public PlaceAdapter(ArrayList<Place> placesList){
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
        holder.mImageView.setImageResource(mPlacesList.get(position).getmPlaceImageResource());
        holder.mTextView1.setText(mPlacesList.get(position).getmPlaceName());
        holder.mTextView2.setText(mPlacesList.get(position).getmPlaceSummary());
        holder.cardviewparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_placesFragment_to_overview);
            }
        });

        float rate = mPlacesList.get(position).getmPlaceRate();
        holder.mTextView3.setText(String.valueOf(rate));            // PLACE RATE TEXT

        for (int i = 1; i <= rate; i++) {                          // PALCE RATE STARS
            ImageView star = (ImageView) holder.mLinearLayout.getChildAt(i);
            star.setImageResource(R.drawable.ic_baseline_star_24);
        }
    }

    @Override
    public int getItemCount() {
        return mPlacesList.size();
    }
}
