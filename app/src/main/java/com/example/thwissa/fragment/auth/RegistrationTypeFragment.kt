package com.example.thwissa.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.thwissa.R
import com.example.thwissa.databinding.RegisterScreenBinding


class RegistrationTypeFragment : Fragment() {


    lateinit var binding: RegisterScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = RegisterScreenBinding.inflate(inflater, container, false)

        binding.btnTravelerRegister.setOnClickListener {
            findNavController().navigate(R.id.action_registrationTypeFragment_to_loginFragment)
        }

        binding.btnAgencyRegister.setOnClickListener {
            findNavController().navigate(R.id.action_registrationTypeFragment_to_agencySignupFragment)
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController(view)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController.popBackStack()
                }
            })
    }
}