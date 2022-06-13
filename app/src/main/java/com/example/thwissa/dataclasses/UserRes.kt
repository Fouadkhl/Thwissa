package com.example.thwissa.dataclasses

import com.google.gson.annotations.SerializedName
import java.util.*

data class UserRes(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("role")
    val role: String ,
    @SerializedName("picture")
    val picture : String

//    val id : String ,
//    val name: String ,
//    val email: String,
//    val location : String ,
//    val PhoneNumber : Int ,
//    val picture : String ,
//    val sex : String ,
//    val birthdayDate : String ,
//    val role : String
)
