package com.example.fetchexercise.data.api

import com.example.fetchexercise.data.api.constants.ApiConstants
import com.example.fetchexercise.data.repository.FetchApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // examples for demonstrating the way I would start structuring multiple api services using Retrofit
    val fetchApiService: FetchApiService = createRetrofit(ApiConstants.FETCH_BASE_URL)
        .create(FetchApiService::class.java)

//    val anotherApiService: AnotherApiService = createRetrofit(ApiConstants.ANOTHER_BASE_URL)
//        .create(AnotherApiService::class.java)
}