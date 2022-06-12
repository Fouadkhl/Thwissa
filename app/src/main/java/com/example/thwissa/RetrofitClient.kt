package com.example.thwissa

import com.example.thwissa.dataclasses.AgencyRes
import com.example.thwissa.dataclasses.FollowMessage
import com.example.thwissa.dataclasses.StoryData
import com.example.thwissa.dataclasses.UserRes
import com.example.thwissa.utils.MyApp
import com.example.thwissa.utils.setCookieStore
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

// TODO: change tthis

private const val BASE_URL = "http://192.168.43.248:5000"

interface RetrofitInterface {
    @POST("/login")
    fun executeLogIn(@Body userinfoMap: HashMap<String, String>): Call<UserRes>

    @Multipart
    @POST("/signupUser")
    fun executeSignUp(
        @PartMap data: HashMap<String, RequestBody>,
        @Part photo: MultipartBody.Part?
    ): Call<UserRes>

    @Multipart
    @POST("/signupAgency")
    fun executeSignUpAgency(
        @PartMap data: HashMap<String, RequestBody>,
        @Part photo: MultipartBody.Part?
    ): Call<AgencyRes>

    @Multipart
    @PATCH("/agencyprofiledit")
    fun updateAgencyProfile(
        @PartMap data: HashMap<String, RequestBody>,
        @Part photo: MultipartBody.Part?
    ): Call<Unit>

    @Multipart
    @PATCH("/signupAgencyf")
    fun uploadAgencyDocuments(
        @Part documents: List<MultipartBody.Part>
    ): Call<Unit>

    @POST("/verifyotp")
    fun postOtp(@Body codeValidation: HashMap<String, String>): Call<Unit>

    @POST("/resendOTPVerification")
    fun resendEmailForResetPassword(@Field("email") email: String): Call<Unit>

    @POST("/forgetpassword/sendmail")
    fun sendEmailForResetPassword(@Field("email") email: String): Call<Unit>

    @GET("/logout")
    fun getLogout(): Call<Unit>

    // TODO: profile section

    @GET("/agencyprofile")
    fun getAgencyData(): Call<AgencyRes>


    @POST("/agency/{agencyid}/follow")
    fun followOrUnfollowAgency (@Path("agencyid" ) agencyid :  String  ) : Call<FollowMessage>

    @Headers("Content-Type: application/json")
    @PATCH("/agencyprofiledit")
    fun updadateDescription(@Body description : HashMap<String, String>) : Call<Unit>

    @GET("/agency/{agencyId}/reviews")
//    fun getAllReviews(@Path("agencyId") agencyid: String) : Call<>

    // TODO: story section

    @Multipart
    @POST("/story/{idLocation}")
    fun uploadStory(
        @Path("idLocation") location: String,
        @Part picture: MultipartBody.Part
    ): Call<Unit>

    @FormUrlEncoded
    @PATCH("/story/{idLocation}/{idPicture}")
    fun updateStoryData(
        @Path("idLocation") location: String,
        @Path("idPicture") pictureid: String ,
        @Field("like")  nbLike : Int ,
        @Field("dislike")  nbDislike : Int ,
        @Field("report") report : Int
    ) : Call<Void>

//    @GET("/story/{idLocation}")
//fun getStories(@Path("idLocation") location: String): Call<List<StoryItem>>

    @GET("/story/medea")
    fun getStories(): Call<StoryData>

    @DELETE("/story/{idLocation}/{idPicture}")
    fun deletePhoto(
        @Path("idLocation") location: String,
        @Path("idPicture") pictureid: String
    )

    @PATCH("/story/{idLocation}/{idPicture}")
    fun updateStory(
        @Path("idLocation") location: String,
        @Path("idPicture") pictureid: String
    )
    //  to indicate that the image can be any size

//    @POST
//    fun uploadPhoto(@Part image: MultipartBody.Part, @Part("myFile") name: RequestBody)


}

/** set up retrofit */

private val okHttpClient = OkHttpClient()
    .newBuilder()
    .setCookieStore(MyApp.getContext())

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient.build())
    .addConverterFactory(GsonConverterFactory.create())
    .build()


object LogService {

    val retrofitService: RetrofitInterface by lazy { retrofit.create(RetrofitInterface::class.java) }

    /**
     * @param serviceType pass the interface
     * @return return  the service
     */

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}

