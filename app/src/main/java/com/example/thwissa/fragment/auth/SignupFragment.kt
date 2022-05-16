package com.example.thwissa.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.thwissa.LogService
import com.example.thwissa.databinding.TouristSignUpBinding
import com.example.thwissa.fragment.auth.validation.BaseValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.ConfirmPasswordValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.EmailValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.EmptyValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.PasswordValidator
import retrofit2.Call
import retrofit2.Response

class SignupFragment : Fragment() {

    private lateinit var binding: TouristSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TouristSignUpBinding.inflate(inflater, container, false)



        binding.btnSignUp.setOnClickListener {
            val userinfo = HashMap<String, Any>()
            userinfo.put("name", binding.etName.text.toString())
            userinfo.put("email", binding.etEmail.text.toString())
            userinfo.put("password", binding.etPassword.text.toString())
            userinfo.put("confirmPassword", binding.etConfirmPassword.text.toString())
            userinfo.put("location", binding.etLocation.text.toString())

            var gender = binding.radioGroup.checkedRadioButtonId // 0 for male and 1 for female
            userinfo.put("gender", gender)

            LogService.retrofitService.executeSignUp(userinfo)
                .enqueue(object : retrofit2.Callback<Boolean> {
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        /**handle the sign up  */
//                        boolearn / id
                        Toast.makeText(
                            this@SignupFragment.context,
                            "sign up successefuly",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Toast.makeText(
                            this@SignupFragment.context,
                            "sign up fail",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
        return binding.root
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
        TODO("logic to sign up user")
    }
}




