package com.example.thwissa.fragment.newsfragment.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.classes.Meteo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MeteoViewPagerAdapter extends RecyclerView.Adapter<MeteoViewPagerAdapter.ViewPagerViewHolder> {

    Context context;
    private ArrayList<Meteo> meteos = new ArrayList<>();

    public MeteoViewPagerAdapter(Context context, ArrayList<Meteo> meteos){
        this.context = context;
        this.meteos = meteos;
    }

    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.meteo_view_pager_layout, parent, false
        );
        return new ViewPagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        //date
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        String day = context.getResources().getStringArray(R.array.days)[
                    cal.get(Calendar.DAY_OF_WEEK)-1
                ];
        String month = context.getResources().getStringArray(R.array.months)[
                    cal.get(Calendar.MONTH)
                ];
        TextView textView = holder.itemView.findViewById(R.id.date);
        textView.setText(day + " " + cal.get(Calendar.DAY_OF_MONTH) +" "+ month);
        //wilaya
        TextView textView2 = holder.itemView.findViewById(R.id.wilaya);
        textView2.setText(meteos.get(position).wilayaName);
        //temp
        TextView textView1 = holder.itemView.findViewById(R.id.temp);
        textView1.setText(meteos.get(position).temp + "°");
        //weather state
        TextView textView3 = holder.itemView.findViewById(R.id.weatherState);
        textView3.setText(meteos.get(position).weatherState);
        //weather state icon
        ImageView imageView = holder.itemView.findViewById(R.id.weatherStateIcon);
        Picasso.with(context).load(meteos.get(position).weatherStateIconUrl).into(imageView);
        //parent background
        RelativeLayout relativeLayout = holder.itemView.findViewById(R.id.parent);
        relativeLayout.setBackground(context.getDrawable(R.drawable.finish_4));
    }

    @Override
    public int getItemCount() {
        return meteos.size();
    }


    public class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
