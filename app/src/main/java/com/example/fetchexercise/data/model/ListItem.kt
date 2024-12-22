package com.example.fetchexercise.data.model

import kotlinx.serialization.Serializable

// Data class that matches the JSON structure
@Serializable
data class ListItem(
    val id: Int,
    val listId: Int,
    val name: String?   // Nullable String to handle empty strings and null values
)
