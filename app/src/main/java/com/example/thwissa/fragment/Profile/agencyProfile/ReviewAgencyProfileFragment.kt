package com.example.thwissa.fragment.Profile.agencyProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thwissa.R
import com.example.thwissa.databinding.FragmentReviewAgencyProfileBinding
import com.example.thwissa.databinding.FragmentReviewsBinding

class ReviewAgencyProfileFragment : Fragment() {

    lateinit var binding  : FragmentReviewAgencyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReviewAgencyProfileBinding.inflate(inflater , container ,false )

        val reviewText  =binding.etAddReview.text.toString()
        val rating  = binding.ratingBarAgencyProfile.rating
        binding.btnShareRating.setOnClickListener {
            uploadRating(reviewText , rating)
        }


        return binding.root
    }

    private fun uploadRating(reviewText: String, rating: Float) {

    }


}