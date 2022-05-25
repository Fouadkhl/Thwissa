package com.example.thwissa.fragment.homefragment.willaya;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thwissa.R;
import com.example.thwissa.databinding.FragmentPlacesBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * create an instance of this fragment.
 */
public class PlacesFragment extends Fragment {
    private FragmentPlacesBinding binding;
    private PlaceAdapter mPlacesAdapter;
    private TopRatedAdapter mTopRatedAdapter;

    private ArrayList<Place> touristicPlacesList;
    private ArrayList<Place> entertainmentPlacesList;
    private ArrayList<Place> commercialPlacesList;
    private ArrayList<Place> historicPlacesList;

    public PlacesFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlacesBinding.inflate(inflater, container, false);

        /** INITALIZE PLACES LISTS */
        initPlacesLists();
        // PLACES
        mPlacesAdapter = new PlaceAdapter(touristicPlacesList);
        binding.placesRecyclerView.setHasFixedSize(true);
        binding.placesRecyclerView.setAdapter(mPlacesAdapter);
        // TOP RATED
        mTopRatedAdapter = new TopRatedAdapter(touristicPlacesList);
        binding.topRatedRecyclerView.setHasFixedSize(true);
        binding.topRatedRecyclerView.setAdapter(mTopRatedAdapter);

        /** CATEGORY BAR CLICK */
        binding.categoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ArrayList<Place> currentPlaceList = new ArrayList<>();
                ArrayList<Place> currentTopRatedList = new ArrayList<>();

                switch (tab.getPosition()){
                    case 0:
                        currentPlaceList = touristicPlacesList;
                        currentTopRatedList = touristicPlacesList;
                        break;
                    case 1:
                        currentPlaceList = entertainmentPlacesList;
                        currentTopRatedList = entertainmentPlacesList;
                        break;
                    case 2:
                        currentPlaceList = commercialPlacesList;
                        currentTopRatedList = commercialPlacesList;
                        break;
                    case 3:
                        currentPlaceList = historicPlacesList;
                        currentTopRatedList = historicPlacesList;
                        break;
                }
                // PLACES
                mPlacesAdapter = new PlaceAdapter(currentPlaceList);
                binding.topRatedRecyclerView.setHasFixedSize(true);
                binding.placesRecyclerView.setAdapter(mPlacesAdapter);
                // TOP RATED
                mTopRatedAdapter = new TopRatedAdapter(currentTopRatedList);
                binding.topRatedRecyclerView.setHasFixedSize(true);
                binding.topRatedRecyclerView.setAdapter(mTopRatedAdapter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });


        /** SET CHECKED POSITION ON SWITCH BUTTON TO PLACES */
        //binding.mapSwitchButton.setCheckedPosition(1);

        return binding.getRoot();
    }


    private void initPlacesLists(){
        /** TOURISTIC PLACES */
        String summary = "Address: Bd de L'A.L.N, Oran\nHours: Open 24 hours \nPromenade lined with palm trees and restaurants above a busy highway.";
        touristicPlacesList = new ArrayList<>();
        touristicPlacesList.add(new Place(R.drawable._3, "Paradise Beach", 3.0f, summary));
        touristicPlacesList.add(new Place(R.drawable.beach, "Bridges", 2.9f, summary));
        touristicPlacesList.add(new Place(R.drawable._3, "Makam Chahid", 4.8f, summary));
        touristicPlacesList.add(new Place(R.drawable.beach, "Timgad", 3.8f, summary));
        touristicPlacesList.add(new Place(R.drawable._3, "Houggar", 1.8f, summary));
        touristicPlacesList.add(new Place(R.drawable.beach, "Santa Cruz", 4.2f, summary));

        /** ENTERTAINMENT PLACES */
        entertainmentPlacesList = new ArrayList<>();
        entertainmentPlacesList.add(new Place(R.drawable.beach, "Park Mall", 4.8f, summary));
        entertainmentPlacesList.add(new Place(R.drawable._3, "Santa Cruz", 4.2f, summary));
        entertainmentPlacesList.add(new Place(R.drawable.beach, "Paradise Beach", 3.0f, summary));

        /** COMMERCIAL PLACES */
        commercialPlacesList = new ArrayList<>();
        commercialPlacesList.add(new Place(R.drawable._3, "Park Mall", 4.8f, summary));
        commercialPlacesList.add(new Place(R.drawable.beach, "Bridges", 2.9f, summary));
        commercialPlacesList.add(new Place(R.drawable._3, "Timgad", 3.8f, summary));

        /** HISTORIC PLACES */
        historicPlacesList = new ArrayList<>();
        historicPlacesList.add(new Place(R.drawable.beach, "Makam Chahid", 4.8f, summary));
        historicPlacesList.add(new Place(R.drawable._3, "Houggar", 1.8f, summary));
        historicPlacesList.add(new Place(R.drawable.beach, "Timgad", 3.8f, summary));
    }
}