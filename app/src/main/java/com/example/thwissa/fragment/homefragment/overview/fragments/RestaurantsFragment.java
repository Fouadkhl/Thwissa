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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thwissa.R;
import com.example.thwissa.fragment.homefragment.overview.adapters.RestaurentsAdapter;
import com.example.thwissa.fragment.homefragment.willaya.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestaurantsFragment extends Fragment {
    private Place place;
    protected String titleText;
    protected int category = 13000;
    private GoogleMap mMap;
    private RecyclerView recyclerView;
    public RestaurantsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurents_hotels, container, false);
        place = (Place) getArguments().getSerializable("place_object");
        setUpMap();
        // Set up Title
        titleText = "Restaurants Near " + place.placeName;
        TextView title = view.findViewById(R.id.Title);
        title.setText(titleText);
        // Set up Recycler View
        recyclerView = view.findViewById(R.id.RestaurantsHotelsRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        /** JUST TO KEEP INITIAL ANIMATION WHILE CONNECTING */
        ArrayList<Place> initRestaurants = new ArrayList<>();
        Place place = new Place();
        place.placeName = "Empty";
        place.placeAddress = "Empty";
        place.placeCategory = "Empty";
        place.placeRate = 0;
        place.placeImagesUrls.add("https://fastly.4sqi.net/img/general/612x612/47143914_jKkDkIEZ8GuobEAu_HZMpHhNgatb44AwA3CnyuTTiyc.jpg");
        initRestaurants.add(place);
        initRestaurants.add(place);
        initRestaurants.add(place);
        RestaurentsAdapter restaurentsAdapter = new RestaurentsAdapter(getContext(), initRestaurants, R.layout.restaurent);
        recyclerView.setAdapter(restaurentsAdapter);

        initPlacesList(category);

        return view;
    }

    private void setPlacesAdapter(ArrayList<Place> placesList){
        RestaurentsAdapter restaurentsAdapter = new RestaurentsAdapter(getContext(), placesList, R.layout.restaurent);
        recyclerView.setAdapter(restaurentsAdapter);
    }

    private void initPlacesList(int category) {
        // Fields: name,location,categories,rating,photos
        String url = "https://api.foursquare.com/v3/places/search?ll=" + place.latitude + "%2C" + place.longitude + "&fields=fsq_id%2Cname%2Clocation%2Ccategories%2Crating%2Cphotos&categories=" + category +"&sort=RATING";
        ArrayList<Place> placesList = new ArrayList<>();
        NumberFormat numberFormat = new DecimalFormat("#0.0");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray resultArray = jsonResponse.getJSONArray("results");
                    if(resultArray.length() == 0)   // case: empty list
                        return;
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject placeJsonObject = resultArray.getJSONObject(i);
                        Place currentPlace = new Place();
                        // Place Name
                        currentPlace.placeName = placeJsonObject.getString("name");
                        // Place Address (Location)
                        try {
                            currentPlace.placeAddress = placeJsonObject.getJSONObject("location").getString("formatted_address");
                        } catch (JSONException e){
                            currentPlace.placeAddress = place.placeAddress;
                        }
                        // Category
                        currentPlace.placeCategory = placeJsonObject.getJSONArray("categories").getJSONObject(0).getString("name");
                        // Rating
                        try {
                            currentPlace.placeRate = Double.parseDouble(numberFormat.format(placeJsonObject.getDouble("rating") / 2));
                        }catch (JSONException e){
                            currentPlace.placeRate = 0;
                        }
                        // Pictures (only 1 picture needed)
                        try {
                            JSONObject pictureJsonObject = placeJsonObject.getJSONArray("photos").getJSONObject(0);
                            String pictureUrl = pictureJsonObject.getString("prefix") + pictureJsonObject.getString("width") + "x" + pictureJsonObject.getString("height") + pictureJsonObject.getString("suffix");
                            currentPlace.placeImagesUrls.add(pictureUrl);
                        }catch (JSONException ignored){}

                        placesList.add(currentPlace);
                    }
                }catch (JSONException ignored){}

                /** INITIALISE */
                setPlacesAdapter(placesList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final String appId = "fsq3+HescpVWi499F8Qtda+huTh/ANyfkuVk3imyuhX9lbM=";
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", appId);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void setUpMap() {
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        });
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