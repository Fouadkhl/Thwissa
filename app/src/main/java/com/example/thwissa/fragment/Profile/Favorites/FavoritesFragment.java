package com.example.thwissa.fragment.Profile.Favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.thwissa.R;
import com.example.thwissa.databinding.FragmentFavoritesBinding;
import com.example.thwissa.fragment.discuss.Discuss;
import com.example.thwissa.fragment.discuss.DiscussAdapter;
import com.example.thwissa.fragment.discuss.User;

import java.util.ArrayList;

/**
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;

    ArrayList<User> users = new ArrayList<>();
    ArrayList<Discuss> discussList = new ArrayList<>();
    private DiscussAdapter discussAdapter;


    public FavoritesFragment() { }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);


        // ADD BACK BUTTON TO TOOLBAR
        binding.toolbar.setNavigationIcon(R.drawable.back_icon);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_favoritesFragment_to_profileFragment);
            }
        });

        // DISCUSS RECYCLER VIEW
        String testDiscuss = "We want to organize a trip this weekend if you want to go dont hesitate to join us";
        users.add(new User("Yusuf Belkhiri", R.drawable.profile_b));
        users.add(new User("Khelil Fouad", R.drawable.profile_b));
        discussList.add(new Discuss(users.get(1), "Media",  testDiscuss, 20, 5, 10));
        discussList.add(new Discuss(users.get(0), "Batna",  testDiscuss, R.drawable.profile_b, 30, 10, 8));
        discussAdapter = new DiscussAdapter(discussList);
        binding.favoritesRecyclerView.setAdapter(discussAdapter);

        return binding.getRoot();
    }
}