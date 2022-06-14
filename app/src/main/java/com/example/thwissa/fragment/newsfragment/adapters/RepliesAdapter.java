package com.example.thwissa.fragment.newsfragment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thwissa.R;
import com.example.thwissa.fragment.discuss.classes.DiscussUtil;
import com.example.thwissa.fragment.newsfragment.NewsService;
import com.example.thwissa.fragment.newsfragment.NewsUtil;
import com.example.thwissa.fragment.newsfragment.classes.ReactionRes;
import com.example.thwissa.fragment.newsfragment.classes.Reply;
import com.example.thwissa.fragment.newsfragment.interfaces.OnMoreButtonClicked;
import com.example.thwissa.repository.userLocalStore.SPUserData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepliesAdapter extends RecyclerView.Adapter<RepliesAdapter.InnerViewHolder> {

    private final Context context;
    private final String postId;
    private final SPUserData userData;
    private boolean deleteOnly = false;
    private ArrayList<Reply> replies = new ArrayList<>();
    private OnMoreButtonClicked onMoreButtonClicked = (replyId, position) -> {};


    public RepliesAdapter(Context context, @NonNull String postId) {
        this.context = context;
        this.postId = postId;
        this.userData = new SPUserData(context);
    }

    public RepliesAdapter(Context context, @NonNull String postId, boolean deleteOnly) {
        this.context = context;
        this.postId = postId;
        this.deleteOnly = deleteOnly;
        this.userData = new SPUserData(context);
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_news_reply, parent, false
        );
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        Reply reply = replies.get(position);
        if(deleteOnly){
            holder.moreButton.setImageResource(
                    R.drawable.ic_baseline_delete_24
            );
        }

        holder.userName.setText(reply.username);
        holder.location.setText(reply.userlocation);
        holder.postTime.setText(reply.replyDate.substring(0, 10).replace("-", "/"));
        holder.content.setText(reply.text);


        // holder.replyPic.setImageBitmap(Util.urlToBitmap(reply.picture));
        if(reply.picture != null && !reply.picture.equals("")) {
            String url = NewsService.BASE_URL + "/" + reply.picture.split("/")[1];
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(holder.replyPic);
        }  else {
            holder.replyPic.setVisibility(View.GONE);
        }

        if(reply.userpicture != null && !reply.userpicture.equals("")) {
            String url = NewsService.BASE_URL + "/" + reply.userpicture.split("/")[1];
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(holder.profilePic);
        } else {
            holder.profilePic.setImageResource(R.drawable.ic_baseline_person_24);
        }

        reply.likeNum = reply.likes.size();
        reply.dislikeNum = reply.dislikes.size();
        reply.isLiked = reply.likes.contains(new ReactionRes(
                userData.getLoggedInUser().getId()
        ));
        reply.isDisliked = reply.dislikes.contains(new ReactionRes(
                userData.getLoggedInUser().getId()
        ));
        holder.upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!reply.isLiked) {
                    holder.upButton.setImageResource(R.drawable.clicked_up_arrow);
                    reply.likeNum++;
                }
                else{
                    holder.upButton.setImageResource(R.drawable.up_arrow_not_clicked);
                    reply.likeNum--;
                }
                if(reply.isDisliked){
                    holder.downButton.setImageResource(R.drawable.down_arrow_not_clicked);
                    reply.dislikeNum--;
                    reply.isDisliked = false;
                    dislikeReply(postId, reply._id);
                }
                likeReply(postId, reply._id);
                reply.isLiked = !reply.isLiked;
                holder.diff.setText(String.valueOf(reply.diff()));
            }
        });
        holder.downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!reply.isDisliked) {
                    holder.downButton.setImageResource(R.drawable.clicked_down_arrow);
                    reply.dislikeNum++;
                }
                else{
                    holder.downButton.setImageResource(R.drawable.down_arrow_not_clicked);
                    reply.dislikeNum--;
                }
                if(reply.isLiked){
                    holder.upButton.setImageResource(R.drawable.up_arrow_not_clicked);
                    reply.likeNum--;
                    reply.isLiked = false;
                    likeReply(postId, reply._id);
                }
                dislikeReply(postId, reply._id);
                reply.isDisliked = !reply.isDisliked;
                holder.diff.setText(String.valueOf(reply.diff()));
            }
        });
        holder.diff.setText(
                String.valueOf(reply.diff())
        );
        holder.moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoreButtonClicked.Click(reply._id, holder.getAdapterPosition());
            }
        });
    }

    public void likeReply(String tripId, String replyId){
        Call<Void> call;
        if(!deleteOnly){
            call = NewsUtil.getInstance()
                    .getNewsService()
                    .likeReply(tripId, replyId);
        } else {
            call = DiscussUtil.getInstance()
                    .getJsonPlaceHolder()
                    .likeReply(tripId, replyId);
        }
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    public void dislikeReply(String tripId, String replyId){
        Call<Void> call;
        if(!deleteOnly) {
            call = NewsUtil.getInstance()
                    .getNewsService()
                    .dislikeReply(tripId, replyId);
        } else {
            call = DiscussUtil.getInstance()
                    .getJsonPlaceHolder()
                    .dislikeReply(tripId, replyId);
        }
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    public void setReplies(ArrayList<Reply> replies) {
        this.replies = replies;
    }

    public void setOnMoreButtonClicked(OnMoreButtonClicked onMoreButtonClicked) {
        this.onMoreButtonClicked = onMoreButtonClicked;
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder{
        public ShapeableImageView profilePic;
        public TextView userName;
        public TextView location;
        public TextView postTime;
        public TextView content;
        public ImageView replyPic;
        public ImageView upButton;
        public ImageView downButton;
        public TextView diff;
        public FloatingActionButton moreButton;


        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.reply_profile_pic);
            userName = itemView.findViewById(R.id.user_name);
            location = itemView.findViewById(R.id.location);
            postTime = itemView.findViewById(R.id.postTime);
            content = itemView.findViewById(R.id.content);
            replyPic = itemView.findViewById(R.id.replyPic);
            upButton = itemView.findViewById(R.id.up_button);
            downButton = itemView.findViewById(R.id.down_button);
            diff = itemView.findViewById(R.id.diff);
            moreButton = itemView.findViewById(R.id.moreButton);
        }
    }
}
