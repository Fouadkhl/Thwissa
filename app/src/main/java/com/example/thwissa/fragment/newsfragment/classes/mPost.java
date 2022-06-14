package com.example.thwissa.fragment.newsfragment.classes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class mPost implements Comparable<mPost>{
    @SerializedName("_id")
    public String _id;
    @SerializedName("date")
    public String date;
    @SerializedName("minduration")
    public int minduration;
    @SerializedName(value = "maxduration")
    public int maxduration;
    @SerializedName("minprice")
    public int minprice;
    @SerializedName(value = "maxprice")
    public int maxprice;
    @SerializedName("meetingplace")
    public String meetingplace;
    @SerializedName("destination")
    public String destination;
    @SerializedName(value = "agencyId", alternate = "userid")
    public String agencyId;
    @SerializedName("pictures")
    public ArrayList<String> pictures;
    @SerializedName("text")
    public String text;
    @SerializedName("username")
    public String username;
    @SerializedName("userpicture")
    public String userpicture;
    @SerializedName("userlocation")
    public String userlocation;
    @SerializedName("likes")
    public List<ReactionRes> likes;
    @SerializedName("dislikes")
    public List<ReactionRes> dislikes;
    @SerializedName("replynumber")
    public int replynumber;
    @SerializedName("tripDate")
    public String tripDate;
    @SerializedName("tags")
    public String[] tags;
    public boolean isLiked = false;
    public boolean isDisliked = false;
    public boolean isBookmarked;
    public int likeNum;
    public int dislikeNum;

    public int diff(){
        return likeNum - dislikeNum;
    }
    public float rate() {
        if(likeNum == 0 && dislikeNum == 0) return 0;
        else return Math.min(0, diff())/((float)(likeNum + dislikeNum));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        mPost post = (mPost) o;
        return _id.equals(post._id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id);
    }

    @Override
    public int compareTo(mPost post) {
        return Float.compare(this.rate(), post.rate());
    }
}
