package com.example.thwissa.fragment.Profile.agencyProfile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thwissa.LogService
import com.example.thwissa.databinding.FragmentReviewAgencyProfileBinding
import com.example.thwissa.dataclasses.AgencyReviews
import com.example.thwissa.fragment.homefragment.overview.adapters.TipsAdapter
import com.example.thwissa.fragment.homefragment.overview.adapters.TipsAgencyAdapter
import com.example.thwissa.fragment.homefragment.willaya.Place
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ReviewAgencyProfileFrag"
const val  MAXNUMBER_OF_REVIEW = 10
class ReviewAgencyProfileFragment : Fragment() {

    lateinit var binding: FragmentReviewAgencyProfileBinding

//    var allreviews = ArrayList<AgencyReviews>(MAXNUMBER_OF_REVIEW)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReviewAgencyProfileBinding.inflate(inflater, container, false)


        binding.btnShareRating.setOnClickListener {
            val reviewText = binding.etAddReview.text.toString()
            val rating = binding.ratingBarAgencyProfile.rating
            Log.d(TAG, "rating: "+ rating)
            Log.d(TAG, "review: "+ reviewText)
            uploadRating("62a77ffa56a17a412b471ac3", reviewText, rating)
        }

//        getAllReviews("" , 5f)

        return binding.root
    }

    private fun uploadRating(agencyid: String, reviewText: String, rating: Float) {
        val map = HashMap<String, Any>()
        map.put("text", reviewText)
        map.put("rate", rating)
        LogService.retrofitService.postReview(agencyid, map).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "success:make sure to be honest", Toast.LENGTH_SHORT)
                        .show()
                    updateuiAfterRating()
                } else {
                    Log.d(TAG, "fail: " + response.message() + response.code())
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d(TAG, "fail: " + t.message)
            }
        })
    }



    private fun getCurrentUserRatingIfItAUser(agencyid: String) {
        LogService.retrofitService.getCurrentUserReview(agencyid).enqueue(object : Callback<AgencyReviews> {
            override fun onResponse(call: Call<AgencyReviews>, response: Response<AgencyReviews>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "success:make sure to be honest", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Log.d(TAG, "fail: " + response.message() + response.code())
                }
            }

            override fun onFailure(call: Call<AgencyReviews>, t: Throwable) {
                Log.d(TAG, "fail: " + t.message)
            }
        })
    }

    private fun getAllReviews(agencyid: String , rate : Float) {
        LogService.retrofitService.getAllReviews(agencyid, rate).enqueue(object : Callback<List<AgencyReviews>> {
            override fun onResponse(call: Call<List<AgencyReviews>>, response: Response<List<AgencyReviews>>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "success:make sure to be honest", Toast.LENGTH_SHORT)
                        .show()
                    var allreviews  = response.body() as ArrayList
                    updateReviewRecyclerview(allreviews)

                } else {
                    Log.d(TAG, "fail all agency review: " + response.message() + response.code())
                }
            }

            override fun onFailure(call: Call<List<AgencyReviews>>, t: Throwable) {
                Log.d(TAG, "fail all agency review: " + t.message)
            }
        })

    }

    private fun updateReviewRecyclerview(allreviews: ArrayList<AgencyReviews>) {
        val tipsAdapter = TipsAgencyAdapter(allreviews)
        binding.rvReviewsAgencyProfile.adapter = tipsAdapter
    }

    private fun updateuiAfterRating() {
        binding.llRating.visibility = View.GONE
    }



}