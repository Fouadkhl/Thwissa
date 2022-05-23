package com.example.thwissa.fragment.Profile.setting;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.thwissa.R;

import java.util.Calendar;

public class EditProfile extends Fragment {

    private DatePickerDialog.OnDateSetListener setListener;
    private Calendar cal = Calendar.getInstance();
    private ImageView backButton;
    private EditText dateOfBirth;

    public EditProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        dateOfBirth = view.findViewById(R.id.date_of_birth);
        backButton = view.findViewById(R.id.back_button);
        initDateOfBirth();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBackButton();
    }

    public void initDateOfBirth(){
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), AlertDialog.THEME_HOLO_LIGHT,
                        setListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)
                        +"/"+cal.get(Calendar.DAY_OF_MONTH);
                dateOfBirth.setText(date);
            }
        };
    }
    public void initBackButton(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });
    }

}