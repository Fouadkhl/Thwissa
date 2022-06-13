package com.example.thwissa.fragment.entertainment;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EntertainmentService {
    String BASE_URL  = "http://192.168.43.248:5000";

    @GET("/intertament/PictureOfTheWeek")
    Call<TopPicsRes> getTopPicsRes();
}
