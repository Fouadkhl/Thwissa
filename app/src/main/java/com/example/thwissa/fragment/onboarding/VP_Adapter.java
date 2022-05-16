package com.example.thwissa.fragment.onboarding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import com.example.thwissa.R;


public class VP_Adapter extends PagerAdapter {

    private final Context context;
    private int previous_position = -1;


    private final int[] images = {
            R.mipmap.planning_your_trip_1,
            R.mipmap.enjoy_with_us_2,
            R.drawable.discover_with_us_3,
            R.drawable.finish_4
    };

    private final int[] texts = {
            R.string.planing_n_your_trip,
            R.string.enjoy_with_US,
            R.string.discover_the_world,
            R.string.take_your_next_step
    };



    public VP_Adapter(Context context){

        this.context = context;


    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                context.LAYOUT_INFLATER_SERVICE
        );
        View view = layoutInflater.inflate(R.layout.slider_layout1, container, false);

        Button button = view.findViewById(R.id.finish_button);
        ImageView imageView = view.findViewById(R.id.image_view);
        TextView textView1 = view.findViewById(R.id.text_view1);
        TextView textView2 = view.findViewById(R.id.text_view2);

        if(position == 3){
            button.setVisibility(View.VISIBLE);
        }
        else{
            button.setVisibility(View.GONE);
        }

        if(position == 0 || position == 1){
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(images[position]);
            view.setBackground(
                    context.getResources().getDrawable(R.drawable.transparent_background)
            );
            textView1.setText(
                    context.getResources().getText(texts[position])
            );
            textView1.setTextColor(
                    context.getColor(R.color.grey)
            );
            textView2.setText("");
        }
        else{
            imageView.setVisibility(View.GONE);
            view.setBackground(
                    context.getResources().getDrawable(images[position])
            );
            textView2.setText(
                    context.getResources().getText(texts[position])
            );
            textView2.setTextColor(
                    context.getColor(R.color.white)
            );
            textView1.setText("");
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
