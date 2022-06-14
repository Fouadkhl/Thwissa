package com.example.mybottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thwissa.LogService
import com.example.thwissa.databinding.ModalBottomSheetContentBinding
import com.example.thwissa.utils.getFilePart
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


@Suppress("DEPRECATION")
class ModalBottomSheet(val itemslist: List<Uri>) : BottomSheetDialogFragment() {
     var picturePath: String? = null
    lateinit var binding: ModalBottomSheetContentBinding

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ModalBottomSheetContentBinding.inflate(inflater, container, false)
//        binding.rvPhoto.adapter = ImageGridAdapter(this.requireContext(), itemslist) //in the case of image grid adapter
        binding.rvPhoto.adapter = PhotoAdapter(itemslist, PhotoAdapter.OnClickListener { uri ->
            context?.contentResolver?.query(uri, null, null, null, null)?.use {
                if (it.moveToFirst()) {
                    picturePath = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DATA))
                }
            }
        })

        // TODO: here the recycler view init
        binding.rvPhoto.layoutManager = GridLayoutManager(context, 3)

        val bottomSheet = binding.rlRv
        val bottomLayout = binding.rlSend

        // initial setting the y coordinates of the bottom layout
        bottomSheet.post {
            val bottomSheetVisibleHeight = bottomSheet.height - bottomSheet.top
            bottomLayout.y = (bottomSheetVisibleHeight - bottomLayout.height).toFloat()
        }


        val selectedItem = binding.spinner.selectedItem.toString()

        binding.btnSending.setOnClickListener {
            if (picturePath == null) {
                Toast.makeText(
                    requireContext(),
                    "make sure you select a picture",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                val pathpart = getFilePart(File(picturePath), "picture")
                uploadPhotoStory(selectedItem, pathpart)
            }
        }

        return binding.root
    }

    private fun uploadPhotoStory(location: String, pathpart: MultipartBody.Part) {
        LogService.retrofitService.uploadStory(location, pathpart).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "photo loaded seccessfuly", Toast.LENGTH_LONG)
                        .show()
                    this@ModalBottomSheet.dismiss()
                } else {
                    Toast.makeText(requireContext(), "something is wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "something is wrong the picture not loaded",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }


    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val offsetFromTop = getBottomSheetDialogDefaultHeight(requireContext(), 25)
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = false
            expandedOffset = offsetFromTop
            state = BottomSheetBehavior.STATE_COLLAPSED
        }


    }

    private fun getBottomSheetDialogDefaultHeight(context: Context, percetage: Int): Int {
        return getWindowHeight(context) * percetage / 100
    }

    private fun getWindowHeight(context: Context): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}
