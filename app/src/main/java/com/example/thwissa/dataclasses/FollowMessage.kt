package com.example.thwissa.dataclasses

import com.google.gson.annotations.SerializedName

data class FollowMessage(
    @SerializedName("msg")
    val followMessage: String
)
