package com.example.thwissa.fragment.newsfragment.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.thwissa.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ReplyFragment extends Fragment {

    private ExtendedFloatingActionButton replyButton;
    private NavController navController;
    private EditText replyContentEditText;
    private FloatingActionButton replyAddPic;
    private ImageView choosedImage;
    public static final int requestCode = 1;
    public static final int permissionCode = 2;
    private String imageCode = "";
    private FloatingActionButton deletePic;
    private RelativeLayout c;

    public ReplyFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reply_, container, false);
        replyContentEditText = view.findViewById(R.id.replyContentEditText);
        replyAddPic = view.findViewById(R.id.replyAddPic);
        deletePic = view.findViewById(R.id.cancelButton);
        c = view.findViewById(R.id.container);
        choosedImage = view.findViewById(R.id.choosedImage);
        //automatically click edit text
        replyContentEditText.post(() -> {
            replyContentEditText.requestFocusFromTouch();
            InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(replyContentEditText, 0);
        });
        initReplyAddPic();
        initDeletePic();
        return view;
    }

    private void initDeletePic() {
        deletePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageCode = "";
                c.setVisibility(View.GONE);
            }
        });
    }

    private void initReplyAddPic() {
        replyAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED
                    ) {
                        pickImagesIntent();
                    }
                    else {
                        requsetPermission(permissionCode);
                    }
                }
                else {
                    pickImagesIntent();
                }
            }
        });
    }

    private void requsetPermission(int permission_Code) {
        ActivityCompat.requestPermissions(
                requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                permission_Code
        );
    }

    private void pickImagesIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ReplyFragment.requestCode){
            if(resultCode == Activity.RESULT_OK){
                if(data != null) {
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver()
                                , uri
                        );
                        imageCode = bitmapToString(bitmap);
                        choosedImage.setImageBitmap(bitmap);
                        c.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String bitmapToString(@NonNull Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == permissionCode && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            pickImagesIntent();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //nav controller
        navController = Navigation.findNavController(view);
        //cancel button
        FloatingActionButton cancelButton = view.findViewById(R.id.replyCancelPic);
        cancelButton.setOnClickListener(view1 -> navController.popBackStack());
        //reply button
        replyButton = view.findViewById(R.id.replyButton);
        initReplyButton();
        //
    }

    private void initReplyButton() {
        replyButton.setOnClickListener(view12 -> {
            //share the reply
            navController.popBackStack();
        });
    }
}