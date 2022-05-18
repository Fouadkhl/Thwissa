package com.example.thwissa.fragment.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thwissa.LogService
import com.example.thwissa.R
import com.example.thwissa.databinding.TouristSignUpBinding
import com.example.thwissa.dataclasses.UserRes
import com.example.thwissa.fragment.auth.validation.BaseValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.ConfirmPasswordValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.EmailValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.EmptyValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.PasswordValidator
import com.example.thwissa.repository.userLocalStore.SPUserData
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap

class SignupFragment : Fragment() {

    private lateinit var binding: TouristSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TouristSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       // getDataAndSignUpUser()
        signUpUser()
    }




    /**
     * get the data from xml control it and pass it to register
     */
    private fun getDataAndSignUpUser() {
        // get the data from xml
        var name = binding.etName.text.toString()
        var email = binding.etEmail.text.toString()
        var password = binding.etPassword.text.toString()
        var confirmPassword = binding.etPassword.text.toString()
        var location = binding.etLocation.text.toString()
        var gender = if (binding.rbtnMale.isChecked) "male" else "female"

        val usernameEmptyValidation = EmptyValidator(name).validate()
        binding.etName.error =
            if (!usernameEmptyValidation.isSuccess) getString(usernameEmptyValidation.message) else null
       val locationEmptyValidation = EmptyValidator(location).validate()
            binding.etLocation.error =
                if (!usernameEmptyValidation.isSuccess) getString(locationEmptyValidation.message) else null


        val emailValidations = BaseValidator.validate(
            EmptyValidator(email), EmailValidator(email)
        )
        binding.etEmail.error = if (!emailValidations.isSuccess) getString(emailValidations.message) else null


        val passwordValidations = BaseValidator.validate(
            EmptyValidator(password), PasswordValidator(password)
        )
        binding.etPassword.error = if (!passwordValidations.isSuccess) getString(passwordValidations.message) else null

        val confirmPasswordValidations = BaseValidator.validate(
            EmptyValidator(confirmPassword), ConfirmPasswordValidator(confirmPassword , password)
        )
        binding.etConfirmPassword.error = if (!confirmPasswordValidations.isSuccess) getString(passwordValidations.message) else null


        if ( usernameEmptyValidation.isSuccess &&
                emailValidations.isSuccess&&
                confirmPasswordValidations.isSuccess){
                    signUpUser()
        }
        // TODO: get the image from the internal storage and pass it and return request object from this fun
        // TODO: set setUserloggedin to true

    }

    private fun signUpUser() {
        binding.btnSignUp.setOnClickListener {
            val userinfo = HashMap<String, Any>()
            userinfo.put("name", binding.etName.text.toString())
            userinfo.put("email", binding.etEmail.text.toString())
            userinfo.put("password", binding.etPassword.text.toString())
            userinfo.put("location", binding.etLocation.text.toString())
//            var gender = binding.radioGroup.checkedRadioButtonId // 0 for male and 1 for female
//            userinfo.put("gender", gender.toString())
            userinfo.put("confirmepassword", binding.etConfirmPassword.text.toString())

            val spUserData = SPUserData(requireContext())
            LogService.retrofitService.executeSignUp(userinfo)
                .enqueue(object : retrofit2.Callback<UserRes> {
                    override fun onResponse(call: Call<UserRes>, response: Response<UserRes>) {
                        /**handle the sign up  */
//                        boolearn / id

                        if (response.isSuccessful){
                            Toast.makeText(
                                this@SignupFragment.context,
                                "sign up successefuly",
                                Toast.LENGTH_SHORT
                            ).show()

                            val res= response.body()
                            // TODO: save data in external storage and set the user loggedin
                            spUserData.setUserLoggedIn(true)
                            spUserData.StoreUserData(res!!)
                            //redirect the to his profile
                            findNavController().navigate(R.id.action_signupFragment_to_profileFragment)

                        }else{
                            Toast.makeText(
                                this@SignupFragment.context,
                                "error in response ${response.message()}, ${response.code()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<UserRes>, t: Throwable) {
                        Toast.makeText(
                            this@SignupFragment.context,
                            "sign up fail ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.i("signupuser", "onFailure:${t.message} ")
                    }
                }
                )
        }
    }
}




