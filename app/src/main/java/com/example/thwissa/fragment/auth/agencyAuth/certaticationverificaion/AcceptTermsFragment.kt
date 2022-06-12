package com.example.thwissa.fragment.auth.agencyAuth.certaticationverificaion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.thwissa.LogService
import com.example.thwissa.R
import com.example.thwissa.databinding.FragmentAcceptTermsBinding
import com.example.thwissa.dataclasses.AgencyRes
import com.example.thwissa.fragment.auth.agencyAuth.certaticationverificaion.AcceptTermsDialogFragment.Companion.REQUEST_CODE
import com.example.thwissa.fragment.auth.agencyAuth.certaticationverificaion.AcceptTermsDialogFragment.Companion.TERACCEPTED_BUNDLE_KEY
import com.example.thwissa.utils.Constants
import com.example.thwissa.utils.createPartFromString
import com.example.thwissa.utils.getFilePart
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File


@Suppress("DEPRECATION")
class AcceptTermsFragment : Fragment() {


    lateinit var binding: FragmentAcceptTermsBinding
    private val viewModelCertafication: CertaficationVerificationViewModel by activityViewModels()
    private var isAccpeted: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAcceptTermsBinding.inflate(inflater, container, false)


        binding.btnAcceptTerms.setOnClickListener {
            acceptTerms()
        }


        binding.btnContinue.setOnClickListener {
            if (isAccpeted) {
                Toast.makeText(requireContext(), "inside", Toast.LENGTH_SHORT).show()


                val map = HashMap<String, RequestBody>()
                val name = arguments?.getString(Constants.SIGNUP_BUNDLE_NAME)
                    ?.let { it1 -> createPartFromString(it1) }

                val email = arguments?.getString(Constants.SIGNUP_EMAIL)
                    ?.let { it1 -> createPartFromString(it1) }

                val password = arguments?.getString(Constants.SIGNUP_PASSWORD)
                    ?.let { it1 -> createPartFromString(it1) }

                val phone = arguments?.getString(Constants.SIGNUP_PHONE_NUMBER)
                    ?.let { it1 -> createPartFromString(it1) }
                val location = arguments?.getString(Constants.SIGNUP_LOCATION)
                    ?.let { it1 -> createPartFromString(it1) }

                val photoPath = arguments?.getString(Constants.PHOTO_PATH)
                val filepart = if (photoPath != null) getFilePart((File(photoPath)) , "photo") else null


                map.apply {
                    put("name", name!!)
                    put("email", email!!)
                    put("password", password!!)
                    put("confirmepassword", password!!)
                    put("location", location!!)
                    put("phonenumber", phone!!)
                }
                 primaryAgencySignUp(map, filepart)

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

    fun primaryAgencySignUp(
        agencyInfo: HashMap<String, RequestBody>,
        image: MultipartBody.Part?
    ){
//        var id: String? = null
        LogService.retrofitService.executeSignUpAgency(agencyInfo, image)
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
                        val id = res?.id
                        //handle the response
                        val bundle = bundleOf(
                            Constants.USER_ID to id,
                            Constants.SIGNUP_EMAIL to arguments?.getString(Constants.SIGNUP_EMAIL)
                        )

                        findNavController().navigate(
                            R.id.action_acceptTermsFragment_to_codeValidationFragment,
                            bundle
                        )

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
//        return id
    }

}