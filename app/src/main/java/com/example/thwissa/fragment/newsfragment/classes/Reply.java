package com.example.thwissa.fragment.newsfragment.classes;

public class Reply {
    private String postID;
    private String content;
    private String picture;
    private String userID;
    private String userName;
    private String userPic;
    private String location;
    private String dateOfPosting;
    private int likes;
    private int dislikes;

    public Reply(String postID, String content, String picture,
                 String userID, String userName, String userPic,
                 String location, String dateOfPosting, int likes, int dislikes) {
        this.postID = postID;
        this.content = content;
        this.picture = picture;
        this.userID = userID;
        this.userName = userName;
        this.userPic = userPic;
        this.location = location;
        this.dateOfPosting = dateOfPosting;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateOfPosting() {
        return dateOfPosting;
    }

    public void setDateOfPosting(String dateOfPosting) {
        this.dateOfPosting = dateOfPosting;
    }
}
