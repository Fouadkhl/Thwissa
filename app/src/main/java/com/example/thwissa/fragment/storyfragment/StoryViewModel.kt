package com.example.thwissa.fragment.storyfragment

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thwissa.LogService
import com.example.thwissa.LogService.retrofitService
import com.example.thwissa.dataclasses.StoryData
import com.example.thwissa.dataclasses.StoryItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "StoryViewModel"

class StoryViewModel(val context: Context) : ViewModel() {


    /**
     * we get stories itemsList and observe the state when the state is done we upload data
     */

    enum class StoryLoadingStatus {
        LOADING,
        ERROR,
        DONE
    }

    /** to  handle status */
    private val _status = MutableLiveData<StoryLoadingStatus>()
    val status: LiveData<StoryLoadingStatus> = _status

    private val _property = MutableLiveData<List<StoryItem>>()
    val property: LiveData<List<StoryItem>> = _property

    private val _isStoryDataUpdated = MutableLiveData<Boolean>()
    val isStoryDataUpdated: LiveData<Boolean> = _isStoryDataUpdated

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        initStoriesList(context)
    }

    private fun initStoriesList(context: Context) {
        coroutineScope.launch {
            val getListStoryData = LogService.retrofitService.getStories()
            try {
                _status.value = StoryLoadingStatus.LOADING
                getListStoryData.enqueue(object : Callback<StoryData> {
                    override fun onResponse(
                        call: Call<StoryData>,
                        response: Response<StoryData>
                    ) {
                        if (response.isSuccessful) {
                            Log.d(TAG, "seccess")
                            _property.value = response.body()?.getPicturs
                            _status.value = StoryLoadingStatus.DONE
                        } else {
                            Log.d(TAG, "response error")
                        }
                    }

                    override fun onFailure(call: Call<StoryData>, t: Throwable) {
                        Log.d(TAG, "onFailure: " + t.message)
                    }
                })

            } catch (t: Throwable) {
                _status.value = StoryLoadingStatus.ERROR
                _property.value = ArrayList()
                Log.d(TAG, "something wrong catch: " + t.message)
            }
        }
    }


    fun addLike(wilaya: String, idStory: String, likes: Int) {
        coroutineScope.launch {
            var call =
                retrofitService.addStoryLike(wilaya, idStory, likes)
            call.enqueue(
                object : Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "response is succeful", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }


    fun addDislike(wilaya: String, idStory: String, dislikes: Int) {
        coroutineScope.launch {
            var call =
                retrofitService.addStoryDislike(wilaya, idStory, dislikes)
            call.enqueue(
                object : Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "response is succeful", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }

     fun addReport(wilaya: String, idStory: String) {
        coroutineScope.launch {
            var call =
                retrofitService.addStoryReport(wilaya, idStory)
            call.enqueue(
                object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "success report", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, "already reported", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "failure" + t.message)
                    }
                })
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}