package com.example.thwissa

import com.example.thwissa.dataclasses.AgencyRes
import com.example.thwissa.dataclasses.UserRes
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

// TODO: change tthis

private const val BASE_URL ="http://192.168.43.248:5000"

interface RetrofitInterface {
    @POST("/login")
    fun executeLogIn(@Body userinfoMap:HashMap<String,String>) : Call<UserRes>

    @POST("/signupUser")
    fun executeSignUp(@Body userinfoMap:HashMap<String,Any>) : Call<UserRes>

    @POST("/signupAgency")
    fun executeSignUpAgency(@Body userinfoMap:HashMap<String,Any>) : Call<AgencyRes>

//    @PATCH("")
//    @PUT

}

/** set up retrofit */
  private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


object LogService {
    val retrofitService : RetrofitInterface by lazy { retrofit.create(RetrofitInterface::class.java) }


    /**
     * @param serviceType pass the interface
     * @return return  the service
     */
    fun  <T> buildService(serviceType : Class<T> ) : T {
        return  retrofit.create(serviceType)
    }
}

