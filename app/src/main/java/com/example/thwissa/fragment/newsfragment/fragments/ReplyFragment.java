package com.example.thwissa.fragment.newsfragment.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.NewsUtil;
import com.example.thwissa.fragment.newsfragment.classes.Reply;
import com.example.thwissa.fragment.newsfragment.classes.Thereply;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class ReplyFragment extends Fragment {

    private ExtendedFloatingActionButton replyButton;
    private NavController navController;
    private EditText replyContentEditText;
    private FloatingActionButton replyAddPic;
    private ImageView choosedImage;
    public static final int requestCode = 1;
    public static final int permissionCode = 2;
    private FloatingActionButton deletePic;
    private RelativeLayout c;
    private Bundle args;
    private Uri uri;
    private String path;


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
        replyButton = view.findViewById(R.id.replyButton);
        //automatically click edit text
        replyContentEditText.post(() -> {
            replyContentEditText.requestFocusFromTouch();
            InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(replyContentEditText, 0);
        });
        initReplyAddPic();
        initDeletePic();
        args = requireArguments();
        if(args.getString("source").equals("postClicked")){
            replyButton.setText(NewsUtil.getString(getContext(), R.string.save));
            getReply(args.getString("postId"), args.getString("replyId"));
        }
        return view;
    }

    private void getReply(String postId, String replyId) {
        Call<Thereply> call = NewsUtil.getInstance().getNewsService().getReplyById(
                postId, replyId
        );
        call.enqueue(new Callback<Thereply>() {
            @Override
            public void onResponse(Call<Thereply> call, Response<Thereply> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null && response.body().reply!=null){
                        Reply reply = response.body().reply;
                        replyContentEditText.setText(reply.text);
                        if(reply.picture != null && reply.picture.equals("")) {
                            choosedImage.setImageBitmap(
                                    NewsUtil.stringToBitmap(reply.picture)
                            );
                            c.setVisibility(View.VISIBLE);
                        }
                        //TODO : set profile picture & user picture
                    }
                }
            }

            @Override
            public void onFailure(Call<Thereply> call, Throwable t) {

            }
        });
    }

    private void initDeletePic() {
        deletePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                path = null;
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

    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ReplyFragment.requestCode){
            if(resultCode == Activity.RESULT_OK){
                if(data != null) {
                    uri = data.getData();
                    choosedImage.setImageURI(uri);
                    c.setVisibility(View.VISIBLE);
                    Cursor cursor = requireContext().getContentResolver().query(
                            uri, null, null, null,null
                    );
                    if(cursor.moveToFirst()){
                        path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                    }
                    cursor.close();
                }
            }
        }
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
        initReplyButton();
    }

    private void initReplyButton() {
        replyButton.setOnClickListener(view12 -> {
            /*Reply reply = new Reply();
            reply.text = replyContentEditText.getText().toString();
            reply.picture = imageCode;*/

            RequestBody text = NewsUtil.createRequestFromString(replyContentEditText.getText().toString());

            HashMap<String, RequestBody> reply = new HashMap<>();
            reply.put("text", text);

            if(args.getString("source").equals("news")) {
                putReply(reply, NewsUtil.getFilePart("picture",path));
            } else if(args.getString("source").equals("postClicked")){
                updateReply();
            }
        });
    }

    private void putReply(HashMap<String, RequestBody> reply, MultipartBody.Part picture){
        Call<Thereply> call = NewsUtil.getInstance().getNewsService().postReply(
                args.getString("postId"), reply, picture
        );
        call.enqueue(new Callback<Thereply>() {
            @Override
            public void onResponse(Call<Thereply> call, Response<Thereply> response) {
                Bundle bundle = new Bundle();
                bundle.putString("source", "reply");
                bundle.putString("postId", args.getString("postId"));
                bundle.putInt("pos", args.getInt("pos"));
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("liveData", bundle);
                navController.popBackStack();
            }
            @Override
            public void onFailure(Call<Thereply> call, Throwable t) {
                Bundle bundle = new Bundle();
                bundle.putString("source", "reply");
                bundle.putString("postId", args.getString("postId"));
                bundle.putInt("pos", args.getInt("pos"));
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("liveData", bundle);
                navController.popBackStack();
            }
        });
    }

    public void updateReply(){
        HashMap<String, RequestBody> body = new HashMap<>();
        body.put("text", NewsUtil.createRequestFromString(replyContentEditText.getText().toString()));
        // the photo
        Call<Thereply> call = NewsUtil.getInstance().getNewsService().updateReply(
                args.getString("postId"), args.getString("replyId"),
                body, NewsUtil.getFilePart("picture", path)
        );
        call.enqueue(new Callback<Thereply>() {
            @Override
            public void onResponse(Call<Thereply> call, Response<Thereply> response) {
                Bundle bundle = new Bundle();
                bundle.putString("source", args.getString("_source"));
                bundle.putString("postId", args.getString("postId"));
                bundle.putInt("pos", args.getInt("pos"));
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("postLiveData", bundle);
                navController.popBackStack();
            }

            @Override
            public void onFailure(Call<Thereply> call, Throwable t) {
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