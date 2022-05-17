package com.example.thwissa.fragment.discuss;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.thwissa.R;
import com.example.thwissa.databinding.FragmentDiscussBinding;

import java.util.ArrayList;

public class DiscussFragment extends Fragment {
    private FragmentDiscussBinding binding;

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Discuss> discussList = new ArrayList<>();
    private DiscussAdapter discussAdapter;

    public DiscussFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDiscussBinding.inflate(inflater, container, false);

        // COMPOSE BUTTON LISTENER
        binding.composeButton.setOnClickListener(v -> composeDiscuss());

        // DISCUSS RECYCLER VIEW
        String testDiscuss = "We want to organize a trip this weekend if you want to go dont hesitate to join us";
        users.add(new User("Yusuf Belkhiri", R.drawable.profile_b));
        users.add(new User("Khelil Fouad", R.drawable.profile_b));
        discussList.add(new Discuss(users.get(0), "Batna",  testDiscuss, R.drawable.profile_b, 30, 10, 8));
        discussList.add(new Discuss(users.get(1), "Media",  testDiscuss, 20, 5, 10));
        discussList.add(new Discuss(users.get(1), "Media",  testDiscuss, 20, 5, 10));
        discussList.add(new Discuss(users.get(0), "Batna",  testDiscuss, R.drawable.profile_b, 10, 3, 2));
        discussAdapter = new DiscussAdapter(discussList);
        binding.discussRecyclerView.setAdapter(discussAdapter);

        return binding.getRoot();
    }



    /** COMPOSE BUTTON LISTENER */
    private  void composeDiscuss(){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.compose_discuss_dialog);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        /** USER INFOS + WILAYA    (CHANGE IT LATER)*/
        TextView userName = dialog.findViewById(R.id.user_name);
        ImageView profilePic = dialog.findViewById(R.id.profile_pic);
        TextView wilaya = dialog.findViewById(R.id.wilaya);

        userName.setText(users.get(1).getUserName());
        profilePic.setImageResource(users.get(1).getProfilePicResource());
        wilaya.setText("Oran");

        /** BUTTONS */
        EditText et_post = dialog.findViewById(R.id.et_post);
        AppCompatButton bt_submit = dialog.findViewById(R.id.bt_submit);
        ImageButton bt_photo = dialog.findViewById(R.id.bt_photo);
        ImageButton bt_location = dialog.findViewById(R.id.bt_location);
        ImageButton bt_setting = dialog.findViewById(R.id.bt_setting);

        bt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "PHOTO", Toast.LENGTH_SHORT).show();
            }
        });

        bt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "LOCATION", Toast.LENGTH_SHORT).show();
            }
        });

        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "MENU", Toast.LENGTH_SHORT).show();
            }
        });

        et_post.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bt_submit.setEnabled(et_post.length() > 0);

                bt_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /** ADD DISCUSS HERE */
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
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}