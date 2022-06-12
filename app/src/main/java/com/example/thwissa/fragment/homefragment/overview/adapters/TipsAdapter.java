package com.example.thwissa.fragment.homefragment.overview.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;
import com.example.thwissa.fragment.homefragment.willaya.Place;

import java.util.ArrayList;


public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.InnerViewHolder>{
    private ArrayList<Place.Tip> tips = new ArrayList<>();

    public TipsAdapter(ArrayList<Place.Tip> tips){
        this.tips = tips;
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder{
        public TextView tipDate;
        public TextView tipText;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            tipDate = (TextView) itemView.findViewById(R.id.tip_date);
            tipText = (TextView) itemView.findViewById(R.id.tip_text);
        }
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tip, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        holder.tipDate.setText(tips.get(position).date);
        holder.tipText.setText(tips.get(position).text);
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }


}
