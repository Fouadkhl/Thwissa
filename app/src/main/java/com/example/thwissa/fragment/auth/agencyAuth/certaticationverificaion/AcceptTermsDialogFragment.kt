package com.example.thwissa.fragment.auth.agencyAuth.certaticationverificaion

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.thwissa.R


@Suppress("DEPRECATION")
class AcceptTermsDialogFragment : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.title_dialog_fragment))
            .setMessage(getString(R.string.order_confirmation))
            .setPositiveButton(getString(R.string.accept)) { dialog, which ->

                val result = true
                parentFragmentManager.setFragmentResult(
                    REQUEST_CODE,
                    bundleOf(TERACCEPTED_BUNDLE_KEY to result)
                )
                dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                dismiss()
            }
        return builder.create()
    }

    companion object {
        const val REQUEST_CODE = "requestTerms"
        const val TERACCEPTED_BUNDLE_KEY = "TermAccepted"
        const val TAG = "AcceptTermsDialogFragment"
    }
}