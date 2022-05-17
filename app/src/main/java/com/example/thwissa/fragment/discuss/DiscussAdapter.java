package com.example.thwissa.fragment.discuss;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;

import java.util.ArrayList;

public class DiscussAdapter extends RecyclerView.Adapter<DiscussAdapter.MyViewHolder> {
    private ArrayList<Discuss> mDiscussList;

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
        }
    }

    public DiscussAdapter(ArrayList<Discuss> discussList){
        mDiscussList = discussList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discuss_recyclerview, parent, false);

        MyViewHolder viewHolder= new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Discuss currentDiscuss = mDiscussList.get(position);

        holder.profilePic.setImageResource(currentDiscuss.getUser().getProfilePicResource());
        holder.discussImage.setImageResource(currentDiscuss.getDiscussImageResource());
        holder.userName.setText(currentDiscuss.getUser().getUserName());
        holder.wilaya.setText(currentDiscuss.getWilaya());
        holder.discussText.setText(currentDiscuss.getDiscussTexte());
        holder.likes.setText(String.valueOf(currentDiscuss.getLikes()));
        holder.dislikes.setText(String.valueOf(currentDiscuss.getDislikes()));
        holder.replies.setText(String.valueOf(currentDiscuss.getRepliesNumber()));

        /** BUTTONS LISTENERS */
        holder.likesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentDiscuss.liked){
                    int newLikes = currentDiscuss.getLikes() + 1;
                    currentDiscuss.setLikes(newLikes);
                    holder.likes.setText(String.valueOf(newLikes));
                    currentDiscuss.liked = true;

                    if(currentDiscuss.disliked){
                        int newDislikes = currentDiscuss.getDislikes() - 1;
                        currentDiscuss.setDislikes(newDislikes);
                        holder.dislikes.setText(String.valueOf(newDislikes));
                        currentDiscuss.disliked = false;
                    }
                }
            }
        });

        holder.dislikesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!currentDiscuss.disliked) {
                    int newDislikes = currentDiscuss.getDislikes() + 1;
                    currentDiscuss.setDislikes(newDislikes);
                    holder.dislikes.setText(String.valueOf(newDislikes));
                    currentDiscuss.disliked = true;

                    if (currentDiscuss.liked) {
                        int newLikes = currentDiscuss.getLikes() - 1;
                        currentDiscuss.setLikes(newLikes);
                        holder.likes.setText(String.valueOf(newLikes));
                        currentDiscuss.liked = false;
                    }
                }
            }
        });


        holder.sendReplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newRepliesNumber = currentDiscuss.getRepliesNumber() + 1;
                currentDiscuss.setRepliesNumber(newRepliesNumber);
                holder.replies.setText(String.valueOf(newRepliesNumber));
            }
        });

        holder.saveButton.setOnClickListener(new View.OnClickListener() {
            /**
             boolean saved = false;
             @Override
             public void onClick(View view) {
             if(!saved){
             saved = true;
             holder.saveButton.setImageResource(R.drawable.bookmark_clicked_icon);
             Toast.makeText(view.getContext(), "Saved", Toast.LENGTH_SHORT).show();
             }
             else {
             saved = false;
             holder.saveButton.setImageResource(R.drawable.bookmark_icon);
             Toast.makeText(view.getContext(), "Removed from saved", Toast.LENGTH_SHORT).show();
             }
             }
             */
            @Override
            public void onClick(View view) {
                if(!currentDiscuss.saved){
                    currentDiscuss.saved = true;
                    holder.saveButton.setImageResource(R.drawable.ic_baseline_bookmark_24);
                    Toast.makeText(view.getContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
                else {
                    currentDiscuss.saved = false;
                    holder.saveButton.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
                    Toast.makeText(view.getContext(), "Removed from saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiscussList.size();
    }
}