package com.example.thwissa.dataclasses

import com.google.gson.annotations.SerializedName

data class AgencySignUPReq(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("confirmepassword")
    val confirmePassword: String,
    @SerializedName("phonenumber")
    val phoneNumber: String,
    @SerializedName("picture")
    val url: String
)