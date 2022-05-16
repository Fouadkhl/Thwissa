package com.example.thwissa.fragment.entertainment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.thwissa.R;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class EntertainmentFragment extends Fragment {

    private int checkedPos;
    private ImageView imageView;

    public EntertainmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entertainment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.topPicsViewPager);
        topPicsAdapter tpa = new topPicsAdapter(this.getContext());
        viewPager.setAdapter(tpa);
        WormDotsIndicator wormDotsIndicator = view.findViewById(R.id.dots_indicator);
        wormDotsIndicator.setViewPager(viewPager);

        ViewPager2 viewPager2 = view.findViewById(R.id.quizViewPager);
        viewPager2.setUserInputEnabled(false);
        QuizPagerAdapter quizPagerAdapter = new QuizPagerAdapter();
        viewPager2.setAdapter(quizPagerAdapter);

        imageView = view.findViewById(R.id.quiz_next_button);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1, true);
            }
        });
    }
}