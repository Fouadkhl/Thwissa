package com.example.thwissa.fragment.newsfragment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.NewsService;
import com.example.thwissa.fragment.newsfragment.NewsUtil;
import com.example.thwissa.fragment.newsfragment.adapters.PostPictursViewPagerAdapter;
import com.example.thwissa.fragment.newsfragment.adapters.RepliesAdapter;
import com.example.thwissa.fragment.newsfragment.classes.Post;
import com.example.thwissa.fragment.newsfragment.classes.ReactionRes;
import com.example.thwissa.fragment.newsfragment.classes.Replies;
import com.example.thwissa.fragment.newsfragment.classes.Reply;
import com.example.thwissa.fragment.newsfragment.classes.Thereply;
import com.example.thwissa.fragment.newsfragment.classes.mPost;
import com.example.thwissa.repository.userLocalStore.SPUserData;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostClickedFragment extends Fragment {

    private View view;
    private Timer timer = new Timer();
    private RecyclerView repliesRecycleView;
    private RepliesAdapter repliesAdapter;
    private FloatingActionButton moreButton;
    private ViewPager2 postPicsViewPager;
    private PostPictursViewPagerAdapter postPictursViewPagerAdapter;
    private FloatingActionButton backButton;
    private NavController navController;
    private final ArrayList<String> arrayList = new ArrayList<>();
    private final ArrayList<Reply> data = new ArrayList<>();//supposed to be replies !!
    private ShapeableImageView profilePicture;
    private TextView userName;
    private TextView location;
    private TextView time;
    private TextView postContent;
    private TextView diff;
    private TextView replyNum;
    private Bundle args;
    private FrameLayout root;
    private mPost _post;
    private SPUserData userData;

    public PostClickedFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_post_clicked, container, false);

            args = requireArguments();

            profilePicture = view.findViewById(R.id.post_profile_pic);
            userName = view.findViewById(R.id.post_user_name);
            location = view.findViewById(R.id.location);
            time = view.findViewById(R.id.time);
            postContent = view.findViewById(R.id.postContent);
            diff = view.findViewById(R.id.diff);
            replyNum = view.findViewById(R.id.reply_num);
            backButton = view.findViewById(R.id.backButton);
            userData = new SPUserData(requireContext());

            // TextView textView = view.findViewById(R.id.diff);
            ImageView upButton = view.findViewById(R.id.up_button);
            ImageView downButton = view.findViewById(R.id.down_button);
            upButton.setOnClickListener(view -> {
                if(!_post.isLiked){
                    upButton.setImageResource(R.drawable.clicked_up_arrow);
                    _post.likeNum++;
                } else {
                    upButton.setImageResource(R.drawable.up_arrow_not_clicked);
                    _post.likeNum--;
                }
                _post.isLiked = !_post.isLiked;
                like(_post._id);
                if(_post.isDisliked){
                    downButton.setImageResource(R.drawable.down_arrow_not_clicked);
                    _post.isDisliked = false;
                    dislike(_post._id);
                }
                diff.setText(String.valueOf(_post.diff()));
            });
            downButton.setOnClickListener(view -> {
                if(!_post.isDisliked){
                    downButton.setImageResource(R.drawable.clicked_down_arrow);
                    _post.dislikeNum++;
                } else {
                    downButton.setImageResource(R.drawable.down_arrow_not_clicked);
                    _post.dislikeNum--;
                }
                _post.isDisliked = !_post.isDisliked;
                dislike(_post._id);
                if(_post.isLiked){
                    downButton.setImageResource(R.drawable.up_arrow_not_clicked);
                    _post.isLiked = false;
                    like(_post._id);
                }
                diff.setText(String.valueOf(_post.diff()));
            });
            //retrieve post data and set the content & image & profile ...
            //replies recycle view
            //reply is also a post
            postPicsViewPager = view.findViewById(R.id.postPicViewPager);
            repliesRecycleView = view.findViewById(R.id.repliesRecycleView);
            initRepliesRecycleView();
            //more button
            moreButton = view.findViewById(R.id.moreButton);

            //post pics view pager

            // initPostPicsViewPager();
            root = view.findViewById(R.id.root);
        }
        return view;
    }

    private void initPostPicsViewPager() {
        postPictursViewPagerAdapter = new PostPictursViewPagerAdapter(requireContext());
        postPictursViewPagerAdapter.setImgIds(arrayList);
        postPicsViewPager.setAdapter(postPictursViewPagerAdapter);
        WormDotsIndicator wormDotsIndicator = view.findViewById(R.id.dots_indicator);
        wormDotsIndicator.setViewPager2(postPicsViewPager);
    }
    private void initRepliesRecycleView() {
        repliesRecycleView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        repliesAdapter = new RepliesAdapter(getContext(), args.getString("postId"));
        repliesAdapter.setOnMoreButtonClicked(this::showRepliesSheet);
        getTripById();
    }
    public void showRepliesSheet(String replyId, int pos){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.reply_bottom_sheet);

        RelativeLayout edit = bottomSheetDialog.findViewById(R.id.edit);
        RelativeLayout delete = bottomSheetDialog.findViewById(R.id.delete);

        if(edit != null){
            edit.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("source", "postClicked");
                bundle.putString("_source", args.getString("source"));
                bundle.putString("replyId", replyId);
                bundle.putString("postId", args.getString("postId"));
                bottomSheetDialog.hide();
                navController.navigate(R.id.action_postClickedFragment_to_replyFragment, bundle);
            });
        }
        if (delete != null) {
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteReply(replyId, pos);
                    bottomSheetDialog.hide();
                }
            });
        }
        bottomSheetDialog.show();
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
            report.setOnClickListener(view -> clickedEffect(report));
        }
        if (follow != null) {
            follow.setOnClickListener(view -> clickedEffect(follow));
        }
        if (bookmark != null) {
            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedEffect(bookmark);
                    assert imageView != null;
                    if(_post.isBookmarked){
                        imageView.setImageResource(R.drawable.ic_baseline_bookmark_24);
                    } else imageView.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
                    bookmark(_post._id);
                }
            });
        }
        if (edit != null) {
            edit.setOnClickListener(view -> {
                //check the user id
                clickedEffect(edit);
                Bundle bundle = new Bundle();
                bundle.putString("source", "postClickedFragment");
                bundle.putString("postId", args.getString("postId"));
                bundle.putInt("pos", args.getInt("pos"));
                bundle.putInt("_source", args.getInt("source"));
                bottomSheetDialog.hide();
                navController.navigate(R.id.action_postClickedFragment_to_composeFragment, bundle);
            });
        }
        if (delete != null) delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedEffect(delete);
                deletePost();
                bottomSheetDialog.hide();
            }
        });

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
        
        if(navController.getCurrentBackStackEntry()!=null){
            navController.getCurrentBackStackEntry().getSavedStateHandle().getLiveData("postLiveData")
                    .observe(getViewLifecycleOwner(), new Observer<Object>() {
                        @Override
                        public void onChanged(Object o) {
                            if(o instanceof Bundle){
                                args = (Bundle) o;
                                getTripById();
                            }
                        }
                    });
        }
    }

    private void initBackButton() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("source", args.getString("source"));
                bundle.putString("postId", args.getString("postId"));
                bundle.putInt("pos", args.getInt("pos"));
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("liveData", bundle);
                navController.popBackStack();
            }
        });
    }

    public void getTripById(){
        String postId = args.getString("postId");
        Call<Post> call = NewsUtil.getInstance().getNewsService().getTripById(postId);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Post p = response.body();
                    if(p != null){
                        _post = p.data;
                        if(_post != null){
                            //
                            _post.likeNum = _post.likes.size();
                            _post.dislikeNum = _post.dislikes.size();
                            _post.isLiked = _post.likes.contains(new ReactionRes(userData.getLoggedInUser().getId()));
                            _post.isDisliked = _post.dislikes.contains(new ReactionRes(userData.getLoggedInUser().getId()));
                            // profilePicture & picture
                            userName.setText(_post.username);
                            location.setText(_post.userlocation);
                            time.setText(_post.tripDate.substring(0, 10).replace("-","/"));
                            String string = NewsUtil.getString(getContext(), R.string.destination).concat(" : "+_post.destination+"\n");
                            string += NewsUtil.getString(getContext(), R.string.date).concat(" : ").concat(
                                    _post.tripDate.substring(0, 10).replace("-","/")+"\n");
                            string += NewsUtil.getString(getContext(), R.string.period).concat(" : ").concat(_post.maxduration+"\n");
                            string += NewsUtil.getString(getContext(), R.string.price).concat(" : ").concat(_post.maxprice+"\n");
                            string += NewsUtil.getString(getContext(), R.string.description)+" : "+_post.text;

                            postContent.setText(string);
                            diff.setText(String.valueOf(_post.diff()));
                            replyNum.setText(String.valueOf(_post.replynumber));

                            String url = NewsService.BASE_URL + "/" + _post.userpicture.split("/")[1];
                            Glide.with(requireContext())
                                    .asBitmap()
                                    .load(url)
                                    .into(profilePicture);

                            arrayList.addAll(_post.pictures);
                            initPostPicsViewPager();

                            getAllReplies(postId);
                        }
                    }
                }else NewsUtil.showMessage(getContext(), R.string.failedToLoadData);
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                NewsUtil.showMessage(getContext(), R.string.failedToLoadData);
            }
        });
    }

    //TODO: userinfos & picture
    public void getAllReplies(String postId){
        Call<Replies> call = NewsUtil.getInstance().getNewsService().getAllReplies(postId);
        call.enqueue(new Callback<Replies>() {
            @Override
            public void onResponse(Call<Replies> call, Response<Replies> response) {
                if(response.isSuccessful()){
                    Replies replies = response.body();
                    if(replies != null && replies.replies != null){
                        data.addAll(replies.replies);
                        repliesAdapter.setReplies(data);
                        repliesRecycleView.setAdapter(repliesAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<Replies> call, Throwable t) {

            }
        });
    }

    public void deleteReply(String replyId, int pos){
        String postId = args.getString("postId");
        Call<Thereply> call = NewsUtil.getInstance().getNewsService().deleteReply(postId, replyId);
        call.enqueue(new Callback<Thereply>() {
            @Override
            public void onResponse(Call<Thereply> call, Response<Thereply> response) {
                if(response.isSuccessful()){
                    int a = Integer.parseInt(replyNum.getText().toString());
                    a--;
                    replyNum.setText(String.valueOf(a));
                    data.remove(pos);
                    repliesAdapter.notifyItemRemoved(pos);
                }
            }
            @Override
            public void onFailure(Call<Thereply> call, Throwable t) {

            }
        });
    }

    public void deletePost(){
        Call<Post> call = NewsUtil.getInstance().getNewsService().deleteTrip(
                args.getString("postId")
        );
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Snackbar.make(root, NewsUtil.getString(requireContext(), R.string.deletedTrip), BaseTransientBottomBar.LENGTH_INDEFINITE)
                            .setActionTextColor(getResources().getColor(R.color.purple_700))
                            .setTextColor(getResources().getColor(R.color.teal_700))
                            .show();
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {}
        });
    }

    public void bookmark(String postId){
        Call<Void> call = NewsUtil.getInstance().getNewsService().bookMark(postId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void like(String id){
        Call<Void> call = NewsUtil.getInstance().getNewsService().likeTrip(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    public void dislike(String id){
        Call<Void> call = NewsUtil.getInstance().getNewsService().dislikeTrip(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}