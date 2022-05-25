package com.example.thwissa.fragment.storyfragment;

import java.util.Date;

/**
 * STORY CLASS
 * FIELDS: storyPictureResource, profilePicResource, userName, wilaya, date, likes, dislikes
 */
public class Story {
    /** FIELDS */
    int storyPictureResource;
    User user;
    String wilaya;
    int likes;
    int dislikes;
    int numberReports;
    Date postingDate;
    String dateText;   // not required from db

    /** TEMPORARY */
    boolean liked = false;
    boolean disliked = false;

    /** CONSTRUCTOR */
    public Story(int storyPictureResource, User user, String wilaya, int likes, int dislikes, int numberReports, Date postingDate) {
        this.storyPictureResource = storyPictureResource;
        this.user = user;
        this.wilaya = wilaya;
        this.likes = likes;
        this.dislikes = dislikes;
        this.numberReports = numberReports;
        this.postingDate = postingDate;

        this.dateText = postingDate.getDate() + "/" + (postingDate.getMonth()+1) + "/" + (postingDate.getYear() + 1900);
    }
}
