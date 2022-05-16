package com.example.thwissa.fragment.homefragment.willaya;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;

import java.util.ArrayList;

/**
 * MY RECYCLER VIEW ADAPTER
 */
public class WilayaAdapter extends RecyclerView.Adapter<WilayaAdapter.MyViewHolder> {
    private ArrayList<Wilaya> mWilayasList;

    /**
     * VIEW HOLDER INNER CLASS
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // OUR RECYLER VIEW ITEM ATTRIBUTES
        public ImageView mImageView;          // wilaya image
        public TextView mTextView;            // wilaya name

        // CONSTRUCTOR (super)
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.wilaya_image);
            mTextView = (TextView) itemView.findViewById(R.id.wilaya_name);
        }
    }

    /**
     * COSNTRUCTOR
     */
    public WilayaAdapter(ArrayList<Wilaya> wilayasList) {
        mWilayasList = wilayasList;
    }


    /**
     * INFALTE A VIEW FROM OUR RECYLER VIEW ITEM LAYOUT & CREATE A VIEW HOLDER USING THE CREATED VIEW
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wilaya_recycler_view_item, parent, false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(v).navigate(R.id.action_wilayasFragment_to_placesFragment);
            }
        });

        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    /**
     * PASS VALUES TO OUR RECYCLER VIEW ITEM ATTRIBUTES HERE
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mImageView.setImageResource(mWilayasList.get(position).getmImageResource());
        holder.mTextView.setText(mWilayasList.get(position).getmWilayaName());
    }

    @Override
    public int getItemCount() {
        return mWilayasList.size();
    }

}
