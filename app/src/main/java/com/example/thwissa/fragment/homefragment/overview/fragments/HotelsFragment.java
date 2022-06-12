package com.example.thwissa.fragment.homefragment.overview.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HotelsFragment extends RestaurantsFragment {
    /** FIELDS TO PASS USING SAFE ARGS */
    private String placeName = "Santa Cruz";
    private String placeAddress = "Oran";     // in case: restaurant doesn't contain an ad, put the place's ad
    private double latitude = 35.727767;
    private double longitude = -0.586837;

    //private GoogleMap mMap;

    public HotelsFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        titleText = "Hotels Near " + placeName;
        category = 19000;
        return super.onCreateView(inflater, container, savedInstanceState);

    }
    /**
    private void setUpMap() {
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        if(mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {
                mMap = googleMap;

                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            });
        }
    } */
}