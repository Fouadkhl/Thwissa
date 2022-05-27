package com.example.thwissa.fragment.newsfragment.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.adapters.PostPictursViewPagerAdapter;
import com.example.thwissa.fragment.newsfragment.adapters.PostsAdapter;
import com.example.thwissa.fragment.newsfragment.classes.mPost;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PostClickedFragment extends Fragment {

    private View view;
    private Timer timer = new Timer();
    private RecyclerView repliesRecycleView;
    private PostsAdapter repliesAdapter;
    private FloatingActionButton moreButton;
    private ViewPager2 postPicsViewPager;
    private PostPictursViewPagerAdapter postPictursViewPagerAdapter;
    private FloatingActionButton backButton;
    private NavController navController;
    private ArrayList<Bitmap> arrayList = new ArrayList<>();
    private ArrayList<mPost> data = new ArrayList<>();//supposed to be replies !!
    private ShapeableImageView profilePicture;
    private TextView userName;
    private TextView location;
    private TextView time;
    private TextView postContent;
    private TextView diff;
    private TextView replyNum;
    private mPost post;

    public PostClickedFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_post_clicked, container, false);

            /*Bundle bundle = requireArguments();
            String id = bundle.getString("id");*/

            profilePicture = view.findViewById(R.id.post_profile_pic);
            userName = view.findViewById(R.id.post_user_name);
            location = view.findViewById(R.id.location);
            time = view.findViewById(R.id.time);
            postContent = view.findViewById(R.id.postContent);
            diff = view.findViewById(R.id.diff);
            replyNum = view.findViewById(R.id.reply_num);
            backButton = view.findViewById(R.id.backButton);

            // TextView textView = view.findViewById(R.id.diff);
            ImageView upButton = view.findViewById(R.id.up_button);
            ImageView downButton = view.findViewById(R.id.down_button);
            upButton.setOnClickListener(view -> {

            });
            downButton.setOnClickListener(view -> {

            });
            ImageView replyIcon  = view.findViewById(R.id.reply_icon);
            replyIcon.setOnClickListener(view -> {

            });
            //retrieve post data and set the content & image & profile ...
            //replies recycle view
            //reply is also a post
            repliesRecycleView = view.findViewById(R.id.repliesRecycleView);
            initRepliesRecycleView();
            //more button
            moreButton = view.findViewById(R.id.moreButton);

            //post pics view pager
            postPicsViewPager = view.findViewById(R.id.postPicViewPager);
            initPostPicsViewPager();

            Bundle bundle = requireArguments();
            String postID = bundle.getString("postID");
            // find post, post
            // set post's replies
            // set post's pictures & other infos
            bundle.clear();

        }
        return view;
    }

    private void initPostPicsViewPager() {
        postPictursViewPagerAdapter = new PostPictursViewPagerAdapter();

        postPictursViewPagerAdapter.setImgIds(arrayList);
        postPicsViewPager.setAdapter(postPictursViewPagerAdapter);
        WormDotsIndicator wormDotsIndicator = view.findViewById(R.id.dots_indicator);
        wormDotsIndicator.setViewPager2(postPicsViewPager);
    }

    private void initRepliesRecycleView() {
        repliesRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        repliesAdapter = new PostsAdapter(getContext());

        repliesAdapter.setData(data);
        repliesAdapter.setBookMarkVisibility(false);
        repliesRecycleView.setAdapter(repliesAdapter);
    }

    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);
        //init our layout elements
        RelativeLayout report = bottomSheetDialog.findViewById(R.id.report);
        RelativeLayout follow = bottomSheetDialog.findViewById(R.id.btn_follow);
        RelativeLayout bookmark = bottomSheetDialog.findViewById(R.id.bookMark);
        RelativeLayout edit = bottomSheetDialog.findViewById(R.id.edit);
        RelativeLayout delete = bottomSheetDialog.findViewById(R.id.delete);
        ImageView imageView = bottomSheetDialog.findViewById(R.id.bookMarkIcon);

        // compare post.getAgencyId() and choose what to show
        // compare post.isBookMarkClicked() and change imageView

        if (report != null) {
            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedEffect(report);
                }
            });
        }
        if (follow != null) {
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedEffect(follow);
                }
            });
        }
        if (bookmark != null) {
            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedEffect(bookmark);
                }
            });
        }
        if (edit != null) {
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedEffect(edit);
                    Bundle bundle = new Bundle();
                    bundle.putString("source", "postClickedFragment");
                    bundle.putString("postID", "post.getPostID()");
                    bottomSheetDialog.hide();
                    navController.navigate(R.id.action_postClickedFragment_to_composeFragment, bundle);
                }
            });
        }
        if (delete != null) {
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {clickedEffect(delete);}
            });
        }

        bottomSheetDialog.show();
    }
    
    private void clickedEffect(View view){
        view.setBackgroundColor(getContext().getResources().getColor(R.color.grey));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                view.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            }
        }, 100);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        initBackButton();
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
    }

    private void stringToBitmap(String[] picture, ArrayList<Bitmap> bitmaps) {
        for(String s: picture){
            byte[] decodedString = Base64.decode(s, Base64.DEFAULT);
            bitmaps.add(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
    }

    private void initBackButton() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });
    }
}