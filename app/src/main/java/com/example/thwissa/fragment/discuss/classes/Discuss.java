package com.example.thwissa.fragment.discuss.classes;

import com.example.thwissa.fragment.newsfragment.classes.ReactionRes;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Discuss {
    @SerializedName("replynumber")
    public int replynumber;
    @SerializedName("_id")
    public String _id;
    @SerializedName("text")
    public String text;
    @SerializedName("userid")
    public String userid;
    @SerializedName("userpicture")
    public String userpicture;
    @SerializedName("userlocation")
    public String userlocation;
    @SerializedName("likes")
    public List<ReactionRes> likes;
    @SerializedName("dislikes")
    public List<ReactionRes> dislikes;
    @SerializedName("tags")
    public String[] tags;
    @SerializedName("questionDate")
    public String date;
    @SerializedName("username")
    public String username;
    @SerializedName("picture")
    public String picture;

    public boolean liked;
    public boolean disliked;
    public boolean saved;
    public int likeNum;
    public int dislikeNum;

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Discuss){
            return this._id.equals(((Discuss) obj)._id);
        }
        return false;
    }
}
