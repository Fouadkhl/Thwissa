package com.example.thwissa.dataclasses

data class UserSignUPReq(
    val name: String,
    val email: String,
    val location: String,
    val picture: String ,
    val password : String,
    val gender : String
)