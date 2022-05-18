package com.example.thwissa.dataclasses

import com.google.gson.annotations.SerializedName

data class UserSignUPReq(
    @SerializedName("name")
    private val name: String,
    @SerializedName("email")
    private val email: String,
    @SerializedName("password")
    private val password : String,
    @SerializedName("location")
    private val location: String,
    @SerializedName("confirmepassword")
    private  val confirmePassword : String
)