package com.example.thwissa.fragment.entertainment;


import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.thwissa.R;
import com.google.android.material.imageview.ShapeableImageView;

public class TopPicsAdapter extends PagerAdapter{

    private final Context context;

    private TopPic[] data;

    public TopPicsAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.length;
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
        String url = EntertainmentService.BASE_URL + "/" + data[position].picture.split("/")[1];
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(imageView);

        ShapeableImageView profile_picture = view.findViewById(R.id.profile_picture);
        if(data[position].userpicture != null && !data[position].userpicture.equals("")){
            url = EntertainmentService.BASE_URL + "/" + data[position].userpicture.split("/")[1];
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(profile_picture);
        } // else profile_picture.setImageResource(R.drawable.ic_baseline_person_24);

        TextView textView = view.findViewById(R.id.name_textView);
        textView.setText(data[position].username);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

    public void setData(TopPic[] data) {
        this.data = data;
    }
}
