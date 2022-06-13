package com.example.thwissa.fragment.discuss;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thwissa.R;
import com.example.thwissa.fragment.discuss.classes.Discuss;
import com.example.thwissa.fragment.discuss.classes.DiscussService;
import com.example.thwissa.fragment.discuss.classes.DiscussUtil;
import com.example.thwissa.fragment.discuss.classes.ReceivedDiscuss;
import com.example.thwissa.fragment.newsfragment.adapters.RepliesAdapter;
import com.example.thwissa.fragment.newsfragment.classes.ReactionRes;
import com.example.thwissa.fragment.newsfragment.classes.Replies;
import com.example.thwissa.fragment.newsfragment.classes.Reply;
import com.example.thwissa.fragment.newsfragment.classes.Thereply;
import com.example.thwissa.fragment.newsfragment.interfaces.OnMoreButtonClicked;
import com.example.thwissa.repository.userLocalStore.SPUserData;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscussAdapter extends RecyclerView.Adapter<DiscussAdapter.MyViewHolder>{
    private ArrayList<Discuss> mDiscussList = new ArrayList<>();
    private final Context context;
    private final ArrayList<Reply> replies = new ArrayList<>();
    private final SPUserData userData;
    // private RepliesAdapter repliesAdapter;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView profilePic; // user profile pic
        public ImageView discussImage; // discuss image
        public TextView userName; // user name
        public TextView wilaya; // Wilaya
        public TextView discussText; // Discuss Text
        public TextView likes; // Likes
        public TextView dislikes; // Dislikes
        public TextView replies; // Replies

        public CardView likesButton;
        public CardView dislikesButton;
        public ImageButton sendReplyButton;
        public ImageButton saveButton;
        public ImageView delete;
        public EditText replyInput;
        public CardView replyButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = (ImageView) itemView.findViewById(R.id.profile_pic);
            discussImage = (ImageView) itemView.findViewById(R.id.discuss_image);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            wilaya = (TextView) itemView.findViewById(R.id.wilaya);
            discussText = (TextView) itemView.findViewById(R.id.discuss_text);
            likes = (TextView) itemView.findViewById(R.id.nbr_likes);
            dislikes = (TextView) itemView.findViewById(R.id.nbr_dislikes);
            replies = (TextView) itemView.findViewById(R.id.nbr_replies);

            likesButton = (CardView) itemView.findViewById(R.id.like_button);
            dislikesButton = (CardView) itemView.findViewById(R.id.dislike_button);
            sendReplyButton = (ImageButton) itemView.findViewById(R.id.send_reply_button);
            saveButton = (ImageButton) itemView.findViewById(R.id.save_button);
            delete = itemView.findViewById(R.id.delete);
            replyInput = itemView.findViewById(R.id.reply_input);
            replyButton = itemView.findViewById(R.id.replies_button);
        }
    }

    public DiscussAdapter(Context context){
        this.context = context;
        this.userData = new SPUserData(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discuss_recyclerview, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {
        Discuss currentDiscuss = mDiscussList.get(position);


        // holder.profilePic.setImageBitmap();
        // holder.discussImage.setImageResource(currentDiscuss.getDiscussImageResource());
        if(currentDiscuss.userpicture != null && !currentDiscuss.userpicture.equals("")){
            String url = DiscussService.BASE_URL + "/" + currentDiscuss.userpicture.split("/")[1];
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(holder.profilePic);
        } else holder.profilePic.setImageResource(R.drawable.ic_baseline_person_24);
        if(currentDiscuss.picture!=null && !currentDiscuss.picture.equals("")){
            String url;
            url = DiscussService.BASE_URL + "/" + currentDiscuss.picture.split("/")[1];
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(holder.discussImage);
        } else holder.discussImage.setVisibility(View.GONE);
        holder.userName.setText(currentDiscuss.username);
        holder.wilaya.setText(currentDiscuss.userlocation);
        holder.discussText.setText(currentDiscuss.text);
        Log.e("reply num : ", ""+currentDiscuss.replynumber);
        holder.replies.setText(String.valueOf(currentDiscuss.replynumber));

        currentDiscuss.liked = currentDiscuss.likes.contains(
                new ReactionRes(userData.getLoggedInUser().getId())
        );
        currentDiscuss.disliked = currentDiscuss.dislikes.contains(
                new ReactionRes(userData.getLoggedInUser().getId())
        );

        currentDiscuss.likeNum = currentDiscuss.likes.size();
        currentDiscuss.dislikeNum = currentDiscuss.dislikes.size();
        holder.likes.setText(String.valueOf(currentDiscuss.likeNum));
        holder.dislikes.setText(String.valueOf(currentDiscuss.dislikeNum));
        holder.likesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context, userData.getLoggedInUser().getId(), Toast.LENGTH_SHORT).show();
                if(!currentDiscuss.liked){
                    currentDiscuss.likeNum++;
                    holder.likes.setText(String.valueOf(currentDiscuss.likeNum));
                    currentDiscuss.liked = true;
                } else {
                    currentDiscuss.likeNum--;
                    holder.likes.setText(String.valueOf(currentDiscuss.likeNum));
                    currentDiscuss.liked = false;
                }
                if(currentDiscuss.disliked){
                    currentDiscuss.dislikeNum++;
                    holder.dislikes.setText(String.valueOf(currentDiscuss.dislikeNum));
                    currentDiscuss.disliked = false;
                    dislike(currentDiscuss._id);
                }
                like(currentDiscuss._id);
            }
        });

        holder.dislikesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!currentDiscuss.disliked) {
                    currentDiscuss.dislikeNum++;
                    holder.dislikes.setText(String.valueOf(currentDiscuss.dislikeNum));
                    currentDiscuss.disliked = true;
                } else {
                    currentDiscuss.dislikeNum--;
                    holder.dislikes.setText(String.valueOf(currentDiscuss.dislikeNum));
                    currentDiscuss.disliked = false;
                }
                if (currentDiscuss.liked) {
                    currentDiscuss.likeNum++;
                    holder.likes.setText(String.valueOf(currentDiscuss.likeNum));
                    currentDiscuss.liked = false;
                    like(currentDiscuss._id);
                }
                dislike(currentDiscuss._id);
            }
        });

        // TODO : picture
        holder.sendReplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reply reply = new Reply();
                reply.text = holder.replyInput.getText().toString();
                postReply(currentDiscuss._id, reply, holder.getAdapterPosition());
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteQuestion(currentDiscuss._id, holder.getAdapterPosition());
            }
        });

        holder.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentDiscuss.saved){
                    currentDiscuss.saved = true;
                    holder.saveButton.setImageResource(R.drawable.ic_baseline_bookmark_24);
                }
                else {
                    currentDiscuss.saved = false;
                    holder.saveButton.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
                }
                bookmark(currentDiscuss._id);
            }
        });

        holder.replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                bottomSheetDialog.setContentView(R.layout.bottom_replies_sheet_discuss);

                RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.repliesRecycleView);
                if(recyclerView!=null){
                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    RepliesAdapter repliesAdapter = new RepliesAdapter(context, currentDiscuss._id, true);
                    repliesAdapter.setOnMoreButtonClicked(new OnMoreButtonClicked() {
                        @Override
                        public void Click(String replyId, int position) {
                            deleteReply(currentDiscuss._id, replyId, position, holder.getAdapterPosition(), repliesAdapter);
                        }
                    });
                    recyclerView.setAdapter(repliesAdapter);
                    getAllReplies(currentDiscuss._id, repliesAdapter);
                    repliesAdapter.setReplies(replies);
                    bottomSheetDialog.show();
                }
            }
        });
    }

    public void setData(ArrayList<Discuss> data){
        this.mDiscussList = data;
    }

    @Override
    public int getItemCount() {
        return mDiscussList.size();
    }

    public void postReply(String postId, Reply reply, int pos){
        Call<Thereply> call = DiscussUtil.getInstance()
                .getJsonPlaceHolder().postReply(postId, reply);
        call.enqueue(new Callback<Thereply>() {
            @Override
            public void onResponse(Call<Thereply> call, Response<Thereply> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    mDiscussList.get(pos).replynumber++;
                    notifyItemChanged(pos);
                }
            }
            @Override
            public void onFailure(Call<Thereply> call, Throwable t) {

            }
        });
    }

    public void deleteQuestion(String postId, int pos){
        Call<ReceivedDiscuss> call = DiscussUtil.getInstance()
                .getJsonPlaceHolder().deleteQuestion(postId);
        call.enqueue(new Callback<ReceivedDiscuss>() {
            @Override
            public void onResponse(Call<ReceivedDiscuss> call, Response<ReceivedDiscuss> response) {
                if(response.isSuccessful()){
                    mDiscussList.remove(pos);
                    notifyItemRemoved(pos);
                }
            }
            @Override
            public void onFailure(Call<ReceivedDiscuss> call, Throwable t) {

            }
        });
    }

    public void getAllReplies(String postId, RepliesAdapter repliesAdapter){
        Call<Replies> call = DiscussUtil.getInstance()
                .getJsonPlaceHolder().getAllReplies(postId);
        call.enqueue(new Callback<Replies>() {
            @Override
            public void onResponse(Call<Replies> call, Response<Replies> response) {
                if(response.isSuccessful() && response.body()!=null){
                    int oldSize = replies.size();
                    replies.removeAll(replies);
                    replies.addAll(response.body().replies);
                    repliesAdapter.notifyItemRangeInserted(0, replies.size()-1);
                    Log.e("replies num ", ""+repliesAdapter.getItemCount());
                }
            }
            @Override
            public void onFailure(Call<Replies> call, Throwable t) {

            }
        });
    }

    public void deleteReply(String id, String replyId, int replyPos, int postPos, RepliesAdapter repliesAdapter){
        Call<Thereply> call = DiscussUtil.getInstance()
                .getJsonPlaceHolder().deleteReply(id, replyId);
        call.enqueue(new Callback<Thereply>() {
            @Override
            public void onResponse(Call<Thereply> call, Response<Thereply> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    replies.remove(replyPos);
                    repliesAdapter.notifyItemRemoved(replyPos);
                    mDiscussList.get(postPos).replynumber--;
                    notifyItemChanged(postPos);
                }
            }
            @Override
            public void onFailure(Call<Thereply> call, Throwable t) {

            }
        });
    }

    public void like(String id){
        Call<Void> call = DiscussUtil.getInstance()
                .getJsonPlaceHolder().likePost(id);
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
        Call<Void> call = DiscussUtil.getInstance()
                .getJsonPlaceHolder().dislikePost(id);
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
        Call<Void> call = DiscussUtil.getInstance().getJsonPlaceHolder().bookMark(id);
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