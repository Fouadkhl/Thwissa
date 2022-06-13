package com.example.thwissa.fragment.newsfragment.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.NewsUtil;
import com.example.thwissa.fragment.newsfragment.adapters.ChoosedImagesAdapter;
import com.example.thwissa.fragment.newsfragment.classes.Post;
import com.example.thwissa.fragment.newsfragment.classes.mPost;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComposeFragment extends Fragment {

    private DatePickerDialog.OnDateSetListener setListener;
    private EditText editDestination;
    private FloatingActionButton replyCancel;
    private NavController navController;
    private ExtendedFloatingActionButton shareButton;
    private FloatingActionButton addPics;
    private EditText date;
    private RecyclerView imagesRecycleView;
    private ChoosedImagesAdapter choosedImagesAdapter;
    private static final int pickCode = 1;
    private static final int permissionCode = 2;
    private EditText editPeriod;
    private ArrayList<Uri> imagesUris = new ArrayList<>();
    private Spinner spinner;
    private EditText editPrice;
    private EditText description;
    private Bundle args;
    private ArrayList<String> paths;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compose, container, false);
        editDestination = view.findViewById(R.id.EditDestination);
        replyCancel = view.findViewById(R.id.replyCancelPic);
        shareButton = view.findViewById(R.id.shareButton);
        addPics = view.findViewById(R.id.replyAddPic);
        date = view.findViewById(R.id.EditDate);
        imagesRecycleView = view.findViewById(R.id.ImagesRecycleView);
        editPeriod = view.findViewById(R.id.EditPeriod);
        spinner = view.findViewById(R.id.spinner);
        editPrice = view.findViewById(R.id.EditPrice);
        description = view.findViewById(R.id.descriptionEditText);
        args = requireArguments();
        if(args.getString("source").equals("postClickedFragment")){
            shareButton.setText(R.string.save);
            getPostById(); //maybe needs to be moved to initChoosedImagesRecycleView
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        //edit text
        initEditDestination();
        //cancel button
        initCancelButton();
        //add photo
        initAddPicButton();
        //date
        initDateFeild();
        //Images recycle view
        initChoosedImagesRecycleView();
        //share button
        initShareButton();
    }

    private void initChoosedImagesRecycleView() {
        imagesRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        choosedImagesAdapter = new ChoosedImagesAdapter();
        choosedImagesAdapter.setImagesIds(imagesUris);
        imagesRecycleView.setAdapter(choosedImagesAdapter);
    }

    private void initDateFeild() {
        Calendar cal = Calendar.getInstance();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),AlertDialog.THEME_HOLO_LIGHT,
                        setListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String _date = datePicker.getDayOfMonth()+"/"+datePicker.getMonth()
                        +"/"+datePicker.getYear();
                date.setText(_date);
            }
        };
    }

    private void initAddPicButton() {
        addPics.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == permissionCode && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            pickImagesIntent();
        } else {
            Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
        }
    }

    private void initShareButton() {
        String source = args.getString("source");
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("compose")){
                    if(editDestination.getText().toString().equals("")){
                        showMessage(requireActivity().getResources().getString(R.string.destinationFieldEmpty));
                    } else if(date.getText().toString().equals("")){
                        showMessage(requireActivity().getResources().getString(R.string.dateFieldEmpty));
                    } else if(beforeCurrentDate(date.getText().toString())){
                        showMessage(requireActivity().getResources().getString(R.string.wrongDate));
                    } else if(editPeriod.getText().toString().equals("")){
                        showMessage(requireActivity().getResources().getString(R.string.periodFieldEmpty));
                    } else if(editPrice.getText().toString().equals("")){
                        showMessage(requireActivity().getResources().getString(R.string.priceFieldEmpty));
                    } else if(choosedImagesAdapter.getItemCount() == 0){
                        showMessage(requireActivity().getResources().getString(R.string.noImages));
                    }else{
                        HashMap<String, RequestBody> body = new HashMap<>();
                        body.put("destination", NewsUtil.createRequestFromString(editDestination.getText().toString()));
                        body.put("tripDate", NewsUtil.createRequestFromString(date.getText().toString().replace("/", "-")+"T00:00:00.000Z"));
                        body.put("duration", NewsUtil.createRequestFromString(editPeriod.getText().toString()));
                        body.put("price", NewsUtil.createRequestFromString(editPrice.getText().toString()));
                        // TODO image & userid
                        //postTrip(p);
                    }
                }
                else if(source.equals("postClickedFragment")){
                    //TODO image & userid
                    updatePost();
                }
            }
        });
    }

    //TODO userid & pic
    private void postTrip(HashMap<String, RequestBody> body, List<MultipartBody.Part> pictures) {
        Call<Post> call = NewsUtil.getInstance().getNewsService().postTrip(body, pictures);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null && response.body().data!=null){
                        Bundle bundle = new Bundle();
                        bundle.putString("source", "composeFragment");
                        bundle.putString("postId", response.body().data._id);
                        bundle.putBoolean("success", true);
                        navController.getPreviousBackStackEntry().getSavedStateHandle()
                                .set("liveDate", bundle);
                        navController.popBackStack();
                    }
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Bundle bundle = new Bundle();
                bundle.putString("source", "composeFragment");
                navController.getPreviousBackStackEntry().getSavedStateHandle()
                        .set("liveDate", bundle);
                navController.popBackStack();
            }
        });
    }


    private boolean beforeCurrentDate(String toString) {
        try {
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(toString);
            Date date2 = new Date();
            if (date1 != null) {
                return date1.before(date2);
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void showMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void initCancelButton() {

        replyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });
    }

    private void initEditDestination() {
        editDestination.post(new Runnable() {
            @Override
            public void run() {
                editDestination.requestFocusFromTouch();
                InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editDestination, 0);
            }
        });
    }

    private void pickImagesIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select image(s)"), pickCode);
    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == pickCode){
            if(resultCode == Activity.RESULT_OK&&data!=null){
                if(data.getClipData() != null){
                    int size = data.getClipData().getItemCount();
                    for(int i = 0;i < size;i++){
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imagesUris.add(
                                imageUri
                        );
                        Cursor cursor = requireContext().getContentResolver().query(
                                imageUri, null, null, null,null
                        );
                        if(cursor.moveToFirst()){
                            paths.add(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
                        }
                    }
                    choosedImagesAdapter.notifyItemRangeInserted(
                            choosedImagesAdapter.getItemCount() - size
                            ,size);
                } else {
                    Uri imageUri = data.getData();
                    imagesUris.add(
                            imageUri
                    );
                    Cursor cursor = requireContext().getContentResolver().query(
                            imageUri, null, null, null,null
                    );
                    if(cursor.moveToFirst()){
                        paths.add(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
                    }
                    choosedImagesAdapter.notifyItemInserted(choosedImagesAdapter.getItemCount()-1);
                }
            }
        }
    }

    public void getPostById(){
        Call<Post> call = NewsUtil.getInstance().getNewsService().getTripById(
                args.getString("postId")
        );
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null && response.body().data != null){
                        mPost p = response.body().data;
                        editDestination.setText(p.destination);
                        date.setText(p.tripDate.substring(0, 11).replace("-","/"));
                        editPeriod.setText(String.valueOf(p.maxduration));
                        editPrice.setText(String.valueOf(p.maxduration));
                        description.setText(p.text);
                        // set pictures
                    }
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public void updatePost(){
        HashMap<String, RequestBody> body = new HashMap<>();
        body.put("duration", NewsUtil.createRequestFromString(editPeriod.getText().toString()));
        body.put("price", NewsUtil.createRequestFromString(editPrice.getText().toString()));
        body.put("description", NewsUtil.createRequestFromString(description.getText().toString()));
        body.put("tripDate", NewsUtil.createRequestFromString(
                date.getText().toString().replace("/", "-")+"T00:00:00.000Z"
        ));
        //picture

        Call<Post> call = NewsUtil.getInstance().getNewsService().updateTrip(
                args.getString("postId"), body,NewsUtil.getFilesParts("pictures", paths)
        );
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Bundle bundle = new Bundle();
                bundle.putString("source", args.getString("_source"));
                bundle.putString("postId", args.getString("postId"));
                bundle.putInt("pos", args.getInt("pos"));
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("postLiveData", bundle);
                navController.popBackStack();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Bundle bundle = new Bundle();
                bundle.putString("source", args.getString("_source"));
                bundle.putString("postId", args.getString("postId"));
                bundle.putInt("pos", args.getInt("pos"));
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("postLiveData", bundle);
                navController.popBackStack();
            }
        });
    }
}