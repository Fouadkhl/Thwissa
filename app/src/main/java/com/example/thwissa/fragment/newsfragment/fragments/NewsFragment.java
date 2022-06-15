package com.example.thwissa.fragment.newsfragment.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.NewsUtil;
import com.example.thwissa.fragment.newsfragment.adapters.MeteoViewPagerAdapter;
import com.example.thwissa.fragment.newsfragment.adapters.PostsAdapter;
import com.example.thwissa.fragment.newsfragment.adapters.TripsAdapter;
import com.example.thwissa.fragment.newsfragment.classes.Meteo;
import com.example.thwissa.fragment.newsfragment.classes.Post;
import com.example.thwissa.fragment.newsfragment.classes.Posts;
import com.example.thwissa.fragment.newsfragment.classes.Trip;
import com.example.thwissa.fragment.newsfragment.classes.mPost;
import com.example.thwissa.fragment.newsfragment.interfaces.OnItemClickedListener;
import com.example.thwissa.fragment.newsfragment.interfaces.OnReplyButtonClicked;
import com.example.thwissa.repository.userLocalStore.SPUserData;
import com.example.thwissa.utils.Constants;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;


public class NewsFragment extends Fragment {

    private ViewPager2 viewPager2;    // meteo view pager
    private MeteoViewPagerAdapter meteoViewPagerAdapter;
    private final ArrayList<Trip> data = new ArrayList<>();
    private final ArrayList<mPost> data1 = new ArrayList<>();
    private View view = null;
    private PostsAdapter postsAdapter;
    private RecyclerView postsRecycleView;
    private ExtendedFloatingActionButton composeButton;
    private NavController navController;
    private SearchView searchView;
    private TextView hint;
    private RecyclerView topRatedTripsRecycleView;
    private TripsAdapter trips_adapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ShimmerFrameLayout shimmerFrameLayout1;
    private SwipeRefreshLayout refreshLayout;
    private AppCompatButton filtersearchview;

    private ArrayList<Meteo> meteos;

    public NewsFragment() {
        // Required empty public constructor
    }

    /*
    * TODO liked,dislikes & bookmarks & pictures in adapters*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //compose
        navController = Navigation.findNavController(view);
        composeButton = view.findViewById(R.id.composeButton);
        initComposeButton();
        postsRecycleView.setAdapter(postsAdapter);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout1.startShimmer();
        initTopRatedTripsRecycleView();
        initPostsRecycleView();
        refreshLayout = view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTopTrips();
                getAllTrips();
            }
        });

        if(navController.getCurrentBackStackEntry() != null) {
            navController.getCurrentBackStackEntry().getSavedStateHandle().getLiveData("liveData")
                    .observe(getViewLifecycleOwner(),
                            new Observer<Object>() {
                                @Override
                                public void onChanged(Object o) {
                                    if(o instanceof Bundle){
                                        Bundle bundle = (Bundle) o;
                                        String source = bundle.getString("source");
                                        if(source != null){
                                            switch (source) {
                                                case "news-posts":
                                                case "reply": {
                                                    int pos = bundle.getInt("pos");
                                                    String postId = bundle.getString("postId");
                                                    updatePostById(postId, pos);
                                                    Log.e("reply", "reply");
                                                    break;
                                                }
                                                case "news-trips":
                                                case "postClicked":{
                                                    int pos = bundle.getInt("pos");
                                                    String postId = bundle.getString("postId");
                                                    updateTripById(postId, pos);
                                                    break;
                                                }
                                                case "composeFragment":
                                                    boolean success = bundle.getBoolean("success", false);
                                                    if (success) {
                                                        addPostedTrip(bundle.getString("postId"));
                                                    }
                                                    break;
                                            }
                                        }
                                        bundle.clear();
                                    }
                                }
                            }
                    );
        }
    }

    private void updateTripById(String postId, int pos) {
        Call<Post> call = NewsUtil.getInstance().getNewsService().getTripById(postId);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, retrofit2.Response<Post> response) {
                if(response.isSuccessful() && response.body()!=null && response.body().data!=null){
                    data.set(pos, new Trip(response.body().data));
                    trips_adapter.notifyItemChanged(pos);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void addPostedTrip(String postID) {
        Call<Post> call = NewsUtil.getInstance().getNewsService().getTripById(
                postID
        );
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, retrofit2.Response<Post> response) {
                if(response.isSuccessful()
                && response.body()!=null && response.body().data!=null){
                    data1.add(response.body().data);
                    postsAdapter.notifyItemInserted(postsAdapter.getItemCount()-1);
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_news, container, false);
            //search view
            hint = view.findViewById(R.id.hint);
            searchView = view.findViewById(R.id.search_bar);
            initSearchView();
            //meteo view pager
            viewPager2 = view.findViewById(R.id.meteoViewPager);
            initMeteoViewPager();
            //recycle view
            topRatedTripsRecycleView = view.findViewById(R.id.topRatedTripsRecycleView);
            //posts recycle view
            postsRecycleView = view.findViewById(R.id.postsRecycleView);
            initPostsRecycleView();
            shimmerFrameLayout = view.findViewById(R.id.shimmer);
            shimmerFrameLayout1 = view.findViewById(R.id.shimmer_trips);
            FrameLayout root = view.findViewById(R.id.root);
            filtersearchview = view.findViewById(R.id.filter_search_news) ;
        }


        filtersearchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerLayout = requireActivity().findViewById(R.id.filter_drawer_layout) ;
                drawerLayout.openDrawer(GravityCompat.START, true) ;
            }
        });

        return view;
    }

    /*public String getCurrentDate(){
        Calendar cal = Calendar.getInstance();
        return requireActivity().getResources().getStringArray(R.array.days)[Calendar.DAY_OF_MONTH-1]
        +" "+requireActivity().getResources().getStringArray(R.array.days)[Calendar.MONTH]+" "+
                cal.get(Calendar.YEAR);
    }*/

