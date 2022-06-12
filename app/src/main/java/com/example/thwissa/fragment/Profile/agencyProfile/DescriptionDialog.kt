package com.example.thwissa.fragment.Profile.agencyProfile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.thwissa.LogService
import com.example.thwissa.databinding.DialogUpdateAgencyDescriptionBinding
import com.example.thwissa.utils.setWidthPercent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class DescriptionDialog(context: Context) : DialogFragment() {

    lateinit var binding: DialogUpdateAgencyDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogUpdateAgencyDescriptionBinding.inflate(inflater, container, false)

        binding.btnPostDescription.setOnClickListener {

            updateDescription(binding.tvAgencyDescription.text.toString())
            parentFragmentManager.setFragmentResult(
                DescriptionDialog.REQUEST_CODE,
                bundleOf(
                    ADD_DESCRIPTION_BUNDLE_KEY to binding.tvAgencyDescription.text.toString()
                )
            )
            dismiss()
        }


        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWidthPercent(95)
    }

    private fun updateDescription(description: String) {
        val map = HashMap<String, String>()
        map.put("description", description)
        LogService.retrofitService.updadateDescription(map).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "successul response" + response.message() + response.code())
                } else {
                    Log.d(TAG, "not succeful" + response.message() + response.code())
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        const val REQUEST_CODE = "add_description"
        const val ADD_DESCRIPTION_BUNDLE_KEY = "add_description_key"
        const val TAG = "DescriptionDialog"
    }
}