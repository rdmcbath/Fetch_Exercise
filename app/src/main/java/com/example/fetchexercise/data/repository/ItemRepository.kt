package com.example.fetchexercise.data.repository

import com.example.fetchexercise.data.api.constants.ApiConstants
import com.example.fetchexercise.data.model.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.net.HttpURLConnection
import java.net.URL

// This interface defines our repository contract, making it easier to test and swap implementations
interface ItemRepository {
    suspend fun fetchItemsBasicMethod(): Result<List<ListItem>>
    suspend fun fetchItemsRetrofit(): Result<List<ListItem>>
}

// The basic implementation that handles the network calls and data processing
class ItemRepositoryImpl : ItemRepository {
    // Using Result type allows us to handle success and failure cases elegantly
    override suspend fun fetchItemsBasicMethod(): Result<List<ListItem>> = runCatching {
        withContext(Dispatchers.IO) {
            val url = URL("https://fetch-hiring.s3.amazonaws.com/hiring.json")
            val connection = url.openConnection() as HttpURLConnection

            try {
                connection.apply {
                    requestMethod = "GET"
                    connectTimeout = 5000
                    readTimeout = 5000
                }

                // Read the response
                val inputStream = connection.inputStream
                val response = inputStream.bufferedReader().use { it.readText() }

                // Parse the JSON response using kotlinx.serialization
                val json = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }

                json.decodeFromString<List<ListItem>>(response)
            } finally {
                connection.disconnect()
            }
        }
    }

    // Retrofit is a more preferred implementation for handling network calls in larger apps
    // These would be placed in separate modules or packages in a real-world app - see example files
    override suspend fun fetchItemsRetrofit(): Result<List<ListItem>> {
        val api = getRetrofit().create(FetchApiService::class.java)

        return runCatching {
            val response = api.getItems()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw Exception("Failed to fetch items: ${response.errorBody()?.string()}")
            }
        }
    }
}

private fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(ApiConstants.FETCH_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // convert JSON to objects
        .build()
}

interface FetchApiService {
    @GET("hiring.json")
    suspend fun getItems(): Response<List<ListItem>>
}