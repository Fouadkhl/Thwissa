package com.example.thwissa.fragment.storyfragment


import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import com.example.thwissa.dataclasses.StoryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "RetrofitCallback"

@Suppress("DEPRECATION")
open class RetrofitCallback(private val mContext: Context) : Callback<StoryData> {
    private lateinit var progressDialog: Dialog

    init {
        progressDialog = ProgressDialog(mContext)
        progressDialog.show()

    }


    override fun onResponse(call: Call<StoryData>, response: Response<StoryData>) {
        if (response.body() == null) {
            //response is null
        } else {
            if (response.code().equals("3333")) {
                //session timed out
            } else if (response.code().equals("1111")) {
                //failed
            }
        }
    }

    override fun onFailure(call: Call<StoryData>, t: Throwable) {
        hideProgress()
        if (t.message != null) {
            Log.e(TAG, t.message!!)
        }
    }


    private fun hideProgress() {
        progressDialog.dismiss()
    }

}
