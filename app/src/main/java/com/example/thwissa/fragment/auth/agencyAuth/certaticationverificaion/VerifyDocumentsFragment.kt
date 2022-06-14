package com.example.thwissa.fragment.auth.agencyAuth.certaticationverificaion

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.thwissa.LogService
import com.example.thwissa.R
import com.example.thwissa.databinding.VerificationScreenBinding
import com.example.thwissa.utils.Constants
import com.example.thwissa.utils.getFilePart
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import java.io.File


@Suppress("DEPRECATION")
class VerifyDocumentsFragment : Fragment() {


    lateinit var binding: VerificationScreenBinding
    private val viewModelCertafication: CertaficationVerificationViewModel by activityViewModels()
    var classificationPath: String? = null
    var certaficationPath: String? = null

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = VerificationScreenBinding.inflate(inflater, container, false)


        val resultLauncherClassification =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    data?.data?.let {

                        context?.contentResolver?.query(it, null, null, null, null)?.use {
                            if (it.moveToFirst()) {
                                classificationPath =
                                    it.getString(it.getColumnIndex(MediaStore.MediaColumns.DATA))
                            }
                        }
                        binding.ivClassification.setImageResource(R.drawable.ic_baseline_check)
                    }
                }
            }

        val resultLauncherCertafication =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    data?.data?.let {

                        context?.contentResolver?.query(it, null, null, null, null)?.use {
                            if (it.moveToFirst()) {
                                certaficationPath =
                                    it.getString(it.getColumnIndex(MediaStore.MediaColumns.DATA))
                            }
                        }
                        binding.ivCertafication.setImageResource(R.drawable.ic_baseline_check)
                    }
                }
            }


        binding.llAddCertaficat.setOnClickListener {
            // TODO: add here the image for certaficat
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                resultLauncherCertafication.launch(it)
            }
        }

        binding.llAddClassification.setOnClickListener {

            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                resultLauncherClassification.launch(it)
            }
        }

        binding.btnSignUp.setOnClickListener {

            // TODO:  implement this
            if (classificationPath != null && certaficationPath != null) {
                val filePartCertatication = getFilePart(File(certaficationPath!!) , "documents")
                val filePartClassification = getFilePart(File(classificationPath!!) , "documents")
                val listofpicture  = ArrayList< MultipartBody.Part>()
                listofpicture.add(filePartCertatication)
                listofpicture.add(filePartClassification)
                uploadDocumentsToDatabase(listofpicture)

            } else {
                Toast.makeText(
                    requireContext(),
                    "make sure you upload all files",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        return binding.root
    }

    fun uploadDocumentsToDatabase(
        documents : ArrayList<MultipartBody.Part>
    ) {

        LogService.retrofitService.uploadAgencyDocuments(
            documents
        )
            .enqueue(object : retrofit2.Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    /**handle the sign up  */
//                        boolearn / id
                    if (response.isSuccessful) {
                        Toast.makeText(
                            context,
                            "sign up successefuly",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.i("signupuser", "success:${response.message()} ")

                        //handle the response

                        findNavController().navigate(
                            R.id.action_verifyDocumentsFragment_to_agencyProfileFragment,
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

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "upload Images fail ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.i("signupuser", "onFailure:${t.message} ")
                }

            }
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.popBackStack()
            }
        })
    }
}
//
//val iduser = arguments?.getString(Constants.USER_ID)
//val email = arguments?.getString(Constants.SIGNUP_EMAIL)

//                        val bundle = bundleOf(
//                            Constants.USER_ID to iduser,
//                            Constants.SIGNUP_EMAIL to email
//                        )