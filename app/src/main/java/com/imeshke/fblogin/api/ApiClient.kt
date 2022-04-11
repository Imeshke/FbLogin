package com.imeshke.fblogin.api

import com.imeshke.fblogin.api.ApiInterface
import com.imeshke.fblogin.api.model.HotelsResponse
import com.imeshke.fblogin.utils.StringUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    private val service: ApiInterface

    init {

        val retrofit = Retrofit.Builder()
                .baseUrl(StringUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(ApiInterface::class.java)
    }

    suspend fun refreshHotelList() : ApiResult<HotelsResponse> {

        try {
            val response = withContext(Dispatchers.IO) { service.getHotelList() }

            if (response.isSuccessful)
                return ApiResult.Success(response.body()!!)

            return ApiResult.UnknownError("Unknown Error");

        } catch (e : Exception){
            return ApiResult.UnknownError("Exception ${e.message}");
        }

    }

}
