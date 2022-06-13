package com.example.thwissa.fragment.discuss.classes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiscussUtil {
    private static DiscussUtil instance;
    private final DiscussService jsonPlaceHolder;

    private DiscussUtil(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DiscussService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.jsonPlaceHolder = retrofit.create(DiscussService.class);
    }

    public static DiscussUtil getInstance() {
        if(instance == null){
            instance = new DiscussUtil();
        }
        return instance;
    }

    public DiscussService getJsonPlaceHolder() {
        return jsonPlaceHolder;
    }
}
