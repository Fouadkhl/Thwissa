package com.example.thwissa.dataclasses

import com.google.gson.annotations.SerializedName

data class StoryItem(
    @SerializedName("__v")
    val __v: Int,
    @SerializedName("_id")
    val _id: String,
    @SerializedName("addAT")
    val addAT: String,
    @SerializedName("dislike")
    val dislike: Int,
    @SerializedName("idLocation")
    val idLocation: String,
    @SerializedName("idUser")
    val idUser: String,
    @SerializedName("like")
    val like: Int,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("report")
    val report: Int,
    @SerializedName("userlocation")
    val userlocation: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("userpicture")
    val userpicture: String
)