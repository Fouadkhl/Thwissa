package com.example.thwissa.dataclasses

data class Data(
    val __v: Int,
    val _id: String,
    val date: String,
    val destination: String,
    val dislikes: List<Any>,
    val likes: List<Any>,
    val maxduration: Int,
    val maxprice: Int,
    val meetingplace: String,
    val minduration: Int,
    val minprice: Int,
    val pictures: List<String>,
    val replynumber: Int,
    val savedByCurrentUser: Boolean,
    val tags: List<Any>,
    val text: String,
    val tripDate: String,
    val userid: String,
    val userlocation: String,
    val username: String,
    val userpicture: String
)