package com.example.thwissa.repository.userLocalStore

import android.content.Context
import android.content.SharedPreferences
import com.example.thwissa.dataclasses.UserRes
import java.util.*


const val SHARED_PREFERENCES = "userDetails"
class SPUserData {


    private val mySharedPrefenreces : SharedPreferences

    constructor(context: Context) {
        this.mySharedPrefenreces = context.getSharedPreferences(SHARED_PREFERENCES , 0)
    }

    /**
     * @param user the user logged in
     */

    fun StoreUserData(user : UserRes) {
        val spEditor = mySharedPrefenreces.edit()
        spEditor.apply{
            putString("name", user.name)
            putString("email", user.email)
            putString("userid", user.id)
            putString("userLocation", user.location)
//            putString("userPicture", user.picture)
            putString("userRole", user.role )
        }
        spEditor.commit()
    }


    /**
     * @return user from the share preferences
     */

    fun getLogInUser() : UserRes{
        val name = mySharedPrefenreces.getString("name" , "")
        val email = mySharedPrefenreces.getString("email" , "")
        val userid = mySharedPrefenreces.getString("userid" , "")
        val userLocation = mySharedPrefenreces.getString("userLocation" , "")
        val userPicture = mySharedPrefenreces.getString("userPicture" , "")
        val userRole = mySharedPrefenreces.getString("userRole" , "")
        return UserRes(userid!! , name!! , email!!, userLocation!!,"user")
    }

    /**
     * @param Boolean if user login
     */
    fun setUserLoggedIn(loggedin: Boolean) {
        val spEditor = mySharedPrefenreces.edit()
        spEditor.putBoolean("loggedIn" , loggedin)
        spEditor.commit()
    }

    /**
     * clear all suer data
     */
    fun clearUserData ()  {
        val spEditor = mySharedPrefenreces.edit()
         spEditor.clear()
         spEditor.commit()
    }

    /**
     *  if not logged in return true else return false
     */
    fun  getUserLoggedIn() = if (mySharedPrefenreces.getBoolean("loggedIn" , false)== true) true else false
}