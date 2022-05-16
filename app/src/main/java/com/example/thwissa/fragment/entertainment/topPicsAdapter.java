package com.example.thwissa.fragment.entertainment;


import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.thwissa.R;


public class topPicsAdapter extends PagerAdapter {

    private final Context context;

    private final int[] img_ids = {
            R.drawable._4,
            R.drawable._4,
            R.drawable._4
    };

    public topPicsAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                LAYOUT_INFLATER_SERVICE
        );

        View view = layoutInflater.inflate(R.layout.top_pictures_viewer_layout, container, false);

        ImageView imageView = view.findViewById(R.id.top_picture);
        imageView.setImageResource(img_ids[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
