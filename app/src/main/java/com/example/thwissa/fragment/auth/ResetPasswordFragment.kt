package com.example.thwissa.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.thwissa.LogService
import com.example.thwissa.R
import com.example.thwissa.databinding.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordFragment : Fragment() {


    lateinit var binding : FragmentResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(inflater , container , false )
        // TODO: post code and password then get the role and navigate to  specific profile

        return binding.root
    }


    // TODO: to edit
    private fun resetPasswordCodeAndNewPassword(code : String  ,newpassword : String ) {
        LogService.retrofitService.sendEmailForResetPassword(code)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {

                        findNavController().navigate(R.id.action_resetPasswordFragment_to_profileFragment)
                        findNavController().navigate(R.id.action_resetPasswordFragment_to_agencyProfileFragment)
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