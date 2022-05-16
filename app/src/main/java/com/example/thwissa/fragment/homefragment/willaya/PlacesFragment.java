package com.example.thwissa.fragment.homefragment.willaya;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thwissa.R;
import com.example.thwissa.databinding.FragmentPlacesBinding;

import java.util.ArrayList;

/**
 * create an instance of this fragment.
 */
public class PlacesFragment extends Fragment {
    private FragmentPlacesBinding binding;
    private PlaceAdapter mPlacesAdapter;
    private TopRatedAdapter mTopRatedAdapter;

    public PlacesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlacesBinding.inflate(inflater, container, false);

        /** PLACES
        /** FAKE DATA BASE (Change it Later) */
        String summary = "Address: Bd de L'A.L.N, Oran\nHours: Open 24 hours \nPromenade lined with palm trees and restaurants above a busy highway, with views out to the water.";
        ArrayList<Place> placesList = new ArrayList<>();
        placesList.add(new Place(R.drawable._3, "Makam Chahid", 4.8f, summary));
        placesList.add(new Place(R.drawable._3, "Timgad", 3.8f, summary));
        placesList.add(new Place(R.drawable.beach, "Bridges", 2.9f, summary));
        placesList.add(new Place(R.drawable.beach, "Park Mall", 4.8f, summary));
        placesList.add(new Place(R.drawable._3, "Houggar", 1.8f, summary));
        placesList.add(new Place(R.drawable._3, "Plage Paradis", 3.0f, summary));
        placesList.add(new Place(R.drawable.beach, "Santa Cruz", 4.2f, summary));

        mPlacesAdapter = new PlaceAdapter(placesList);
        binding.placesRecyclerView.setHasFixedSize(true);
        binding.placesRecyclerView.setAdapter(mPlacesAdapter);


        /** TOP RATED   */
        mTopRatedAdapter = new TopRatedAdapter(placesList);
        binding.topRatedRecyclerView.setHasFixedSize(true);
        binding.topRatedRecyclerView.setAdapter(mTopRatedAdapter);


        return binding.getRoot();
    }
}