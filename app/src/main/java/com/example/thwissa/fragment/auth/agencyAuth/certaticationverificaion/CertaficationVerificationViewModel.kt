package com.example.thwissa.fragment.auth.agencyAuth.certaticationverificaion

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CertaficationVerificationViewModel() : ViewModel() {

    private val _agencyprimarydata = MutableLiveData<HashMap<String, Any>>()
    val agencyprimarydata: LiveData<HashMap<String, Any>> get() =  _agencyprimarydata

    fun addPrimaryData(data: HashMap<String, Any>) {
        _agencyprimarydata.value = data
    }

    fun uploadImageToDatabase(curFile: Uri?) = CoroutineScope(Dispatchers.IO).launch {
        try {
            curFile?.let {
//                imageRef.child("images/$fileName").putFile(it).await()
                withContext(Dispatchers.Main) {
//                    Toast.makeText(
//                        context, "the image is uploaded to the storage ",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
//                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}