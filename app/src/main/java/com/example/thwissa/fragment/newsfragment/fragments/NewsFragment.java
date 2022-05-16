package com.example.thwissa.fragment.newsfragment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.adapters.MeteoViewPagerAdapter;
import com.example.thwissa.fragment.newsfragment.adapters.PostsAdapter;
import com.example.thwissa.fragment.newsfragment.adapters.RV_Adapter;
import com.example.thwissa.fragment.newsfragment.classes.Trip;
import com.example.thwissa.fragment.newsfragment.classes.mPost;
import com.example.thwissa.fragment.newsfragment.interfaces.OnItemClickedListener;
import com.example.thwissa.fragment.newsfragment.interfaces.OnReplyButtonClicked;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class NewsFragment extends Fragment {

    private Timer timer;
    private ViewPager2 viewPager2;
    private MeteoViewPagerAdapter meteoViewPagerAdapter;
    private ArrayList<Trip> data = new ArrayList<>();
    private ArrayList<mPost> data1 = new ArrayList<>();
    private View view = null;
    private PostsAdapter postsAdapter;
    private RecyclerView postsRecycleView;
    private ExtendedFloatingActionButton composeButton;
    private NavController navController;
    private SearchView searchView;
    private TextView hint;
    private RecyclerView topRatedTripsRecycleView;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //compose
        navController = Navigation.findNavController(view);
        composeButton = view.findViewById(R.id.composeButton);
        initComposeButton();
        initPostAdapter();
        postsRecycleView.setAdapter(postsAdapter);
        if(navController.getCurrentBackStackEntry() != null) {
            // final Bundle bundle;
            navController.getCurrentBackStackEntry().getSavedStateHandle().getLiveData("composeFragment")
                    .observe(getViewLifecycleOwner(),
                            new Observer<Object>() {
                                @Override
                                public void onChanged(Object o) {
                                    if(o instanceof Bundle){
                                        // Bundle bundle = (Bundle) o;
                                        if(((Bundle) o).getString("source") != null &&
                                                ((Bundle) o).getString("source").equals("composeFragment"))
                                        {
                                            String destination = ((Bundle) o).getString("destination");
                                            String date = ((Bundle) o).getString("date");
                                            String period = ((Bundle) o).getString("period");
                                            String price = ((Bundle) o).getString("price");
                                            String description = ((Bundle) o).getString("description");
                                            ArrayList<String> arrayBitmaps = ((Bundle) o).getStringArrayList("imagesBitmaps");
                                            String[] imagesBitmaps = new String[arrayBitmaps.size()];
                                            imagesBitmaps = arrayBitmaps.toArray(imagesBitmaps);
                                            Toast.makeText(getContext(), price, Toast.LENGTH_SHORT).show();
                                            mPost post = new mPost(
                                                    getCurrentDate(), period, Integer.parseInt(price), "", destination, 0, imagesBitmaps[0],
                                                    "name", "location", description, imagesBitmaps, 0, 0,
                                                    date, null
                                            );
                                            //post
                                            data1.add(post);
                                            postsAdapter.notifyItemInserted(postsAdapter.getItemCount() - 1);
                                            ((Bundle) o).clear();
                                        }
                                    }
                                }
                            }
                    );
        }
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
            initTopRatedTripsRecycleView();
            //posts recycle view
            postsRecycleView = view.findViewById(R.id.postsRecycleView);
            initPostsRecycleView();
        }
        return view;
    }

    public String getCurrentDate(){
        Calendar cal = Calendar.getInstance();
        return requireActivity().getResources().getStringArray(R.array.days)[Calendar.DAY_OF_MONTH-1]
        +" "+requireActivity().getResources().getStringArray(R.array.days)[Calendar.MONTH]+" "+
                cal.get(Calendar.YEAR);
    }

    private void initPostsRecycleView() {
        postsRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        postsAdapter = new PostsAdapter(getContext());
        //get posts from data base
        postsAdapter.setData(data1);
    }

    private void initTopRatedTripsRecycleView() {
        topRatedTripsRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        RV_Adapter rv_adapter = new RV_Adapter();
        data.add(
                new Trip(R.drawable.images_1,
                        "trip to Biskra limited djdhdjidhdnbbdbdbb"
                        , 4.8f)
        );
        data.add(
                new Trip(R.drawable.images_1,
                        "visit Tlemcen to be bdbddjjddjjd"
                        , 3.5f)
        );
        rv_adapter.setData(data);
        topRatedTripsRecycleView.setAdapter(rv_adapter);
    }

    private void initMeteoViewPager() {
        meteoViewPagerAdapter = new MeteoViewPagerAdapter(getContext());
        viewPager2.setAdapter(meteoViewPagerAdapter);
        viewPager2.setUserInputEnabled(false);
        timer = new Timer();
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
        composeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("source", "compose");
                navController.navigate(R.id.action_newsFragment_to_composeFragment, bundle);
            }
        });
    }
    public void initPostAdapter(){
        postsAdapter.setOnReplyIconClicked(new OnReplyButtonClicked() {
            @Override
            public void replyButtonClicked(String postID) {
                Bundle bundle = new Bundle();
                bundle.putString("postID", postID);
                navController.navigate(R.id.action_newsFragment_to_replyFragment, bundle);
            }
        });
        postsAdapter.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public void ItemClicked(String postID) {
                //pass post data
                Bundle bundle = new Bundle();
                bundle.putString("postID", postID);
                navController.navigate(R.id.action_newsFragment_to_postClickedFragment, bundle);
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
}