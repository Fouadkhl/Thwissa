package com.example.thwissa.fragment.Profile.agencyProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.thwissa.Adapter.ViewPagerAdapter
import com.example.thwissa.LogService
import com.example.thwissa.databinding.AgencyProfileBinding
import com.example.thwissa.dataclasses.AgencyRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgencyProfileFragment : Fragment() {


    lateinit var binding: AgencyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = AgencyProfileBinding.inflate(inflater, container, false)
        setUpAgencyData()
        addFragment()



        return binding.root
    }

    private fun setUpAgencyData() {
        LogService.retrofitService.getAgencyData().enqueue(object : Callback<AgencyRes> {
            override fun onResponse(call: Call<AgencyRes>, response: Response<AgencyRes>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    res?.let {
                        updateAgencyUi(
                            res.name,
                            res.email,
                            res.Phonenumber,
                            res.location,
                            res.rate,
                            res.nbfollowers
                        )
                    }

                    Toast.makeText(requireContext(), "req done ", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), response.message() +response.code(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AgencyRes>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateAgencyUi(
        name: String,
        email: String,
        phonenumber: String,
        location: String,
        rate: Int,
        nbfollowers: Int
    ) {
        binding.apply {
            agencyName.text = name
            agencyEmail.text = email
            phoneNumber.text = phonenumber
            tvLocationAgency.text = location
            ratingBar.rating = rate.toFloat()
            tvRatingNumber.text = rate.toString()
            tvNombreFollowers.text = nbfollowers.toString()

        }
    }


    private fun addFragment() {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(MapAgencyProfileFragment(), "map")
        adapter.addFragment(ReviewAgencyProfileFragment(), "reviews")
        adapter.addFragment(TripPlanAgencyProfileFragment(), "Trip Plan")
        binding.vpAgencyProfile.adapter = adapter
        binding.tablayout.setupWithViewPager(binding.vpAgencyProfile)
    }

}