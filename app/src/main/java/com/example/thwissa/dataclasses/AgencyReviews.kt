package com.example.thwissa.dataclasses

import com.google.gson.annotations.SerializedName

data class AgencyReviews(
    @SerializedName("")
    val name  : String  ,
    @SerializedName("")
    val  willay : String ,
    @SerializedName("")
    val date : String ,
    @SerializedName("")
    val rating : Float  ,
    @SerializedName("")
    val ratingText : String
)
