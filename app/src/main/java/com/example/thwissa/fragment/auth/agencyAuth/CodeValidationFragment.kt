package com.example.thwissa.fragment.auth.agencyAuth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thwissa.LogService
import com.example.thwissa.R
import com.example.thwissa.databinding.ConfirmationCodeBinding
import com.example.thwissa.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CodeValidationFragment : Fragment() {


    lateinit var binding: ConfirmationCodeBinding
    var isconfirmed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = ConfirmationCodeBinding.inflate(inflater, container, false)

        // test if the code matche the generated code
//        binding.etCode
        val map = HashMap<String, String>()
        map.apply {
            put("otp", binding.etCode.text.toString())
            arguments?.getString(Constants.USER_ID)?.let { put("userid", it) }
        }

        binding.btnSignUp.setOnClickListener {
            if (configmationdone(map)) {
                val role = arguments?.getInt(Constants.USER_ROLE)
                if (role != null) {
                    if (role == 1) {
                        findNavController().navigate(R.id.action_codeValidationFragment_to_profileFragment)
                    }else{
                        findNavController().navigate(R.id.action_codeValidationFragment_to_agencyProfileFragment)
                    }
                }else {
                    Toast.makeText(
                        requireContext(),
                        "data not passed to code validation fragment",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.btnResendConfirmationCode.setOnClickListener {
            val email = arguments?.getString(Constants.SIGNUP_EMAIL)
            resendEmailCode(email!!)
        }
        return binding.root
    }

    private fun resendEmailCode(email: String) {
            LogService.retrofitService.resendEmailForResetPassword(email).enqueue(object : Callback<Unit>{
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (! response.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            response.message() + response.code(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        " some thing wrong check your internet connection ",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

    private fun configmationdone(map: HashMap<String, String>): Boolean {

        LogService.retrofitService.postOtp(map).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    isconfirmed = true
                    Toast.makeText(requireContext(), "registration done", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "response is not succesfull ${response.code()} ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(requireContext(), "response fail" + t.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })
        return isconfirmed
    }

//    private fun getValidationData(): HashMap<String, String> {
//
//        return map
//    }

}