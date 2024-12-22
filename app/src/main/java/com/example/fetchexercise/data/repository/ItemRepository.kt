package com.example.fetchexercise.data.repository

import com.example.fetchexercise.data.model.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

// This interface defines our repository contract, making it easier to test and swap implementations
interface ItemRepository {
    suspend fun fetchItems(): Result<List<ListItem>>
}

// The actual implementation that handles the network calls and data processing
class ItemRepositoryImpl : ItemRepository {
    // Using Result type allows us to handle success and failure cases elegantly
    override suspend fun fetchItems(): Result<List<ListItem>> = runCatching {
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
}