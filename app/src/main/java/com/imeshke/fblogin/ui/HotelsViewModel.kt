package com.imeshke.fblogin.ui.main

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imeshke.fblogin.api.ApiClient
import com.imeshke.fblogin.api.ApiResult
import com.imeshke.fblogin.api.model.Hotel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HotelsViewModel: ViewModel() {
    private val hotels: MutableLiveData<List<Hotel>> = MutableLiveData()


    fun getHotels(): MutableLiveData<List<Hotel>> {
        refreshHotels()
        return  hotels
    }
    private fun refreshHotels() {
        viewModelScope.launch(Dispatchers.Default) {
            val apiResult = ApiClient.refreshHotelList()

            when (apiResult) {
                is ApiResult.Success -> hotels.postValue(apiResult.data.data)
                is ApiResult.UnknownError -> Log.d("HotelsViewModel","ApiError: ${apiResult.errorMessage}")
            }
        }
    }

}