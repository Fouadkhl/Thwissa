package com.example.thwissa.fragment.newsfragment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.NewsService;
import com.example.thwissa.fragment.newsfragment.NewsUtil;
import com.example.thwissa.fragment.newsfragment.classes.ReactionRes;
import com.example.thwissa.fragment.newsfragment.classes.mPost;
import com.example.thwissa.fragment.newsfragment.interfaces.OnItemClickedListener;
import com.example.thwissa.fragment.newsfragment.interfaces.OnReplyButtonClicked;
import com.example.thwissa.repository.userLocalStore.SPUserData;
import com.google.android.material.imageview.ShapeableImageView;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.InnerViewHolder>{

    private ArrayList<mPost> data = new ArrayList<>();
    private final Context context;
    private final SPUserData userData;

    private OnReplyButtonClicked onReplyButtonClicked = (postID, pos) -> {};
    private OnItemClickedListener onItemClickedListener = (postID, pos) -> {};

    public PostsAdapter(Context context){
        this.context = context;
        userData = new SPUserData(context);
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        mPost post = data.get(position);

        if(post.isLiked){
            holder.upButton.setImageResource(R.drawable.clicked_up_arrow);
        }
        else if(post.isDisliked){
            holder.downButton.setImageResource(R.drawable.clicked_down_arrow);
        }
        if(!post.isBookmarked){
            holder.bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
        }
        // holder.profile_pic.setImageBitmap(NewsUtil.urlToBitmap(post.userpicture));

        if(post.userpicture != null && !post.userpicture.equals("")) {
            String url = NewsService.BASE_URL + "/" + post.userpicture.split("/")[1];
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(holder.profile_pic);
        } else holder.profile_pic.setImageResource(R.drawable.ic_baseline_person_24);

        holder.user_name.setText(post.username);
        holder.location.setText(post.userlocation);
        holder.bookmark.setOnClickListener(view -> {
            if(!post.isBookmarked){
                holder.bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24);
            } else {
                holder.bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
            }
            bookmark(post._id);
            post.isBookmarked = !post.isBookmarked;
        });
        /*if(post.picture != null) {
            ArrayList<Bitmap> arrayList = new ArrayList<>();
            arrayList.add(NewsUtil.urlToBitmap(post.picture));
            PostPictursViewPagerAdapter postPictursViewPagerAdapter = new PostPictursViewPagerAdapter();
            postPictursViewPagerAdapter.setImgIds(arrayList);
            holder.viewPager2.setAdapter(postPictursViewPagerAdapter);
            holder.wormDotsIndicator.setViewPager2(holder.viewPager2);
        }
        else{
            holder.viewPager2.setVisibility(View.GONE);
        }
        holder.wormDotsIndicator.setVisibility(View.GONE);*/

        if(post.pictures != null && post.pictures.size() != 0){
            PostPictursViewPagerAdapter postPictursViewPagerAdapter = new PostPictursViewPagerAdapter(context);
            postPictursViewPagerAdapter.setImgIds(post.pictures);
            holder.viewPager2.setAdapter(postPictursViewPagerAdapter);
            if(post.pictures.size()!=1) {
                holder.wormDotsIndicator.setViewPager2(holder.viewPager2);
            } else holder.wormDotsIndicator.setVisibility(View.GONE);
        } else {
            holder.viewPager2.setVisibility(View.GONE);
            holder.wormDotsIndicator.setVisibility(View.GONE);
        }


        holder.diff.setText(String.valueOf(post.diff()));
        holder.reply_num.setText(String.valueOf(post.replynumber).concat(" comments"));

        post.likeNum = post.likes.size();
        post.dislikeNum = post.dislikes.size();
        post.isLiked = post.likes.contains(new ReactionRes(userData.getLoggedInUser().getId()));
        post.isLiked = post.dislikes.contains(new ReactionRes(userData.getLoggedInUser().getId()));
        holder.upButton.setOnClickListener(view -> {
            if(!post.isLiked) {
                holder.upButton.setImageResource(R.drawable.clicked_up_arrow);
                post.likeNum++;
            }
            else{
                holder.upButton.setImageResource(R.drawable.up_arrow_not_clicked);
                post.dislikeNum--;
            }
            if(post.isDisliked){
                holder.downButton.setImageResource(R.drawable.down_arrow_not_clicked);
                post.likeNum--;
                post.isDisliked = false;
                dislikePost(post._id);
            }
            likePost(post._id);
            post.isLiked = !post.isLiked;
            holder.diff.setText(String.valueOf(post.diff()));
        });
        holder.downButton.setOnClickListener(view -> {
            if(!post.isDisliked) {
                holder.downButton.setImageResource(R.drawable.clicked_down_arrow);
                post.dislikeNum++;
            }
            else{
                holder.downButton.setImageResource(R.drawable.down_arrow_not_clicked);
                post.dislikeNum--;
            }
            if(post.isLiked){
                holder.upButton.setImageResource(R.drawable.up_arrow_not_clicked);
                post.likeNum--;
                post.isLiked = false;
                likePost(post._id);
            }
            dislikePost(post._id);
            post.isDisliked = !post.isDisliked;
            holder.diff.setText(String.valueOf(post.diff()));
        });
        holder.replyIcon.setOnClickListener(view -> onReplyButtonClicked.replyButtonClicked(post._id, holder.getAdapterPosition()));

        String string = "";
        string += context.getResources().getString(R.string.destination).concat(" :").concat(post.destination+"\n");
        string += context.getResources().getString(R.string.date).concat(" :").concat(
                post.tripDate.substring(0, 10).replace("-", "/")
                +"\n");
        string += context.getResources().getString(R.string.period).concat(" :").concat(post.maxduration+"\n");
        string += context.getResources().getString(R.string.price).concat(" :").concat(post.maxprice +"\n");
        string += context.getResources().getString(R.string.description)+" :"+post.text;

        holder.content.setText(string);
        holder.itemView.setOnClickListener(view -> onItemClickedListener.ItemClicked(post._id, holder.getAdapterPosition()));
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<mPost> data) {
        this.data = data;
    }
    public void setOnReplyIconClicked(OnReplyButtonClicked onReplyButtonClicked){
        this.onReplyButtonClicked = onReplyButtonClicked;
    }
    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        ShapeableImageView profile_pic;
        ViewPager2 viewPager2;
        TextView user_name, location, diff, reply_num, content;
        ImageView bookmark, upButton, downButton, replyIcon;
        WormDotsIndicator wormDotsIndicator;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_pic = itemView.findViewById(R.id.reply_profile_pic);
            user_name = itemView.findViewById(R.id.user_name);
            location = itemView.findViewById(R.id.location);
            bookmark = itemView.findViewById(R.id.bookMark);
            viewPager2 = itemView.findViewById(R.id.postPicViewPager);
            upButton = itemView.findViewById(R.id.up_button);
            diff = itemView.findViewById(R.id.diff);
            downButton = itemView.findViewById(R.id.down_button);
            reply_num = itemView.findViewById(R.id.reply_num);
            replyIcon = itemView.findViewById(R.id.reply_icon);
            content = itemView.findViewById(R.id.content);
            linearLayout = itemView.findViewById(R.id.parent);
            wormDotsIndicator = itemView.findViewById(R.id.dots_indicator);
        }
    }

    public void likePost(String postId){
        Call<Void> call = NewsUtil.getInstance()
                .getNewsService()
                .likeTrip(postId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
    public void dislikePost(String postId){
        Call<Void> call = NewsUtil.getInstance()
                .getNewsService()
                .dislikeTrip(postId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
    public void bookmark(String id){
        Call<Void> call = NewsUtil.getInstance().getNewsService().bookMark(id);
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