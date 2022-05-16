package com.example.thwissa.fragment.homefragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModelHomeFragment : ViewModel(){
     private val _canLoad = MutableLiveData<Boolean>()
      val canLoad: LiveData<Boolean> = _canLoad

    init {
        _canLoad.value = false
    }

    fun setcanload(){
        _canLoad.value = true
    }

    fun setCanNotLoad(){
        _canLoad.value = false
    }

}