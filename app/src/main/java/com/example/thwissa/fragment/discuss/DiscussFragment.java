package com.example.thwissa.fragment.discuss;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thwissa.R;
import com.example.thwissa.databinding.FragmentDiscussBinding;

import java.util.ArrayList;

public class DiscussFragment extends Fragment {
    private FragmentDiscussBinding binding;
    private DiscussAdapter discussAdapter;

    public DiscussFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDiscussBinding.inflate(inflater, container, false);

        User testUser1 = new User("Yusuf Belkhiri", R.drawable.profile_b);
        User testUser2 = new User("Khelil Fouad", R.drawable.profile_b);
        String testDiscuss = "We want to organize a trip this weekend if you want to go dont hesitate to join us";
        ArrayList<Discuss> discussList = new ArrayList<>();
        discussList.add(new Discuss(testUser1, "Batna",  testDiscuss, R.drawable.profile_b, 30, 10, 8));
        discussList.add(new Discuss(testUser2, "Media",  testDiscuss, 20, 5, 10));
        discussList.add(new Discuss(testUser2, "Media",  testDiscuss, 20, 5, 10));
        discussList.add(new Discuss(testUser1, "Batna",  testDiscuss, R.drawable.profile_b, 10, 3, 2));

        discussAdapter = new DiscussAdapter(discussList);
        binding.discussRecyclerView.setAdapter(discussAdapter);

        return binding.getRoot();
    }
}