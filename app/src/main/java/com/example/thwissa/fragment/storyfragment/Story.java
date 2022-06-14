package com.example.thwissa.fragment.storyfragment;

import java.util.Date;

/**
 * STORY CLASS
 * FIELDS: storyPictureResource, profilePicResource, userName, wilaya, date, likes, dislikes
 */
@SuppressWarnings("ALL")
public class Story {
    /** FIELDS */

    String  idStory ;
    String storyPictureResource;
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
    boolean reported  = false ;

    /** CONSTRUCTOR */
    public Story(String storyPictureResource, User user, String wilaya, int likes, int dislikes, int numberReports, Date postingDate) {
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
