package com.example.thwissa.fragment.newsfragment.classes;

import android.graphics.Bitmap;

public class mPost {
    private String postID;
    private String Date;

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    private String maxduration;
    private int Maxprice;
    private String Meetingplace;
    private String Destination;
    private int agencyId;
    private String agencypicture;
    private String agencyname;
    private String agencylocation;
    private String text;
    private String[] picture;
    private int likes;
    private int dislikes;
    private int replynumber;
    private String tripDate;
    private String[] tags;

    public int getReplynumber() {
        return replynumber;
    }

    public void setReplynumber(int replynumber) {
        this.replynumber = replynumber;
    }

    public mPost(String date, String maxduration, int maxprice, String meetingplace,
                 String destination, int agencyId, String agencypicture, String agencyname,
                 String agencylocation, String text, String[] picture, int likes, int dislikes,
                 String tripDate, String[] tags) {
        Date = date;
        this.maxduration = maxduration;
        Maxprice = maxprice;
        Meetingplace = meetingplace;
        Destination = destination;
        this.agencyId = agencyId;
        this.agencypicture = agencypicture;
        this.agencyname = agencyname;
        this.agencylocation = agencylocation;
        this.text = text;
        this.picture = picture;
        this.likes = likes;
        this.dislikes = dislikes;
        this.tripDate = tripDate;
        this.tags = tags;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public void setAgencypicture(String agencypicture) {
        this.agencypicture = agencypicture;
    }

    public void setAgencyname(String agencyname) {
        this.agencyname = agencyname;
    }

    public void setAgencylocation(String agencylocation) {
        this.agencylocation = agencylocation;
    }

    public String getAgencypicture() {
        return agencypicture;
    }

    public String getAgencyname() {
        return agencyname;
    }

    public String getAgencylocation() {
        return agencylocation;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setMaxduration(String maxduration) {
        this.maxduration = maxduration;
    }

    public void setMaxprice(int maxprice) {
        Maxprice = maxprice;
    }

    public void setMeetingplace(String meetingplace) {
        Meetingplace = meetingplace;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPicture(String[] picture) {
        this.picture = picture;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getDate() {
        return Date;
    }

    public String getMaxduration() {
        return maxduration;
    }

    public int getMaxprice() {
        return Maxprice;
    }

    public String getMeetingplace() {
        return Meetingplace;
    }

    public String getDestination() {
        return Destination;
    }

    public String getText() {
        return text;
    }

    public String[] getPicture() {
        return picture;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public String getTripDate() {
        return tripDate;
    }

    public String[] getTags() {
        return tags;
    }
    public boolean isBookMarkClicked(){
        return true;
    }
}
