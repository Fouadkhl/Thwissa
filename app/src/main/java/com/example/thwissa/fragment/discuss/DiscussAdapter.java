package com.example.thwissa.fragment.discuss;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thwissa.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;

public class DiscussAdapter extends RecyclerView.Adapter<DiscussAdapter.MyViewHolder> {
    private ArrayList<Discuss> mDiscussList;


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView1; // user profile pic
        public ImageView imageView2; // discuss image
        public TextView textView1; // user name
        public TextView textView2; // Wilaya
        public TextView textView3; // Discuss Text
        public ExtendedFloatingActionButton button1; // Likes Button
        public ExtendedFloatingActionButton button2; // Dislikes Button
        public ExtendedFloatingActionButton button3; // Reply Button


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.profile_pic);
            imageView2 = (ImageView) itemView.findViewById(R.id.discuss_image);
            textView1 = (TextView) itemView.findViewById(R.id.user_name);
            textView2 = (TextView) itemView.findViewById(R.id.wilaya);
            textView3 = (TextView) itemView.findViewById(R.id.discuss_text);
            button1 = (ExtendedFloatingActionButton) itemView.findViewById(R.id.like_button);
            button2 = (ExtendedFloatingActionButton) itemView.findViewById(R.id.dislike_button);
            button3 = (ExtendedFloatingActionButton) itemView.findViewById(R.id.reply_button);
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView1.setImageResource(mDiscussList.get(position).getUser().getProfilePicResource());
        holder.imageView2.setImageResource(mDiscussList.get(position).getDiscussImageResource());
        holder.textView1.setText(mDiscussList.get(position).getUser().getUserName());
        holder.textView2.setText(mDiscussList.get(position).getWilaya());
        holder.textView3.setText(mDiscussList.get(position).getDiscussTexte());
        holder.button1.setText(String.valueOf(mDiscussList.get(position).getLikes()));
        holder.button2.setText(String.valueOf(mDiscussList.get(position).getDislikes()));
        holder.button3.setText(String.valueOf(mDiscussList.get(position).getRepliesNumber()) + " Replies");
    }

    @Override
    public int getItemCount() {
        return mDiscussList.size();
    }
}
