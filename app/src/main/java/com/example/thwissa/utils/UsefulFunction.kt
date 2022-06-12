@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.thwissa.utils

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Rect
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import com.example.thwissa.R
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * function for fields that are in database as string
 */
fun createPartFromString(param: String) =
    param.toRequestBody("multipart/form-data".toMediaTypeOrNull())

/**
 * helper function to upload photo to database
 */
fun getFilePart(file: File, name: String): MultipartBody.Part {
    val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
    val filePart = MultipartBody.Part.createFormData(
        name,
        file.name,
        reqFile
    )
    return filePart
}

/**
 * Call this method (in onActivityCreated or later) to set
 * the width of the dialog to a percentage of the current
 * screen width.
 */
fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
}

/**
 * Call this method (in onActivityCreated or later)
 * to make the dialog near-full screen.
 */
fun DialogFragment.setFullScreen() {
    dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
}


object UsefulFct {

    private const val TAG = "UsefulFunction"

    /**
     * function that change a string to date
     */
    @SuppressLint("SimpleDateFormat")
    fun fromStringToDate(stringDate: String): Date {
        val a = stringDate.subSequence(0, 19).toString() + "Z"
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return format.parse(a)
    }

    /**
     * change date to string
     */
    @SuppressLint("SimpleDateFormat")
    fun fromDateToString(date: Date): String {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val dateTime = dateFormat.format(date)
        return dateTime
    }

    fun bindImage(imgView: ImageView, imgUrl: String) {
        Log.d(TAG, "bindImage: $imgUrl")

       val prefix = "http://192.168.43.248:5000/"
        val stringurl =prefix +imgUrl
//            "http://192.168.43.248:5000/ccc.PNG"
        stringurl.let {
            //toUri si an extention fun from the ktx lib
            val imgUri = it.toUri().buildUpon().build()
            Log.d(TAG, "bindImage: $imgUri")
            //to load an image with picasso
            Picasso
                .with(imgView.context)
                .load(imgUri)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(imgView)
        }
    }
}



//touri si an extention fun from the ktx lib
//Uri myUri = Uri.parse(story.storyPictureResource) ;
//Log.d(TAG, "bindImage: $imgUri") ;
////to load an image with glide
//Picasso.with(binding.storyPic.getContext())
//.load(myUri)
//.placeholder(R.drawable.loading_animation)
//.error(R.drawable.ic_broken_image)
//.into(binding.storyPic) ;

