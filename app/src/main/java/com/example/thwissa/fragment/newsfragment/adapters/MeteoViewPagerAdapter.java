package com.example.thwissa.fragment.newsfragment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.classes.Meteo;

import java.util.Calendar;
import java.util.Date;

public class MeteoViewPagerAdapter extends RecyclerView.Adapter<MeteoViewPagerAdapter.ViewPagerViewHolder> {

    Context context;
    public MeteoViewPagerAdapter(Context context){
        this.context = context;
    }

    private Meteo[] meteos = {
            new Meteo(
                    R.drawable._3 , 28,"algeria",Meteo.Sunny
            ),
            new Meteo(
                    R.drawable._3 , 10,"mostaganem",Meteo.Cloudy
            )
    };

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
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int index = meteos[position].getWeatherState();

        String day = context.getResources().getStringArray(R.array.days)[
                    cal.get(Calendar.DAY_OF_WEEK)-1
                ];
        String month = context.getResources().getStringArray(R.array.months)[
                    cal.get(Calendar.MONTH)
                ];
        String weatherState =  context.getResources().getStringArray(R.array.weather_states)[
                    index
                ];
        //date
        TextView textView = holder.itemView.findViewById(R.id.date);
        textView.setText(day + " " + cal.get(Calendar.DAY_OF_MONTH) +" "+ month);
        //backgroundImage
        ImageView imageView = holder.itemView.findViewById(R.id.weatherStateIcon);
        imageView.setImageResource(R.drawable.ic_icons8_soleil);
        //temp
        TextView textView1 = holder.itemView.findViewById(R.id.temp);
        textView1.setText(meteos[position].temp+"°");
        //wilaya
        TextView textView2 = holder.itemView.findViewById(R.id.wilaya);
        textView2.setText(meteos[position].wilayaName);
        //weather state
        TextView textView3 = holder.itemView.findViewById(R.id.weatherState);
        textView3.setText(weatherState);
        //parent background
        RelativeLayout relativeLayout = holder.itemView.findViewById(R.id.parent);
        relativeLayout.setBackground(context.getDrawable(R.drawable.finish_4));
    }

    @Override
    public int getItemCount() {
        return meteos.length;
    }


    public class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
