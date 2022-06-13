package com.example.thwissa.fragment.entertainment;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EntertainmentUtil {
    private static EntertainmentUtil instance;
    private EntertainmentService service;

    private EntertainmentUtil(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EntertainmentService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = retrofit.create(EntertainmentService.class);
    }

    public static EntertainmentUtil getInstance() {
        if(instance == null){
            instance = new EntertainmentUtil();
        }
        return instance;
    }

    public EntertainmentService getService() {
        return service;
    }
}
