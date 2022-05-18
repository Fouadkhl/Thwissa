package com.example.thwissa.dataclasses

import com.google.gson.annotations.SerializedName

data class UserSignInRequest(
    @SerializedName("email")
    private val email : String ,
    @SerializedName("password")
    private val password : String
)


