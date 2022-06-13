package com.example.thwissa.fragment.discuss.classes;

import com.example.thwissa.fragment.newsfragment.classes.Replies;
import com.example.thwissa.fragment.newsfragment.classes.Reply;
import com.example.thwissa.fragment.newsfragment.classes.Thereply;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface DiscussService {

    String BASE_URL = "http://192.168.43.248:5000";
    // String BASE_URL = "http://192.168.43.157:5000"; //

    // Questions

    @GET("discuss/questions")
    Call<Discusses> getAllQuestions();

    @GET("discuss/questions/{id}")
    Call<ReceivedDiscuss> getQuestionById(@Path("id") String questionId);

    @Multipart
    @POST("discuss/questions")
    Call<ReceivedDiscuss> postQuestion(@PartMap HashMap<String, RequestBody> body,
                                       @Part MultipartBody.Part picture);

    @DELETE("discuss/questions/{id}")
    Call<ReceivedDiscuss> deleteQuestion(@Path("id") String questionId);

    // Replies

    @GET("discuss/questions/{id}/reply")
    Call<Replies> getAllReplies(@Path("id") String id);

    @GET("discuss/questions/{id}/reply/{replyId}")
    Call<Thereply> getReplyById(@Path("id") String id, @Path("replyId") String replyId);

    @POST("discuss/questions/{id}/reply")
    Call<Thereply> postReply(@Path("id") String id, @Body Reply reply);

    @DELETE("discuss/questions/{id}/reply/{replyId}")
    Call<Thereply> deleteReply(@Path("id") String questionId, @Path("replyId") String replyId);

    //reactions

    @POST("discuss/questions/{tripId}/like")
    Call<Void> likePost(@Path("tripId") String tripId);

    @POST("discuss/questions/{tripId}/dislike")
    Call<Void> dislikePost(@Path("tripId") String tripID);

    @POST("/discuss/questions/{id}/favorie")
    Call<Void> bookMark(@Path("id") String id);

    @POST("/discuss/questions/{tripId}/reply/{replyId}/like")
    Call<Void> likeReply(@Path("tripId") String tripId, @Path("replyId") String replyId);

    @POST("/discuss/questions/{tripId}/reply/{replyId}/dislike")
    Call<Void> dislikeReply(@Path("tripId") String tripId, @Path("replyId") String replyId);
}
