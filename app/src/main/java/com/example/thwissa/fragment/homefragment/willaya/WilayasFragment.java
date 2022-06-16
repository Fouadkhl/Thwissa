package com.example.thwissa.fragment.homefragment.willaya;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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


import com.example.thwissa.LogService;
import com.example.thwissa.R;
import com.example.thwissa.databinding.FragmentWilayasBinding;
import com.example.thwissa.dataclasses.Willaya;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * create an instance of this fragment.
 */

public class WilayasFragment extends Fragment {

    private static final String TAG="WilayasFragment";

    private FragmentWilayasBinding binding;
    private WilayaAdapter mAdapter ;
    ArrayList<Willaya> wilayasList = new ArrayList<>();


    public WilayasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWilayasBinding.inflate(inflater, container, false);

        int  willyaid  = getArguments().getInt("one") ;

        if (willyaid ==  1) {
            requestCoastal() ;
        }else if(willyaid == 2 ) {
            requestHills();
        }else {
            requestDesert();
        }

        /** WILAYAS LIST INITIALISATION (Change it later)*/




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


    public void requestCoastal(){
        Call<List<Willaya>> logService = LogService.INSTANCE.getRetrofitService().getWillayaCoastal();
        logService.enqueue(new Callback<List<Willaya>>() {
            @Override
            public void onResponse(Call<List<Willaya>> call, Response<List<Willaya>> response) {

                if (response.isSuccessful()){
                    Log.d(TAG, "success: "+response.message());
                    setupResponse(response.body()) ;
                }else {
                    Log.d(TAG, "fail: "+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Willaya>> call, Throwable t) {
                Log.d(TAG, "failure: "+t.getMessage());

            }
        });
    }

    public void requestHills(){
        Call<List<Willaya>> logService = LogService.INSTANCE.getRetrofitService().getWillayaHills();
        logService.enqueue(new Callback<List<Willaya>>() {
            @Override
            public void onResponse(Call<List<Willaya>> call, Response<List<Willaya>> response) {

                if (response.isSuccessful()){
                    Log.d(TAG, "success: "+response.message());
                    setupResponse(response.body()) ;
                }else {
                    Log.d(TAG, "fail: "+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Willaya>> call, Throwable t) {
                Log.d(TAG, "failure: "+t.getMessage());

            }
        });
    }

    public void requestDesert(){
        Call<List<Willaya>> logService = LogService.INSTANCE.getRetrofitService().getWillayaDesert();
        logService.enqueue(new Callback<List<Willaya>>() {
            @Override
            public void onResponse(Call<List<Willaya>> call, Response<List<Willaya>> response) {

                if (response.isSuccessful()){
                    Log.d(TAG, "success: "+response.message());
                    setupResponse(response.body()) ;
                }else {
                    Log.d(TAG, "fail: "+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Willaya>> call, Throwable t) {
                Log.d(TAG, "failure: "+t.getMessage());

            }
        });
    }

    private void setupResponse(List<Willaya> body) {
        if (body == null) {
            mAdapter = new WilayaAdapter(wilayasList, getContext());
        }else{
            mAdapter = new WilayaAdapter((ArrayList)body, getContext());
        }

        binding.wilayasRecyclerView.setHasFixedSize(true);   // just for better performances (change it later for any issue)
        binding.wilayasRecyclerView.setAdapter(mAdapter);
    }


}