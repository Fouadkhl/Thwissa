package com.example.thwissa.fragment.homefragment.overview.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.thwissa.R;
import com.example.thwissa.fragment.homefragment.overview.adapters.Adapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class Overview extends Fragment {

    public Overview() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager);
        DotsIndicator dotsIndicator = view.findViewById(R.id.dotsIndicator);

        Adapter adapter = new Adapter(getContext());
        viewPager2.setAdapter(adapter);
        dotsIndicator.setViewPager2(viewPager2);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        LinearLayout restaurants = view.findViewById(R.id.restaurants);
        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_overview_to_restaurantsFragment);
            }
        });

        LinearLayout hotels = view.findViewById(R.id.hotels);
        hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_overview_to_hotelsFragment2);
            }
        });

        LinearLayout reviews = view.findViewById(R.id.Reviews);
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_overview_to_reviewsFragment2);
            }
        });

        LinearLayout articles = view.findViewById(R.id.articles);
        articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_overview_to_articleFragment2);
            }
        });

        ExtendedFloatingActionButton buttonMap = view.findViewById(R.id.mapButton);
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_overview_to_mapFragment2);
            }
        });
    }
}