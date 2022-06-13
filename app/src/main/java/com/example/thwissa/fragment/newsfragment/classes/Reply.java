package com.example.thwissa.fragment.newsfragment.classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reply {
    @SerializedName("_id")
    public String _id;
    @SerializedName("text")
    public String text;
    @SerializedName("picture")
    public String picture;
    @SerializedName("userid")
    public String userid;
    @SerializedName("username")
    public String username;
    @SerializedName("userlocation")
    public String userlocation;
    @SerializedName("userpicture")
    public String userpicture;
    @SerializedName("likes")
    public List<ReactionRes> likes;
    @SerializedName("dislikes")
    public List<ReactionRes> dislikes;
    /*@SerializedName("questionId")
    public String questionId;*/
    @SerializedName("replyDate")
    public String replyDate;
    @SerializedName("tripid")
    public String tripid;

    public boolean isLiked;
    public boolean isDisliked;
    public int likeNum;
    public int dislikeNum;

    public int diff(){
        return likeNum-dislikeNum;
    }
}
