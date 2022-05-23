package com.example.thwissa.dataclasses

import com.google.gson.annotations.SerializedName

data class AgencyRes(
    @SerializedName("phonenumber")
    val Phonenumber: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nbfollowers")
    val nbfollowers: Int,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("role")
    val role: String ,
    @SerializedName("picture")
    val description :String ,
    @SerializedName("picture")
    val picture :String ,
    @SerializedName("isvalidate")
    val isvalidate :Boolean
)