package com.example.thwissa.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.thwissa.R
import com.example.thwissa.databinding.ProfileScreenBinding


@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {

    lateinit var binding: ProfileScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_screen, container, false)
        setUpUiForProfile()

        binding.apply {
            settings.ivNextMyTrip.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_profileFragment_to_settingsFragment)
            }

            myTrip.ivNextMyTrip.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_profileFragment_to_editProfile)
            }
        }

        return binding.root
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setUpUiForProfile() {
        binding.apply {
            myTrip.apply {
                colorBack = resources.getColor(R.color.colorback1)
                photo = resources.getDrawable(R.drawable.ic_person_icon)
            }
            settings.apply {
                colorBack = resources.getColor(R.color.colorback2)
                photo = resources.getDrawable(R.drawable.ic_baseline_settings_24)

            }
            contactUs.apply {
                colorBack = resources.getColor(R.color.colorback4)
                photo = resources.getDrawable(R.drawable.ic_baseline_phone_forwarded_24)
            }
            feedBack.apply {
                colorBack = resources.getColor(R.color.colorback5)
                photo = resources.getDrawable(R.drawable.ic_baseline_feedback_24)
            }
            favorite.apply {
                colorBack = resources.getColor(R.color.colorback3)
                photo = resources.getDrawable(R.drawable.ic_baseline_bookmark_24)
            }
            logOut.apply {
                colorBack = resources.getColor(R.color.colorback6)
                photo = resources.getDrawable(R.drawable.ic_baseline_logout_24)
            }
        }
    }
}
