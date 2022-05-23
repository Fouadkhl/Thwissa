package com.example.thwissa.fragment.Profile.agencyProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thwissa.Adapter.ViewPagerAdapter
import com.example.thwissa.R
import com.example.thwissa.databinding.AgencyProfileBinding

class AgencyProfileFragment : Fragment() {


    lateinit var binding : AgencyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = AgencyProfileBinding.inflate(inflater ,  container, false)
        addFragment()



        return binding.root
    }

    private fun addFragment(){
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(MapAgencyProfileFragment() , "map")
        adapter.addFragment(ReviewAgencyProfileFragment() , "reviews")
        adapter.addFragment(TripPlanAgencyProfileFragment() , "Trip Plan")
        binding.vpAgencyProfile.adapter = adapter
        binding.tablayout.setupWithViewPager(binding.vpAgencyProfile)
    }

}