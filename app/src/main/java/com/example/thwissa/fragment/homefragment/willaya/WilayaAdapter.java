package com.example.thwissa.fragment.homefragment.willaya;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thwissa.R;
import com.example.thwissa.dataclasses.Willaya;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * MY RECYCLER VIEW ADAPTER
 */
public class WilayaAdapter extends RecyclerView.Adapter<WilayaAdapter.MyViewHolder> {
    private ArrayList<Willaya> mWilayasList;
    private Context context;

    /**
     * VIEW HOLDER INNER CLASS
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;          // wilaya image
        public TextView mTextView;            // wilaya name
        public CardView cardviewparent;
        private  Context context ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.wilaya_image);
            mTextView = (TextView) itemView.findViewById(R.id.wilaya_name);
            cardviewparent = (CardView) itemView.findViewById(R.id.cv_wilaya_item);
        }
    }

    /**
     * COSNTRUCTOR
     */
    public WilayaAdapter(ArrayList<Willaya> wilayasList , Context  context) {
        mWilayasList = wilayasList;this.context = context;

    }


    /**
     * INFALTE A VIEW FROM OUR RECYLER VIEW ITEM LAYOUT & CREATE A VIEW HOLDER USING THE CREATED VIEW
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wilaya_recycler_view_item, parent, false);


        /**
         v.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("wilaya_name", "hhhh");
        Navigation.findNavController(v).navigate(R.id.action_wilayasFragment_to_placesFragment);
        }
        }); */

        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    /**
     * PASS VALUES TO OUR RECYCLER VIEW ITEM ATTRIBUTES HERE
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Willaya currentWilaya = mWilayasList.get(position);
        Log.d("one", ""+ currentWilaya.getWilayapicture());

        Glide.with(holder.mImageView)
                .asBitmap()
                .load( "http://192.168.43.248:5000/" + currentWilaya.getWilayapicture())
                .into(holder.mImageView) ;

        Picasso.
                with(context).
                load(currentWilaya.getWilayapicture()).
                into(holder.mImageView);


        holder.mTextView.setText(currentWilaya.getName());

        holder.cardviewparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("wilaya_name", currentWilaya.getName());
                Navigation.findNavController(view).navigate(R.id.action_wilayasFragment_to_placesFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWilayasList.size();
    }

}
