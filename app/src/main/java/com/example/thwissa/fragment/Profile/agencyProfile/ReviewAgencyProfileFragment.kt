package com.example.thwissa.fragment.Profile.agencyProfile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.thwissa.LogService
import com.example.thwissa.databinding.FragmentReviewAgencyProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ReviewAgencyProfileFrag"

class ReviewAgencyProfileFragment : Fragment() {

    lateinit var binding: FragmentReviewAgencyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReviewAgencyProfileBinding.inflate(inflater, container, false)

        val reviewText = binding.etAddReview.text.toString()
        val rating = binding.ratingBarAgencyProfile.rating
        binding.btnShareRating.setOnClickListener {
            uploadRating("", reviewText, rating)
        }


        return binding.root
    }

    private fun uploadRating(agencyid: String, reviewText: String, rating: Float) {
        val map = HashMap<String, Any>()
        map.put("text", reviewText)
        map.put("rate", rating)
        LogService.retrofitService.postReview(agencyid, map).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "make sure to be honest", Toast.LENGTH_SHORT)
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

    private fun getCurrentUserRatingIfItAUser() {

    }

    private fun updateuiAfterRating() {
        binding.llRating.visibility = View.GONE
    }


}