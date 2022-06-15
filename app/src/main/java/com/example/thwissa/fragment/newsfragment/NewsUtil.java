package com.example.thwissa.fragment.newsfragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsUtil {

    private static NewsUtil instance;
    private final NewsService jsonPlaceHolder;

    private NewsUtil(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.jsonPlaceHolder = retrofit.create(NewsService.class);
    }

    public void getUrl(){

    }

    public static NewsUtil getInstance() {
        if(instance == null){
            instance = new NewsUtil();
        }
        return instance;
    }


    //static functions

    public static Bitmap urlToBitmap(String picture) {
        return null;
    }

    public static String bitmapToUrl(Bitmap bitmap){return "";}

    public static Bitmap stringToBitmap(String pic) {
        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static void showMessage(Context context, int resId){
        if(context != null) {
            Toast.makeText(context, getString(context, resId), Toast.LENGTH_LONG).show();
        }
    }

    public static String getString(Context context, int resId){
        if(context != null){
            return context.getResources().getString(resId);
        }
        return "";
    }

    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public NewsService getNewsService() {
        return jsonPlaceHolder;
    }

    public static RequestBody createRequestFromString(String s) {
        return RequestBody.create(MultipartBody.FORM, s);
    }

    public static List<MultipartBody.Part> getFilesParts(String partName, List<String> path){
        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        for(String str : path){
            list.add(getFilePart(partName, str));
        }
        return list;
    }

    public static MultipartBody.Part getFilePart(String partName, String path){
        if(path == null) return null;
        File file = new File(path);

        RequestBody requestFile = RequestBody.create(
                MediaType.parse("image/*"),
                file
        );
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static ArrayList<Bitmap> urlsToBitmaps(ArrayList<String> strings){
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        String url;
        Bitmap bt;
        for(String str : strings){
            if(str != null && !str.equals("")) {
                url = NewsService.BASE_URL + "/" + str.split("/")[1];
                try {
                    URL u = new URL(url);
                    try {
                        bt = BitmapFactory.decodeStream(u.openConnection().getInputStream());
                        bitmaps.add(bt);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmaps;
    }
}
