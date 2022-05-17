package com.example.thwissa.fragment.discuss;

public class Discuss {
    /** FIELDS */
    private User user;
    private String wilaya;
    private String discussTexte;
    private int discussImageResource;
    private int likes = 0;
    private int dislikes = 0;
    private int repliesNumber = 0;

    // TEMPORARY (CHANGE THEM LATER )
    boolean liked = false;
    boolean disliked = false;
    boolean saved = false;

    /** CONSTRUCTOR */
    public Discuss(User user, String wilaya, String discussTexte, int discussImageResource, int likes, int dislikes, int repliesNumber) {
        this.user = user;
        this.wilaya = wilaya;
        this.discussTexte = discussTexte;
        this.discussImageResource = discussImageResource;
        this.likes = likes;
        this.dislikes = dislikes;
        this.repliesNumber = repliesNumber;
    }
    public Discuss(User user, String wilaya, String discussTexte, int likes, int dislikes, int repliesNumber) {
        this.user = user;
        this.wilaya = wilaya;
        this.discussTexte = discussTexte;
        this.likes = likes;
        this.dislikes = dislikes;
        this.repliesNumber = repliesNumber;
    }
    // FOR NEW-ADDED DISCUSS
    public Discuss(User user, String wilaya, String discussTexte, int discussImageResource) {
        this.user = user;
        this.wilaya = wilaya;
        this.discussTexte = discussTexte;
        this.discussImageResource = discussImageResource;
    }
    public Discuss(User user, String wilaya, String discussTexte) {
        this.user = user;
        this.wilaya = wilaya;
        this.discussTexte = discussTexte;
    }


    /** METHODS */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWilaya() {
        return wilaya;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }

    public String getDiscussTexte() {
        return discussTexte;
    }

    public void setDiscussTexte(String discussTexte) {
        this.discussTexte = discussTexte;
    }

    public int getDiscussImageResource() {
        return discussImageResource;
    }

    public void setDiscussImageResource(int discussImageResource) {
        this.discussImageResource = discussImageResource;
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

    public int getRepliesNumber() {
        return repliesNumber;
    }

    public void setRepliesNumber(int repliesNumber) {
        this.repliesNumber = repliesNumber;
    }
}