    private void initPostsRecycleView() {
        postsRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        postsAdapter = new PostsAdapter(getContext());
        initPostAdapter();
        postsRecycleView.setAdapter(postsAdapter);
        //get posts from data base
        getAllTrips();
    }

    public void initPostAdapter(){
        postsAdapter.setData(data1);
        postsAdapter.setOnReplyIconClicked(new OnReplyButtonClicked() {
            @Override
            public void replyButtonClicked(String postId, int position) {
                //TODO if user logged in
                Bundle bundle = new Bundle();
                bundle.putString("source", "news");
                bundle.putString("postId", postId);
                bundle.putInt("pos", position);
                navController.navigate(R.id.action_newsFragment_to_replyFragment, bundle);
            }
        });
        postsAdapter.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public void ItemClicked(String postId, int position) {
                //pass post data
                Bundle bundle = new Bundle();
                bundle.putString("source", "news-posts");
                bundle.putString("postId", postId);
                bundle.putInt("pos", position);
                navController.navigate(R.id.action_newsFragment_to_postClickedFragment, bundle);
            }
        });
    }

    private void initTopRatedTripsRecycleView() {
        topRatedTripsRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        trips_adapter = new TripsAdapter(getContext());
        trips_adapter.setData(data);
        topRatedTripsRecycleView.setAdapter(trips_adapter);
        trips_adapter.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public void ItemClicked(String postId, int pos) {
                Bundle bundle = new Bundle();
                bundle.putString("source", "news-trips");
                bundle.putString("postId", postId);
                bundle.putInt("pos", pos);
                navController.navigate(R.id.action_newsFragment_to_postClickedFragment, bundle);
            }
        });
        getTopTrips();
    }

    private void initMeteoViewPager() {
        // SET METEOS LIST
        meteos = new ArrayList<>(Arrays.asList(new Meteo("Algiers"), new Meteo("Oran"), new Meteo("Setif")));
        for (Meteo meteo : meteos) {
            getWilayaWeather(meteo, getContext());
        }
        meteoViewPagerAdapter = new MeteoViewPagerAdapter(getContext(), meteos);
        viewPager2.setAdapter(meteoViewPagerAdapter);
        // viewPager2.setUserInputEnabled(false);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                viewPager2.setCurrentItem(
                        nextItem(), true
                );
            }
        }, new Date(System.currentTimeMillis()+5000), 5000);
    }


    public int nextItem(){
        if(viewPager2.getCurrentItem() < meteoViewPagerAdapter.getItemCount()-1){
            return  viewPager2.getCurrentItem()+1;
        }
        else {
            return 0;
        }
    }
    public void initComposeButton(){
        SPUserData spuserdata = new SPUserData(requireContext());
        composeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spuserdata.getUserLoggedIn()){
                    if(spuserdata.getLoggedInUser().getRole().equals("user")) return; //TODO show a messgae
                        Bundle bundle = new Bundle();
                        bundle.putString("source", "compose");
                        navController.navigate(R.id.action_newsFragment_to_composeFragment, bundle);
                }else{
                    navController.navigate(R.id.action_newsFragment_to_registrationTypeFragment);
                }
            }
        });
    }

    public void initSearchView(){
        searchView.setQueryHint("search here");
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hint.setVisibility(View.GONE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                hint.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }



    public void getWilayaWeather(Meteo meteo, Context context){
        /** Get Url using wilaya name */
        // OPEN WEATHER MAP API
        final String url = Constants.WEATHER_URL;
        final String appId = "f21603913cc37defa5563046a448ce36";
        String tempUrl = url + "?q=" + meteo.wilayaName + "&appid=" + appId;
        /** Create Request Using the Url*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    // Temp
                    JSONObject jsonobjectMain = jsonResponse.getJSONObject("main");
                    meteo.temp = (int) Math.round(jsonobjectMain.getDouble("temp")-273.15);
                    // Description (weather state)
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    meteo.weatherState= jsonObjectWeather.getString("description");
                    // ICON
                    String iconId = jsonObjectWeather.getString("icon");
                    meteo.weatherStateIconUrl = "https://openweathermap.org/img/w/" + iconId + ".png";
                } catch(JSONException e){
                    e.printStackTrace();
                }
                meteoViewPagerAdapter = new MeteoViewPagerAdapter(getContext(), meteos);
                viewPager2.setAdapter(meteoViewPagerAdapter);
                //viewPager2.setUserInputEnabled(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}                   // ERROR
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //TODO userinfos & picture
    public void getAllTrips(){
        Call<Posts> call = NewsUtil.getInstance().getNewsService().getAllTrips();
        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, retrofit2.Response<Posts> response) {
                if(response.isSuccessful()){
                    Posts posts = response.body();
                    if(posts != null && posts.trips != null){
                        data1.clear();
                        postsAdapter.notifyDataSetChanged();
                        Collections.shuffle(posts.trips);
                        //int oldSize = data1.size();
                        data1.addAll(posts.trips.subList(0, Math.min(5, posts.trips.size())));
                        postsAdapter.notifyItemRangeInserted(0, postsAdapter.getItemCount()-1);

                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        postsRecycleView.setVisibility(View.VISIBLE);
                    }
                }
                refreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Log.e("all trips : ", "trips");
                // showSnackBar();
            }
        });
    }

    //TODO userinfos & picture & sort
    public void getTopTrips(){
        Call<Posts> call = NewsUtil.getInstance().getNewsService().getAllTrips();
        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, retrofit2.Response<Posts> response) {
                if(response.isSuccessful()){
                    Posts posts = response.body();
                    if(posts != null && posts.trips != null){
                        Collections.sort(posts.trips);
                        data.clear();
                        for(int i = 0;i < Math.min(10, posts.trips.size());i++){
                            data.add(new Trip(posts.trips.get(i)));
                        }
                        trips_adapter.notifyItemRangeInserted(0, trips_adapter.getItemCount()-1);

                        shimmerFrameLayout1.stopShimmer();
                        shimmerFrameLayout1.setVisibility(View.GONE);
                        topRatedTripsRecycleView.setVisibility(View.VISIBLE);
                    }
                }
                refreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                // showSnackBar();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    public void updatePostById(String postId, int pos){
        Call<Post> call = NewsUtil.getInstance().getNewsService().getTripById(postId);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, retrofit2.Response<Post> response) {
                if(response.isSuccessful()){
                    Post p = response.body();
                    if(p!=null){
                        data1.set(pos, p.data);
                        postsAdapter.notifyItemChanged(pos);
                    }
                }
                else if(response.errorBody()!=null) {
                    Log.e("update : ","not updated");
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        // This Message means that the post doesn't exist anymore
                        if(jObjError.getString("msg").equals("no trip with id :".concat(postId))){
                            data1.remove(pos);
                            postsAdapter.notifyItemRemoved(pos);
                        }
                    } catch (Exception e) {
                        Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
            }
        });
    }

//    private  void showSnackBar(){
//        Snackbar.make(root, Util.getString(requireContext(), R.string.failedToLoadData), BaseTransientBottomBar.LENGTH_INDEFINITE)
//                .setAction(R.string.Retry, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(data1 == null || data1.size() == 0)
//                            getAllTrips();
//                        if(data == null || data.size() == 0)
//                            getTopTrips();
//                    }
//                })
//                .setActionTextColor(requireContext().getResources().getColor(R.color.teal_700))
//                .setTextColor(requireContext().getResources().getColor(R.color.white))
//                .show();
//    }
}