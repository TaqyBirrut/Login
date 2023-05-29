package com.example.login.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.login.api.ApiConfig
import com.example.login.response.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailStoryViewModel: ViewModel() {
    val resultSucces = MutableLiveData<Story>()
    val resultError = MutableLiveData<String>()
    val resultLoading = MutableLiveData<Boolean>()

    fun getDetailStory(id: String) {
        GlobalScope.launch (Dispatchers.IO){
            launch (Dispatchers.Main){
                flow {
                    val response = ApiConfig
                        .dicodingStoryService
                        .storiesDetail(id)
                    emit(response)
                }.onStart {
                    resultLoading.value = true
                }.onCompletion {
                    resultLoading.value = false
                }.catch {
                    Log.e("Error", it.message.toString())
                    it.printStackTrace()
                    resultError.value = it.message.toString()
                }.collect{
                    resultSucces.value = it.story
                }
            }
        }
    }
}