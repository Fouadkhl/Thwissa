package com.example.thwissa.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapQuery {
//    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
//
//    {
//        @Override
//        public boolean onQueryTextSubmit (String query){
//
//        return false;
//    }
//    }

    public static void getMap(Context MapsActivity, String query, GoogleMap mMap){
        //search
        Geocoder geocoder = new Geocoder(MapsActivity);
        List<Address> results = new ArrayList<>();
        try {
            results = geocoder.getFromLocationName(query, 1);

            if(results != null && results.size() >= 1){
                Address address = results.get(0);
                if(mMap != null) {
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng).title(query);

                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
