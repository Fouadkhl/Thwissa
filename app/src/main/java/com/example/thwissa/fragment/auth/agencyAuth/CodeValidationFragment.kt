package com.example.thwissa.fragment.auth.agencyAuth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.thwissa.R
import com.example.thwissa.databinding.ConfirmationCodeBinding
import com.example.thwissa.databinding.RegisterScreenBinding
import com.example.thwissa.databinding.VerificationScreenBinding


class CodeValidationFragment : Fragment() {


    lateinit var binding : ConfirmationCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = ConfirmationCodeBinding.inflate(inflater, container, false)

        // test if the code matche the generated code
//        binding.etCode
        binding.btnSignUp.setOnClickListener {
            if (configmationdone()) {
                // TODO: we just navigate to profile fragment change this again
                findNavController().navigate(R.id.action_codeValidationFragment_to_profileFragment)
            }
        }
        return binding.root
    }

    private fun configmationdone(): Boolean {
        // TODO: change the value when connect to the backend
        return true
    }
}