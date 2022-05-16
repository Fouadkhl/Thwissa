package com.example.thwissa.fragment.Profile.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thwissa.R;
import com.google.android.material.imageview.ShapeableImageView;


public class SettingsFragment extends Fragment {

    RelativeLayout language;
    LinearLayout languages;
    ImageView backbtn;
    ShapeableImageView shapeableImageView;

    RelativeLayout privacy;
    LinearLayout privacyChoices;
    ShapeableImageView shapeableImageView2;
    TextView changePassword;
    TextView changeEmail;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        language = view.findViewById(R.id.language);
        languages = view.findViewById(R.id.languages);
        backbtn = view.findViewById(R.id.back_button);
        shapeableImageView = view.findViewById(R.id.languageArrow);

        privacy = view.findViewById(R.id.privacy);
        privacyChoices = view.findViewById(R.id.privacyChoices);
        shapeableImageView2 = view.findViewById(R.id.privacyArrow);
        changePassword = view.findViewById(R.id.changePassword);
        changeEmail = view.findViewById(R.id.changeEmail);

        initBackButton();
        initLanguages();
        initPrivacy();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_passwordFragment);
            }
        });
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_emailFragment);
            }
        });
    }

    public void initBackButton(){
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_profileFragment);
            }
        });
    }
    public void initLanguages(){
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(languages.getVisibility() == View.GONE){
                    languages.setVisibility(View.VISIBLE);
                    shapeableImageView.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
                else{
                    languages.setVisibility(View.GONE);
                    shapeableImageView.setImageResource(R.drawable.ic_baseline_navigate_next_24);
                }
            }
        });
    }
    public void initPrivacy(){
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(privacyChoices.getVisibility() == View.GONE){
                    privacyChoices.setVisibility(View.VISIBLE);
                    shapeableImageView2.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
                else {
                    privacyChoices.setVisibility(View.GONE);
                    shapeableImageView2.setImageResource(R.drawable.ic_baseline_navigate_next_24);
                }
            }
        });
    }
}