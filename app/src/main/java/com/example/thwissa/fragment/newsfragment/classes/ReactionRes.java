package com.example.thwissa.fragment.newsfragment.classes;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ReactionRes {
    @SerializedName("userid")
    public String userid;

    @Override
    public boolean equals(Object o) {
        if(o instanceof  ReactionRes){
            return this.userid.equals(((ReactionRes) o).userid);
        } return  false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid);
    }

    public ReactionRes(String userid){
        this.userid = userid;
    }
}
