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
import com.example.thwissa.databinding.LogInScreenBinding
import com.example.thwissa.dataclasses.UserRes
import com.example.thwissa.fragment.auth.validation.BaseValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.EmailValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.EmptyValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.PasswordValidator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class   LoginFragment : Fragment() {

    private lateinit var binding  : LogInScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding= LogInScreenBinding.inflate(inflater, container, false)


        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }



        // get data to login user
        binding.btnSignIn.setOnClickListener{
            val userinfo_map = HashMap<String , String>()
             userinfo_map.put("email" , binding.etEmail.text.toString())
             userinfo_map.put("password" , binding.etPassword.text.toString())
             LogService.retrofitService.executeLogIn(userinfo_map).enqueue(object : Callback<UserRes>{
                 override fun onResponse(
                     call: Call<UserRes>,
                     response: Response<UserRes>
                 ) {
                        /** handle the sign in */

                     Toast.makeText(
                         this@LoginFragment.context,
                         "sign in successefuly",
                         Toast.LENGTH_SHORT
                     ).show()
                 }
                 override fun onFailure(call: Call<UserRes>, t: Throwable) {
                     Toast.makeText(this@LoginFragment.context, "failure", Toast.LENGTH_SHORT).show()
                 }
             })
        }
        return binding.root
    }

    private fun getDataForLoginUser() {
       val email =  binding.etEmail.text.toString()
       val password = binding.etPassword.text.toString()

        val emailValidations = BaseValidator.validate(
            EmptyValidator(email), EmailValidator(email)
        )

        binding.etEmail.error = if (!emailValidations.isSuccess) getString(emailValidations.message) else null

        val passwordValidations = BaseValidator.validate(
            EmptyValidator(password), PasswordValidator(password)
        )

        binding.etPassword.error = if (!passwordValidations.isSuccess) getString(passwordValidations.message) else null

        if (emailValidations.isSuccess && passwordValidations.isSuccess) {
            signinUser()
        }

    }

    private fun signinUser() {
        TODO("insert here when the user click sign up btn")
    }

    // TODO: in the case of log in we will set it to new user
    // TODO: set setUserloggedin of share preferences to true

}