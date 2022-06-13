package com.example.thwissa.fragment.Profile.agencyProfile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.thwissa.LogService
import com.example.thwissa.R
import com.example.thwissa.databinding.FragmentEditAgencyProfileBinding
import com.example.thwissa.dataclasses.AgencyRes
import com.example.thwissa.fragment.auth.validation.BaseValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.EmailValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.EmptyValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.PhoneNumberValidation
import com.example.thwissa.utils.createPartFromString
import com.example.thwissa.utils.getFilePart
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

private const val TAG = "EditAgencyProfile"

@Suppress("DEPRECATION")
class EditAgencyProfile : Fragment() {

    private lateinit var binding: FragmentEditAgencyProfileBinding
    var curFile: Uri? = null
    var picturePath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditAgencyProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val data: Intent? = result.data
                    data?.data?.let { it ->
                        curFile = it
                        binding.ivShapableAgencySignUp.setImageURI(it)

                        context?.contentResolver?.query(it, null, null, null, null)?.use {
                            if (it.moveToFirst()) {
                                picturePath =
                                    it.getString(it.getColumnIndex(MediaStore.MediaColumns.DATA))
                            }
                        }

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


        binding.btnUpdate.setOnClickListener {

            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val location = binding.etLocation.text.toString()
            val phoneNumber = binding.etPhoneNumber.text.toString()
            val photoPath = if (curFile != null) picturePath else ""

            val map = HashMap<String, RequestBody>()

            map.apply {
                put("name", createPartFromString(name))
//                put("email", createPartFromString(email))
                put("location", createPartFromString(location))
                put("phonenumber", createPartFromString(phoneNumber))
            }

            val filepart = if (photoPath != null) getFilePart((File(photoPath)), "picture") else null

            var controlPassed =
                controlAgencyData(name, email, location, phoneNumber)
            if (controlPassed) {
                updateAllData(map, filepart)
                navigateBackToProfile()
            } else {
                Toast.makeText(
                    requireContext(),
                    "make sure that you enter all the information",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateAllData(map: HashMap<String, RequestBody>, filepart: MultipartBody.Part?) {
        LogService.retrofitService.updateAgencyProfile(map, filepart)
            .enqueue(object : retrofit2.Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
//                        Toast.makeText(requireContext(), "data is updated", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "success")

                        navigateBackToProfile()
                    } else {
                        Log.d(TAG, "fail :" +response.message() )
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }


    /**
     * get the data from xml control it and pass it to register
     */
    private fun controlAgencyData(
        name: String,
        email: String,
        location: String,
        phoneNumber: String
    ): Boolean {
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

        val phonenumbervalidation = BaseValidator.validate(
            PhoneNumberValidation(phonenumber = phoneNumber)
        )
        binding.etPhoneNumber.error =
            if (!phonenumbervalidation.isSuccess) getString(phonenumbervalidation.message) else null

        return (usernameEmptyValidation.isSuccess
                && phonenumbervalidation.isSuccess)
    }

    private fun navigateBackToProfile() {
        lifecycleScope.launchWhenResumed {
            findNavController().popBackStack()
        }
    }

}







