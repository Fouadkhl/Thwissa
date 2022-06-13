package com.example.thwissa.fragment.newsfragment;

import com.example.thwissa.fragment.newsfragment.classes.Post;
import com.example.thwissa.fragment.newsfragment.classes.Posts;
import com.example.thwissa.fragment.newsfragment.classes.Replies;
import com.example.thwissa.fragment.newsfragment.classes.Thereply;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface NewsService {

     // String BASE_URL = "http://192.168.43.220:5000"; // MY PHONE
    // String BASE_URL = "http://192.168.1.17:5000"; // HOME
    String BASE_URL = "http://192.168.43.248:5000"; //FOUAD
     // String BASE_URL = "http://192.168.43.157:5000";

    // trips
    @Multipart
    @POST("/news/trips")
    Call<Post> postTrip(@PartMap HashMap<String, RequestBody> body, @Part List<MultipartBody.Part> pictures);

    @GET("/news/trips/{id}")
    Call<Post> getTripById(@Path("id") String _id);

    @GET("/news/trips")
    Call<Posts> getAllTrips();

    @Multipart
    @PATCH("/news/trips/{id}")
    Call<Post> updateTrip(@Path("id") String id, @PartMap HashMap<String, RequestBody> body,
                          @Part List<MultipartBody.Part> pictures);

    @DELETE("/news/trips/{id}")
    Call<Post> deleteTrip(@Path("id") String tripId);

    // trips replies
    @Multipart
    @POST("/news/trips/{id}/reply")
    Call<Thereply> postReply(@Path("id") String tripId,
                             @PartMap HashMap<String, RequestBody> reply,
                             @Part MultipartBody.Part image);

    @GET("news/trips/{tripId}/reply/{replyId}")
    Call<Thereply> getReplyById(@Path("tripId") String tripId, @Path("replyId") String replyId);

    @GET("news/trips/{tripid}/reply")
    Call<Replies> getAllReplies(@Path("tripid") String tripId);

    @Multipart
    @PATCH("news/trips/{tripId}/reply/{replyId}")
    Call<Thereply> updateReply(@Path("tripId") String tripId, @Path("replyId") String replyId,
                               @PartMap HashMap<String, RequestBody> body, @Part MultipartBody.Part picture);

    @DELETE("news/trips/{tripId}/reply/{replyId}")
    Call<Thereply> deleteReply(@Path("tripId") String tripId, @Path("replyId") String replyId);

    // reactions

    @POST("news/trips/{tripId}/like")
    Call<Void> likeTrip(@Path("tripId") String tripId);

    @POST("news/trips/{tripId}/dislike")
    Call<Void> dislikeTrip(@Path("tripId") String tripID);

    @POST("/discuss/trips/{tripId}/reply/{replyId}/like")
    Call<Void> likeReply(@Path("tripId") String tripId, @Path("replyId") String replyId);

    @POST("/discuss/trips/{tripId}/reply/{replyId}/dislike")
    Call<Void> dislikeReply(@Path("tripId") String tripId, @Path("replyId") String replyId);

    @POST("/news/trips/{id}/favorie")
    Call<Void> bookMark(@Path("id") String id);
}