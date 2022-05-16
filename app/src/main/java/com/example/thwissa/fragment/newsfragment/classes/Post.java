package com.example.thwissa.fragment.newsfragment.classes;


public class Post {
    public int profile_id;
    public int diff;
    public int replies_num;
    public int[] imgIds = null;
    public String userName;
    public String text;
    public String location;
    public Post[] replies = null;

    public boolean bookmarkClicked = false;
    public boolean upButtonClicked = false;
    public boolean downButtonClicked = false;

    public Post(int profile_id, int diff, int replies_num,
                int[] imgIds, String userName, String text, String location){

        this.profile_id = profile_id;
        this.diff = diff;
        this.replies_num = replies_num;
        this.imgIds = imgIds;
        this.userName = userName;
        this.text = text;
        this.location = location;
    }

}
