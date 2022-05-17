package com.example.thwissa.dataclasses

data class UserSignUPReq(
    private val name: String,
    private val email: String,
    private val password : String,
    private val location: String,
    private val gender : String  ,
    private val picture: String ,
    private  val confirmedPassword : String
)