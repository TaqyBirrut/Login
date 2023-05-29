package com.example.login.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.login.api.ApiConfig
import com.example.login.response.ListStoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class StoryViewModel: ViewModel() {

    val resultSucces = MutableLiveData<MutableList<ListStoryItem>>()
    val resultError = MutableLiveData<String>()
    val resultLoading = MutableLiveData<Boolean>()

    fun getStory() {
        GlobalScope.launch (Dispatchers.IO){
            launch (Dispatchers.Main){
                flow {
                    val response = ApiConfig
                        .dicodingStoryService
                        .stories()
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
                    resultSucces.value = it.listStory
                }
            }
        }
    }
}