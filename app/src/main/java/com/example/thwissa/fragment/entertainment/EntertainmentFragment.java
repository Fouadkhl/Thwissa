package com.example.thwissa.fragment.entertainment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thwissa.R;
import com.example.thwissa.dataclasses.UserRes;
import com.example.thwissa.repository.userLocalStore.SPUserData;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntertainmentFragment extends Fragment {

    private ImageView next;
    private ImageView user_profile_image;
    private TextView user_name;
    private TextView score;
    private ViewPager topPicsViewPager;
    private ViewPager2 quizViewPager;
    private WormDotsIndicator dotsIndicator;

    public EntertainmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entertainment, container, false);
        user_profile_image = view.findViewById(R.id.user_profile_image);
        user_name = view.findViewById(R.id.user_name);
        score = view.findViewById(R.id.score);
        topPicsViewPager = view.findViewById(R.id.topPicsViewPager);
        quizViewPager = view.findViewById(R.id.quizViewPager);
        quizViewPager.setUserInputEnabled(false);
        dotsIndicator = view.findViewById(R.id.dots_indicator);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserRes userRes = new SPUserData(requireContext()).getLoggedInUser();
        user_name.setText(userRes.getName());
        //TODO : profile picture
        setTopPics();

        next = view.findViewById(R.id.quiz_next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizViewPager.setCurrentItem(quizViewPager.getCurrentItem()+1, true);
            }
        });
    }

    public void setTopPics(){
        Call<TopPicsRes> call = EntertainmentUtil.getInstance().getService().getTopPicsRes();
        call.enqueue(new Callback<TopPicsRes>() {
            @Override
            public void onResponse(Call<TopPicsRes> call, Response<TopPicsRes> response) {
                if(response.isSuccessful() && response.body()!=null){
                    // Top Pictures
                    TopPicsAdapter adapter = new TopPicsAdapter(requireContext());
                    adapter.setData(response.body().pictures);
                    topPicsViewPager.setAdapter(adapter);
                    dotsIndicator.setViewPager(topPicsViewPager);

                    // quiz
                    QuizPagerAdapter quizPagerAdapter = new QuizPagerAdapter(quizViewPager);
                    quizPagerAdapter.setQuizzes(response.body().toInnerQuizList());
                    quizViewPager.setAdapter(quizPagerAdapter);
                }
            }
            @Override
            public void onFailure(Call<TopPicsRes> call, Throwable t) {

            }
        });
    }
}