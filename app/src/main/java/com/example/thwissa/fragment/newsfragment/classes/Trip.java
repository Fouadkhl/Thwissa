package com.example.thwissa.fragment.newsfragment.classes;

public class Trip {
    public String picture;
    public String description;
    public float rating;
    public String _id;

    public Trip(mPost post){
        this.picture = post.pictures.get(0);
        this.description = post.text;
        this.rating = post.rate();
        this._id = post._id;
    }
}
