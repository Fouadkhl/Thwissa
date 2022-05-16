package com.example.mybottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thwissa.databinding.ModalBottomSheetContentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


@Suppress("DEPRECATION")
class ModalBottomSheet(val itemslist: List<Uri>) : BottomSheetDialogFragment() {
    lateinit var binding: ModalBottomSheetContentBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ModalBottomSheetContentBinding.inflate(inflater, container, false)
//        binding.rvPhoto.adapter = ImageGridAdapter(this.requireContext(), itemslist) //in the case of image grid adapter
        binding.rvPhoto.adapter = PhotoAdapter(itemslist)
        binding.rvPhoto.layoutManager = GridLayoutManager(context, 3)
        val bottomSheet = binding.rlRv
        val bottomLayout = binding.rlSend

        // initial setting the y coordinates of the bottom layout
        bottomSheet.post {
            val bottomSheetVisibleHeight = bottomSheet.height - bottomSheet.top
            bottomLayout.y = (bottomSheetVisibleHeight - bottomLayout.height).toFloat()
        }
        return binding.root
    }


    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val offsetFromTop = getBottomSheetDialogDefaultHeight(requireContext(), 25)
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = false
            expandedOffset= offsetFromTop
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
