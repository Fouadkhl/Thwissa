package com.example.thwissa.fragment.auth.agencyAuth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.thwissa.R
import com.example.thwissa.databinding.FragmentAgencySignUpBinding
import com.example.thwissa.fragment.auth.agencyAuth.certaticationverificaion.CertaficationVerificationViewModel
import com.example.thwissa.fragment.auth.validation.BaseValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.*
import com.example.thwissa.utils.Constants

class AgencySignupFragment : Fragment() {

    private lateinit var binding: FragmentAgencySignUpBinding
    private val viewModelCertafications: CertaficationVerificationViewModel by activityViewModels()
    var curFile: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgencySignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val data: Intent? = result.data
                    data?.data?.let {
                        curFile = it
                        //then we set the image in the image view
                        binding.ivShapableAgencySignUp.setImageURI(it)

                    }
                }
            }

        binding.ivShapableAgencySignUp.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                resultLauncher.launch(it)
            }
        }


        binding.btnNext.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            val location = binding.etLocation.text.toString()
            val phoneNumber = binding.etPhoneNumber.text.toString()

            var controlPassed =controlAgencyData(name , email , location, password, confirmPassword, phoneNumber)
            if (
                controlPassed

            ) {
                val bundle = bundleOf(
                    Constants.SIGNUP_BUNDLE_NAME to name,
                    Constants.SIGNUP_EMAIL to email,
                    Constants.SIGNUP_PASSWORD to password,
                    Constants.SIGNUP_LOCATION to location,
                    Constants.SIGNUP_PHONE_NUMBER to phoneNumber
                )
//                )
//              put("picture" ,curFile.toString())
                navigateToFragmentCertaficationVerification(bundle)
            } else {
                Toast.makeText(
                    requireContext(),
                    "make sure that you enter all the information",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

    /**
     * get the data from xml control it and pass it to register
     */
    private fun controlAgencyData(
        name: String,
        email: String,
        location: String,
        password: String,
        confirmPassword: String,
        phoneNumber: String
    ) : Boolean{
        // get the data from xml

        // name validation
        val usernameEmptyValidation = EmptyValidator(name).validate()
        binding.etName.error =
            if (!usernameEmptyValidation.isSuccess) getString(usernameEmptyValidation.message) else null
        val locationEmptyValidation = EmptyValidator(location).validate()
        binding.etLocation.error =
            if (!usernameEmptyValidation.isSuccess) getString(locationEmptyValidation.message) else null

        //email validation
        val emailValidations = BaseValidator.validate(
            EmptyValidator(email), EmailValidator(email)
        )
        binding.etEmail.error =
            if (!emailValidations.isSuccess) getString(emailValidations.message) else null

        // password validation
        val passwordValidations = BaseValidator.validate(
            EmptyValidator(password), PasswordValidator(password)
        )
        binding.etPassword.error =
            if (!passwordValidations.isSuccess) getString(passwordValidations.message) else null

        // confirm password validation
        val confirmPasswordValidations = BaseValidator.validate(
            EmptyValidator(confirmPassword), ConfirmPasswordValidator(confirmPassword, password)
        )
        binding.etConfirmPassword.error =
            if (!confirmPasswordValidations.isSuccess) getString(passwordValidations.message) else null

        val phonenumbervalidation = BaseValidator.validate(
            PhoneNumberValidation(phonenumber = phoneNumber)
        )
        binding.etPhoneNumber.error =
            if (!phonenumbervalidation.isSuccess) getString(phonenumbervalidation.message) else null

        return (usernameEmptyValidation.isSuccess
                && emailValidations.isSuccess
                && confirmPasswordValidations.isSuccess
                && phonenumbervalidation.isSuccess)
    }


    // TODO: get the image from the internal storage and pass it and return request object from this fun
    // TODO: set setUserloggedin to true

    private fun navigateToFragmentCertaficationVerification(bundle: Bundle) {
        findNavController().navigate(
            R.id.action_agencySignupFragment_to_certaficationVerificationFragment,
            bundle
        )
    }

}







