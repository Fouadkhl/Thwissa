package com.example.thwissa.fragment.newsfragment.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.classes.Reply;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;


public class repliesAdapter extends RecyclerView.Adapter<repliesAdapter.InnerViewHolder> {

    private Context context;
    private ArrayList<Reply> replies = new ArrayList<>();

    public repliesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_reply_, parent, false
        );
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        Reply reply = replies.get(position);
        holder.profilePic.setImageBitmap(stringToBitmap(reply.getUserPic()));
        holder.userName.setText(reply.getUserName());
        holder.location.setText(reply.getLocation());
        holder.postTime.setText(reply.getDateOfPosting());
        holder.content.setText(reply.getContent());
        holder.replyPic.setImageBitmap(stringToBitmap(reply.getPicture()));
        holder.upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.diff.setText(
                String.valueOf(reply.getLikes()-reply.getDislikes())
        );
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    private Bitmap stringToBitmap(String userPic) {
        byte[] decodedString = Base64.decode(userPic, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public void setReplies(ArrayList<Reply> replies) {
        this.replies = replies;
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
        }
    }
}
