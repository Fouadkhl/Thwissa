package com.example.thwissa.fragment.Profile.agencyProfile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thwissa.LogService
import com.example.thwissa.R
import com.example.thwissa.databinding.FragmentTripPlanAgencyProfileBinding
import com.example.thwissa.dataclasses.LastTrip
import com.example.thwissa.fragment.newsfragment.adapters.ChoosedImagesAdapter
import com.example.thwissa.fragment.newsfragment.classes.Post
import com.example.thwissa.repository.userLocalStore.SPUserData
import com.example.thwissa.utils.Constants
import com.example.thwissa.utils.MyApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "TripPlanAgencyProfileFr"
class TripPlanAgencyProfileFragment : Fragment() {

    lateinit var binding  : FragmentTripPlanAgencyProfileBinding
    lateinit var  sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTripPlanAgencyProfileBinding.inflate(inflater , container , false)
        sharedPreferences = MyApp.getContext()
            .getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)

        val role = sharedPreferences.getString("userRole" , "")
        if (role.equals("Agency")){
            val agencyid = sharedPreferences.getString("agencyid" , "")!!
            Log.d(TAG, "the agency existe :"+ role)
            Log.d(TAG, "agency id " +  agencyid)
            getLastAgencyTrip(agencyid)
        }else{
            Log.d(TAG, "the agency n'exist pas : " +role)
        }
        return binding.root
    }

    private fun getLastAgencyTrip(agencyid: String) {
        LogService.retrofitService.getLastAgencyTrip(agencyid).enqueue(object  : Callback<LastTrip>{
            override fun onResponse(call: Call<LastTrip>, response: Response<LastTrip>) {
                if (response.isSuccessful) {
                    upDateLastTrip(response.body()!!)
                }else {
                    Log.d(TAG, "fail: "+ response.message() + response.code())
                }
            }

            override fun onFailure(call: Call<LastTrip>, t: Throwable) {
                Log.d(TAG, "onfailure" + t.message)
            }
        })
    }

    private fun upDateLastTrip(body: LastTrip) {
        binding.date.text = body.data.date
        binding.destination.text = body.data.destination
        binding.description.text = body.data.text
        binding.price.text = body.data.maxprice.toString()
        binding.period.text = body.data.maxduration.toString()

//        val adapter = ChoosedImagesAdapter()
        // TODO : pictures
//        binding.ViewPagerphotos.adapter = adapter
    }

}