package com.example.thwissa

import com.example.thwissa.dataclasses.AgencyRes
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

//
//    @Multipart
    @POST("/signupUser")
    fun executeSignUp(@Body userinfoMap: HashMap<String, Any>): Call<UserRes>

    @POST("/signupAgency")
    fun executeSignUpAgency(@Body userinfoMap: HashMap<String, Any>): Call<AgencyRes>

    @GET("/agencyprofile")
    fun getAgencyData(): Call<AgencyRes>

    @POST("/verifyotp")
    fun postOtp(@Body codeValidation: HashMap<String, String>): Call<Unit>

    @POST("/resendOTPVerification")
    fun resendEmailForResetPassword(@Field("email") email: String): Call<Unit>

    @POST("/forgetpassword/sendmail")
    fun sendEmailForResetPassword(@Field("email") email: String): Call<Unit>

    @GET("/logout")
    fun getLogout(): Call<Unit>

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

