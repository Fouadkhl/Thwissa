package com.example.thwissa.fragment.storyfragment;

/**
 * STORY CLASS
 * FIELDS: storyPictureResource, profilePicResource, userName, wilaya, date, likes, dislikes
 */
public class Story {
    /** FIELDS */
    int storyPictureResource;
    User user;
    String wilaya;
    String date;
    int likes;
    int dislikes;
    int numberReports;
    boolean liked = false;
    boolean disliked = false;

   /** CONSTRUCTOR */
    public Story(int storyPictureResource, User user, String wilaya, String date, int likes, int dislikes, int numberReports) {
        this.storyPictureResource = storyPictureResource;
        this.user = user;
        this.wilaya = wilaya;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
        this.numberReports = numberReports;
    }
}
