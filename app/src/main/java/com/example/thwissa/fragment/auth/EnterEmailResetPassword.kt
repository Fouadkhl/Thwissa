package com.example.thwissa.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thwissa.LogService
import com.example.thwissa.R
import com.example.thwissa.databinding.FragmentResetPasswordEnterEmailBinding
import com.example.thwissa.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterEmailResetPassword : Fragment() {


    lateinit var binding: FragmentResetPasswordEnterEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordEnterEmailBinding.inflate(inflater, container, false)

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmailResetPassword.text.toString()
            resetPassword(email)

        }
        return binding.root
    }

    private fun resetPassword(email: String) {
        LogService.retrofitService.sendEmailForResetPassword(email)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        findNavController().navigate(R.id.action_enterEmailResetPassword_to_resetPasswordFragment, )
                    }else{
                        Toast.makeText(
                            requireContext(),
                            "something wrong in response" + response.message(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
}