package com.example.thwissa.fragment.newsfragment.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thwissa.R;
import com.example.thwissa.fragment.newsfragment.classes.mPost;
import com.example.thwissa.fragment.newsfragment.interfaces.OnItemClickedListener;
import com.example.thwissa.fragment.newsfragment.interfaces.OnReplyButtonClicked;
import com.google.android.material.imageview.ShapeableImageView;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;
import java.util.ArrayList;
import java.util.Collections;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.InnerViewHolder>{

    private ArrayList<mPost> data = new ArrayList<>();
    private Context context;
    private boolean isVisible = true;

    private OnReplyButtonClicked onReplyButtonClicked = new OnReplyButtonClicked() {
        @Override
        public void replyButtonClicked(String postID) {}
    };
    private OnItemClickedListener onItemClickedListener = new OnItemClickedListener() {
        @Override
        public void ItemClicked(String postID) { }
    };

    public PostsAdapter(Context context){
        this.context = context;
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

        holder.profile_pic.setImageBitmap(stringToBitmap(post.getAgencypicture()));
        holder.user_name.setText(post.getAgencyname());
        holder.location.setText(post.getAgencylocation());
        if(!isVisible){
            holder.bookmark.setVisibility(View.GONE);
        }
        else{
            holder.bookmark.setVisibility(View.VISIBLE);
        }
        /*holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!post.isBookMarkClicked()){
                    holder.bookmark.setImageResource(R.drawable.ic_baseline_bookmark_clicked_24);
                    addToBookmark(data.get(position));
                }
                else{
                    holder.bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
                    removeFromBookmark(data.get(post).)
                }
            }
        });*/
        if(post.getPicture() != null && post.getPicture().length != 0) {
            ArrayList<Bitmap> arrayList = new ArrayList<>();
            stringToBitmap(post.getPicture(), arrayList);
            PostPictursViewPagerAdapter postPictursViewPagerAdapter = new PostPictursViewPagerAdapter();
            postPictursViewPagerAdapter.setImgIds(arrayList);
            holder.viewPager2.setAdapter(postPictursViewPagerAdapter);
            holder.wormDotsIndicator.setViewPager2(holder.viewPager2);
        }
        else{
            holder.viewPager2.setVisibility(View.GONE);
        }
        if(post.getPicture() == null || post.getPicture().length <= 1){
            holder.wormDotsIndicator.setVisibility(View.GONE);
        }
        holder.diff.setText(String.valueOf(post.getLikes()-post.getDislikes()));
        holder.reply_num.setText(String.valueOf(post.getReplynumber()).concat(" comments"));
        holder.upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(!post.upButtonClicked) {
                    holder.upButton.setImageResource(R.drawable.clicked_up_arrow);
                    post.diff++;
                }
                else{
                    holder.upButton.setImageResource(R.drawable.up_arrow_not_clicked);
                    post.diff--;
                }
                if(post.downButtonClicked){
                    holder.downButton.setImageResource(R.drawable.down_arrow_not_clicked);
                    post.diff++;
                    post.downButtonClicked = false;
                }
                post.upButtonClicked = !post.upButtonClicked;
                holder.diff.setText(String.valueOf(post.diff));*/
            }
        });
        holder.downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(!post.downButtonClicked) {
                    holder.downButton.setImageResource(R.drawable.clicked_down_arrow);
                    post.diff--;
                }
                else{
                    holder.downButton.setImageResource(R.drawable.down_arrow_not_clicked);
                    post.diff++;
                }
                if(post.upButtonClicked){
                    holder.upButton.setImageResource(R.drawable.up_arrow_not_clicked);
                    post.diff--;
                    post.upButtonClicked = false;
                }
                post.downButtonClicked = !post.downButtonClicked;
                holder.diff.setText(String.valueOf(post.diff));*/
            }
        });
        holder.replyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReplyButtonClicked.replyButtonClicked(post.getPostID());
            }
        });
        String string = "";
        string += context.getResources().getString(R.string.destination).concat(" :").concat(post.getDestination()+"\n");
        string += context.getResources().getString(R.string.date).concat(" :").concat(post.getTripDate()+"\n");
        string += context.getResources().getString(R.string.period).concat(" :").concat(post.getMaxduration()+"\n");
        string += context.getResources().getString(R.string.price).concat(" :").concat(String.valueOf(post.getMaxprice())+"\n");
        string += context.getResources().getString(R.string.description)+" :"+post.getText();

        holder.content.setText(string);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickedListener.ItemClicked(post.getPostID());
            }
        });
    }

    private Bitmap stringToBitmap(String agencypicture) {
        byte[] decodedString = Base64.decode(agencypicture, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    private void stringToBitmap(String[] picture, ArrayList<Bitmap> bitmaps) {
        for(String s: picture){
            byte[] decodedString = Base64.decode(s, Base64.DEFAULT);
             bitmaps.add(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
    }

    private void addToBookmark(mPost mPost) {
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
    public void setBookMarkVisibility(boolean isVisible){
        this.isVisible = isVisible;
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

}
