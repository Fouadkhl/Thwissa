package com.example.thwissa.fragment.homefragment.willaya;

import android.icu.number.NumberFormatter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
import com.example.thwissa.R;
import com.example.thwissa.databinding.FragmentPlacesBinding;
import com.example.thwissa.fragment.homefragment.overview.interfaces.OnPlaceClickedListener;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * create an instance of this fragment.
 */
public class PlacesFragment extends Fragment {
    private FragmentPlacesBinding binding;
    //private PlaceAdapter mPlacesAdapter;
    //private TopRatedAdapter mTopRatedAdapter;
    private HashMap<String, ArrayList<Place>> totalPlaces;
    private HashMap<String, ArrayList<Place>> totalTopRatedPlaces;
    private String currentCategory = "12000,16000";   // by default: touristic category

    // 14 FIELDS: fsq_id,name,location,categories,distance,description,tel,website,rating,stats,popularity,price,photos,tips,geocodes
    private final String appId = "fsq3+HescpVWi499F8Qtda+huTh/ANyfkuVk3imyuhX9lbM=";


    private NavController navController;


    public PlacesFragment() { }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        String wilayaName = getArguments().getString("wilaya_name");
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
        totalTopRatedPlaces = new HashMap<String, ArrayList<Place>>(){{
            put("12000,16000", new ArrayList<Place>());
            put("10000,14000,18000", new ArrayList<Place>());
            put("11000,17000", new ArrayList<Place>());
            put("13000", new ArrayList<Place>());
            put("19000", new ArrayList<Place>());
            put("15000", new ArrayList<Place>());
        }};

        initPlaces(wilayaName, "POPULARITY", totalPlaces);
        initPlaces(wilayaName, "RATING", totalTopRatedPlaces);
        /** CATEGORY BAR CLICK */
        initCategoryBar();
        /** SET CHECKED POSITION ON SWITCH BUTTON TO PLACES */
        //binding.mapSwitchButton.setCheckedPosition(1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlacesBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

    private void initCategoryBar(){
        binding.categoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    default:
                        currentCategory = "12000,16000";        // Touristic
                        break;
                    case 1:
                        currentCategory = "10000,14000,18000";      // Entertainment
                        break;
                    case 2:
                        currentCategory = "11000,17000";            //Commercial
                        break;
                    case 3:
                        currentCategory = "15000";                  // Health
                        break;
                    case 4:
                        currentCategory = "13000";                  // Food
                        break;
                    case 5:
                        currentCategory = "19000";                  // Hotels
                        break;
                }
                setPlacesAdapter(totalPlaces.get(currentCategory));                    // PLACES
                setTopRatedAdapter(totalTopRatedPlaces.get(currentCategory));                  // TOP RATED
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    /** ADAPTRES */
    private void setPlacesAdapter(ArrayList<Place> currentPlaceList){
        PlaceAdapter mPlacesAdapter = new PlaceAdapter(getContext(), currentPlaceList);

        mPlacesAdapter.setOnPlaceClickedListener(new OnPlaceClickedListener() {
            @Override
            public void placeClicked(Place place) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("place_object", place);
                navController.navigate(R.id.action_placesFragment_to_overview, bundle);
            }
        });

        binding.placesRecyclerView.setAdapter(mPlacesAdapter);
    }

    private void setTopRatedAdapter(ArrayList<Place> currentTopRatedList){
        TopRatedAdapter mTopRatedAdapter = new TopRatedAdapter(getContext(), currentTopRatedList);

        mTopRatedAdapter.setOnPlaceClickedListener(new OnPlaceClickedListener() {
            @Override
            public void placeClicked(Place place) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("place_object", place);
                navController.navigate(R.id.action_placesFragment_to_overview, bundle);
            }
        });
        binding.topRatedRecyclerView.setAdapter(mTopRatedAdapter);
    }



    public void initPlaces(String wilayaName, String sort, Map<String, ArrayList<Place>> placesCollection) {
        NumberFormat numberFormat = new DecimalFormat("#0.0");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String fields = "fsq_id%2Cname%2Clocation%2Ccategories%2Cdistance%2Cdescription%2Ctel%2Cwebsite%2Crating%2Cstats%2Cpopularity%2Cprice%2Cphotos%2Ctips%2Cgeocodes";

        for (String category: placesCollection.keySet()) {
            ArrayList<Place> currentPlacesList = placesCollection.get(category);
            // Search Parameters
            String placeUrl = "https://api.foursquare.com/v3/places/search?categories=" + category + "&fields=" + fields + "&near=" + wilayaName + "&sort=" + sort + "&limit=50";
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
                            // Place Address (Location)
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
                                currentPlace.placeRate = Double.parseDouble(numberFormat.format(placeJsonObject.getDouble("rating") / 2));
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
                            currentPlace.placePopularity = Double.parseDouble(numberFormat.format(10 * placeJsonObject.getDouble("popularity")));
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
                            // GEOCODES (latitude, longitude)
                            currentPlace.latitude = placeJsonObject.getJSONObject("geocodes").getJSONObject("main").getDouble("latitude");
                            currentPlace.longitude = placeJsonObject.getJSONObject("geocodes").getJSONObject("main").getDouble("longitude");


                            currentPlacesList.add(currentPlace);
                        }
                    }
                    catch (JSONException ignored){}

                    /** INITIALISE */
                    if(placesCollection == totalPlaces)
                        setPlacesAdapter(placesCollection.get(currentCategory));           // PLACES
                    else
                        setTopRatedAdapter(placesCollection.get(currentCategory));         // TOP RATED
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