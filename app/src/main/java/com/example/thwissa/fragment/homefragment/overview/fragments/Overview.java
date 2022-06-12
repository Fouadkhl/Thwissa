package com.example.thwissa.fragment.homefragment.overview.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.thwissa.R;
import com.example.thwissa.databinding.ScroolLayoutBinding;
import com.example.thwissa.fragment.homefragment.overview.adapters.Adapter;
import com.example.thwissa.fragment.homefragment.willaya.Place;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.example.thwissa.databinding.FragmentOverviewBinding;

public class Overview extends Fragment {
    private FragmentOverviewBinding binding;
    private Place place;
    public Overview() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOverviewBinding.inflate(inflater, container, false);

        setLayout();

        return binding.getRoot();
    }

    private void setLayout() {
        // Safe Args
        place = (Place) getArguments().getSerializable("place_object");

        Adapter adapter = new Adapter(getContext(), place);
        binding.viewPager.setAdapter(adapter);
        binding.dotsIndicator.setViewPager2(binding.viewPager);

        binding.collapsingToolbar.setTitle(place.placeName);
        ScroolLayoutBinding scroolLayout= binding.scroolLayout;
        scroolLayout.placeCategory.setText(place.placeCategory);
        String rateText = place.placeRate + " (" + place.placeStats.totalTips + " Reviews)";
        scroolLayout.rateReviewsNumber.setText(rateText);
        scroolLayout.placeDescription.setText(place.placeDescription);
        scroolLayout.placeAddress.setText(place.placeAddress);
        scroolLayout.placePrice.setText(place.placePrice);
        scroolLayout.placePopularity.setText(place.placePopularity + "/10.0");
        scroolLayout.placeDistance.setText(place.placeDistance + "m");
        scroolLayout.placePhoneNumber.setText(place.placePhoneNumber);
        scroolLayout.placeWebSite.setText(place.placeWebsite);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        LinearLayout restaurants = view.findViewById(R.id.restaurants);
        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_overview_to_restaurantsFragment, getBundle());
            }
        });

        LinearLayout hotels = view.findViewById(R.id.hotels);
        hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_overview_to_hotelsFragment2, getBundle());
            }
        });

        LinearLayout reviews = view.findViewById(R.id.Reviews);
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_overview_to_reviewsFragment2, getBundle());
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
                Bundle bundle = new Bundle();
                bundle.putString("place_name", place.placeName);
                navController.navigate(R.id.action_overview_to_mapFragment2, bundle);
            }
        });
    }


    private Bundle getBundle(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("place_object", place);
        return bundle;
    }

}