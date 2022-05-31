package com.example.thwissa.fragment.homefragment.willaya;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thwissa.databinding.FragmentPlacesBinding;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * create an instance of this fragment.
 */
public class PlacesFragment extends Fragment {
    private FragmentPlacesBinding binding;
    private PlaceAdapter mPlacesAdapter;
    private TopRatedAdapter mTopRatedAdapter;


    // Categories: Touristic:12000,16000     Entertainment: 10000,14000,18000       Commercial: 11000,17000         Food: 13000       Hotels: 19000        Health:15000
    // 14 FIELDS: fsq_id,name,location,categories,distance,description,tel,website,rating,stats,popularity,price,photos,tips
    private HashMap<String, ArrayList<Place>> totalPlaces;
    private final String url = "https://api.foursquare.com/v3/places/search";
    private final String appId = "fsq3+HescpVWi499F8Qtda+huTh/ANyfkuVk3imyuhX9lbM=";
    // Search Parameters
    private final String fields = "fsq_id%2Cname%2Clocation%2Ccategories%2Cdistance%2Cdescription%2Ctel%2Cwebsite%2Crating%2Cstats%2Cpopularity%2Cprice%2Cphotos%2Ctips";
    private final int placesNumberLimit = 50;   // max number of places;
    private String sort = "RATING";

    private String currentCategory = "12000,16000";   // by default: touristic category

    public PlacesFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlacesBinding.inflate(inflater, container, false);

        /** INITALIZE PLACES LISTS */
        // Category / Places List
        totalPlaces = new HashMap<String, ArrayList<Place>>(){{
            put("12000,16000", new ArrayList<Place>());
            put("10000,14000,18000", new ArrayList<Place>());
            put("11000,17000", new ArrayList<Place>());
            put("13000", new ArrayList<Place>());
            put("19000", new ArrayList<Place>());
            put("15000", new ArrayList<Place>());
        }};
        initPlaces();
        /** CATEGORY BAR CLICK */
        initCategoryBar();
        /** SET CHECKED POSITION ON SWITCH BUTTON TO PLACES */
        //binding.mapSwitchButton.setCheckedPosition(1);

        return binding.getRoot();
    }

    private void initCategoryBar(){
        binding.categoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    default:
                        currentCategory = "12000,16000";
                        break;
                    case 1:
                        currentCategory = "10000,14000,18000";
                        break;
                    case 2:
                        currentCategory = "11000,17000";
                        break;
                    case 3:
                        currentCategory = "13000";
                        break;
                }
                setPlacesAdapter(totalPlaces.get(currentCategory));                    // PLACES
                setTopRatedAdapter(totalPlaces.get(currentCategory));                  // TOP RATED
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void setPlacesAdapter(ArrayList<Place> currentPlaceList){
        mPlacesAdapter = new PlaceAdapter(getContext(), currentPlaceList);
        binding.placesRecyclerView.setAdapter(mPlacesAdapter);
    }

    private void setTopRatedAdapter(ArrayList<Place> currentTopRatedList){
        mTopRatedAdapter = new TopRatedAdapter(getContext(), currentTopRatedList);
        binding.topRatedRecyclerView.setAdapter(mTopRatedAdapter);
    }



    public void initPlaces() {
        //////////////////////////////////////////////////// CHANGE IT LATER
        String wilayaName = "algiers";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        for (String category: totalPlaces.keySet()) {
            ArrayList<Place> currentPlacesList = totalPlaces.get(category);
            String placeUrl = url + "?categories=" + category + "&fields=" + fields + "&near=" + wilayaName + "&sort=" + sort + "&limit=" + placesNumberLimit;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, placeUrl, new Response.Listener<String>() {
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
                            // Place Fsq Id
                            currentPlace.fsqId = placeJsonObject.getString("fsq_id");
                            // Place Name
                            currentPlace.placeName = placeJsonObject.getString("name");
                            // Place Address
                            try {
                                currentPlace.placeAddress = placeJsonObject.getJSONObject("location").getString("formatted_address");
                            } catch (JSONException e){
                                currentPlace.placeAddress = wilayaName;
                            }
                            // Category
                            currentPlace.placeCategory = placeJsonObject.getJSONArray("categories").getJSONObject(0).getString("name");
                            // Distance
                            currentPlace.placeDistance = placeJsonObject.getInt("distance");
                            // Description
                            try {
                                currentPlace.placeDescription = placeJsonObject.getString("description");
                            }catch (JSONException e){
                                currentPlace.placeDescription = "No Description";
                            }
                            // Phone Number
                            try{
                                currentPlace.placePhoneNumber = placeJsonObject.getString("tel");
                            }catch (JSONException e){
                                currentPlace.placePhoneNumber = "No Phone Number";
                            }
                            // Website
                            try{
                                currentPlace.placeWebsite = placeJsonObject.getString("website");
                            }catch (JSONException e){
                                currentPlace.placeWebsite = "No Website";
                            }
                            // Rating
                            try {
                                currentPlace.placeRate = placeJsonObject.getDouble("rating");
                            }catch (JSONException e){
                                currentPlace.placeRate = 0;
                            }
                            // Stats
                            try {
                                JSONObject statJsonObject = placeJsonObject.getJSONObject("stats");
                                currentPlace.placeStats = new Place.Stats(statJsonObject.getInt("total_photos"), statJsonObject.getInt("total_ratings"), statJsonObject.getInt("total_tips"));
                            }catch (JSONException e){
                                currentPlace.placeStats = new Place.Stats(0, 0, 0);
                            }
                            // Popularity
                            currentPlace.placePopularity = placeJsonObject.getDouble("popularity");
                            // Price
                            try {
                                switch (placeJsonObject.getInt("price")){
                                    case 1:
                                        currentPlace.placePrice = "Cheap";
                                        break;
                                    case 2:
                                        currentPlace.placePrice = "Moderate";
                                        break;
                                    case 3:
                                        currentPlace.placePrice = "Expensive";
                                        break;
                                    case 4:
                                        currentPlace.placePrice = "Very Expensive";
                                        break;
                                }
                            }catch (JSONException e){
                                currentPlace.placePrice = "No Price";
                            }
                            // Pictures
                            try {
                                JSONArray picturesJsonArray = placeJsonObject.getJSONArray("photos");
                                for (int j = 0; j < picturesJsonArray.length(); j++) {
                                    JSONObject pictureJsonObject = picturesJsonArray.getJSONObject(j);
                                    String pictureUrl = pictureJsonObject.getString("prefix") + pictureJsonObject.getString("width") + "x" + pictureJsonObject.getString("height") + pictureJsonObject.getString("suffix");
                                    currentPlace.placeImagesUrls.add(pictureUrl);
                                }
                            }catch (JSONException ignored){}
                            // Tips
                            try {
                                JSONArray tipsJsonArray = placeJsonObject.getJSONArray("tips");
                                for (int j = 0; j < tipsJsonArray.length(); j++) {
                                    JSONObject tipJsonObject = tipsJsonArray.getJSONObject(j);
                                    currentPlace.placeTips.add(new Place.Tip(tipJsonObject.getString("text"), tipJsonObject.getString("created_at")));
                                }
                            }catch (JSONException ignored){}

                            currentPlacesList.add(currentPlace);
                        }
                    }
                    catch (JSONException ignored){}

                    /** INITIALISE */
                    setPlacesAdapter(totalPlaces.get(currentCategory));           // PLACES
                    setTopRatedAdapter(totalPlaces.get(currentCategory));         // TOP RATED
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Accept", "application/json");
                    params.put("Authorization", appId);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}