package com.example.thwissa.dataclasses

import com.google.gson.annotations.SerializedName

data class AgencyRes(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("phonenumber")
    val Phonenumber: String,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("role")
    val role: String ,
    @SerializedName("nbfollowers")
    val nbfollowers: Int,
    @SerializedName("picture")
    val picture : String  ,
    @SerializedName("NbOfTrips")
    val nbofTrips : Int

//    @SerializedName("picture")
//    val description :String ,
//    @SerializedName("picture")
//    val picture :String ,
//    @SerializedName("isvalidate")
//    val isvalidate :Boolean
//
)