package com.example.thwissa.fragment.Profile.agencyProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thwissa.Adapter.ViewPagerAdapter
import com.example.thwissa.LogService
import com.example.thwissa.R
import com.example.thwissa.databinding.AgencyProfileBinding
import com.example.thwissa.dataclasses.AgencyRes
import com.example.thwissa.dataclasses.FollowMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgencyProfileFragment : Fragment() {


    lateinit var binding: AgencyProfileBinding
    var descripiton: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = AgencyProfileBinding.inflate(inflater, container, false)

        setUpAgencyData()
        addFragment()

        binding.tvDescription.setOnClickListener {
            launchDescriptionDialog()
        }

        binding.ivEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_agencyProfileFragment_to_editAgencyProfile)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener(
            DescriptionDialog.REQUEST_CODE, this
        ) { requestKey, result ->
            descripiton = result.getString(DescriptionDialog.ADD_DESCRIPTION_BUNDLE_KEY)
                ?: "empty description"
            binding.tvDescription.text = descripiton
        }
    }

    private fun launchDescriptionDialog() {
        val dialogFragment = DescriptionDialog(requireContext())
        dialogFragment.show(
            childFragmentManager,
            DescriptionDialog.TAG
        )
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

                        Toast.makeText(requireContext(), res.id, Toast.LENGTH_SHORT).show()
                        binding.btnFollow.setOnClickListener {
                            followOrUnfollowAgency(res.id, res.nbfollowers)
                        }
                    }

                    Toast.makeText(requireContext(), "req done ", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        response.message() + response.code(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<AgencyRes>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    /** follow or unfollow agency*/
    private fun followOrUnfollowAgency(agencyId: String, nbfollowers: Int) {
        LogService.retrofitService.followOrUnfollowAgency(agencyId)
            .enqueue(object : Callback<FollowMessage> {
                override fun onResponse(
                    call: Call<FollowMessage>,
                    response: Response<FollowMessage>
                ) {
                    if (response.isSuccessful) {
                        val res = response.body()!!.followMessage
                        if (res.equals("Follow sucuss")) {
                            updateFollowButtonText("follow", nbfollowers)
                        } else if (res.equals("infollow sucuss")) {
                            updateFollowButtonText("unfollow", nbfollowers)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "no attr follow message",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "not success" + response.message() + response.code(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<FollowMessage>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "some error is occured" + t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun updateFollowButtonText(followText: String, nbfollowers: Int) {
        binding.btnFollow.text = followText
        binding.tvNombreFollowers.text = nbfollowers.toString()
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