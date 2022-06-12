package com.example.thwissa.fragment.storyfragment

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thwissa.LogService
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

    fun updateDataforStory(
        idLocation: String,
        idStory: String,
        like: Int,
        dislike: Int,
        report: Int
    ) {
        if (_status.value == StoryLoadingStatus.DONE) {
            LogService.retrofitService.updateStoryData(
                idLocation, idStory, like, dislike, report
            ).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        _isStoryDataUpdated.value = true
                        Toast.makeText(context, "you just add like", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "some thing wrong now ", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    _isStoryDataUpdated.value = false
                    Toast.makeText(context, "onfailure invoked" + t.message, Toast.LENGTH_SHORT)
                        .show()
                }

            })
        } else {
            Toast.makeText(context, "story is not loaded", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}