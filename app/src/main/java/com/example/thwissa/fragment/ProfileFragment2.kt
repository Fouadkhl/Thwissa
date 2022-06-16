package com.example.thwissa.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thwissa.R
import com.example.thwissa.databinding.FragmentProfileBinding
import com.example.thwissa.databinding.ProfileScreenBinding
import com.example.thwissa.utils.Constants
import com.example.thwissa.utils.MyApp


class ProfileFragment2 : Fragment() {


    lateinit var binding: FragmentProfileBinding
    lateinit var  sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater , container , false  )



        return  binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences =  MyApp.getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)

        if (
            sharedPreferences.getString("userRole" , "")!!.equals("Agency")
        ) {
            findNavController().navigate(R.id.action_profileFragment_to_profileFragment2)
        }else if(sharedPreferences.getString("userRole" , "")!!.equals("User")){
            findNavController().navigate(R.id.action_profileFragment_to_agencyProfileFragment)
        }


    }
}
