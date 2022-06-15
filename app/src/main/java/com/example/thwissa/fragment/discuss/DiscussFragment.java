package com.example.thwissa.fragment.discuss;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.thwissa.R;
import com.example.thwissa.databinding.FragmentDiscussBinding;
import com.example.thwissa.fragment.discuss.classes.Discuss;
import com.example.thwissa.fragment.discuss.classes.DiscussUtil;
import com.example.thwissa.fragment.discuss.classes.Discusses;
import com.example.thwissa.fragment.discuss.classes.ReceivedDiscuss;
import com.example.thwissa.fragment.newsfragment.NewsUtil;
import com.example.thwissa.repository.userLocalStore.SPUserData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class DiscussFragment extends Fragment {
    private FragmentDiscussBinding binding;

    private ArrayList<Discuss> discussList = new ArrayList<>();
    private DiscussAdapter discussAdapter;
    private SPUserData spUserData;
    private static final int permissionCode = 2;
    public static final int pickCode = 1;
    private ImageView choosedImage;
    private String path;


    public DiscussFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDiscussBinding.inflate(inflater, container, false);

        binding.composeButton.setOnClickListener(v -> composeDiscuss());
        binding.shimmer.startShimmer();

        spUserData = new SPUserData(requireContext());

        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllQuestions();
            }
        });

        binding.searchBarDiscuss.btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerLayout = requireActivity().findViewById(R.id.filter_drawer_layout) ;
                drawerLayout.openDrawer(GravityCompat.START, true) ;

            }
        });


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPostsRecycleView();
    }

    private void initPostsRecycleView() {
        discussAdapter = new DiscussAdapter(requireContext());
        discussAdapter.setData(discussList);
        binding.discussRecyclerView.setAdapter(discussAdapter);
        getAllQuestions();
    }

    private void composeDiscuss() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.compose_discuss_dialog);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView userName = dialog.findViewById(R.id.user_name);
        ImageView profilePic = dialog.findViewById(R.id.profile_pic);
        TextView wilaya = dialog.findViewById(R.id.wilaya);

//        userName.setText(spUserData.getLogInUser().getName());
        //profilePic.setImageResource(users.get(1).getProfilePicResource());
//        wilaya.setText(spUserData.getLogInUser().getLocation());

        EditText et_post = dialog.findViewById(R.id.et_post);
        AppCompatButton bt_submit = dialog.findViewById(R.id.bt_submit);
        ImageButton bt_photo = dialog.findViewById(R.id.bt_photo);
        // ImageButton bt_location = dialog.findViewById(R.id.bt_location);
        ImageButton bt_setting = dialog.findViewById(R.id.bt_setting);
        choosedImage = dialog.findViewById(R.id.choosedImage);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("id : ", spUserData.getLoggedInUser().getName());
                HashMap<String, RequestBody> body = new HashMap<>();
                RequestBody req = NewsUtil.createRequestFromString(et_post.getText().toString());
                body.put("text", req);
                MultipartBody.Part picture = NewsUtil.getFilePart("picture", path);
                postQuestion(body, picture);
                dialog.dismiss();
            }
        });
        bt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAddPicButton();
            }
        });
        /*et_post.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bt_submit.setEnabled(et_post.length() > 0);

                bt_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Discuss newDiscuss = new Discuss(users.get(1), wilaya.getText().toString(), et_post.getText().toString());
                        discussList.add(0, newDiscuss);
                        discussAdapter.notifyItemInserted(0);
                        dialog.cancel();
                    }
                });
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void initAddPicButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
            ) {
                pickImagesIntent();
            } else {
                requsetPermission(permissionCode);
            }
        } else {
            pickImagesIntent();
        }
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
        startActivityForResult(intent, pickCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImagesIntent();
        } else {
            Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pickCode) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri uri = data.getData();
                choosedImage.setImageURI(uri);
                choosedImage.setVisibility(View.VISIBLE);
                Cursor cursor = requireContext().getContentResolver().query(
                        uri, null, null, null, null
                );
                if (cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                }
                cursor.close();
            }
        }
    }


    public void getAllQuestions() {
        Call<Discusses> call = DiscussUtil.getInstance()
                .getJsonPlaceHolder()
                .getAllQuestions();
        call.enqueue(new Callback<Discusses>() {
            @Override
            public void onResponse(Call<Discusses> call, Response<Discusses> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //discussList.clear();
                    int oldSize = discussList.size();
                    discussList.removeAll(
                            discussList
                    );
                    discussAdapter.notifyItemRangeRemoved(0, oldSize);
                    List<Discuss> oldList = response.body().questions;

                    Collections.shuffle(oldList, new Random(System.currentTimeMillis()));
                    discussList.addAll(oldList.subList(0, Math.min(5, oldList.size())));
                    discussAdapter.notifyItemRangeInserted(0, discussAdapter.getItemCount() - 1);

                    binding.shimmer.stopShimmer();
                    binding.shimmer.setVisibility(View.GONE);
                    binding.discussRecyclerView.setVisibility(View.VISIBLE);
                }
                binding.swipe.setRefreshing(false);
                ;
            }

            @Override
            public void onFailure(Call<Discusses> call, Throwable t) {
                binding.swipe.setRefreshing(false);
                Toast.makeText(requireContext(), "failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postQuestion(HashMap<String, RequestBody> body, MultipartBody.Part picture) {
        Call<ReceivedDiscuss> call = DiscussUtil.getInstance()
                .getJsonPlaceHolder().postQuestion(body, picture);
        call.enqueue(new Callback<ReceivedDiscuss>() {
            @Override
            public void onResponse(Call<ReceivedDiscuss> call, Response<ReceivedDiscuss> response) {
                if (response.isSuccessful() && response.body() != null) {
                    discussList.add(response.body().newquestion);
                    discussAdapter.notifyItemInserted(discussAdapter.getItemCount() - 1);
                } else
                    Toast.makeText(requireContext(), "code : " + response.code() + " message :" + response.message() + " is null : " + (response.body() == null), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ReceivedDiscuss> call, Throwable t) {
                Toast.makeText(requireContext(), "failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}