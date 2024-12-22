package com.example.fetchexercise.ui

import GroupedListScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.fetchexercise.ui.screens.groupedList.GroupedListViewModel

class MainActivity : ComponentActivity() {
    // Create the ViewModel at the activity level so it survives configuration changes
    private val viewModel: GroupedListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                // Surface ensures proper Material theming and background color
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    GroupedListScreen(viewModel = viewModel)
                }
            }
        }
    }
}