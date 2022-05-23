package com.example.thwissa.fragment.auth.agencyAuth.certaticationverificaion

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.thwissa.LogService
import com.example.thwissa.R
import com.example.thwissa.databinding.VerificationScreenBinding
import com.example.thwissa.dataclasses.AgencyRes
import com.example.thwissa.fragment.auth.agencyAuth.certaticationverificaion.AcceptTermsDialogFragment.Companion.REQUEST_CODE
import com.example.thwissa.fragment.auth.agencyAuth.certaticationverificaion.AcceptTermsDialogFragment.Companion.TERACCEPTED_BUNDLE_KEY
import com.example.thwissa.utils.Constants
import retrofit2.Call
import retrofit2.Response


@Suppress("DEPRECATION")
class CertaficationVerificationFragment : Fragment() {


    lateinit var binding: VerificationScreenBinding
    private val viewModelCertafication: CertaficationVerificationViewModel by activityViewModels()
    var curFile: Uri? = null
    private var isAccpeted: Boolean = false
    private var issignup: Boolean = false
    var map = HashMap<String, Any>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = VerificationScreenBinding.inflate(inflater, container, false)
        var resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val data: Intent? = result.data
                    data?.data?.let {
                        curFile = it
                        //then we set the image in the image view
                        binding.ivCertafication.setImageURI(it)
                    }
                }
            }


        binding.llAddCertaficat.setOnClickListener {
            // TODO: add here the image for certaficat
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                resultLauncher.launch(it)
            }
        }

//        // TODO: add here the image for certaficat
//        var resultLauncher =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                // There are no request codes
//                val data: Intent? = result.data
//                data?.data?.let {
//                    // TODO: change this to another file
//                    curFile = it
//                    //then we set the image in the image view
//                    binding.ivCertafication.setImageURI(it)
//                }
//            }
//        }
        binding.llAddClassification.setOnClickListener {

            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                resultLauncher.launch(it)
            }
        }

        binding.btnAcceptTerms.setOnClickListener {
            acceptTerms()
        }


        binding.btnSignUp.setOnClickListener {
            if (isAccpeted) {
                Toast.makeText(requireContext(), "inside", Toast.LENGTH_SHORT).show()
//                val agencyprimaryinfo = viewModelCertafication.agencyprimarydata
//                primaryAgencySignUp(agencyprimaryinfo.value!!)
//                viewModelCertafication.uploadImageToDatabase(curFile)
                //go to code verification
                var map = HashMap<String, Any>()

                arguments?.apply {
                    val name = getString(Constants.SIGNUP_BUNDLE_NAME)
                    val email = getString(Constants.SIGNUP_EMAIL)
                    val password = getString(Constants.SIGNUP_PASSWORD)
                    val phone = getString(Constants.SIGNUP_PHONE_NUMBER)
                    val location = getString(Constants.SIGNUP_LOCATION)
                    map.apply {
                        put("name", name!!)
                        put("email", email!!)
                        put("password", password!!)
                        put("confirmepassword", password!!)
                        put("location", location!!)
                        put("phonenumber", phone!!)
                    }
                    primaryAgencySignUp(map)
                }

                findNavController().navigate(R.id.action_certaficationVerificationFragment_to_codeValidationFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    "make sure you accept terms$isAccpeted",
                    Toast.LENGTH_SHORT
                ).show()
            }
            // TODO: set data passed from agency sign up Fragment to primaryagencySignUP function
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener(
            REQUEST_CODE, this
        ) { requestKey, result ->
            isAccpeted = result.getBoolean(TERACCEPTED_BUNDLE_KEY)
        }
    }

    private fun acceptTerms() {
        val dialogFragment = AcceptTermsDialogFragment()
        dialogFragment.show(
            childFragmentManager,
            AcceptTermsDialogFragment.TAG
        )
    }

    fun primaryAgencySignUp(agencyInfo: HashMap<String, Any>) {
        LogService.retrofitService.executeSignUpAgency(agencyInfo)
            .enqueue(object : retrofit2.Callback<AgencyRes> {
                override fun onResponse(call: Call<AgencyRes>, response: Response<AgencyRes>) {
                    /**handle the sign up  */
//                        boolearn / id

                    if (response.isSuccessful) {
                        Toast.makeText(
                            context,
                            "sign up successefuly",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.i("signupuser", "onFailure:${response.message()} ")

                        val res = response.body()
                        //handle the response

                    } else {
                        Log.i("signupuser", "onFailure:${response.message()} ")
                        Toast.makeText(
                            context,
                            "error in response ${response.message()}, ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AgencyRes>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "sign up fail ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.i("signupuser", "onFailure:${t.message} ")
                }
            }
            )
    }
}