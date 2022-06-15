package com.example.thwissa.repository.userLocalStore

import android.content.Context
import android.content.SharedPreferences
import com.example.thwissa.dataclasses.AgencyRes
import com.example.thwissa.dataclasses.UserRes
import com.example.thwissa.utils.Constants.SHARED_PREFERENCES
import java.util.*


class SPUserData(context: Context) {

    private val mySharedPreferences : SharedPreferences

    fun getSharedPreferences() = mySharedPreferences

    init {
        this.mySharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES , Context.MODE_PRIVATE)
    }

    /**
     * @param user the user logged in
     */

    fun StoreUserData(user : UserRes) {
        val spEditor = mySharedPreferences.edit()
        spEditor.apply{
            putString("name", user.name)
            putString("email", user.email)
            putString("userid", user.id)
            putString("userLocation", user.location)
            putString("userPicture", user.picture)
            putString("userRole", user.role )
        }
        spEditor.apply()
    }

    /**
     * store agency data
     */
    fun storeAgencyToGetData(user : AgencyRes) {
        val spEditor = mySharedPreferences.edit()
        spEditor.apply{
            putString("agencyId", user.id)
            putString("userRole", user.role)
//            putBoolean("isvalidate" , user.isvalidate)
        }
        spEditor.apply()
    }

    fun getAgencyId() = mySharedPreferences.getString("agencyId" , "error")
    fun getUserRole() = mySharedPreferences.getString("userRole" , "error")

    /**
     * @return user from the share preferences
     */
    fun getLoggedInUser() : UserRes{
        val name = mySharedPreferences.getString("name" , "")
        val email = mySharedPreferences.getString("email" , "")
        val userid = mySharedPreferences.getString("userid" , "")
        val userLocation = mySharedPreferences.getString("userLocation" , "")
        val userPicture = mySharedPreferences.getString("userPicture" , "")
        val userRole = mySharedPreferences.getString("userRole" , "")
        return UserRes(userid!! , name!! , email!!, userLocation!!,"user" , userPicture!!)
    }

    /**
     * @param Boolean if user login
     */
    fun setUserLoggedIn(loggedin: Boolean) {
        val spEditor = mySharedPreferences.edit()
        spEditor.putBoolean("loggedIn" , loggedin)
        spEditor.apply()
    }

    /**
     * clear all suer data
     */
    fun clearUserData ()  {
        val spEditor = mySharedPreferences.edit()
         spEditor.clear()
         spEditor.apply()
    }

    /**
     *  if not logged in return true else return false
     */
    fun  getUserLoggedIn() = mySharedPreferences.getBoolean("loggedIn" , false)
}