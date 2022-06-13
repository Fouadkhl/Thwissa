package com.example.thwissa.fragment.homefragment.willaya;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.thwissa.R;
import com.example.thwissa.databinding.FragmentWilayasBinding;

import java.util.ArrayList;

/**
 * create an instance of this fragment.
 */
public class WilayasFragment extends Fragment {
    private FragmentWilayasBinding binding;
    private WilayaAdapter mAdapter ;

    public WilayasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWilayasBinding.inflate(inflater, container, false);

        /** WILAYAS LIST INITIALISATION (Change it later)*/
        ArrayList<Wilaya> wilayasList = new ArrayList<>();
        wilayasList.add(new Wilaya(R.drawable._3, "Algiers"));
        wilayasList.add(new Wilaya(R.drawable.beach, "Constantine"));
        wilayasList.add(new Wilaya(R.drawable._3, "Setif"));
        wilayasList.add(new Wilaya(R.drawable.beach, "Batna"));
        wilayasList.add(new Wilaya(R.drawable.beach, "Touggourt"));
        wilayasList.add(new Wilaya(R.drawable._3, "Bejaia"));
        wilayasList.add(new Wilaya(R.drawable.beach, "Oran"));
        wilayasList.add(new Wilaya(R.drawable._4, "Batna"));
        wilayasList.add(new Wilaya(R.drawable.beach, "Algiers"));
        wilayasList.add(new Wilaya(R.drawable._3, "Blida"));
        wilayasList.add(new Wilaya(R.drawable.beach, "Touggourt"));
        wilayasList.add(new Wilaya(R.drawable.beach, "Bejaia"));
        wilayasList.add(new Wilaya(R.drawable.beach, "Oran"));

        mAdapter = new WilayaAdapter(wilayasList);
        binding.wilayasRecyclerView.setHasFixedSize(true);   // just for better performances (change it later for any issue)
        binding.wilayasRecyclerView.setAdapter(mAdapter);

        return binding.getRoot();
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