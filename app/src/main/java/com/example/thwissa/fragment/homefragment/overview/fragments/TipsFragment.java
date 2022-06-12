package com.example.thwissa.fragment.homefragment.overview.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thwissa.databinding.FragmentTipsBinding;
import com.example.thwissa.R;
import com.example.thwissa.fragment.homefragment.overview.adapters.TipsAdapter;
import com.example.thwissa.fragment.homefragment.willaya.Place;

import java.util.ArrayList;


public class TipsFragment extends Fragment {
    private FragmentTipsBinding binding;

    public TipsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTipsBinding.inflate(inflater, container, false);
        setLayout();
        return binding.getRoot();
    }

    private void setLayout() {
        Place place = (Place) getArguments().getSerializable("place_object");
        binding.reviewsRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        TipsAdapter tipsAdapter = new TipsAdapter(place.placeTips);
        binding.reviewsRecycleView.setAdapter(tipsAdapter);

        binding.placeRatingBar.setRating((float) place.placeRate);
        binding.placeRateText.setText(String.valueOf(place.placeRate));
        binding.placeReviewsNumber.setText(place.placeStats.totalTips + " Reviews");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true ) {
            @Override
            public void handleOnBackPressed() {
                navController.popBackStack();
            }
        });
    }
}